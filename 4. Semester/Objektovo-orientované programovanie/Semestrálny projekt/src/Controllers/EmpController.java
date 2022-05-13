package Controllers;

import Exceptions.EmployeeException;
import Pc.Pc;
import Persons.Employee;
import Store.Store;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

public class EmpController {

    protected Employee loggedEmp;
    protected Store store;
    protected Pc pc;

    protected ObservableList<Employee> obsEmployees = FXCollections.observableArrayList();

    @FXML protected ComboBox<Employee> employeesComboBox;
    @FXML protected Button confirmButton;
    @FXML protected Button logOutButton;
    @FXML protected Label loggedEmpText;


    public void confirm() {
        try {
            Employee selectedEmp = employeesComboBox.getSelectionModel().getSelectedItem();
            if (selectedEmp != null) {
                loggedEmp.movePc(this.loggedEmp, selectedEmp, this.pc);
                employeesComboBox.setDisable(true);
                confirmButton.setDisable(true);
                obsEmployees.remove(selectedEmp);

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.getDialogPane().setPrefSize(350, 100);
                alert.setHeaderText("INFO");
                alert.setContentText("Poziadavka bola odoslana (" + selectedEmp + ")");
                alert.showAndWait();

                clearScreen();
            }
            else throw new EmployeeException("Treba zvolit zamestnanca");
        }
        catch (EmployeeException ex) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.getDialogPane().setPrefSize(350, 100);
            alert.setHeaderText("CHYBA");
            alert.setContentText(ex.getMessage());
            alert.showAndWait();
        }
    }

    public void logOut(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/LogInScreen.fxml"));
        Parent root = loader.load();

        LogInController logInController = loader.getController();
        logInController.setData(this.store);

        Scene scene = new Scene(root);
        Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        appStage.setResizable(false);
        appStage.setScene(scene);
        appStage.show();
    }

    public void setData(Store store, Employee loggedEmp) {
        this.store = store;
        this.loggedEmp = loggedEmp;
        this.pc = loggedEmp.getPc();

        this.loggedEmpText.setText("PRIHLASENY:\t" + loggedEmp.getName() + " " + loggedEmp.getSurname() + " (" + loggedEmp.getWorkPosition() + ")");
        this.loggedEmpText.setUnderline(true);
        this.employeesComboBox.setDisable(true);
        this.confirmButton.setDisable(true);
    }

    public void clearScreen() {}
}
