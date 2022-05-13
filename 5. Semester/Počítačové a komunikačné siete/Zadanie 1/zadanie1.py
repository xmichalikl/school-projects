from binascii import hexlify
import scapy.all as scapy
import os

#Trieda pre UDP porty
class UDPPorts:
    srcPort = None
    desPort = None

    def __init__(self, srcPort, desPort):
        self.srcPort = srcPort
        self.desPort = desPort


#Trieda pre ARP komunikáciu
class ARPCom:
    closed = False
    srcIP = None
    desIP = None
    srcMAC = None
    frames = []
    frameNum = None

    def __init__(self, srcIP, desIP, srcMAC, frameNum, closed):
        self.closed = closed
        self.srcIP = srcIP
        self.desIP = desIP
        self.srcMAC = srcMAC
        self.frames = []
        self.frameNum = frameNum


#Zsití, či sa medzi ostatnými ARP nachádza 'duplikát' s rovnakou požiadavkou
def checkArpDuplicate(arpFramesArr, arpFrameSrcIp, arpFrameDesIp, arpFrameMAC):
    for pos in range(0, len(arpFramesArr)):
        if arpFramesArr[pos].closed:
            continue
        else:
            if arpFramesArr[pos].srcIP == arpFrameSrcIp:
                if arpFramesArr[pos].desIP == arpFrameDesIp:
                    if arpFramesArr[pos].srcMAC == arpFrameMAC:
                        return pos
            if arpFramesArr[pos].srcIP == arpFrameDesIp:
                if arpFramesArr[pos].desIP == arpFrameSrcIp:
                    if arpFramesArr[pos].srcMAC == arpFrameMAC:
                        return pos
    return -1


#Vypis celého ramca
def printFullFrame(frame):
    count = 0
    for byte in frame:
        print('{:02x} '.format(byte), end='')
        count += 1
        if count % 16 == 0:
            print('')
        elif count % 8 == 0:
            print(' ', end='')
    print('')


#Vráti MAC adresu v podobe stringu
def getMacadressStr(macAdrRaw):
    macAdrStr = ''
    for byte in macAdrRaw:
        macAdrStr = macAdrStr + '{:02x} '.format((byte))
    return macAdrStr


#Výpis cieľovej MAC adresy
def printDesMacAddress(frame):
    print("Cieľová MAC adresa: ", end='')
    for byte in range(0, 6):
        print('{:02x} '.format(frame[byte]), end='')
    print('')


#Výpis zdrojovej MAC adresy
def printSrcMacAddress(frame):
    print("Zdrojová MAC adresa: ", end='')
    for byte in range(6, 12):
        print('{:02x} '.format(frame[byte]), end='')
    print('')


#Vráti IP adresu
def getIpAdressStr(ipAdrRaw):
    ipAdrStr = ''
    counter = 0

    for byte in ipAdrRaw:
        counter +=1
        ipAdrStr = ipAdrStr + str(byte)

        if counter < 4:
            ipAdrStr = ipAdrStr + '.'

    return ipAdrStr


#Vypíše IP adresu
def printIpAddress(ipAdress):
    count = 0
    for byte in ipAdress:
        count += 1
        if count < 4:
            print(byte, end='.')
        else:
            print(byte)


#Výpis cieľovej IP adresy
def printDesIpAddress(frame):
    desIpAdress = frame[30:34]
    count = 0
    print("   Cieľová IP adresa: ", end='')
    for byte in desIpAdress:
        count += 1
        if count < 4:
            print(byte, end='.')
        else:
            print(byte)


#Výpis zdrojovej IP adresy
def printSrcIpAddress(frame):
    srcIpAdress = frame[26:30]
    count = 0
    print("   Zdrojová IP adresa: ", end='')
    for byte in srcIpAdress:
        count += 1
        if count < 4:
            print(byte, end='.')
        else:
            print(byte)


