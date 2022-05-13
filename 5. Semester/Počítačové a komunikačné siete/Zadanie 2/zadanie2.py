# Lukas Michalik - UDP komunikator
import threading
import socket
import math
import time
import os

keepAliveStatus = False

def keepAlive(clientSocket, serverAddress):
    if (keepAliveStatus):
        threading.Timer(10.0, keepAlive, args=(clientSocket,serverAddress)).start()
        keepAliveMessage = createHeader(1, 0, 0, 0)
        clientSocket.sendto(keepAliveMessage, serverAddress)

#-- HEADER ------ 1B --- 3B --- 2B -- 2B
def createHeader(type, packet, crc, frag):
    type = type.to_bytes(1, 'big')
    packet = packet.to_bytes(3, 'big')
    crc = crc.to_bytes(2, 'big')
    frag = frag.to_bytes(2, 'big')

    return type+packet+crc+frag


# CRC16_MODBUS
def crc16ModBus(data: bytes):
    data = bytearray(data)
    poly = 0xA001
    crc = 0xFFFF
    for b in data:
        crc ^= (0xFF & b)
        for _ in range(0, 8):
            if (crc & 0x0001):
                crc = ((crc >> 1) & 0xFFFF) ^ poly
            else:
                crc = ((crc >> 1) & 0xFFFF)
    return crc & 0xFFFF

def switchRoles(socket, address):
    while True:
        print("Switch to (C)lient")
        print("Switch to (S)erver")
        command = input()
        if command == 'C' or command == 'c':
            clientStart(socket, address)
            break
        elif command == 'S' or command == 's':
            serverStart(socket, address)
            break
        else:
            print("Neplatny prikaz")

#----------------------------------- SERVER -----------------------------------

# nadviazanie spojenia s klientom 
def serverConnect():
    serverSocket = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)

    serverIp = "127.0.0.1" 
    serverPort = int(input("Server port: "))
    try:
        serverAdr = (serverIp, serverPort)
        serverSocket.bind(serverAdr)
        
        print("Cakam na spojenie")
        data, address = serverSocket.recvfrom(1500)
        header = data[:8]
        type = int.from_bytes(header[:1], 'big')

        if (type == 0):
            connectMessage = createHeader(6, 0, 0, 0)
            serverSocket.sendto(connectMessage, address)
            print("Spojenie nadviazane s adresou: ", address)
            serverStart(serverSocket, address)
        else:
            print("Nepodarilo sa pripojit!")
    except:
        print("Nepodarilo sa pripojit!")

# start servera
def serverStart(serverSocket, clientAddress):
    connected = True

    while connected:
        print("--- SERVER MENU ---")
        print("( )server start")
        print("(S)witch roles")
        print("(E)xit")

        command = input()
        if command == 'S' or command == 's':
            switchRoles(serverSocket, clientAddress)
            connected = False
        elif command == 'E' or command == 'e':
            connected = False
        elif command == ' ' or command == '':
            print("Server start")
            try:
                serverSocket.settimeout(30)
                while True:
                    messageInfo = serverSocket.recv(1500)
                    header = messageInfo[:8]
                    type = int.from_bytes(header[:1], 'big')

                    if (type == 1): # KA
                        pass 

                    elif (type == 2): # T
                        packetsCount = int.from_bytes(header[1:4], 'big')
                        receiveMessage(serverSocket, clientAddress, packetsCount, 'T')
                        break
                    elif (type == 3): # F  + doplnit nazov suboru
                        packetsCount = int.from_bytes(header[1:4], 'big')
                        fileName = messageInfo[8:]
                        fileName = fileName.decode()
                        receiveMessage(serverSocket, clientAddress, packetsCount, 'F', fileName)
                        break

            except socket.timeout:
                print("Casovac vyprsal")
                serverSocket.close() 
                return

        else:
            print("Neplatny prikaz")

    # ukoncenie spojenia
    serverSocket.close() 
   

#---------------------------------- CLIENT -----------------------------------

