using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.NetworkInformation;
using System.Text;
using System.Threading.Tasks;
using PacketDotNet;

namespace psip_switch {
    internal class Filter {
        public IPAddress ipAddressIn;
        public IPAddress ipAddressOut;

        public PhysicalAddress macAddressIn;
        public PhysicalAddress macAddressOut;

        public int portNumIn;
        public int portNumOut;

        // packet type IN OUT
    }
}