#Výpis cieľového portu
def printDesPort(frame, portsRules):
    ihl = getHalfByte(frame[14], 2) * 4
    desPortRaw = frame[14+ihl+2:14+ihl+2+2]
    desPortDec = int(hexlify(desPortRaw), 16)
    desPortHex = bytesToHexStr(desPortRaw)
    print("      Cieľový port:", desPortDec, '(' + getKeyFromRules(portsRules, desPortHex) + ')')


#Výpis zdrojového portu
def printSrcPort(frame, portsRules):
    ihl = getHalfByte(frame[14], 2) * 4
    srcPortRaw = frame[14+ihl:14+ihl+2]
    srcPortDec = int(hexlify(srcPortRaw), 16)
    srcPortHex = bytesToHexStr(srcPortRaw)
    print("      Zdrojový port:", srcPortDec, '(' + getKeyFromRules(portsRules, srcPortHex) + ')')


#Vráti hodnotu portu v byte 
def getPort(frame, portType):
    ihl = getHalfByte(frame[14], 2) * 4
    portRaw = frame[14+ihl+portType:14+ihl+2+portType]
    return portRaw
    

#Výpis ICMP typu
def printIcmpType(frame, icmpTypesRules):
    ihl = getHalfByte(frame[14], 2) * 4
    icmpTypeRaw = frame[14+ihl:14+ihl+1]
    icmpTypeHex = bytesToHexStr(icmpTypeRaw)
    print("   -> ICMP typ:", getKeyFromRules(icmpTypesRules, icmpTypeHex))


#Výpis dĺžky rámca
def printFrameLen(frame):
    frameLen = len(frame)
    print("Dĺžka rámca z poskytnutá pcap API: ", frameLen, "B")
    if frameLen + 4 > 64:
        print("Dĺžka rámca prenášaného po médiu: ", frameLen + 4, "B")
    else:
        print("Dĺžka rámca prenášaného po médiu: 64 B")


#Výpis typu rámca
def printFrameType(frame):
    frameTypeRaw = frame[12:14]
    frameTypeDec = int(hexlify(frameTypeRaw), 16)
    print("Typ rámca: ", end='')
    if frameTypeDec > 1500:
        print("Ethernet II")
    else:
        frameTypeDec = frame[14]
        print("802.3 ", end='')    
        if frameTypeDec == 0xFF:
            print("- RAW")
        elif frameTypeDec == 0xAA:
            print("- LLC + SNAP")
        else:
            print("- LLC")


#Výpis konkrétneho rámca
def printSpecificFrame(pcap, frameNum):
    if len(pcap) >= frameNum > 0:
        raw = scapy.raw(pcap[frameNum - 1])
        print("Rámec ", frameNum)
        printFrameLen(raw)
        printFrameType(raw)
        printDesMacAddress(raw)
        printSrcMacAddress(raw)
        getNestedProtocol(raw)
        printFullFrame(raw)
    else:
        print("Neplatné číslo rámca!")


#Výpis všetkých rámcov
def printAllFrames(pcap):
    pcapLen = len(pcap)
    srcIpDictionary = {}
    for frameNum in range(0, pcapLen):
        raw = scapy.raw(pcap[frameNum])
        print("Rámec ", frameNum + 1)
        printFrameLen(raw)
        printFrameType(raw)
        printDesMacAddress(raw)
        printSrcMacAddress(raw)
        getNestedProtocol(raw)
        checkIpDictionary(raw, srcIpDictionary)
        printFullFrame(raw)
        print('')
    printSrcIpDictionary(srcIpDictionary)


#Vypíše IP adresy vysielajúcich uzlov
def printSrcIpDictionary(srcIpDictionary):
    mostPackets = 0
    print("IP adresy vysielajúcich uzlov:")
    for srcIp in sorted(srcIpDictionary.keys(), reverse=True):
        count = 0
        for byte in srcIp:
            count += 1
            if count < 4:
                print(byte, end='.')
            else:
                print(byte, end='\t')
        print(srcIpDictionary[srcIp])
        if srcIpDictionary[srcIp] > mostPackets:
            mostPackets = srcIpDictionary[srcIp]
    print('')
    print("Adresa uzla s najväčším počtom odoslaných paketov:")
    for srcIp in srcIpDictionary:
        if srcIpDictionary[srcIp] == mostPackets:
            count = 0
            for byte in srcIp:
                count += 1
                if count < 4:
                    print(byte, end='.')
                else:
                    print(byte, end='\t')
            print(srcIpDictionary[srcIp])
    print('')