# nadviazanie spojenia so serverom 
def clientConnect():
    clientSocket = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)

    try:
        serverIp = "127.0.0.1" 
        serverPort = 9090 

        serverIp = str(input("Server IP: "))
        serverPort = int(input("Server port: "))

        serverAdr = (serverIp, serverPort)
        connectMessage = createHeader(0, 0, 0, 0)
        clientSocket.sendto(connectMessage, serverAdr)

        data, address = clientSocket.recvfrom(1500)
        header = data[:8]
        type = int.from_bytes(header[:1], 'big')

        if type == 6:
            print("Spojenie nadviazane so serverom: ", address)
            clientStart(clientSocket, address)
        else:
            print("Nepodarilo sa pripojit!")
    except:
        print("Nepodarilo sa pripojit!")

# start klienta
def clientStart(clientSocket, serverAddress):
    global keepAliveStatus
    keepAliveStatus = True
    connected = True
    #keepAlive(clientSocket, serverAddress)

    while connected:
        keepAliveStatus = True
        print("--- CLIENT MENU ---")
        print("(T)ext message")
        print("(F)ile message")
        print("(S)witch roles")
        print("(E)xit")

        command = input()
        if command == 'T' or command == 't':
            keepAliveStatus = False
            sendMessage(clientSocket, serverAddress, "T")
            
        elif command == 'F' or command == 'f':
            keepAliveStatus = False
            sendMessage(clientSocket, serverAddress, "F")

        elif command == 'S' or command == 's':
            keepAliveStatus = False
            switchRoles(clientSocket, serverAddress)
            connected = False

        elif command == 'E' or command == 'e':
            keepAliveStatus = False
            print("Exit")
            connected = False

        else:
            print("Neplatny prikaz")
    
    # ukoncenie spojenia
    clientSocket.close()
            

def sendMessage(clientSocket, serverAddress, messageType):
    mesageComplete = False
    #fragmentation = 2 #1464
    packetNum = 1
    crc = 0
    
    fragmentation = int(input("Zadaj velkost fragmentacie: "))

    # informacny paket pre textovu spravu
    if (messageType == "T"):
        message = (str(input("Zadaj spravu: ")))
        message = message.encode('utf-8')
         
        packetsCount =  (int(math.ceil(len(message) / fragmentation)))
        print("Bude odoslanych ", packetsCount, " paketov")

        infoMessage = createHeader(2, packetsCount, 0, fragmentation)
        clientSocket.sendto(infoMessage, serverAddress)
    
    # informacny paket pre suborovu spravu
    if (messageType == "F"):
        fileDir = os.path.dirname(__file__)

        fileLoaded = False
        while (not fileLoaded):
            try:
                fileName = input("Input file name: ")
                filePath = fileDir+"\\"+fileName
                fileSize = os.path.getsize(filePath)

                print("Nazov suboru:", fileName, "Velkost:", fileSize, "B")
                print("Absolutna cesta:", os.path.abspath(fileName))

                file = open(filePath, "rb")
                message = file.read()
                file.close()
                fileLoaded = True
            except:
                print("Neplatny nazov suboru!")

        packetsCount = (int(math.ceil(len(message) / fragmentation)))
        print("Bude odoslanych ", packetsCount, " paketov")

        header = createHeader(3, packetsCount, 0, fragmentation)
        infoMessage = header+fileName.encode('utf-8')
        clientSocket.sendto(infoMessage, serverAddress)

    
    # kontrola od servera
    response = clientSocket.recv(1500)
    responseType = int.from_bytes(response[:1], 'big')
    print("Resposne = ", responseType)

    wrongPacket = False
    wrongPacketNum = 0
    while True:
        command2 = input("Chybny paket? (Y)/(N) ")
        if command2 == 'Y':
            try:
                wrongPacketNum = int(input("Zadaj cislo paketu: "))
                if (wrongPacketNum > packetsCount or wrongPacketNum < 1):
                    print("Zly format")
                    continue
                else:
                    wrongPacket = True
                    break
            except:
                print("Zly format")
        elif command2 == 'N':
            break
        else:
            print("Neznamy prikaz")
    

    

    while not mesageComplete:
        messagePart = message[:fragmentation]

        crc = crc16ModBus(messagePart)
        header = createHeader(5, packetNum, crc, fragmentation)

        if (wrongPacket and wrongPacketNum == packetNum):
            clientSocket.sendto(header+message[:fragmentation-1], serverAddress)
            wrongPacket = False

        else:
            clientSocket.sendto(header+messagePart, serverAddress)
            print("Paket = ", packetNum)

        # odpoved od servera
        response = clientSocket.recv(1500)
        responseType = int.from_bytes(response[:1], 'big')

        attempts = 0
        packetOk = False
        # + timer
        while (not packetOk and attempts < 3): 
            # uspesne doruceny paket
            if (responseType == 8):
                message = message[fragmentation:]
                packetOk = True

            # neuspesne doruceny paket
            elif (responseType == 9): 
                clientSocket.sendto(header+messagePart, serverAddress)
                print("Posielam znovu paket: ", packetNum)
                attempts+=1

                response = clientSocket.recv(1500)
                responseType = int.from_bytes(response[:1], 'big')
                
        
        if (packetOk):
            # kontrola posledneho paketu
            if (packetNum == packetsCount):
                mesageComplete = True
                #clientSocket.close()
                print("Vybavene OK")
            else:
                packetNum+=1
        else:
            clientSocket.close()
            print("Neuspesny prenos!")
            break


