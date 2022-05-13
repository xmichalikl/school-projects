using System;
using System.Threading;
using System.Collections.Generic;
using SharpPcap.LibPcap;
using SharpPcap;
using PacketDotNet;


namespace psip_switch {
    public class Port {
        public readonly Switch sw;
        public LibPcapLiveDevice device;
        public LibPcapLiveDevice sendToDevice;

        public int portNum;
        public int packetInCount;
        public int packetOutCount;

        public Timer timer;
        public int actualCounter;
        public readonly int defaultCounter;

        public List<String> packetBuffer;
        public Dictionary<String, int> portInStats;
        public Dictionary<String, int> portOutStats;

        public Port(Switch sw, int portNum) {
            this.sw = sw;
            this.portNum = portNum;
            this.packetInCount = 0;
            this.packetOutCount = 0;
            
            this.packetBuffer = new List<String>();
            this.portInStats = new Dictionary<String, int>
            {
                { "ETH" , 0 }, { "ARP" , 0 },
                { "IP"  , 0 }, { "TCP" , 0 },
                { "UDP" , 0 }, { "ICMP", 0 },
                { "HTTP", 0 }
            };
            this.portOutStats = new Dictionary<String, int>(portInStats);
            this.timer = new Timer((state) => checkCableConnection(), null, Timeout.Infinite, Timeout.Infinite);
            this.defaultCounter = 600;
        }

        public void setDevice(LibPcapLiveDevice device, LibPcapLiveDevice sendToDevice) {
            this.device = device;
            this.sendToDevice = sendToDevice;
        }
        
        public void startListen() {
            if (this.device != null) {
                this.device.OnPacketArrival += new PacketArrivalEventHandler(deviceOnPacketArrival);

                // Open Device
                int readTimeoutMilliseconds = 10;
                this.device.Open(mode: DeviceModes.Promiscuous | DeviceModes.DataTransferUdp | DeviceModes.NoCaptureLocal, read_timeout: readTimeoutMilliseconds);
                Console.WriteLine("\nListening on {0}", this.device.Description);
                this.device.StartCapture();
                this.actualCounter = this.defaultCounter;
                this.timer.Change(TimeSpan.Zero, TimeSpan.FromSeconds(1));
            }
            else {
                Console.WriteLine("No device is set!");
                return;
            }
        }

        public void stopListen() {
            if (this.device != null) {
                this.device.StopCapture();
                this.device.Close();
                this.timer.Change(Timeout.Infinite, Timeout.Infinite);
                Console.WriteLine("Capture stopped!");
                printPortStats();
                clearPortStats();
                clearPacketBuffer();
            }
            else {
                Console.WriteLine("No device is set!");
                return;
            }
        }

        public void deviceOnPacketArrival(object sender, PacketCapture e) {
            // Raw Data
            var rawCapture = e.GetPacket();
            var payloadStr = BitConverter.ToString(rawCapture.Data);

            // Time & Length
            DateTime time = rawCapture.Timeval.Date;
            var len = e.Data.Length;

            // Packet
            var packet = rawCapture.GetPacket();
            var ethPacket = packet.Extract<EthernetPacket>();

            // MAC Address
            var srcMac = ethPacket.SourceHardwareAddress;
            var dstMac = ethPacket.DestinationHardwareAddress;

            // IN / OUT Filters
            // TODO

            // Loop Filter
            if (srcMac.ToString().Equals("02004C4F4F50")) {
                return;
            }

            // Check Port Buffer
            if (this.packetBuffer.Contains(payloadStr)) {
                this.packetBuffer.Remove(payloadStr);
                updatePortStats(rawCapture.GetPacket(), this.portOutStats, false);
                this.packetOutCount++;
                return;
            }

            // Create/Update MAC Table Record
            this.sw.macTable.addNewRecord(srcMac, this.portNum);

            // Update Atual Counter
            this.actualCounter = defaultCounter;

            // Send Packet TODO -> Check MAC table !!!
            if (this.sw.macTable.getDestinationPort(dstMac, out int dstPortNum)) {
                if (dstPortNum == this.portNum) {
                    return;
                }
                Port otherPort = this.sw.allPorts[dstPortNum];
                Console.WriteLine("+++++++++++++++++++ TO ONE ++++++++++++++++++++++ {0} -> {1} | ARP: {2} | ICMP: {3}", this.portNum, otherPort.portNum, isPacketTypeOf<ArpPacket>(rawCapture), isPacketTypeOf<IcmpV4Packet>(rawCapture));
                sendPacketToPort(otherPort, rawCapture, payloadStr);
                updatePortStats(packet, this.portInStats, true);
                this.packetInCount++;
                return;
            }

            // Send packet to "all" ports
            foreach (KeyValuePair<int, Port> port in this.sw.allPorts) {
                int otherPortNum = port.Key;
                Port otherPort = port.Value;

                if (!this.portNum.Equals(otherPortNum)) {
                    Console.WriteLine("+++++++++++++++++++ TO ALL ++++++++++++++++++++++ {0} -> {1} | ARP: {2} | ICMP: {3}", this.portNum, otherPort.portNum, isPacketTypeOf<ArpPacket>(rawCapture), isPacketTypeOf<IcmpV4Packet>(rawCapture));
                    sendPacketToPort(otherPort, rawCapture, payloadStr);
                    updatePortStats(packet, this.portInStats, true);
                    this.packetInCount++;
                }
            }

            // Send packet to another port
            /*if (this.portNum == 1) {
                this.sw.port_2.packetBuffer.Add(payloadStr);
                this.sw.port_2.packetBuffer.Add(payloadStr);
                this.sendToDevice.SendPacket(rawCapture);
                updatePortStats(rawCapture.GetPacket(), this.portInStats, true);
                this.packetInCount++;
                return;
            }
            if (this.portNum == 2) {
                this.sw.port_1.packetBuffer.Add(payloadStr);
                this.sw.port_1.packetBuffer.Add(payloadStr);
                this.sendToDevice.SendPacket(rawCapture);
                updatePortStats(rawCapture.GetPacket(), this.portInStats, true);
                this.packetInCount++;
                return;
            }*/

        }