#Zapíše odosielajúcu IP adresu a počet odoslaných paketov
def checkIpDictionary(frame, srcIpDictionary):
    etherTypeDec = int(hexlify(frame[12:14]), 16)
    if etherTypeDec == 0x0800: 
        srcIp = frame[26:30]   
        if srcIp in srcIpDictionary:
            srcIpDictionary[srcIp] += 1
        else:
            srcIpDictionary[srcIp] = 1


#Zistí vnorený protokol
def getNestedProtocol(frame):
    etherTypeRaw = frame[12:14]
    etherTypeDec = int(hexlify(etherTypeRaw), 16)
    etherTypeHex = bytesToHexStr(etherTypeRaw)

    etherTypesRules = getRules('ether_types.txt')
    llcTypesRules = getRules('llc_saps.txt')
      
    #Ethernet II
    if etherTypeDec > 1500:
        print('->', getKeyFromRules(etherTypesRules, etherTypeHex))
        #ARP
        if etherTypeDec == 0x0806:
            arpOperation = frame[21]
            if arpOperation == 0x01:
                print("   ARP typ: Request")
            elif arpOperation == 0x02:
                print("   ARP typ: Reply")

            print("   Zdrojová IP adresa: ", end='')
            printIpAddress(frame[28:32])

            print("   Cieľová IP adresa: ", end='')
            printIpAddress(frame[38:42])

        #IPv4        
        if etherTypeDec == 0x0800: 
            printSrcIpAddress(frame)
            printDesIpAddress(frame)

            ipProtocolsRules = getRules('ip_protocols.txt')
            ipProtocol = frame[23]

            print('   ->', getKeyFromRules(ipProtocolsRules, hex(ipProtocol)))

            #ICMP
            if ipProtocol == 0x01:
                icmpTypesRules = getRules('icmp_types.txt')
                printIcmpType(frame, icmpTypesRules)

            #TCP/UDP
            else:
                if ipProtocol == 0x06:
                    portsRules = getRules('tcp_ports.txt')
                elif ipProtocol == 0x11:
                    portsRules = getRules('udp_ports.txt')
                printSrcPort(frame, portsRules)
                printDesPort(frame, portsRules)
    #802.3
    else:
        dsap = frame[14]

        #802.3 LLC + SNAP
        if dsap == 0xAA:
            etherTypeRaw = frame[20:22]
            etherTypeHex = bytesToHexStr(etherTypeRaw)
            print('  ->', getKeyFromRules(etherTypesRules, etherTypeHex))
            # if IPv4 or ARP
            # continue

        #802.3 RAW
        elif dsap == 0xFF:
            print("  -> IPX")

        #802.3 LLC
        else:
            print('  ->', getKeyFromRules(llcTypesRules, hex(dsap)))


#Premení byte na hexString
def bytesToHexStr(bytes):
    hexStr = '0x' + bytes.hex()
    hexStr = hex(int(hexStr, 16))
    return hexStr


# Vráti polovičnú časť byte
def getHalfByte(byte, half):
    byte = hex(byte)

    if half < 1 or half > 2 or len(byte) > 4:
        return -1
    elif len(byte) == 4:
        if half == 1:
            byte = byte[2]
        elif half == 2:
            byte = byte[3]
    elif len(byte) == 3:
        if half == 1:
            byte = 0
        elif half == 2:
            byte = byte[2]
    return int(byte)


#Vráti klúč z poľa pravidiel
def getKeyFromRules(rules, key):
    for rule in rules:
        if hex(int(rule.partition(' ')[0], 16)) == key:
            return(rule.partition(' ')[2])
    return("Neznámy")