def receiveMessage(serverSocket, clientAddress, packetsCount, messageType, fileName=""):
    receivedPackets = 0
    messageFull = []

    if (messageType == 'F'):
        while True:
            fileDir = input("Adresar na ulozenie suboru: ")
            if (os.path.isdir(fileDir)):
                print("Subor bude ulozeny do: ", fileDir+"\\"+fileName)
                break
            else:
                print("Neplatny adresar")

    serverSocket.sendto(createHeader(7, 0, 0, 0), clientAddress)
    print("Bude prijatych ", packetsCount, " paketov")

    while (packetsCount > receivedPackets):

        message = serverSocket.recv(1500)
        messagePart = message[8:]
        header = message[:8]

        type = int.from_bytes(header[:1], 'big')
        packet = int.from_bytes(header[1:4], 'big')
        crc = int.from_bytes(header[4:6], 'big')
        frag = int.from_bytes(header[6:8], 'big')


        crcCalc = crc16ModBus(messagePart)
        #CRC Calcucalte
        if (crc == crcCalc):
            if (messageType == 'T'):
                messageFull.append(messagePart.decode())
            elif (messageType == 'F'):
                messageFull.append(messagePart)

            serverSocket.sendto(createHeader(8, packet, 0, 0), clientAddress)
            receivedPackets+=1
            
            #print("Message = ", messagePart.decode())
            print("Prijaty paket: ", receivedPackets)

        else:
            print("Poskodeny paket: ", packet)
            serverSocket.sendto(createHeader(9, packet, 0, 0), clientAddress)

    if (messageType == 'T'):
        print("Dorucena sprava:")
        print(''.join(messageFull))

    if (messageType == 'F'):
        filePath = fileDir+"\\"+fileName
        fileWrite = open(filePath, "wb")
        for byte in messageFull:
            fileWrite.write(byte)
        fileWrite.close()

        fileSize = os.path.getsize(filePath)
        print("Nazov suboru:", fileName, "Velkost:", fileSize, "B")
        print("Absolutna cesta: ", filePath)

# Main
def main():
    running = True

    while running:
        print("--- MAIN MENU ---")
        print("(C)lient")
        print("(S)erver")
        print("(E)xit")
       
        command = input()
        if command == 'C' or command == 'c':
            clientConnect()
        elif command == 'S' or command == 's':
            serverConnect()
        elif command == 'E' or command == 'e':
            running = False
        else:
            print("Neplatný príkaz!")
        print()
        
main()