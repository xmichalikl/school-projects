using System;
using System.Collections.Generic;
using System.Linq;
using System.Net.NetworkInformation;
using System.Text;
using System.Threading;
using System.Threading.Tasks;

namespace psip_switch {
    public class MacTable {
        public Dictionary<PhysicalAddress, MacTableRecord> records;
        public Timer timer;
        public Switch sw;
        public int counter;

        public MacTable(Switch sw) {
            this.records = new Dictionary<PhysicalAddress, MacTableRecord>();
            this.timer = new Timer((state) => updateAllRecords(sw.form1.updateFormMacTable), null, Timeout.Infinite, Timeout.Infinite);
            this.sw = sw; 
            this.counter = 60;
        }

        public void updateAllRecords(Action<List<MacTableRecord>> callback) {
            Console.WriteLine("MAC Table:");
            for (int index = records.Count - 1; index >= 0; index--) {
                var recordKey = records.ElementAt(index).Key;
                var recordValue = records.ElementAt(index).Value;
                recordValue.counter--;

                if (recordValue.counter < 1) {
                    records.Remove(recordKey);
                    Console.WriteLine("{0} counter: {1} -> Record removed!", recordValue.address, recordValue.counter);
                }
                else {
                    Console.WriteLine("{0} | counter: {1} | port: {2}", recordValue.address, recordValue.counter, recordValue.portNum);
                }
            }
            callback(this.records.Values.ToList());
            Console.WriteLine();
            Console.WriteLine();
        }

        public void addNewRecord(PhysicalAddress macAddress, int portNum) {
            try {
                if (this.records.TryGetValue(macAddress, out MacTableRecord record)) {
                    record.counter = this.counter;
                    if (record.portNum != portNum) {
                        record.portNum = portNum;
                        resetMacTable();
                        this.records.Add(macAddress, record);
                    }
                    return;
                }
                this.records.Add(macAddress, new MacTableRecord(macAddress, portNum, this.counter));
            }
            catch (Exception) {
                Console.WriteLine("Mac Table exception!");
            }
        }

        public bool getDestinationPort(PhysicalAddress macAddress, out int portNum) {
            if (this.records.TryGetValue(macAddress, out MacTableRecord record)) {
                portNum = record.portNum;
                return true;
            }
            portNum = -1;
            return false;
        }

        public void setCounter(int newCounter) {
            this.counter = newCounter;
        }

        public void startMacTable() {
            this.timer.Change(TimeSpan.Zero, TimeSpan.FromSeconds(1));
        }

        public void stopMacTable() {
            this.timer.Change(Timeout.Infinite, Timeout.Infinite);
            resetMacTable();
        }

        public void resetMacTable() {
            this.records.Clear();
            this.sw.form1.updateFormMacTable(this.records.Values.ToList());
            //updateAllRecords(this.sw.form1.updateFormMacTable);
        }

        public void deleteFromMacTable(int portNum) {
            for (int index = records.Count - 1; index >= 0; index--) {
                var recordKey = records.ElementAt(index).Key;
                var recordValue = records.ElementAt(index).Value;

                if (recordValue.portNum == portNum) {
                    records.Remove(recordKey);
                }
            }
            this.sw.form1.updateFormMacTable(this.records.Values.ToList());
        }
    }
}