#Zistí, či sa daný kľúč nacházda medzi pravidlami
def checkKeyFromRules(rules, key):
    for rule in rules:
        if hex(int(rule.partition(' ')[0], 16)) == key:
            return True
    return False


#Načíta pravidlá k analýze zo súboru
def getRules(file):
    filePath = __file__
    srcFileName = os.path.basename(__file__)
    filePath = filePath.replace(srcFileName,'rules\\')

    rules = []
    with open(filePath + file) as file:
        etherTypes = file.readlines()
    for line in etherTypes:
        line = line.replace('\n', '')
        rules.append(line)
    return rules


#Načítanie pcap súboru
def loadPcap():
    filePath = __file__
    srcFileName = os.path.basename(__file__)
    filePath = filePath.replace(srcFileName,'examples\\')
    file = input("Vyber pcap súbor z adresára .\\examples\\")

    if file == "exit":
        print("Súbor sa nepodarilo načítať!")
        return None
        
    while not os.path.exists(filePath + file):
        print("Neplatný súbor!")
        file = input("Vyber pcap súbor z adresára .\\examples\\")
        if file == "exit":
            print("Súbor sa nepodarilo načítať!")
            return None
    if file.endswith('.pcap'):
        print("Súbor " + file + " bol úspešne načítaný!")
        return scapy.rdpcap(os.path.join(filePath + file))
    else:
        print("Nesprávny formát súboru!")
        return None


#Vypíše ARP hlacičku
def printArpFrameHeader(pcap, frameNum):
    arpFrame = scapy.raw(pcap[frameNum])               
    arpOperation = arpFrame[21]
    
    arpFrameSrcIp = getIpAdressStr(arpFrame[28:32])
    arpFrameDesIp = getIpAdressStr(arpFrame[38:42])

    print("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~")
    #ARP Request
    if arpOperation == 0x01: 
        arpFrameMAC = arpFrame[22:28]
        print("ARP-Request,", "IP adresa:", arpFrameDesIp + ',', "MAC adresa: ???")
        print("Zdrojová IP adresa:", arpFrameSrcIp + ',', "Cieľová IP adresa", arpFrameDesIp)

    #ARP Reply
    elif arpOperation == 0x02: 
        arpFrameMAC = arpFrame[32:38]
        print("ARP-Reply,", "IP adresa:", arpFrameDesIp + ',', "MAC adresa:", getMacadressStr(arpFrameMAC))
        print("Zdrojová IP adresa:", arpFrameSrcIp + ',', "Cieľová IP adresa", arpFrameDesIp)
    print("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~")


#Vypíše ARP komunikáciu (dvojce)
def printArpCom(pcap, arpFramesArr, limit):
    print('')
    count = 0
    
    #ARP dvojce
    if len(arpFramesArr) > 20 and limit:
        limit = True
    elif len(arpFramesArr) <= 20 and limit:
        limit = False

    for arp in arpFramesArr:
        if arp.closed and len(arp.frames) > 0:
            count += 1
            
            if limit:
                if count == 11:
                    print('.')
                    print('.')
                    print('.')
                if count > 10 and count <= (len(arpFramesArr)-10):
                    continue
            print("----------------------- KOMUNIKÁCIA Č.", count, "-----------------------")
            print('')
            printArpFrameHeader(pcap, arp.frameNum)
            printSpecificFrame(pcap, arp.frameNum+1)
            

            for x in arp.frames:
                print('                                \/')
                printArpFrameHeader(pcap, x)
                printSpecificFrame(pcap, x+1)
            print('')
            print('')

    print('')
    print("-------------------------- ZVYŠNÉ ARP --------------------------")
    count = 0

    #ARP zvyšné
    for arp in arpFramesArr:
        if (not arp.closed) or (arp.closed and len(arp.frames) == 0):
            count += 1

            if limit:
                if count == 11:
                    print('.')
                    print('.')
                    print('.')
                if count > 10 and count <= (len(arpFramesArr)-10):
                    continue
            
            printArpFrameHeader(pcap, arp.frameNum)
            printSpecificFrame(pcap, arp.frameNum+1)
            
            for x in arp.frames:
                print('                                \/')
                printArpFrameHeader(pcap, x)
                printSpecificFrame(pcap, x+1)
            print('')
            print('')