        public void sendPacketToPort(Port port, RawCapture rawCapture, String payloadStr) {
            port.packetBuffer.Add(payloadStr);
            port.packetBuffer.Add(payloadStr);
            port.device.SendPacket(rawCapture);
        }

        public void updatePortStats(Packet packet, Dictionary<String, int> portStats, bool direction) {
            if (packet.Extract<EthernetPacket>() != null) {
                portStats["ETH"]++;

                if (packet.Extract<ArpPacket>() != null) {
                    portStats["ARP"]++;
                }

                if (packet.Extract<IPPacket>() != null) {
                    portStats["IP"]++;

                    if (packet.Extract<IcmpV4Packet>() != null ||
                        packet.Extract<IcmpV6Packet>() != null) {
                        portStats["ICMP"]++;
                    }

                    if (packet.Extract<UdpPacket>() != null) {
                        portStats["UDP"]++;
                    }

                    if (packet.Extract<TcpPacket>() != null) {
                        portStats["TCP"]++;

                        int desPort = packet.Extract<TcpPacket>().DestinationPort;
                        int srcPort = packet.Extract<TcpPacket>().SourcePort;

                        if (desPort == 80 || srcPort == 80) {
                            portStats["HTTP"]++;
                        }
                    }
                }
                this.sw.form1.updateFormPortStats(portStats, this.portNum, direction);
            }
        }

        

        public void printPortStats() {
            Console.WriteLine("Port {0} IN stats:", this.portNum);
            foreach (KeyValuePair<String, int> stats in this.portInStats) {
                Console.WriteLine("{0}: {1}", stats.Key, stats.Value);
            }
            Console.WriteLine();
            Console.WriteLine("Port {0} OUT stats:", this.portNum);
            foreach (KeyValuePair<String, int> stats in this.portOutStats) {
                Console.WriteLine("{0}: {1}", stats.Key, stats.Value);
            }
            Console.WriteLine();
            Console.WriteLine();
        }

        public void clearPacketBuffer() {
            this.packetBuffer.Clear();
        }

        public void clearPortStats() {
            // In
            this.packetInCount = 0;
            this.portInStats.Clear();
            this.portInStats = initStats();

            // Out
            this.packetOutCount = 0;
            this.portOutStats.Clear();
            this.portOutStats = initStats();

            // Form
            this.sw.form1.updateFormPortStats(this.portInStats, this.portNum, true);
            this.sw.form1.updateFormPortStats(this.portOutStats, this.portNum, false);
        }

        public bool isPacketTypeOf<T>(RawCapture rawCapture) where T : Packet {
            var packet = rawCapture.GetPacket();
            var packetType = packet.Extract<T>();

            if (packetType != null) {
                return true;
            }
            else {
                return false;
            }
        }

        /*public void updateFormStats(Dictionary<String, int> portStats) {
            foreach (KeyValuePair<String, int> item in portStats) {
                Console.WriteLine("PORT: {0} {1}", item.Key, item.Value);

                String s = item.Key;
                String num = item.Value.ToString();

                (this.sw.form1.stats_in_p1[s]).Text = num;
            }
        }*/

        public Dictionary<String, int> initStats() {
            return new Dictionary<String, int>
            {
                { "ETH" , 0 }, { "ARP" , 0 },
                { "IP"  , 0 }, { "TCP" , 0 },
                { "UDP" , 0 }, { "ICMP", 0 },
                { "HTTP", 0 }
            };
        }

        public void checkCableConnection() {
            Console.WriteLine("Port: {0} | Counter: {1}", this.portNum, this.actualCounter);
            if (this.actualCounter > 0) {
                this.actualCounter--;
                return;
            }
            if (this.actualCounter == 0) {
                this.actualCounter--;
                this.sw.macTable.deleteFromMacTable(this.portNum);
                return;
            }
        }
    }
}
