package Controllers;

import Persons.ConfigurationTechnician;
import Persons.DiagnosticTechnician;
import Persons.Employee;
import Store.Store;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class DiagnosticController extends EmpController implements Initializable {

    private final List<CheckBox> checkBoxArr = new ArrayList<>();
    private final List<Text> textArr = new ArrayList<>();

    @FXML private CheckBox cpuCheck, gpuCheck, ramCheck, mboCheck, hddCheck, psuCheck;
    @FXML private Text cpuText, gpuText, ramText, mboText, hddText, psuText;

    @Override
    public void confirm() {
        checkComponents();
        super.confirm();
    }

    public void checkComponents() {

        if (!cpuCheck.isSelected()) {
            this.pc.getCpu().setWorking(false);
            ((DiagnosticTechnician)this.loggedEmp).getBrokenComponents().add(this.pc.getCpu());
        }
        if (!gpuCheck.isSelected()) {
            this.pc.getGpu().setWorking(false);
            ((DiagnosticTechnician)this.loggedEmp).getBrokenComponents().add(this.pc.getGpu());
        }
        if (!ramCheck.isSelected()) {
            this.pc.getRam().setWorking(false);
            ((DiagnosticTechnician)this.loggedEmp).getBrokenComponents().add(this.pc.getRam());
        }
        if (!mboCheck.isSelected()) {
            this.pc.getMbo().setWorking(false);
            ((DiagnosticTechnician)this.loggedEmp).getBrokenComponents().add(this.pc.getMbo());
        }
        if (!hddCheck.isSelected()) {
            this.pc.getHdd().setWorking(false);
            ((DiagnosticTechnician)this.loggedEmp).getBrokenComponents().add(this.pc.getHdd());
        }
        if (!psuCheck.isSelected()) {
            this.pc.getPsu().setWorking(false);
            ((DiagnosticTechnician)this.loggedEmp).getBrokenComponents().add(this.pc.getPsu());
        }
    }

    @Override
    public void clearScreen() {
        for (Text t : this.textArr)
            t.setText("Nie je k dispozicii");
        for (CheckBox ch :  this.checkBoxArr)
            ch.setDisable(true);
    }

    @Override
    public void setData(Store store, Employee loggedEmp) {
        super.setData(store, loggedEmp);

        if (this.pc != null) {
            this.employeesComboBox.setDisable(false);
            this.confirmButton.setDisable(false);

            for (Employee emp : this.store.getEmployees()) {
                if (emp instanceof ConfigurationTechnician && emp.getIsAvailable())
                    this.obsEmployees.add(emp);
            }
            this.employeesComboBox.setItems(this.obsEmployees);

            this.cpuText.setText(this.pc.getCpu().toString());
            this.gpuText.setText(this.pc.getGpu().toString());
            this.ramText.setText(this.pc.getRam().toString());
            this.mboText.setText(this.pc.getMbo().toString());
            this.hddText.setText(this.pc.getHdd().toString());
            this.psuText.setText(this.pc.getPsu().toString());

            for (CheckBox ch :  this.checkBoxArr)
                ch.setDisable(false);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        this.checkBoxArr.add(cpuCheck);
        this.checkBoxArr.add(gpuCheck);
        this.checkBoxArr.add(ramCheck);
        this.checkBoxArr.add(mboCheck);
        this.checkBoxArr.add(hddCheck);
        this.checkBoxArr.add(psuCheck);

        this.textArr.add(cpuText);
        this.textArr.add(gpuText);
        this.textArr.add(ramText);
        this.textArr.add(mboText);
        this.textArr.add(hddText);
        this.textArr.add(psuText);

        if (this.pc == null) {
            for (Text t : this.textArr)
                t.setText("Nie je k dispozicii");

            for (CheckBox ch :  this.checkBoxArr)
                ch.setDisable(true);
        }
    }
}
