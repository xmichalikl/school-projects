using System;
using System.Net.NetworkInformation;
using System.ComponentModel;

namespace psip_switch {
    public class MacTableRecord {
        [DisplayName("MAC Address")]
        public PhysicalAddress address { get; set; }

        [DisplayName("Port")]
        public int portNum { get; set; }

        [DisplayName("Counter")]
        public int counter { get; set; }

        public MacTableRecord(PhysicalAddress address, int portNum, int counter) {
            this.address = address;
            this.portNum = portNum;
            this.counter = counter;
        }
    }
}