#Vypíše ICMP komunikáciu
def printIcmpCom(pcap, icmoFramesArr, limit):
    if len(icmoFramesArr) > 20 and limit:
        limit = True
    elif len(icmoFramesArr) <= 20 and limit:
        limit = False
    count = 0

    for icmpFrameNum in icmoFramesArr:
        count += 1
        if limit:
            if count == 11:
                print('.')
                print('.')
                print('.')
            if count > 10 and count <= (len(icmoFramesArr)-10):
                continue
        printSpecificFrame(pcap, icmpFrameNum+1)
        print('')


#Vyfiltrovanie komunikácií
def filter(pcap, filter, opt, limit):
    
    print('')
    if limit:
        print("Výpis s limitom")
    else:
        print("Výpis bez limitu")
    print('')

    tcpPortsRules = getRules('tcp_ports.txt')

    udpNextFilter = None
    tftpPorts = []

    tcpFramesArr = []
    udpFramesArr = []
    icmoFramesArr = []
    arpFramesArr = []

    for frameNum in range(0, len(pcap)):
        frame = scapy.raw(pcap[frameNum])

        etherTypeRaw = frame[12:14]
        etherTypeDec = int(hexlify(etherTypeRaw), 16)


        #IPv4 TCP (Filtre 1-6)
        if opt <= 6 and etherTypeDec == 0x0800:
            ipProtocol = frame[23]
            
            #TCP
            if ipProtocol == 0x06:
                srcPortRaw = getPort(frame, 0)
                srcPortDec = int(hexlify(srcPortRaw), 16)

                desPortRaw = getPort(frame, 2)
                desPortDec = int(hexlify(desPortRaw), 16)

                if filter == srcPortDec:
                    #if checkKeyFromRules(tcpPortsRules, bytesToHexStr(srcPortRaw)):
                        tcpFramesArr.append(frameNum)
                        continue
                elif filter == desPortDec:
                    #if checkKeyFromRules(tcpPortsRules, bytesToHexStr(desPortRaw)):
                        tcpFramesArr.append(frameNum)
                        continue
                else:
                    continue 
                    
        #IPv4 UDP (Filter 7)
        if opt == 7 and etherTypeDec == 0x0800:
            ipProtocol = frame[23]
            
            #UDP
            if ipProtocol == 0x11:
                srcPortRaw = getPort(frame, 0)
                srcPortDec = int(hexlify(srcPortRaw), 16)

                desPortRaw = getPort(frame, 2)
                desPortDec = int(hexlify(desPortRaw), 16)
                
                #Začaitok komunikácie
                if srcPortDec == filter:
                    udpNextFilter = desPortDec
                elif desPortDec == filter:
                    udpNextFilter = srcPortDec

                #Priraďovanie portov do 'slovníka'
                if udpNextFilter != None:          
                    if udpNextFilter == srcPortDec or udpNextFilter == desPortDec:
                        if srcPortDec != filter and desPortDec != filter:
                            same = False
                            if len(tftpPorts) > 0:
                                for udpPort in tftpPorts:
                                    if srcPortDec == udpPort.srcPort or srcPortDec == udpPort.desPort:
                                        if desPortDec == udpPort.srcPort or desPortDec == udpPort.desPort:
                                            same = True   
                                if not same:
                                    tftpPorts.append(UDPPorts(srcPortDec, desPortDec))      
                            else:
                                tftpPorts.append(UDPPorts(srcPortDec, desPortDec))

                    if udpNextFilter == srcPortDec:
                        udpFramesArr.append(frameNum)
                        continue
                    elif udpNextFilter == desPortDec:                    
                        udpFramesArr.append(frameNum)
                        continue
                    else:
                        continue 
                
        #IPv4 ICMP (Filter 8)
        if opt == 8 and etherTypeDec == 0x0800:
            ipProtocol = frame[23]
            #ICMP
            if ipProtocol == 0x01:
                icmoFramesArr.append(frameNum)
        
        #ARP (Filter 9)
        elif opt == 9 and etherTypeDec == 0x0806:
            arpOperation = frame[21]

            arpFrameSrcIp = frame[28:32]
            arpFrameDesIp = frame[38:42]
            arpFrameMAC = None

            #ARP Request
            if arpOperation == 0x01: 
                arpFrameMAC = frame[22:28]
            #ARP Reply
            elif arpOperation == 0x02: 
                arpFrameMAC = frame[32:38]
                
            duplicateIndex = checkArpDuplicate(arpFramesArr, arpFrameSrcIp, arpFrameDesIp, arpFrameMAC)

            #Ak existuje 'duplikát', tak ho pripojí k originálu
            if duplicateIndex >= 0:
                arpFramesArr[duplicateIndex].frames.append(frameNum)
                #Uzatvorí komunikáciu
                if arpOperation == 0x02:
                    arpFramesArr[duplicateIndex].closed = True

            #Ak neexistuje 'duplikát', tak vytvorí novú komunikáciu      
            elif duplicateIndex == -1:
                #ARP Request
                if arpOperation == 0x01:
                    arpFramesArr.append(ARPCom(arpFrameSrcIp, arpFrameDesIp, arpFrameMAC, frameNum, False))

                #ARP Reply
                elif arpOperation == 0x02: 
                    arpFramesArr.append(ARPCom(arpFrameSrcIp, arpFrameDesIp, arpFrameMAC, frameNum, True))
    
    #Výpis filtrov
    if opt <= 6:
        printTcpCom(pcap, tcpFramesArr, limit) #TCP
    if opt == 7:
        printUdpCom(pcap, udpFramesArr, tftpPorts, limit) #UDP (TFTP)
    if opt == 8:
        printIcmpCom(pcap, icmoFramesArr, limit) #ICMP
    if opt == 9:
        printArpCom(pcap, arpFramesArr, limit) # ARP


