using SharpPcap;
using SharpPcap.LibPcap;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading;


namespace psip_switch {
    public class Switch {
        public Form1 form1;
        public Port port_1;
        public Port port_2;
        public MacTable macTable;
        public Dictionary<int, Port> allPorts;

        public Switch(Form1 form1) {
            this.form1 = form1;
            this.port_1 = new Port(this, 1);
            this.port_2 = new Port(this, 2);
            this.macTable = new MacTable(this);
            this.allPorts = new Dictionary<int, Port> 
            {
                {1, this.port_1}, {2, this.port_2}
            };
        }

        public void startSwitch(String deviceName_1, String deviceName_2) {
            // Find Devices
            var device_1 = findDevice(deviceName_1);
            var device_2 = findDevice(deviceName_2);

            if (device_1 != null && device_2 != null) {
                // Set Devices
                this.port_1.setDevice(device_1, device_2);
                this.port_2.setDevice(device_2, device_1);

                // Start MAC table
                this.macTable.startMacTable();

                // Start Ports
                this.port_1.startListen();
                this.port_2.startListen();
            }
            else {
                Console.WriteLine("Devices error!");
            }
        }

        public void stopSwitch() {
            // Stop Ports
            this.port_1.stopListen();
            this.port_2.stopListen();

            // Stop MAC Table
            this.macTable.stopMacTable();
        }

        public LibPcapLiveDevice findDevice(String deviceName) {
            // Get Devices List
            var allDevices = CaptureDeviceList.Instance;

            if (allDevices.Count < 1) {
                Console.WriteLine("No devices were found on this machine!");
                return null;
            }

            // Find Device Index By Device Name
            int deviceIndex = allDevices.ToList().FindIndex((dev) => {
                return dev.Description.Equals(deviceName);
            });

            if (deviceIndex >= 0) {
                return (LibPcapLiveDevice)allDevices[deviceIndex];
            }
            else {
                Console.WriteLine("Cannot find \"{0}\" device!", deviceName);
                return null;
            }
        }

        public CaptureDeviceList liveDevices() {
            return CaptureDeviceList.Instance;
        }
    }
}
