using System.Windows.Forms;

namespace psip_switch
{
    partial class Form1
    {
        /// <summary>
        /// Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// Clean up any resources being used.
        /// </summary>
        /// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows Form Designer generated code

        /// <summary>
        /// Required method for Designer support - do not modify
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            this.start_button = new System.Windows.Forms.Button();
            this.stop_button = new System.Windows.Forms.Button();
            this.port1_label = new System.Windows.Forms.Label();
            this.eth_ii_stats_in_p1_label = new System.Windows.Forms.Label();
            this.arp_stats_in_p1_label = new System.Windows.Forms.Label();
            this.ip_stats_in_p1_label = new System.Windows.Forms.Label();
            this.tcp_stats_in_p1_label = new System.Windows.Forms.Label();
            this.udp_stats_in_p1_label = new System.Windows.Forms.Label();
            this.http_stats_in_p1_label = new System.Windows.Forms.Label();
            this.icmp_stats_in_p1_label = new System.Windows.Forms.Label();
            this.eth_ii_stats_in_p1 = new System.Windows.Forms.TextBox();
            this.arp_stats_in_p1 = new System.Windows.Forms.TextBox();
            this.ip_stats_in_p1 = new System.Windows.Forms.TextBox();
            this.udp_stats_in_p1 = new System.Windows.Forms.TextBox();
            this.tcp_stats_in_p1 = new System.Windows.Forms.TextBox();
            this.icmp_stats_in_p1 = new System.Windows.Forms.TextBox();
            this.http_stats_in_p1 = new System.Windows.Forms.TextBox();
            this.http_stats_out_p1 = new System.Windows.Forms.TextBox();
            this.udp_stats_out_p1 = new System.Windows.Forms.TextBox();
            this.tcp_stats_out_p1 = new System.Windows.Forms.TextBox();
            this.icmp_stats_out_p1 = new System.Windows.Forms.TextBox();
            this.ip_stats_out_p1 = new System.Windows.Forms.TextBox();
            this.arp_stats_out_p1 = new System.Windows.Forms.TextBox();
            this.eth_ii_stats_out_p1 = new System.Windows.Forms.TextBox();
            this.icmp_stats_out_p1_label = new System.Windows.Forms.Label();
            this.http_stats_out_p1_label = new System.Windows.Forms.Label();
            this.udp_stats_out_p1_label = new System.Windows.Forms.Label();
            this.tcp_stats_out_p1_label = new System.Windows.Forms.Label();
            this.ip_stats_out_p1_label = new System.Windows.Forms.Label();
            this.arp_stats_out_p1_label = new System.Windows.Forms.Label();
            this.eth_ii_stats_out_p1_label = new System.Windows.Forms.Label();
            this.port1_in_label = new System.Windows.Forms.Label();
            this.port1_out_label = new System.Windows.Forms.Label();
            this.port2_out_label = new System.Windows.Forms.Label();
            this.port2_in_label = new System.Windows.Forms.Label();
            this.http_stats_out_p2 = new System.Windows.Forms.TextBox();
            this.udp_stats_out_p2 = new System.Windows.Forms.TextBox();
            this.tcp_stats_out_p2 = new System.Windows.Forms.TextBox();
            this.icmp_stats_out_p2 = new System.Windows.Forms.TextBox();
            this.ip_stats_out_p2 = new System.Windows.Forms.TextBox();
            this.arp_stats_out_p2 = new System.Windows.Forms.TextBox();
            this.eth_ii_stats_out_p2 = new System.Windows.Forms.TextBox();
            this.icmp_stats_out_p2_label = new System.Windows.Forms.Label();
            this.http_stats_out_p2_label = new System.Windows.Forms.Label();
            this.udp_stats_out_p2_label = new System.Windows.Forms.Label();
            this.tcp_stats_out_p2_label = new System.Windows.Forms.Label();
            this.ip_stats_out_p2_label = new System.Windows.Forms.Label();
            this.arp_stats_out_p2_label = new System.Windows.Forms.Label();
            this.eth_ii_stats_out_p2_label = new System.Windows.Forms.Label();
            this.http_stats_in_p2 = new System.Windows.Forms.TextBox();
            this.udp_stats_in_p2 = new System.Windows.Forms.TextBox();
            this.tcp_stats_in_p2 = new System.Windows.Forms.TextBox();
            this.icmp_stats_in_p2 = new System.Windows.Forms.TextBox();
            this.ip_stats_in_p2 = new System.Windows.Forms.TextBox();
            this.arp_stats_in_p2 = new System.Windows.Forms.TextBox();
            this.eth_ii_stats_in_p2 = new System.Windows.Forms.TextBox();
            this.icmp_stats_in_p2_label = new System.Windows.Forms.Label();
            this.http_stats_in_p2_label = new System.Windows.Forms.Label();
            this.udp_stats_in_p2_label = new System.Windows.Forms.Label();
            this.tcp_stats_in_p2_label = new System.Windows.Forms.Label();
            this.ip_stats_in_p2_label = new System.Windows.Forms.Label();
            this.arp_stats_in_p2_label = new System.Windows.Forms.Label();
            this.eth_ii_stats_in_p2_label = new System.Windows.Forms.Label();
            this.port2_label = new System.Windows.Forms.Label();
            this.mac_table_label = new System.Windows.Forms.Label();
            this.mac_table = new SafeDataGridView();
            this.interface_p1 = new System.Windows.Forms.ComboBox();
            this.interface_p2 = new System.Windows.Forms.ComboBox();
            this.interface_p1_label = new System.Windows.Forms.Label();
            this.interface_p2_label = new System.Windows.Forms.Label();
            this.switch_label = new System.Windows.Forms.Label();
            this.mac_counter = new System.Windows.Forms.TextBox();
            this.mac_counter_update_button = new System.Windows.Forms.Button();
            this.mac_counter_label = new System.Windows.Forms.Label();
            this.reset_stats_button = new System.Windows.Forms.Button();
            this.reset_mac_button = new System.Windows.Forms.Button();
            ((System.ComponentModel.ISupportInitialize)(this.mac_table)).BeginInit();
            this.SuspendLayout();
            // 
            // start_button
            // 
            this.start_button.Location = new System.Drawing.Point(560, 50);
            this.start_button.Margin = new System.Windows.Forms.Padding(3, 2, 3, 2);
            this.start_button.Name = "start_button";
            this.start_button.Size = new System.Drawing.Size(75, 26);
            this.start_button.TabIndex = 0;
            this.start_button.Text = "Start";
            this.start_button.UseVisualStyleBackColor = true;
            this.start_button.Click += new System.EventHandler(this.start_button_Click);
            // 
            // stop_button
            // 
            this.stop_button.Location = new System.Drawing.Point(645, 50);
            this.stop_button.Margin = new System.Windows.Forms.Padding(3, 2, 3, 2);
            this.stop_button.Name = "stop_button";
            this.stop_button.Size = new System.Drawing.Size(75, 26);
            this.stop_button.TabIndex = 1;
            this.stop_button.Text = "Stop";
            this.stop_button.UseVisualStyleBackColor = true;
            this.stop_button.Click += new System.EventHandler(this.stop_button_Click);
            // 
            // port1_label
            // 
            this.port1_label.AutoSize = true;
            this.port1_label.Font = new System.Drawing.Font("Microsoft Sans Serif", 14F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(238)));
            this.port1_label.Location = new System.Drawing.Point(103, 114);
            this.port1_label.Name = "port1_label";
            this.port1_label.Size = new System.Drawing.Size(143, 29);
            this.port1_label.TabIndex = 2;
            this.port1_label.Text = "Port 1 stats";
            // 
            // eth_ii_stats_in_p1_label
            // 
            this.eth_ii_stats_in_p1_label.AutoSize = true;
            this.eth_ii_stats_in_p1_label.Font = new System.Drawing.Font("Microsoft Sans Serif", 10F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(238)));
            this.eth_ii_stats_in_p1_label.Location = new System.Drawing.Point(17, 208);
            this.eth_ii_stats_in_p1_label.Margin = new System.Windows.Forms.Padding(3, 0, 3, 10);
            this.eth_ii_stats_in_p1_label.Name = "eth_ii_stats_in_p1_label";
            this.eth_ii_stats_in_p1_label.Size = new System.Drawing.Size(56, 20);
            this.eth_ii_stats_in_p1_label.TabIndex = 3;
            this.eth_ii_stats_in_p1_label.Text = "ETH II";
            // 
            // arp_stats_in_p1_label
            // 
            this.arp_stats_in_p1_label.AutoSize = true;
            this.arp_stats_in_p1_label.Font = new System.Drawing.Font("Microsoft Sans Serif", 10F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(238)));
            this.arp_stats_in_p1_label.Location = new System.Drawing.Point(17, 238);
            this.arp_stats_in_p1_label.Margin = new System.Windows.Forms.Padding(3, 0, 3, 10);
            this.arp_stats_in_p1_label.Name = "arp_stats_in_p1_label";
            this.arp_stats_in_p1_label.Size = new System.Drawing.Size(43, 20);
            this.arp_stats_in_p1_label.TabIndex = 4;
            this.arp_stats_in_p1_label.Text = "ARP";
            // 
            // ip_stats_in_p1_label
            // 
            this.ip_stats_in_p1_label.AutoSize = true;
            this.ip_stats_in_p1_label.Font = new System.Drawing.Font("Microsoft Sans Serif", 10F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(238)));
            this.ip_stats_in_p1_label.Location = new System.Drawing.Point(17, 270);
            this.ip_stats_in_p1_label.Margin = new System.Windows.Forms.Padding(3, 0, 3, 10);
            this.ip_stats_in_p1_label.Name = "ip_stats_in_p1_label";
            this.ip_stats_in_p1_label.Size = new System.Drawing.Size(24, 20);
            this.ip_stats_in_p1_label.TabIndex = 5;
            this.ip_stats_in_p1_label.Text = "IP";
            // 
            // tcp_stats_in_p1_label
            // 
            this.tcp_stats_in_p1_label.AutoSize = true;
            this.tcp_stats_in_p1_label.Font = new System.Drawing.Font("Microsoft Sans Serif", 10F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(238)));
            this.tcp_stats_in_p1_label.Location = new System.Drawing.Point(17, 298);
            this.tcp_stats_in_p1_label.Margin = new System.Windows.Forms.Padding(3, 0, 3, 10);
            this.tcp_stats_in_p1_label.Name = "tcp_stats_in_p1_label";
            this.tcp_stats_in_p1_label.Size = new System.Drawing.Size(42, 20);
            this.tcp_stats_in_p1_label.TabIndex = 6;
            this.tcp_stats_in_p1_label.Text = "TCP";
            // 
            // udp_stats_in_p1_label
            // 
            this.udp_stats_in_p1_label.AutoSize = true;
            this.udp_stats_in_p1_label.Font = new System.Drawing.Font("Microsoft Sans Serif", 10F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(238)));
            this.udp_stats_in_p1_label.Location = new System.Drawing.Point(17, 327);
            this.udp_stats_in_p1_label.Margin = new System.Windows.Forms.Padding(3, 0, 3, 10);
            this.udp_stats_in_p1_label.Name = "udp_stats_in_p1_label";
            this.udp_stats_in_p1_label.Size = new System.Drawing.Size(45, 20);
            this.udp_stats_in_p1_label.TabIndex = 7;
            this.udp_stats_in_p1_label.Text = "UDP";
            // 
            // http_stats_in_p1_label
            // 
            this.http_stats_in_p1_label.AutoSize = true;
            this.http_stats_in_p1_label.Font = new System.Drawing.Font("Microsoft Sans Serif", 10F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(238)));
            this.http_stats_in_p1_label.Location = new System.Drawing.Point(17, 388);
            this.http_stats_in_p1_label.Margin = new System.Windows.Forms.Padding(3, 0, 3, 10);
            this.http_stats_in_p1_label.Name = "http_stats_in_p1_label";
            this.http_stats_in_p1_label.Size = new System.Drawing.Size(53, 20);
            this.http_stats_in_p1_label.TabIndex = 8;
            this.http_stats_in_p1_label.Text = "HTTP";
            // 
            // icmp_stats_in_p1_label
            // 
            this.icmp_stats_in_p1_label.AutoSize = true;
            this.icmp_stats_in_p1_label.Font = new System.Drawing.Font("Microsoft Sans Serif", 10F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(238)));
            this.icmp_stats_in_p1_label.Location = new System.Drawing.Point(17, 359);
            this.icmp_stats_in_p1_label.Margin = new System.Windows.Forms.Padding(3, 0, 3, 10);
            this.icmp_stats_in_p1_label.Name = "icmp_stats_in_p1_label";
            this.icmp_stats_in_p1_label.Size = new System.Drawing.Size(50, 20);
            this.icmp_stats_in_p1_label.TabIndex = 9;
            this.icmp_stats_in_p1_label.Text = "ICMP";
            // 
            // eth_ii_stats_in_p1
            // 
            this.eth_ii_stats_in_p1.Location = new System.Drawing.Point(83, 208);
            this.eth_ii_stats_in_p1.Margin = new System.Windows.Forms.Padding(3, 2, 3, 2);
            this.eth_ii_stats_in_p1.Name = "eth_ii_stats_in_p1";
            this.eth_ii_stats_in_p1.Size = new System.Drawing.Size(75, 22);
            this.eth_ii_stats_in_p1.TabIndex = 10;
            this.eth_ii_stats_in_p1.Text = "0";
            this.eth_ii_stats_in_p1.TextAlign = System.Windows.Forms.HorizontalAlignment.Center;
            // 
            // arp_stats_in_p1
            // 
            this.arp_stats_in_p1.Location = new System.Drawing.Point(83, 236);
            this.arp_stats_in_p1.Margin = new System.Windows.Forms.Padding(3, 2, 3, 2);
            this.arp_stats_in_p1.Name = "arp_stats_in_p1";
            this.arp_stats_in_p1.Size = new System.Drawing.Size(75, 22);
            this.arp_stats_in_p1.TabIndex = 11;
            this.arp_stats_in_p1.Text = "0";
            this.arp_stats_in_p1.TextAlign = System.Windows.Forms.HorizontalAlignment.Center;
            // 
            // ip_stats_in_p1
            // 
            this.ip_stats_in_p1.Location = new System.Drawing.Point(83, 265);
            this.ip_stats_in_p1.Margin = new System.Windows.Forms.Padding(3, 2, 3, 2);
            this.ip_stats_in_p1.Name = "ip_stats_in_p1";
            this.ip_stats_in_p1.Size = new System.Drawing.Size(75, 22);
            this.ip_stats_in_p1.TabIndex = 12;
            this.ip_stats_in_p1.Text = "0";
            this.ip_stats_in_p1.TextAlign = System.Windows.Forms.HorizontalAlignment.Center;
            // 
            // udp_stats_in_p1
            // 
            this.udp_stats_in_p1.Location = new System.Drawing.Point(83, 325);
            this.udp_stats_in_p1.Margin = new System.Windows.Forms.Padding(3, 2, 3, 2);
            this.udp_stats_in_p1.Name = "udp_stats_in_p1";
            this.udp_stats_in_p1.Size = new System.Drawing.Size(75, 22);
            this.udp_stats_in_p1.TabIndex = 15;
            this.udp_stats_in_p1.Text = "0";
            this.udp_stats_in_p1.TextAlign = System.Windows.Forms.HorizontalAlignment.Center;
            // 
            // tcp_stats_in_p1
            // 
            this.tcp_stats_in_p1.Location = new System.Drawing.Point(83, 295);
            this.tcp_stats_in_p1.Margin = new System.Windows.Forms.Padding(3, 2, 3, 2);
            this.tcp_stats_in_p1.Name = "tcp_stats_in_p1";
            this.tcp_stats_in_p1.Size = new System.Drawing.Size(75, 22);
            this.tcp_stats_in_p1.TabIndex = 14;
            this.tcp_stats_in_p1.Text = "0";
            this.tcp_stats_in_p1.TextAlign = System.Windows.Forms.HorizontalAlignment.Center;
            // 
            // icmp_stats_in_p1
            // 
            this.icmp_stats_in_p1.Location = new System.Drawing.Point(83, 354);
            this.icmp_stats_in_p1.Margin = new System.Windows.Forms.Padding(3, 2, 3, 2);
            this.icmp_stats_in_p1.Name = "icmp_stats_in_p1";
            this.icmp_stats_in_p1.Size = new System.Drawing.Size(75, 22);
            this.icmp_stats_in_p1.TabIndex = 13;
            this.icmp_stats_in_p1.Text = "0";
            this.icmp_stats_in_p1.TextAlign = System.Windows.Forms.HorizontalAlignment.Center;
            // 
            // http_stats_in_p1
            // 
            this.http_stats_in_p1.Location = new System.Drawing.Point(83, 386);
            this.http_stats_in_p1.Margin = new System.Windows.Forms.Padding(3, 2, 3, 2);
            this.http_stats_in_p1.Name = "http_stats_in_p1";
            this.http_stats_in_p1.Size = new System.Drawing.Size(75, 22);
            this.http_stats_in_p1.TabIndex = 16;
            this.http_stats_in_p1.Text = "0";
            this.http_stats_in_p1.TextAlign = System.Windows.Forms.HorizontalAlignment.Center;
            // 
            // http_stats_out_p1
            // 
            this.http_stats_out_p1.Location = new System.Drawing.Point(255, 386);
            this.http_stats_out_p1.Margin = new System.Windows.Forms.Padding(3, 2, 3, 2);
            this.http_stats_out_p1.Name = "http_stats_out_p1";
            this.http_stats_out_p1.Size = new System.Drawing.Size(75, 22);
            this.http_stats_out_p1.TabIndex = 30;
            this.http_stats_out_p1.Text = "0";
            this.http_stats_out_p1.TextAlign = System.Windows.Forms.HorizontalAlignment.Center;
            // 
            // udp_stats_out_p1
            // 
            this.udp_stats_out_p1.Location = new System.Drawing.Point(255, 325);
            this.udp_stats_out_p1.Margin = new System.Windows.Forms.Padding(3, 2, 3, 2);
            this.udp_stats_out_p1.Name = "udp_stats_out_p1";
            this.udp_stats_out_p1.Size = new System.Drawing.Size(75, 22);
            this.udp_stats_out_p1.TabIndex = 29;
            this.udp_stats_out_p1.Text = "0";
            this.udp_stats_out_p1.TextAlign = System.Windows.Forms.HorizontalAlignment.Center;
            // 
            // tcp_stats_out_p1
            // 
            this.tcp_stats_out_p1.Location = new System.Drawing.Point(255, 295);
            this.tcp_stats_out_p1.Margin = new System.Windows.Forms.Padding(3, 2, 3, 2);
            this.tcp_stats_out_p1.Name = "tcp_stats_out_p1";
            this.tcp_stats_out_p1.Size = new System.Drawing.Size(75, 22);
            this.tcp_stats_out_p1.TabIndex = 28;
            this.tcp_stats_out_p1.Text = "0";
            this.tcp_stats_out_p1.TextAlign = System.Windows.Forms.HorizontalAlignment.Center;
            // 
            // icmp_stats_out_p1
            // 
            this.icmp_stats_out_p1.Location = new System.Drawing.Point(255, 354);
            this.icmp_stats_out_p1.Margin = new System.Windows.Forms.Padding(3, 2, 3, 2);
            this.icmp_stats_out_p1.Name = "icmp_stats_out_p1";
            this.icmp_stats_out_p1.Size = new System.Drawing.Size(75, 22);
            this.icmp_stats_out_p1.TabIndex = 27;
            this.icmp_stats_out_p1.Text = "0";
            this.icmp_stats_out_p1.TextAlign = System.Windows.Forms.HorizontalAlignment.Center;
            // 
            // ip_stats_out_p1
            // 
            this.ip_stats_out_p1.Location = new System.Drawing.Point(255, 265);
            this.ip_stats_out_p1.Margin = new System.Windows.Forms.Padding(3, 2, 3, 2);
            this.ip_stats_out_p1.Name = "ip_stats_out_p1";
            this.ip_stats_out_p1.Size = new System.Drawing.Size(75, 22);
            this.ip_stats_out_p1.TabIndex = 26;
            this.ip_stats_out_p1.Text = "0";
            this.ip_stats_out_p1.TextAlign = System.Windows.Forms.HorizontalAlignment.Center;
            // 
            // arp_stats_out_p1
            // 
            this.arp_stats_out_p1.Location = new System.Drawing.Point(255, 236);
            this.arp_stats_out_p1.Margin = new System.Windows.Forms.Padding(3, 2, 3, 2);
            this.arp_stats_out_p1.Name = "arp_stats_out_p1";
            this.arp_stats_out_p1.Size = new System.Drawing.Size(75, 22);
            this.arp_stats_out_p1.TabIndex = 25;
            this.arp_stats_out_p1.Text = "0";
            this.arp_stats_out_p1.TextAlign = System.Windows.Forms.HorizontalAlignment.Center;
            // 
            // eth_ii_stats_out_p1
            // 
            this.eth_ii_stats_out_p1.Location = new System.Drawing.Point(255, 208);
            this.eth_ii_stats_out_p1.Margin = new System.Windows.Forms.Padding(3, 2, 3, 2);
            this.eth_ii_stats_out_p1.Name = "eth_ii_stats_out_p1";
            this.eth_ii_stats_out_p1.Size = new System.Drawing.Size(75, 22);
            this.eth_ii_stats_out_p1.TabIndex = 24;
            this.eth_ii_stats_out_p1.Text = "0";
            this.eth_ii_stats_out_p1.TextAlign = System.Windows.Forms.HorizontalAlignment.Center;
            // 
            // icmp_stats_out_p1_label
            // 
            this.icmp_stats_out_p1_label.AutoSize = true;
            this.icmp_stats_out_p1_label.Font = new System.Drawing.Font("Microsoft Sans Serif", 10F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(238)));
            this.icmp_stats_out_p1_label.Location = new System.Drawing.Point(189, 359);
            this.icmp_stats_out_p1_label.Margin = new System.Windows.Forms.Padding(3, 0, 3, 10);
            this.icmp_stats_out_p1_label.Name = "icmp_stats_out_p1_label";
            this.icmp_stats_out_p1_label.Size = new System.Drawing.Size(50, 20);
            this.icmp_stats_out_p1_label.TabIndex = 23;
            this.icmp_stats_out_p1_label.Text = "ICMP";
            // 
            // http_stats_out_p1_label
            // 
            this.http_stats_out_p1_label.AutoSize = true;
            this.http_stats_out_p1_label.Font = new System.Drawing.Font("Microsoft Sans Serif", 10F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(238)));
            this.http_stats_out_p1_label.Location = new System.Drawing.Point(189, 388);
            this.http_stats_out_p1_label.Margin = new System.Windows.Forms.Padding(3, 0, 3, 10);
            this.http_stats_out_p1_label.Name = "http_stats_out_p1_label";
            this.http_stats_out_p1_label.Size = new System.Drawing.Size(53, 20);
            this.http_stats_out_p1_label.TabIndex = 22;
            this.http_stats_out_p1_label.Text = "HTTP";
            // 
            // udp_stats_out_p1_label
            // 
            this.udp_stats_out_p1_label.AutoSize = true;
            this.udp_stats_out_p1_label.Font = new System.Drawing.Font("Microsoft Sans Serif", 10F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(238)));
            this.udp_stats_out_p1_label.Location = new System.Drawing.Point(189, 327);
            this.udp_stats_out_p1_label.Margin = new System.Windows.Forms.Padding(3, 0, 3, 10);
            this.udp_stats_out_p1_label.Name = "udp_stats_out_p1_label";
            this.udp_stats_out_p1_label.Size = new System.Drawing.Size(45, 20);
            this.udp_stats_out_p1_label.TabIndex = 21;
            this.udp_stats_out_p1_label.Text = "UDP";
            // 
            // tcp_stats_out_p1_label
            // 
            this.tcp_stats_out_p1_label.AutoSize = true;
            this.tcp_stats_out_p1_label.Font = new System.Drawing.Font("Microsoft Sans Serif", 10F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(238)));
            this.tcp_stats_out_p1_label.Location = new System.Drawing.Point(189, 298);
            this.tcp_stats_out_p1_label.Margin = new System.Windows.Forms.Padding(3, 0, 3, 10);
            this.tcp_stats_out_p1_label.Name = "tcp_stats_out_p1_label";
            this.tcp_stats_out_p1_label.Size = new System.Drawing.Size(42, 20);
            this.tcp_stats_out_p1_label.TabIndex = 20;
            this.tcp_stats_out_p1_label.Text = "TCP";
            // 
            // ip_stats_out_p1_label
            // 
            this.ip_stats_out_p1_label.AutoSize = true;
            this.ip_stats_out_p1_label.Font = new System.Drawing.Font("Microsoft Sans Serif", 10F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(238)));
            this.ip_stats_out_p1_label.Location = new System.Drawing.Point(189, 270);
            this.ip_stats_out_p1_label.Margin = new System.Windows.Forms.Padding(3, 0, 3, 10);
            this.ip_stats_out_p1_label.Name = "ip_stats_out_p1_label";
            this.ip_stats_out_p1_label.Size = new System.Drawing.Size(24, 20);
            this.ip_stats_out_p1_label.TabIndex = 19;
            this.ip_stats_out_p1_label.Text = "IP";
            // 
            // arp_stats_out_p1_label
            // 
            this.arp_stats_out_p1_label.AutoSize = true;
            this.arp_stats_out_p1_label.Font = new System.Drawing.Font("Microsoft Sans Serif", 10F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(238)));
            this.arp_stats_out_p1_label.Location = new System.Drawing.Point(189, 238);
            this.arp_stats_out_p1_label.Margin = new System.Windows.Forms.Padding(3, 0, 3, 10);
            this.arp_stats_out_p1_label.Name = "arp_stats_out_p1_label";
            this.arp_stats_out_p1_label.Size = new System.Drawing.Size(43, 20);
            this.arp_stats_out_p1_label.TabIndex = 18;
            this.arp_stats_out_p1_label.Text = "ARP";
            // 
            // eth_ii_stats_out_p1_label
            // 
            this.eth_ii_stats_out_p1_label.AutoSize = true;
            this.eth_ii_stats_out_p1_label.Font = new System.Drawing.Font("Microsoft Sans Serif", 10F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(238)));
            this.eth_ii_stats_out_p1_label.Location = new System.Drawing.Point(189, 208);
            this.eth_ii_stats_out_p1_label.Margin = new System.Windows.Forms.Padding(3, 0, 3, 10);
            this.eth_ii_stats_out_p1_label.Name = "eth_ii_stats_out_p1_label";
            this.eth_ii_stats_out_p1_label.Size = new System.Drawing.Size(56, 20);
            this.eth_ii_stats_out_p1_label.TabIndex = 17;
            this.eth_ii_stats_out_p1_label.Text = "ETH II";
            // 
            // port1_in_label
            // 
            this.port1_in_label.AutoSize = true;
            this.port1_in_label.Font = new System.Drawing.Font("Microsoft Sans Serif", 12F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(238)));
            this.port1_in_label.Location = new System.Drawing.Point(65, 158);
            this.port1_in_label.Name = "port1_in_label";
            this.port1_in_label.Size = new System.Drawing.Size(33, 25);
            this.port1_in_label.TabIndex = 31;
            this.port1_in_label.Text = "IN";
            // 
            // port1_out_label
            // 
            this.port1_out_label.AutoSize = true;
            this.port1_out_label.Font = new System.Drawing.Font("Microsoft Sans Serif", 12F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(238)));
            this.port1_out_label.Location = new System.Drawing.Point(232, 158);
            this.port1_out_label.Name = "port1_out_label";
            this.port1_out_label.Size = new System.Drawing.Size(58, 25);
            this.port1_out_label.TabIndex = 32;
            this.port1_out_label.Text = "OUT";
            // 
            // port2_out_label
            // 
            this.port2_out_label.AutoSize = true;
            this.port2_out_label.Font = new System.Drawing.Font("Microsoft Sans Serif", 12F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(238)));
            this.port2_out_label.Location = new System.Drawing.Point(1159, 158);
            this.port2_out_label.Name = "port2_out_label";
            this.port2_out_label.Size = new System.Drawing.Size(58, 25);
            this.port2_out_label.TabIndex = 62;
            this.port2_out_label.Text = "OUT";
            // 
            // port2_in_label
            // 
            this.port2_in_label.AutoSize = true;
            this.port2_in_label.Font = new System.Drawing.Font("Microsoft Sans Serif", 12F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(238)));
            this.port2_in_label.Location = new System.Drawing.Point(991, 158);
            this.port2_in_label.Name = "port2_in_label";
            this.port2_in_label.Size = new System.Drawing.Size(33, 25);
            this.port2_in_label.TabIndex = 61;
            this.port2_in_label.Text = "IN";
            // 
            // http_stats_out_p2
            // 
            this.http_stats_out_p2.Location = new System.Drawing.Point(1180, 386);
            this.http_stats_out_p2.Margin = new System.Windows.Forms.Padding(3, 2, 3, 2);
            this.http_stats_out_p2.Name = "http_stats_out_p2";
            this.http_stats_out_p2.Size = new System.Drawing.Size(75, 22);
            this.http_stats_out_p2.TabIndex = 60;
            this.http_stats_out_p2.Text = "0";
            this.http_stats_out_p2.TextAlign = System.Windows.Forms.HorizontalAlignment.Center;
            // 
            // udp_stats_out_p2
            // 
            this.udp_stats_out_p2.Location = new System.Drawing.Point(1180, 325);
            this.udp_stats_out_p2.Margin = new System.Windows.Forms.Padding(3, 2, 3, 2);
            this.udp_stats_out_p2.Name = "udp_stats_out_p2";
            this.udp_stats_out_p2.Size = new System.Drawing.Size(75, 22);
            this.udp_stats_out_p2.TabIndex = 59;
            this.udp_stats_out_p2.Text = "0";
            this.udp_stats_out_p2.TextAlign = System.Windows.Forms.HorizontalAlignment.Center;
            // 
            // tcp_stats_out_p2
            // 
            this.tcp_stats_out_p2.Location = new System.Drawing.Point(1180, 295);
            this.tcp_stats_out_p2.Margin = new System.Windows.Forms.Padding(3, 2, 3, 2);
            this.tcp_stats_out_p2.Name = "tcp_stats_out_p2";
            this.tcp_stats_out_p2.Size = new System.Drawing.Size(75, 22);
            this.tcp_stats_out_p2.TabIndex = 58;
            this.tcp_stats_out_p2.Text = "0";
            this.tcp_stats_out_p2.TextAlign = System.Windows.Forms.HorizontalAlignment.Center;
            // 
            // icmp_stats_out_p2
            // 
            this.icmp_stats_out_p2.Location = new System.Drawing.Point(1180, 354);
            this.icmp_stats_out_p2.Margin = new System.Windows.Forms.Padding(3, 2, 3, 2);
            this.icmp_stats_out_p2.Name = "icmp_stats_out_p2";
            this.icmp_stats_out_p2.Size = new System.Drawing.Size(75, 22);
            this.icmp_stats_out_p2.TabIndex = 57;
            this.icmp_stats_out_p2.Text = "0";
            this.icmp_stats_out_p2.TextAlign = System.Windows.Forms.HorizontalAlignment.Center;
            // 
            // ip_stats_out_p2
            // 
            this.ip_stats_out_p2.Location = new System.Drawing.Point(1180, 265);
            this.ip_stats_out_p2.Margin = new System.Windows.Forms.Padding(3, 2, 3, 2);
            this.ip_stats_out_p2.Name = "ip_stats_out_p2";
            this.ip_stats_out_p2.Size = new System.Drawing.Size(75, 22);
            this.ip_stats_out_p2.TabIndex = 56;
            this.ip_stats_out_p2.Text = "0";
            this.ip_stats_out_p2.TextAlign = System.Windows.Forms.HorizontalAlignment.Center;
            // 
            // arp_stats_out_p2
            // 
            this.arp_stats_out_p2.Location = new System.Drawing.Point(1180, 236);
            this.arp_stats_out_p2.Margin = new System.Windows.Forms.Padding(3, 2, 3, 2);
            this.arp_stats_out_p2.Name = "arp_stats_out_p2";
            this.arp_stats_out_p2.Size = new System.Drawing.Size(75, 22);
            this.arp_stats_out_p2.TabIndex = 55;
            this.arp_stats_out_p2.Text = "0";
            this.arp_stats_out_p2.TextAlign = System.Windows.Forms.HorizontalAlignment.Center;
            // 
            // eth_ii_stats_out_p2
            // 
            this.eth_ii_stats_out_p2.Location = new System.Drawing.Point(1180, 208);
            this.eth_ii_stats_out_p2.Margin = new System.Windows.Forms.Padding(3, 2, 3, 2);
            this.eth_ii_stats_out_p2.Name = "eth_ii_stats_out_p2";
            this.eth_ii_stats_out_p2.Size = new System.Drawing.Size(75, 22);
            this.eth_ii_stats_out_p2.TabIndex = 54;
            this.eth_ii_stats_out_p2.Text = "0";
            this.eth_ii_stats_out_p2.TextAlign = System.Windows.Forms.HorizontalAlignment.Center;
            // 
            // icmp_stats_out_p2_label
            // 
            this.icmp_stats_out_p2_label.AutoSize = true;
            this.icmp_stats_out_p2_label.Font = new System.Drawing.Font("Microsoft Sans Serif", 10F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(238)));
            this.icmp_stats_out_p2_label.Location = new System.Drawing.Point(1115, 359);
            this.icmp_stats_out_p2_label.Margin = new System.Windows.Forms.Padding(3, 0, 3, 10);
            this.icmp_stats_out_p2_label.Name = "icmp_stats_out_p2_label";
            this.icmp_stats_out_p2_label.Size = new System.Drawing.Size(50, 20);
            this.icmp_stats_out_p2_label.TabIndex = 53;
            this.icmp_stats_out_p2_label.Text = "ICMP";
            // 
            // http_stats_out_p2_label
            // 
            this.http_stats_out_p2_label.AutoSize = true;
            this.http_stats_out_p2_label.Font = new System.Drawing.Font("Microsoft Sans Serif", 10F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(238)));
            this.http_stats_out_p2_label.Location = new System.Drawing.Point(1115, 388);
            this.http_stats_out_p2_label.Margin = new System.Windows.Forms.Padding(3, 0, 3, 10);
            this.http_stats_out_p2_label.Name = "http_stats_out_p2_label";
            this.http_stats_out_p2_label.Size = new System.Drawing.Size(53, 20);
            this.http_stats_out_p2_label.TabIndex = 52;
            this.http_stats_out_p2_label.Text = "HTTP";
            // 
            // udp_stats_out_p2_label
            // 
            this.udp_stats_out_p2_label.AutoSize = true;
            this.udp_stats_out_p2_label.Font = new System.Drawing.Font("Microsoft Sans Serif", 10F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(238)));
            this.udp_stats_out_p2_label.Location = new System.Drawing.Point(1115, 327);
            this.udp_stats_out_p2_label.Margin = new System.Windows.Forms.Padding(3, 0, 3, 10);
            this.udp_stats_out_p2_label.Name = "udp_stats_out_p2_label";
            this.udp_stats_out_p2_label.Size = new System.Drawing.Size(45, 20);
            this.udp_stats_out_p2_label.TabIndex = 51;
            this.udp_stats_out_p2_label.Text = "UDP";
            // 
            // tcp_stats_out_p2_label
            // 
            this.tcp_stats_out_p2_label.AutoSize = true;
            this.tcp_stats_out_p2_label.Font = new System.Drawing.Font("Microsoft Sans Serif", 10F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(238)));
            this.tcp_stats_out_p2_label.Location = new System.Drawing.Point(1115, 298);
            this.tcp_stats_out_p2_label.Margin = new System.Windows.Forms.Padding(3, 0, 3, 10);
            this.tcp_stats_out_p2_label.Name = "tcp_stats_out_p2_label";
            this.tcp_stats_out_p2_label.Size = new System.Drawing.Size(42, 20);
            this.tcp_stats_out_p2_label.TabIndex = 50;
            this.tcp_stats_out_p2_label.Text = "TCP";
            // 
            // ip_stats_out_p2_label
            // 
            this.ip_stats_out_p2_label.AutoSize = true;
            this.ip_stats_out_p2_label.Font = new System.Drawing.Font("Microsoft Sans Serif", 10F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(238)));
            this.ip_stats_out_p2_label.Location = new System.Drawing.Point(1115, 270);
            this.ip_stats_out_p2_label.Margin = new System.Windows.Forms.Padding(3, 0, 3, 10);
            this.ip_stats_out_p2_label.Name = "ip_stats_out_p2_label";
            this.ip_stats_out_p2_label.Size = new System.Drawing.Size(24, 20);
            this.ip_stats_out_p2_label.TabIndex = 49;
            this.ip_stats_out_p2_label.Text = "IP";
            // 
            // arp_stats_out_p2_label
            // 
            this.arp_stats_out_p2_label.AutoSize = true;
            this.arp_stats_out_p2_label.Font = new System.Drawing.Font("Microsoft Sans Serif", 10F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(238)));
            this.arp_stats_out_p2_label.Location = new System.Drawing.Point(1115, 238);
            this.arp_stats_out_p2_label.Margin = new System.Windows.Forms.Padding(3, 0, 3, 10);
            this.arp_stats_out_p2_label.Name = "arp_stats_out_p2_label";
            this.arp_stats_out_p2_label.Size = new System.Drawing.Size(43, 20);
            this.arp_stats_out_p2_label.TabIndex = 48;
            this.arp_stats_out_p2_label.Text = "ARP";
            // 
            // eth_ii_stats_out_p2_label
            // 
            this.eth_ii_stats_out_p2_label.AutoSize = true;
            this.eth_ii_stats_out_p2_label.Font = new System.Drawing.Font("Microsoft Sans Serif", 10F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(238)));
            this.eth_ii_stats_out_p2_label.Location = new System.Drawing.Point(1115, 208);
            this.eth_ii_stats_out_p2_label.Margin = new System.Windows.Forms.Padding(3, 0, 3, 10);
            this.eth_ii_stats_out_p2_label.Name = "eth_ii_stats_out_p2_label";
            this.eth_ii_stats_out_p2_label.Size = new System.Drawing.Size(56, 20);
            this.eth_ii_stats_out_p2_label.TabIndex = 47;
            this.eth_ii_stats_out_p2_label.Text = "ETH II";
            // 
            // http_stats_in_p2
            // 
            this.http_stats_in_p2.Location = new System.Drawing.Point(1008, 386);
            this.http_stats_in_p2.Margin = new System.Windows.Forms.Padding(3, 2, 3, 2);
            this.http_stats_in_p2.Name = "http_stats_in_p2";
            this.http_stats_in_p2.Size = new System.Drawing.Size(75, 22);
            this.http_stats_in_p2.TabIndex = 46;
            this.http_stats_in_p2.Text = "0";
            this.http_stats_in_p2.TextAlign = System.Windows.Forms.HorizontalAlignment.Center;
            // 
            // udp_stats_in_p2
            // 
            this.udp_stats_in_p2.Location = new System.Drawing.Point(1008, 325);
            this.udp_stats_in_p2.Margin = new System.Windows.Forms.Padding(3, 2, 3, 2);
            this.udp_stats_in_p2.Name = "udp_stats_in_p2";
            this.udp_stats_in_p2.Size = new System.Drawing.Size(75, 22);
            this.udp_stats_in_p2.TabIndex = 45;
            this.udp_stats_in_p2.Text = "0";
            this.udp_stats_in_p2.TextAlign = System.Windows.Forms.HorizontalAlignment.Center;
            // 
            // tcp_stats_in_p2
            // 
            this.tcp_stats_in_p2.Location = new System.Drawing.Point(1008, 295);
            this.tcp_stats_in_p2.Margin = new System.Windows.Forms.Padding(3, 2, 3, 2);
            this.tcp_stats_in_p2.Name = "tcp_stats_in_p2";
            this.tcp_stats_in_p2.Size = new System.Drawing.Size(75, 22);
            this.tcp_stats_in_p2.TabIndex = 44;
            this.tcp_stats_in_p2.Text = "0";
            this.tcp_stats_in_p2.TextAlign = System.Windows.Forms.HorizontalAlignment.Center;
            // 
            // icmp_stats_in_p2
            // 
            this.icmp_stats_in_p2.Location = new System.Drawing.Point(1008, 354);
            this.icmp_stats_in_p2.Margin = new System.Windows.Forms.Padding(3, 2, 3, 2);
            this.icmp_stats_in_p2.Name = "icmp_stats_in_p2";
            this.icmp_stats_in_p2.Size = new System.Drawing.Size(75, 22);
            this.icmp_stats_in_p2.TabIndex = 43;
            this.icmp_stats_in_p2.Text = "0";
            this.icmp_stats_in_p2.TextAlign = System.Windows.Forms.HorizontalAlignment.Center;
            // 
            // ip_stats_in_p2
            // 
            this.ip_stats_in_p2.Location = new System.Drawing.Point(1008, 265);
            this.ip_stats_in_p2.Margin = new System.Windows.Forms.Padding(3, 2, 3, 2);
            this.ip_stats_in_p2.Name = "ip_stats_in_p2";
            this.ip_stats_in_p2.Size = new System.Drawing.Size(75, 22);
            this.ip_stats_in_p2.TabIndex = 42;
            this.ip_stats_in_p2.Text = "0";
            this.ip_stats_in_p2.TextAlign = System.Windows.Forms.HorizontalAlignment.Center;
            // 
            // arp_stats_in_p2
            // 
            this.arp_stats_in_p2.Location = new System.Drawing.Point(1008, 236);
            this.arp_stats_in_p2.Margin = new System.Windows.Forms.Padding(3, 2, 3, 2);
            this.arp_stats_in_p2.Name = "arp_stats_in_p2";
            this.arp_stats_in_p2.Size = new System.Drawing.Size(75, 22);
            this.arp_stats_in_p2.TabIndex = 41;
            this.arp_stats_in_p2.Text = "0";
            this.arp_stats_in_p2.TextAlign = System.Windows.Forms.HorizontalAlignment.Center;
            // 
            // eth_ii_stats_in_p2
            // 
            this.eth_ii_stats_in_p2.Location = new System.Drawing.Point(1008, 208);
            this.eth_ii_stats_in_p2.Margin = new System.Windows.Forms.Padding(3, 2, 3, 2);
            this.eth_ii_stats_in_p2.Name = "eth_ii_stats_in_p2";
            this.eth_ii_stats_in_p2.Size = new System.Drawing.Size(75, 22);
            this.eth_ii_stats_in_p2.TabIndex = 40;
            this.eth_ii_stats_in_p2.Text = "0";
            this.eth_ii_stats_in_p2.TextAlign = System.Windows.Forms.HorizontalAlignment.Center;
            // 
            // icmp_stats_in_p2_label
            // 
            this.icmp_stats_in_p2_label.AutoSize = true;
            this.icmp_stats_in_p2_label.Font = new System.Drawing.Font("Microsoft Sans Serif", 10F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(238)));
            this.icmp_stats_in_p2_label.Location = new System.Drawing.Point(943, 359);
            this.icmp_stats_in_p2_label.Margin = new System.Windows.Forms.Padding(3, 0, 3, 10);
            this.icmp_stats_in_p2_label.Name = "icmp_stats_in_p2_label";
            this.icmp_stats_in_p2_label.Size = new System.Drawing.Size(50, 20);
            this.icmp_stats_in_p2_label.TabIndex = 39;
            this.icmp_stats_in_p2_label.Text = "ICMP";
            // 
            // http_stats_in_p2_label
            // 
            this.http_stats_in_p2_label.AutoSize = true;
            this.http_stats_in_p2_label.Font = new System.Drawing.Font("Microsoft Sans Serif", 10F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(238)));
            this.http_stats_in_p2_label.Location = new System.Drawing.Point(943, 388);
            this.http_stats_in_p2_label.Margin = new System.Windows.Forms.Padding(3, 0, 3, 10);
            this.http_stats_in_p2_label.Name = "http_stats_in_p2_label";
            this.http_stats_in_p2_label.Size = new System.Drawing.Size(53, 20);
            this.http_stats_in_p2_label.TabIndex = 38;
            this.http_stats_in_p2_label.Text = "HTTP";
            // 
            // udp_stats_in_p2_label
            // 
            this.udp_stats_in_p2_label.AutoSize = true;
            this.udp_stats_in_p2_label.Font = new System.Drawing.Font("Microsoft Sans Serif", 10F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(238)));
            this.udp_stats_in_p2_label.Location = new System.Drawing.Point(943, 327);
            this.udp_stats_in_p2_label.Margin = new System.Windows.Forms.Padding(3, 0, 3, 10);
            this.udp_stats_in_p2_label.Name = "udp_stats_in_p2_label";
            this.udp_stats_in_p2_label.Size = new System.Drawing.Size(45, 20);
            this.udp_stats_in_p2_label.TabIndex = 37;
            this.udp_stats_in_p2_label.Text = "UDP";
            // 
            // tcp_stats_in_p2_label
            // 
            this.tcp_stats_in_p2_label.AutoSize = true;
            this.tcp_stats_in_p2_label.Font = new System.Drawing.Font("Microsoft Sans Serif", 10F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(238)));
            this.tcp_stats_in_p2_label.Location = new System.Drawing.Point(943, 298);
            this.tcp_stats_in_p2_label.Margin = new System.Windows.Forms.Padding(3, 0, 3, 10);
            this.tcp_stats_in_p2_label.Name = "tcp_stats_in_p2_label";
            this.tcp_stats_in_p2_label.Size = new System.Drawing.Size(42, 20);
            this.tcp_stats_in_p2_label.TabIndex = 36;
            this.tcp_stats_in_p2_label.Text = "TCP";
            // 
            // ip_stats_in_p2_label
            // 
            this.ip_stats_in_p2_label.AutoSize = true;
            this.ip_stats_in_p2_label.Font = new System.Drawing.Font("Microsoft Sans Serif", 10F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(238)));
            this.ip_stats_in_p2_label.Location = new System.Drawing.Point(943, 270);
            this.ip_stats_in_p2_label.Margin = new System.Windows.Forms.Padding(3, 0, 3, 10);
            this.ip_stats_in_p2_label.Name = "ip_stats_in_p2_label";
            this.ip_stats_in_p2_label.Size = new System.Drawing.Size(24, 20);
            this.ip_stats_in_p2_label.TabIndex = 35;
            this.ip_stats_in_p2_label.Text = "IP";
            // 
            // arp_stats_in_p2_label
            // 
            this.arp_stats_in_p2_label.AutoSize = true;
            this.arp_stats_in_p2_label.Font = new System.Drawing.Font("Microsoft Sans Serif", 10F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(238)));
            this.arp_stats_in_p2_label.Location = new System.Drawing.Point(943, 238);
            this.arp_stats_in_p2_label.Margin = new System.Windows.Forms.Padding(3, 0, 3, 10);
            this.arp_stats_in_p2_label.Name = "arp_stats_in_p2_label";
            this.arp_stats_in_p2_label.Size = new System.Drawing.Size(43, 20);
            this.arp_stats_in_p2_label.TabIndex = 34;
            this.arp_stats_in_p2_label.Text = "ARP";
            // 
            // eth_ii_stats_in_p2_label
            // 
            this.eth_ii_stats_in_p2_label.AutoSize = true;
            this.eth_ii_stats_in_p2_label.Font = new System.Drawing.Font("Microsoft Sans Serif", 10F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(238)));
            this.eth_ii_stats_in_p2_label.Location = new System.Drawing.Point(943, 208);
            this.eth_ii_stats_in_p2_label.Margin = new System.Windows.Forms.Padding(3, 0, 3, 10);
            this.eth_ii_stats_in_p2_label.Name = "eth_ii_stats_in_p2_label";
            this.eth_ii_stats_in_p2_label.Size = new System.Drawing.Size(56, 20);
            this.eth_ii_stats_in_p2_label.TabIndex = 33;
            this.eth_ii_stats_in_p2_label.Text = "ETH II";
            // 
            // port2_label
            // 
            this.port2_label.AutoSize = true;
            this.port2_label.Font = new System.Drawing.Font("Microsoft Sans Serif", 14F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(238)));
            this.port2_label.Location = new System.Drawing.Point(1028, 114);
            this.port2_label.Name = "port2_label";
            this.port2_label.Size = new System.Drawing.Size(143, 29);
            this.port2_label.TabIndex = 63;
            this.port2_label.Text = "Port 2 stats";
            // 
            // mac_table_label
            // 
            this.mac_table_label.AutoSize = true;
            this.mac_table_label.Font = new System.Drawing.Font("Microsoft Sans Serif", 14F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(238)));
            this.mac_table_label.Location = new System.Drawing.Point(571, 114);
            this.mac_table_label.Name = "mac_table_label";
            this.mac_table_label.Size = new System.Drawing.Size(133, 29);
            this.mac_table_label.TabIndex = 64;
            this.mac_table_label.Text = "MAC table";
            // 
            // mac_table
            // 
            this.mac_table.AllowUserToAddRows = false;
            this.mac_table.AllowUserToDeleteRows = false;
            this.mac_table.AllowUserToResizeColumns = false;
            this.mac_table.AllowUserToResizeRows = false;
            this.mac_table.AutoSizeColumnsMode = System.Windows.Forms.DataGridViewAutoSizeColumnsMode.Fill;
            this.mac_table.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.mac_table.Location = new System.Drawing.Point(403, 208);
            this.mac_table.Margin = new System.Windows.Forms.Padding(4);
            this.mac_table.Name = "mac_table";
            this.mac_table.RowHeadersWidth = 51;
            this.mac_table.Size = new System.Drawing.Size(469, 199);
            this.mac_table.TabIndex = 65;
            // 
            // interface_p1
            // 
            this.interface_p1.FormattingEnabled = true;
            this.interface_p1.Location = new System.Drawing.Point(21, 50);
            this.interface_p1.Margin = new System.Windows.Forms.Padding(4);
            this.interface_p1.Name = "interface_p1";
            this.interface_p1.Size = new System.Drawing.Size(308, 24);
            this.interface_p1.TabIndex = 66;
            this.interface_p1.Text = "-- Not Selected --";
            this.interface_p1.SelectedIndexChanged += new System.EventHandler(this.interface_p1_SelectedIndexChanged);
            // 
            // interface_p2
            // 
            this.interface_p2.FormattingEnabled = true;
            this.interface_p2.Location = new System.Drawing.Point(947, 50);
            this.interface_p2.Margin = new System.Windows.Forms.Padding(4);
            this.interface_p2.Name = "interface_p2";
            this.interface_p2.Size = new System.Drawing.Size(308, 24);
            this.interface_p2.TabIndex = 67;
            this.interface_p2.Text = "-- Not Selected --";
            this.interface_p2.SelectedIndexChanged += new System.EventHandler(this.interface_p2_SelectedIndexChanged);
            // 
            // interface_p1_label
            // 
            this.interface_p1_label.AutoSize = true;
            this.interface_p1_label.Font = new System.Drawing.Font("Microsoft Sans Serif", 15F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(238)));
            this.interface_p1_label.Location = new System.Drawing.Point(15, 11);
            this.interface_p1_label.Name = "interface_p1_label";
            this.interface_p1_label.Size = new System.Drawing.Size(142, 29);
            this.interface_p1_label.TabIndex = 68;
            this.interface_p1_label.Text = "Interface 1";
            // 
            // interface_p2_label
            // 
            this.interface_p2_label.AutoSize = true;
            this.interface_p2_label.Font = new System.Drawing.Font("Microsoft Sans Serif", 15F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(238)));
            this.interface_p2_label.Location = new System.Drawing.Point(1104, 11);
            this.interface_p2_label.Name = "interface_p2_label";
            this.interface_p2_label.Size = new System.Drawing.Size(142, 29);
            this.interface_p2_label.TabIndex = 69;
            this.interface_p2_label.Text = "Interface 2";
            // 
            // switch_label
            // 
            this.switch_label.AutoSize = true;
            this.switch_label.Font = new System.Drawing.Font("Microsoft Sans Serif", 15F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(238)));
            this.switch_label.Location = new System.Drawing.Point(575, 9);
            this.switch_label.Name = "switch_label";
            this.switch_label.Size = new System.Drawing.Size(117, 29);
            this.switch_label.TabIndex = 77;
            this.switch_label.Text = "SWITCH";
            // 
            // mac_counter
            // 
            this.mac_counter.Location = new System.Drawing.Point(602, 156);
            this.mac_counter.Margin = new System.Windows.Forms.Padding(3, 2, 3, 2);
            this.mac_counter.Name = "mac_counter";
            this.mac_counter.Size = new System.Drawing.Size(73, 22);
            this.mac_counter.TabIndex = 78;
            this.mac_counter.Text = "60";
            this.mac_counter.TextAlign = System.Windows.Forms.HorizontalAlignment.Center;
            // 
            // mac_counter_update_button
            // 
            this.mac_counter_update_button.Location = new System.Drawing.Point(682, 155);
            this.mac_counter_update_button.Margin = new System.Windows.Forms.Padding(3, 2, 3, 2);
            this.mac_counter_update_button.Name = "mac_counter_update_button";
            this.mac_counter_update_button.Size = new System.Drawing.Size(75, 27);
            this.mac_counter_update_button.TabIndex = 80;
            this.mac_counter_update_button.Text = "Update";
            this.mac_counter_update_button.UseVisualStyleBackColor = true;
            this.mac_counter_update_button.Click += new System.EventHandler(this.mac_counter_update_button_Click);
            // 
            // mac_counter_label
            // 
            this.mac_counter_label.AutoSize = true;
            this.mac_counter_label.Font = new System.Drawing.Font("Microsoft Sans Serif", 12F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(238)));
            this.mac_counter_label.Location = new System.Drawing.Point(505, 158);
            this.mac_counter_label.Name = "mac_counter_label";
            this.mac_counter_label.Size = new System.Drawing.Size(89, 25);
            this.mac_counter_label.TabIndex = 83;
            this.mac_counter_label.Text = "Counter";
            // 
            // reset_stats_button
            // 
            this.reset_stats_button.Location = new System.Drawing.Point(403, 469);
            this.reset_stats_button.Margin = new System.Windows.Forms.Padding(3, 2, 3, 2);
            this.reset_stats_button.Name = "reset_stats_button";
            this.reset_stats_button.Size = new System.Drawing.Size(123, 26);
            this.reset_stats_button.TabIndex = 84;
            this.reset_stats_button.Text = "Reset stats";
            this.reset_stats_button.UseVisualStyleBackColor = true;
            this.reset_stats_button.Click += new System.EventHandler(this.reset_stats_button_Click);
            // 
            // reset_mac_button
            // 
            this.reset_mac_button.Location = new System.Drawing.Point(569, 469);
            this.reset_mac_button.Margin = new System.Windows.Forms.Padding(3, 2, 3, 2);
            this.reset_mac_button.Name = "reset_mac_button";
            this.reset_mac_button.Size = new System.Drawing.Size(123, 26);
            this.reset_mac_button.TabIndex = 85;
            this.reset_mac_button.Text = "Reset MAC";
            this.reset_mac_button.UseVisualStyleBackColor = true;
            this.reset_mac_button.Click += new System.EventHandler(this.reset_mac_button_Click);
            // 
            // Form1
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(8F, 16F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(1271, 554);
            this.Controls.Add(this.reset_mac_button);
            this.Controls.Add(this.reset_stats_button);
            this.Controls.Add(this.mac_counter_label);
            this.Controls.Add(this.mac_counter_update_button);
            this.Controls.Add(this.mac_counter);
            this.Controls.Add(this.switch_label);
            this.Controls.Add(this.interface_p2_label);
            this.Controls.Add(this.interface_p1_label);
            this.Controls.Add(this.interface_p2);
            this.Controls.Add(this.interface_p1);
            this.Controls.Add(this.mac_table);
            this.Controls.Add(this.mac_table_label);
            this.Controls.Add(this.port2_label);
            this.Controls.Add(this.port2_out_label);
            this.Controls.Add(this.port2_in_label);
            this.Controls.Add(this.http_stats_out_p2);
            this.Controls.Add(this.udp_stats_out_p2);
            this.Controls.Add(this.tcp_stats_out_p2);
            this.Controls.Add(this.icmp_stats_out_p2);
            this.Controls.Add(this.ip_stats_out_p2);
            this.Controls.Add(this.arp_stats_out_p2);
            this.Controls.Add(this.eth_ii_stats_out_p2);
            this.Controls.Add(this.icmp_stats_out_p2_label);
            this.Controls.Add(this.http_stats_out_p2_label);
            this.Controls.Add(this.udp_stats_out_p2_label);
            this.Controls.Add(this.tcp_stats_out_p2_label);
            this.Controls.Add(this.ip_stats_out_p2_label);
            this.Controls.Add(this.arp_stats_out_p2_label);
            this.Controls.Add(this.eth_ii_stats_out_p2_label);
            this.Controls.Add(this.http_stats_in_p2);
            this.Controls.Add(this.udp_stats_in_p2);
            this.Controls.Add(this.tcp_stats_in_p2);
            this.Controls.Add(this.icmp_stats_in_p2);
            this.Controls.Add(this.ip_stats_in_p2);
            this.Controls.Add(this.arp_stats_in_p2);
            this.Controls.Add(this.eth_ii_stats_in_p2);
            this.Controls.Add(this.icmp_stats_in_p2_label);
            this.Controls.Add(this.http_stats_in_p2_label);
            this.Controls.Add(this.udp_stats_in_p2_label);
            this.Controls.Add(this.tcp_stats_in_p2_label);
            this.Controls.Add(this.ip_stats_in_p2_label);
            this.Controls.Add(this.arp_stats_in_p2_label);
            this.Controls.Add(this.eth_ii_stats_in_p2_label);
            this.Controls.Add(this.port1_out_label);
            this.Controls.Add(this.port1_in_label);
            this.Controls.Add(this.http_stats_out_p1);
            this.Controls.Add(this.udp_stats_out_p1);
            this.Controls.Add(this.tcp_stats_out_p1);
            this.Controls.Add(this.icmp_stats_out_p1);
            this.Controls.Add(this.ip_stats_out_p1);
            this.Controls.Add(this.arp_stats_out_p1);
            this.Controls.Add(this.eth_ii_stats_out_p1);
            this.Controls.Add(this.icmp_stats_out_p1_label);
            this.Controls.Add(this.http_stats_out_p1_label);
            this.Controls.Add(this.udp_stats_out_p1_label);
            this.Controls.Add(this.tcp_stats_out_p1_label);
            this.Controls.Add(this.ip_stats_out_p1_label);
            this.Controls.Add(this.arp_stats_out_p1_label);
            this.Controls.Add(this.eth_ii_stats_out_p1_label);
            this.Controls.Add(this.http_stats_in_p1);
            this.Controls.Add(this.udp_stats_in_p1);
            this.Controls.Add(this.tcp_stats_in_p1);
            this.Controls.Add(this.icmp_stats_in_p1);
            this.Controls.Add(this.ip_stats_in_p1);
            this.Controls.Add(this.arp_stats_in_p1);
            this.Controls.Add(this.eth_ii_stats_in_p1);
            this.Controls.Add(this.icmp_stats_in_p1_label);
            this.Controls.Add(this.http_stats_in_p1_label);
            this.Controls.Add(this.udp_stats_in_p1_label);
            this.Controls.Add(this.tcp_stats_in_p1_label);
            this.Controls.Add(this.ip_stats_in_p1_label);
            this.Controls.Add(this.arp_stats_in_p1_label);
            this.Controls.Add(this.eth_ii_stats_in_p1_label);
            this.Controls.Add(this.port1_label);
            this.Controls.Add(this.stop_button);
            this.Controls.Add(this.start_button);
            this.Margin = new System.Windows.Forms.Padding(4);
            this.Name = "Form1";
            this.Text = "Switch";
            this.Load += new System.EventHandler(this.Form1_Load);
            ((System.ComponentModel.ISupportInitialize)(this.mac_table)).EndInit();
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.Button start_button;
        private System.Windows.Forms.Button stop_button;
        private System.Windows.Forms.Label port1_label;
        private System.Windows.Forms.Label eth_ii_stats_in_p1_label;
        private System.Windows.Forms.Label arp_stats_in_p1_label;
        private System.Windows.Forms.Label ip_stats_in_p1_label;
        private System.Windows.Forms.Label tcp_stats_in_p1_label;
        private System.Windows.Forms.Label udp_stats_in_p1_label;
        private System.Windows.Forms.Label http_stats_in_p1_label;
        private System.Windows.Forms.Label icmp_stats_in_p1_label;
        private System.Windows.Forms.TextBox eth_ii_stats_in_p1;
        private System.Windows.Forms.TextBox arp_stats_in_p1;
        private System.Windows.Forms.TextBox ip_stats_in_p1;
        private System.Windows.Forms.TextBox udp_stats_in_p1;
        private System.Windows.Forms.TextBox tcp_stats_in_p1;
        private System.Windows.Forms.TextBox icmp_stats_in_p1;
        private System.Windows.Forms.TextBox http_stats_in_p1;
        private System.Windows.Forms.TextBox http_stats_out_p1;
        private System.Windows.Forms.TextBox udp_stats_out_p1;
        private System.Windows.Forms.TextBox tcp_stats_out_p1;
        private System.Windows.Forms.TextBox icmp_stats_out_p1;
        private System.Windows.Forms.TextBox ip_stats_out_p1;
        private System.Windows.Forms.TextBox arp_stats_out_p1;
        private System.Windows.Forms.TextBox eth_ii_stats_out_p1;
        private System.Windows.Forms.Label icmp_stats_out_p1_label;
        private System.Windows.Forms.Label http_stats_out_p1_label;
        private System.Windows.Forms.Label udp_stats_out_p1_label;
        private System.Windows.Forms.Label tcp_stats_out_p1_label;
        private System.Windows.Forms.Label ip_stats_out_p1_label;
        private System.Windows.Forms.Label arp_stats_out_p1_label;
        private System.Windows.Forms.Label eth_ii_stats_out_p1_label;
        private Label port1_in_label;
        private Label port1_out_label;
        private Label port2_out_label;
        private Label port2_in_label;
        private TextBox http_stats_out_p2;
        private TextBox udp_stats_out_p2;
        private TextBox tcp_stats_out_p2;
        private TextBox icmp_stats_out_p2;
        private TextBox ip_stats_out_p2;
        private TextBox arp_stats_out_p2;
        private TextBox eth_ii_stats_out_p2;
        private Label icmp_stats_out_p2_label;
        private Label http_stats_out_p2_label;
        private Label udp_stats_out_p2_label;
        private Label tcp_stats_out_p2_label;
        private Label ip_stats_out_p2_label;
        private Label arp_stats_out_p2_label;
        private Label eth_ii_stats_out_p2_label;
        private TextBox http_stats_in_p2;
        private TextBox udp_stats_in_p2;
        private TextBox tcp_stats_in_p2;
        private TextBox icmp_stats_in_p2;
        private TextBox ip_stats_in_p2;
        private TextBox arp_stats_in_p2;
        private TextBox eth_ii_stats_in_p2;
        private Label icmp_stats_in_p2_label;
        private Label http_stats_in_p2_label;
        private Label udp_stats_in_p2_label;
        private Label tcp_stats_in_p2_label;
        private Label ip_stats_in_p2_label;
        private Label arp_stats_in_p2_label;
        private Label eth_ii_stats_in_p2_label;
        private Label port2_label;
        private Label mac_table_label;
        //private DataGridView mac_table;
        private SafeDataGridView mac_table;
        private ComboBox interface_p1;
        private ComboBox interface_p2;
        private Label interface_p1_label;
        private Label interface_p2_label;
        private Label switch_label;
        private TextBox mac_counter;
        private Button mac_counter_update_button;
        private Label mac_counter_label;
        private Button reset_stats_button;
        private Button reset_mac_button;
    }
}