#Vypíše TCP komunikáciu
def printTcpCom(pcap, tcpFramesArr, limit):
    if len(tcpFramesArr) > 20 and limit:
        limit = True
    elif len(tcpFramesArr) <= 20 and limit:
        limit = False
    count = 0

    for tcpFrameNum in tcpFramesArr:
        count += 1
        if limit:
            if count == 11:
                print('.')
                print('.')
                print('.')
            if count > 10 and count <= (len(tcpFramesArr)-10):
                continue
        printSpecificFrame(pcap, tcpFrameNum+1)

#Vypíše UDP (TFTP) komunikáciu
def printUdpCom(pcap, udpFramesArr, tftpPorts, limit):
    nextPort = None
    tempLimit = limit

    for com in range(0, len(tftpPorts)):
        udpCom = []
        print('')
        print("------------ KOMUNIKÁCIA Č.", com+1, "------------")

        for udpFrameNum in udpFramesArr:        
            udpFrame = scapy.raw(pcap[udpFrameNum]) 

            srcPortDec = int(hexlify(getPort(udpFrame, 0)), 16)
            desPortDec = int(hexlify(getPort(udpFrame, 2)), 16)

            if nextPort == None:
                if (srcPortDec == 0x0045) and (desPortDec == tftpPorts[com].srcPort or desPortDec == tftpPorts[com].desPort):
                    nextPort = desPortDec
                    udpCom.append(udpFrameNum)                    
  
                elif (desPortDec == 0x0045) and (srcPortDec == tftpPorts[com].srcPort or srcPortDec == tftpPorts[com].desPort):
                    nextPort = srcPortDec
                    udpCom.append(udpFrameNum)                    
         
            elif nextPort != None:
                if srcPortDec == tftpPorts[com].srcPort or srcPortDec == tftpPorts[com].desPort:
                    if desPortDec == tftpPorts[com].srcPort or desPortDec == tftpPorts[com].desPort:
                        udpCom.append(udpFrameNum)                   

        if len(udpCom) > 20 and limit:
            tempLimit = True
        elif len(udpCom) <= 20 and limit:
            tempLimit = False
        count = 0

        for frame in udpCom:
            count += 1
            if tempLimit:
                if count == 11:
                    print('.')
                    print('.')
                    print('.')
                    print('')
                if count > 10 and count <= (len(udpCom)-10):
                    continue
            printSpecificFrame(pcap, frame+1)
            print('')
        nextPort = None

