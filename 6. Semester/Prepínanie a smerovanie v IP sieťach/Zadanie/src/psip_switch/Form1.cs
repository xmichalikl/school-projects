using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace psip_switch {
    public partial class Form1 : Form {
        private Switch sw;
        private List<String> device_name_list;

        private Dictionary<String, TextBox> stats_in_p1;
        private Dictionary<String, TextBox> stats_out_p1;

        private Dictionary<String, TextBox> stats_in_p2;
        private Dictionary<String, TextBox> stats_out_p2;

        public Form1() {
            this.sw = new Switch(this);
            this.stats_in_p1 = new Dictionary<String, TextBox>();
            this.stats_out_p1 = new Dictionary<String, TextBox>();
            this.stats_in_p2 = new Dictionary<String, TextBox>();
            this.stats_out_p2 = new Dictionary<String, TextBox>();
            this.device_name_list = new List<String>();
            InitializeComponent();
            InicializeForm();
        }
        private void InicializeForm() {
            foreach (Control control in this.Controls) {
                if (control is TextBox && control.Name.Contains("stats")) {
                    ((TextBox)control).Text = "0";

                    // Port 1
                    if (control.Name.Contains("in_p1")) {
                        this.stats_in_p1.Add(control.Name.Split('_')[0].ToUpper(), (TextBox)control);
                    }
                    else if (control.Name.Contains("out_p1")) {
                        this.stats_out_p1.Add(control.Name.Split('_')[0].ToUpper(), (TextBox)control);
                    }
                    // Port 2
                    else if (control.Name.Contains("in_p2")) {
                        this.stats_in_p2.Add(control.Name.Split('_')[0].ToUpper(), (TextBox)control);
                    }
                    else if (control.Name.Contains("out_p2")) {
                        this.stats_out_p2.Add(control.Name.Split('_')[0].ToUpper(), (TextBox)control);
                    }
                }
            }
            foreach (var device in this.sw.liveDevices()) {
                if (device.Description.Contains("KM-TEST")) {
                    this.device_name_list.Add(device.Description);
                    this.interface_p1.Items.Add(device.Description);
                    this.interface_p2.Items.Add(device.Description);
                }
            }
            this.device_name_list.Add("-- Not Selected --");
            this.interface_p1.Items.Add("-- Not Selected --");
            this.interface_p2.Items.Add("-- Not Selected --");
        }

        private void Form1_Load(object sender, EventArgs e) {

        }

        private void start_button_Click(object sender, EventArgs e) {
            String interface_p1 = this.interface_p1.Text;
            String interface_p2 = this.interface_p2.Text;
            //sw.startSwitch("Microsoft KM-TEST Loopback Adapter", "Microsoft KM-TEST Loopback Adapter #2");
            if (!interface_p1.Equals("-- Not Selected --") && !interface_p2.Equals("-- Not Selected --")) { 
                sw.startSwitch(interface_p1, interface_p2);
            }
        }

        private void stop_button_Click(object sender, EventArgs e) {
            sw.stopSwitch();
        }

        public void updateFormMacTable(List<MacTableRecord> recordsList) {
            // LIST
            /*this.mac_table.Invoke((MethodInvoker)delegate {
                mac_table.DataSource = null;
                mac_table.DataSource = recordsList;
            });*/

            if (this.mac_table.InvokeRequired) {
                this.mac_table.Invoke(new MethodInvoker(() => {
                    this.mac_table.DataSource = recordsList;
                }));
            }
            else {
                this.mac_table.DataSource = recordsList;
            }
        }

        public void updateFormPortStats(Dictionary<String, int> portStats, int portNum, bool direction) {
            Dictionary<String, TextBox> stats = null;
            if (portNum == 1 && direction) stats = this.stats_in_p1;
            else if (portNum == 1 && !direction) stats = this.stats_out_p1;
            else if (portNum == 2 && direction) stats = this.stats_in_p2;
            else if (portNum == 2 && !direction) stats = this.stats_out_p2;
            else return;

            foreach (KeyValuePair<String, int> item in portStats) {
                String stat = item.Key;
                String num = item.Value.ToString();
                stats[stat].Text = num;
            }
        }

        private void interface_p1_SelectedIndexChanged(object sender, EventArgs e) {
            String selectedInterface = this.interface_p1.Text;
            this.interface_p2.Items.Clear();
            this.interface_p2.Items.AddRange(this.device_name_list.ToArray<object>());
            if (!selectedInterface.Equals("-- Not Selected --")) {
                this.interface_p2.Items.Remove(selectedInterface);
            }
        }

        private void interface_p2_SelectedIndexChanged(object sender, EventArgs e) {
            String selectedInterface = this.interface_p2.Text;
            this.interface_p1.Items.Clear();
            this.interface_p1.Items.AddRange(this.device_name_list.ToArray<object>());
            if (!selectedInterface.Equals("-- Not Selected --")) {
                this.interface_p1.Items.Remove(selectedInterface);
            }
        }

        private void mac_counter_update_button_Click(object sender, EventArgs e) {
            String newCounterStr = this.mac_counter.Text;
            if (int.TryParse(newCounterStr, out int newCounterNum)) {
                this.sw.macTable.setCounter(newCounterNum);
            }
        }

        private void reset_stats_button_Click(object sender, EventArgs e) {
            foreach (Port port in this.sw.allPorts.Values) {
                port.clearPortStats();
            }
        }

        private void reset_mac_button_Click(object sender, EventArgs e) {
            this.sw.macTable.resetMacTable();
        }
    }
}
