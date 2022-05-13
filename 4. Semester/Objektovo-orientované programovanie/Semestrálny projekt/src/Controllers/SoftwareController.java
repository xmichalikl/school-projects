package Controllers;

import Exceptions.AssemblyException;
import Exceptions.SoftwareException;
import Pc.BrokenPc;
import Pc.NewPc;
import Persons.Employee;
import Persons.SoftwareTechnician;
import Persons.TestingTechnician;
import Store.Store;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class SoftwareController extends EmpController implements Initializable {

    private final List<Button> buttonArr = new ArrayList<>();

    @FXML private Button installOsNewPcButton;
    @FXML private Button installDriversNewPcButton;

    @FXML private Button backupDataButton;

    @FXML private Button installOsBrokenPcButton;
    @FXML private Button recoverDataButton;

    @FXML private Button removeVirusButton;
    @FXML private Button updateOsButton;

    @FXML private Button installDriversBrokenPcButton;


    @Override
    public void confirm() {
        if (checkSoftware())
            super.confirm();
    }

    public boolean checkSoftware() {
        try {
            for (Button btn : this.buttonArr) {
                if (!btn.isDisabled()) {
                    throw new SoftwareException("Nie je nainstalovany vsetok pozadovany software!");
                }
            }
            return true;
        }
        catch (SoftwareException ex) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.getDialogPane().setPrefSize(350, 100);
            alert.setHeaderText("CHYBA");
            alert.setContentText(ex.getMessage());
            alert.showAndWait();
            return false;
        }
    }


    public void backupData() {
        ((SoftwareTechnician)this.loggedEmp).backupData((BrokenPc) this.pc);
        this.backupDataButton.setDisable(true);
        this.installOsBrokenPcButton.setDisable(false);
    }

    public void installOsNewPc() {
        ((SoftwareTechnician)this.loggedEmp).installOs((NewPc) this.pc);
        this.installOsNewPcButton.setDisable(true);
        this.installDriversNewPcButton.setDisable(false);
    }

    public void installOsBrokenPc() {
        ((SoftwareTechnician)this.loggedEmp).installOs((BrokenPc) this.pc);
        this.installOsBrokenPcButton.setDisable(true);

        if ( ((BrokenPc) this.pc).getPcBackup())
            this.recoverDataButton.setDisable(false);

        else if ( ((BrokenPc) this.pc).getVirus()) {
            this.removeVirusButton.setDisable(false);
        }
        else
            this.updateOsButton.setDisable(false);
    }

    public void recoverData() {
        ((SoftwareTechnician)this.loggedEmp).returnData((BrokenPc) this.pc);
        this.recoverDataButton.setDisable(true);

        if ( ((BrokenPc) this.pc).getVirus()) {
            this.removeVirusButton.setDisable(false);
        }
        else
            this.updateOsButton.setDisable(false);
    }

    public void removeVirus() {
        ((SoftwareTechnician)this.loggedEmp).removeVirus((BrokenPc) this.pc);
        this.removeVirusButton.setDisable(true);
        this.updateOsButton.setDisable(false);
    }

    public void updateOs() {
        ((SoftwareTechnician)this.loggedEmp).updateOs((BrokenPc) this.pc);
        this.updateOsButton.setDisable(true);
        this.installDriversBrokenPcButton.setDisable(false);
    }

    public void installDrivers() {
        ((SoftwareTechnician)this.loggedEmp).installDrivers(this.pc);
        this.installDriversNewPcButton.setDisable(true);
        this.installDriversBrokenPcButton.setDisable(true);
    }

    @Override
    public void clearScreen() {
        for (Button btn : this.buttonArr)
            btn.setDisable(true);
    }

    @Override
    public void setData(Store store, Employee loggedEmp) {
        super.setData(store, loggedEmp);

        if (this.pc != null) {

            this.employeesComboBox.setDisable(false);
            this.confirmButton.setDisable(false);

            for (Employee emp : this.store.getEmployees()) {
                if (emp instanceof TestingTechnician && emp.getIsAvailable())
                    this.obsEmployees.add(emp);
            }
            this.employeesComboBox.setItems(this.obsEmployees);



            if (this.pc instanceof NewPc && !((NewPc)this.pc).getOsInstalled()) {
                this.installOsNewPcButton.setDisable(false);
            }

            else if (this.pc instanceof BrokenPc && !((BrokenPc)this.pc).getOsUpdate()) {

                if ( ((BrokenPc)this.pc).getPcBackup()) {
                    this.backupDataButton.setDisable(false);
                }
                else if ( !((BrokenPc)this.pc).getPcBackup() && ((BrokenPc)this.pc).getPcCleanup()) {
                    this.installOsBrokenPcButton.setDisable(false);
                }
                else {
                    if ( ((BrokenPc)this.pc).getVirus()) {
                        this.removeVirusButton.setDisable(false);
                    }
                    else {
                        this.updateOsButton.setDisable(false);
                    }
                }
            }
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        this.buttonArr.add(installOsNewPcButton);
        this.buttonArr.add(installDriversNewPcButton);

        this.buttonArr.add(backupDataButton);

        this.buttonArr.add(installOsBrokenPcButton);
        this.buttonArr.add(recoverDataButton);

        this.buttonArr.add(removeVirusButton);
        this.buttonArr.add(updateOsButton);

        this.buttonArr.add(installDriversBrokenPcButton);

        for (Button btn : this.buttonArr)
            btn.setDisable(true);

    }
}