#Main
def main():
    pcap = None
    running = True

    while running:
        print('')
        print("------ Menu ------")
        print("1 - Načítaj súbor")
        print("2 - Ukonči program")
        try:
            command1 = int(input("Vyber možnosť: "))

            if command1 == 1:
                pcap = loadPcap()

                while pcap != None:
                    print('')
                    print("--------- Možnosti ---------")
                    print("1 - Výpis všetkých rámcov")
                    print("2 - Výpis konkrétneho rámca")
                    print("3 - Filter protokolov")
                    print("4 - Výber druhého súboru")
                    try:
                        command2 = int(input("Vyber možnosť: "))
                        if command2 == 1:
                            print('')
                            printAllFrames(pcap)

                        elif command2 == 2:
                            try:
                                frameNum = int(input("Zadaj číslo rámca: "))
                                print('')
                                printSpecificFrame(pcap, frameNum)
                            except:
                                print("Neplatný príkaz!")

                        elif command2 == 3:

                            command3 = None
                            limit = False                
                            while command3 != 10:
                                print('')
                                print("------------ Filter ------------")
                                print("1  - Výpis HTTP rámcov")
                                print("2  - Výpis HTTPS rámcov")
                                print("3  - Výpis TELNET rámcov")
                                print("4  - Výpis SSH rámcov")
                                print("5  - Výpis FTP riadiace rámcov")
                                print("6  - Výpis FTP dátové rámcov")
                                print("7  - Výpis TFTP rámcov")
                                print("8  - Výpis ICMP rámcov")
                                print("9  - Výpis ARP rámcov")
                                print("10 - Naspäť")
                                try:
                                    command3 = int(input("Vyber filter: ")) 
                                    if command3 != 10:
                                        command4 = str(input("Limit výpisu 10 & 10 riadkov (Y/N): "))     
                                        if command4 == 'Y' or command4 == 'y':
                                            limit = True
                                        elif command4 == 'N' or command4 == 'n':
                                            limit = False
                                        else:
                                            limit = False

                                    if command3 == 1:
                                        filter(pcap, 0x0050, command3, limit)
                                    elif command3 == 2:
                                        filter(pcap, 0x01BB, command3, limit)
                                    elif command3 == 3:
                                        filter(pcap, 0x0017, command3, limit)
                                    elif command3 == 4:
                                        filter(pcap, 0x0016, command3, limit)
                                    elif command3 == 5:
                                        filter(pcap, 0x0015, command3, limit)
                                    elif command3 == 6:
                                        filter(pcap, 0x0014, command3, limit)
                                    elif command3 == 7:
                                        filter(pcap, 0x0045, command3, limit)
                                    elif command3 == 8:
                                        filter(pcap, 0x0001, command3, limit)  
                                    elif command3 == 9:
                                        filter(pcap, 0x0806, command3, limit)   

                                    elif command3 == 10:
                                        command3 = 10

                                    else:
                                        print("Neplatný príkaz!")
                                except:
                                    print("Neplatný príkaz!")

                        elif command2 == 4:
                            pcap = None

                        else:
                            print("Neplatný príkaz!")
                    except:
                        print("Neplatný príkaz!")

            elif command1 == 2:
                running = False

            else:
                print("Neplatný príkaz!")
        except:
            print("Neplatný príkaz!")

main()
    