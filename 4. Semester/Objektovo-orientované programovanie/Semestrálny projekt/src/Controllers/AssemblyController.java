package Controllers;

import Components.*;
import Exceptions.AssemblyException;
import Pc.BrokenPc;
import Persons.AssemblyTechnician;
import Persons.Employee;
import Persons.SoftwareTechnician;
import Store.Store;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;

import java.net.URL;
import java.util.ResourceBundle;

public class AssemblyController extends EmpController implements Initializable {

    private ObservableList<Component> obsBrokenCom = FXCollections.observableArrayList();
    private ObservableList<Component> obsNewCom = FXCollections.observableArrayList();

    @FXML private ComboBox<Component> choiceBrokenCom;
    @FXML private ComboBox<Component> choiceNewCom;

    @FXML private Button removeComButton;
    @FXML private Button addComButton;


    @Override
    public void confirm() {
        if (checkComponents())
            super.confirm();
    }

    public boolean checkComponents() {
        try {
            if (this.obsBrokenCom.size() > 0)
                throw new AssemblyException("Treba odstranit vsetky nefunkcne komponenty!");
            if (this.obsNewCom.size() > 0)
                throw new AssemblyException("Treba nainstalovat vsetky nove komponenty!");

            return true;
        }
        catch (AssemblyException ex) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.getDialogPane().setPrefSize(350, 100);
            alert.setHeaderText("CHYBA");
            alert.setContentText(ex.getMessage());
            alert.showAndWait();

            return false;
        }
    }

    public void removeCom() {
        Component brokenCom = this.choiceBrokenCom.getSelectionModel().getSelectedItem();
        try {
            if (brokenCom == null)
                throw new AssemblyException("Treba vybrat komponent na odstranenie!");
            else {
                ((AssemblyTechnician) this.loggedEmp).removeComponent(brokenCom);
                this.obsBrokenCom.remove(brokenCom);

                if (obsBrokenCom.size() == 0) {
                    this.choiceBrokenCom.setDisable(true);
                    this.removeComButton.setDisable(true);
                }
            }
        }
        catch (AssemblyException ex) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.getDialogPane().setPrefSize(350, 100);
            alert.setHeaderText("CHYBA");
            alert.setContentText(ex.getMessage());
            alert.showAndWait();
        }
    }

    public void installCom() {
        Component newCom = this.choiceNewCom.getSelectionModel().getSelectedItem();
        try {
            if (newCom == null)
                throw new AssemblyException("Treba vybrat komponent na instalaciu!");
            else {
                ((AssemblyTechnician) this.loggedEmp).addComponent(newCom);
                this.obsNewCom.remove(newCom);

                if (obsNewCom.size() == 0) {
                    this.choiceNewCom.setDisable(true);
                    this.addComButton.setDisable(true);
                }
            }
        }
        catch (AssemblyException ex) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.getDialogPane().setPrefSize(350, 100);
            alert.setHeaderText("CHYBA");
            alert.setContentText(ex.getMessage());
            alert.showAndWait();
        }
    }

    @Override
    public void clearScreen() {
        this.choiceBrokenCom.setDisable(true);
        this.choiceNewCom.setDisable(true);
        this.removeComButton.setDisable(true);
        this.addComButton.setDisable(true);
    }

    @Override
    public void setData(Store store, Employee loggedEmp) {
        super.setData(store, loggedEmp);

        if (this.pc != null) {
            this.employeesComboBox.setDisable(false);
            this.confirmButton.setDisable(false);

            for (Employee emp : this.store.getEmployees()) {
                if (emp instanceof SoftwareTechnician && emp.getIsAvailable())
                    this.obsEmployees.add(emp);
            }
            this.employeesComboBox.setItems(this.obsEmployees);

            if ( ((AssemblyTechnician) this.loggedEmp).getNewComponents().size() > 0){
                this.obsNewCom.addAll( ((AssemblyTechnician) this.loggedEmp).getNewComponents());
                this.choiceNewCom.setItems(this.obsNewCom);
                this.choiceNewCom.setDisable(false);
                this.addComButton.setDisable(false);
            }

            if (this.pc instanceof BrokenPc && ((AssemblyTechnician) this.loggedEmp).getBrokenComponents().size() > 0) {
                this.obsBrokenCom.addAll( ((AssemblyTechnician) this.loggedEmp).getBrokenComponents());
                this.choiceBrokenCom.setItems(this.obsBrokenCom);
                this.choiceBrokenCom.setDisable(false);
                this.removeComButton.setDisable(false);
            }
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        this.choiceBrokenCom.setDisable(true);
        this.choiceNewCom.setDisable(true);
        this.removeComButton.setDisable(true);
        this.addComButton.setDisable(true);
    }
}
