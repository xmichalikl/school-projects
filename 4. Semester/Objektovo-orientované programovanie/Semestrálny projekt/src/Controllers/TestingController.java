package Controllers;


import Persons.DiagnosticTechnician;
import Persons.Employee;
import Persons.TestingTechnician;
import Store.Store;
import Store.Order;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class TestingController extends EmpController implements Initializable {

    private final List<Text> textArr = new ArrayList<>();
    private final List<Button> buttonArr = new ArrayList<>();

    @FXML private Text cpuText, gpuText, ramText, mboText, hddText, psuText;

    @FXML private Button cpuTestButton;
    @FXML private Button gpuTestButton;
    @FXML private Button ramTestButton;
    @FXML private Button mboTestButton;
    @FXML private Button hddTestButton;
    @FXML private Button psuTestButton;


    @Override
    public void confirm() {
        super.confirm();
    }

    public void cpuTest() {
        if (this.pc.getCpu().stressTest(this.pc)) {
            cpuTestButton.setDisable(true);
            gpuTestButton.setDisable(false);
            cpuText.setText("OK");
            cpuText.setFill(Color.DARKGREEN);
        }
        else {
            clearScreen();
            this.employeesComboBox.setDisable(false);
            this.confirmButton.setDisable(false);
            cpuText.setText("CHYBA");
            cpuText.setFill(Color.DARKRED);
        }
    }

    public void gpuTest() {
        if (this.pc.getGpu().stressTest(this.pc)) {
            gpuTestButton.setDisable(true);
            ramTestButton.setDisable(false);
            gpuText.setText("OK");
            gpuText.setFill(Color.DARKGREEN);
        }
        else {
            clearScreen();
            this.employeesComboBox.setDisable(false);
            this.confirmButton.setDisable(false);
            gpuText.setText("CHYBA");
            gpuText.setFill(Color.DARKRED);
        }
    }

    public void ramTest() {
        if (this.pc.getRam().stressTest(this.pc)) {
            ramTestButton.setDisable(true);
            mboTestButton.setDisable(false);
            ramText.setText("OK");
            ramText.setFill(Color.DARKGREEN);
        }
        else {
            clearScreen();
            this.employeesComboBox.setDisable(false);
            this.confirmButton.setDisable(false);
            ramText.setText("CHYBA");
            ramText.setFill(Color.DARKRED);
        }
    }

    public void mboTest() {
        if (this.pc.getMbo().stressTest(this.pc)) {
            mboTestButton.setDisable(true);
            hddTestButton.setDisable(false);
            mboText.setText("OK");
            mboText.setFill(Color.DARKGREEN);
        }
        else {
            clearScreen();
            this.employeesComboBox.setDisable(false);
            this.confirmButton.setDisable(false);
            mboText.setText("CHYBA");
            mboText.setFill(Color.DARKRED);
        }
    }

    public void hddTest() {
        if (this.pc.getHdd().stressTest(this.pc)) {
            hddTestButton.setDisable(true);
            psuTestButton.setDisable(false);
            hddText.setText("OK");
            hddText.setFill(Color.DARKGREEN);
        }
        else {
            clearScreen();
            this.employeesComboBox.setDisable(false);
            this.confirmButton.setDisable(false);
            hddText.setText("CHYBA");
            hddText.setFill(Color.DARKRED);
        }
    }

    public void psuTest() {
        if (this.pc.getPsu().stressTest(this.pc)) {
            psuTestButton.setDisable(true);
            psuText.setText("OK");
            psuText.setFill(Color.DARKGREEN);

            Order completeOrder = ((TestingTechnician)this.loggedEmp).deleteOrder(this.pc);

            if (completeOrder != null) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.getDialogPane().setPrefSize(350, 100);
                alert.setHeaderText("INFO");
                alert.setContentText(completeOrder.toString() + " bola vybavena");
                alert.showAndWait();
            }
        }
        else {
            clearScreen();
            this.employeesComboBox.setDisable(false);
            this.confirmButton.setDisable(false);
            psuText.setText("CHYBA");
            psuText.setFill(Color.DARKRED);
        }
    }

    @Override
    public void clearScreen() {
        for (Button b : this.buttonArr)
            b.setDisable(true);
    }

    @Override
    public void setData(Store store, Employee loggedEmp) {
        super.setData(store, loggedEmp);

        if (this.pc != null) {
            for (Employee emp : this.store.getEmployees()) {
                if (emp instanceof DiagnosticTechnician && emp.getIsAvailable())
                    this.obsEmployees.add(emp);
            }
            this.employeesComboBox.setItems(this.obsEmployees);

            for (Text t : this.textArr)
                t.setText("Zatial netestovane");

            this.cpuTestButton.setDisable(false);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        this.textArr.add(cpuText);
        this.textArr.add(gpuText);
        this.textArr.add(ramText);
        this.textArr.add(mboText);
        this.textArr.add(hddText);
        this.textArr.add(psuText);

        this.buttonArr.add(cpuTestButton);
        this.buttonArr.add(gpuTestButton);
        this.buttonArr.add(ramTestButton);
        this.buttonArr.add(mboTestButton);
        this.buttonArr.add(hddTestButton);
        this.buttonArr.add(psuTestButton);

        if (this.pc == null) {
            for (Text t : this.textArr)
                t.setText("Nie je k dispozicii");

            for (Button b : this.buttonArr)
                b.setDisable(true);
        }
    }
}
