using System;
using System.Windows.Forms;

public class SafeDataGridView : DataGridView {
    protected override void OnPaint(System.Windows.Forms.PaintEventArgs e) {
        try {
            base.OnPaint(e);
        }
        catch (Exception) {
            Console.WriteLine("*********************** RED X ***********************");
            this.Invalidate();
        }
    }
}
