package Controllers;

import java.net.URL;
import java.util.ResourceBundle;

import Exceptions.EmployeeException;
import Persons.AssemblyTechnician;
import Persons.ConfigurationTechnician;
import Persons.DiagnosticTechnician;
import Persons.Employee;
import Persons.Manager;
import Persons.SoftwareTechnician;
import Persons.TestingTechnician;
import Store.Store;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class EmpCreateController implements Initializable {
	
	private Store store;
	private Manager manager;

	private ManagerController managerController;
	
	@FXML private ChoiceBox<String> workPosition;
	
	@FXML private TextField newEmpNameField;
	@FXML private TextField newEmpSurnameField;
	
	@FXML private TextField newEmpUsernameField;
	@FXML private TextField newEmpPasswordField;
	
	@FXML private Button newEmpButton;

	public void newEmployee(ActionEvent event) {
		String name = newEmpNameField.getText();
		String surname = newEmpSurnameField.getText();

		String username = newEmpUsernameField.getText();
		String password = newEmpPasswordField.getText();

		Employee newEmp = null;

		try {
			if (workPosition.getSelectionModel().getSelectedItem() == null)
				throw new EmployeeException("Treba zvolit pracovnu poziciu!");

			else if (name.length() == 0 || surname.length() == 0 || username.length() == 0 || password.length() == 0)
				throw new EmployeeException("Treba vyplnit vsetky udaje!");

			else {
				switch (workPosition.getSelectionModel().getSelectedItem()) {
				  case "Montáž":
					newEmp = new AssemblyTechnician(this.store, name, surname, username, password);
					break;
				  case "Diagnostika":
					newEmp = new DiagnosticTechnician(this.store, name, surname, username, password);
					break;
				  case "Software":
					newEmp = new SoftwareTechnician(this.store, name, surname, username, password);
					break;
				  case "Konfigurácia":
					newEmp = new ConfigurationTechnician(this.store, name, surname, username, password);
					break;
				  case "Testovanie":
					newEmp = new TestingTechnician(this.store, name, surname, username, password);
					break;
				}
			}

			if (newEmp != null) {
				this.manager.createNewEmployee(newEmp);
				this.managerController.updateEmp(newEmp);

				Alert alert = new Alert(Alert.AlertType.INFORMATION);
				alert.getDialogPane().setPrefSize(350, 100);
				alert.setHeaderText("INFO");
				alert.setContentText("Pouzivatel " + name + " " + surname + " (" + newEmp.getWorkPosition() + ") bol pridany do systemu!");
				alert.showAndWait();

				Stage stage = (Stage) newEmpButton.getScene().getWindow();
				stage.close();
			}
			else {
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.getDialogPane().setPrefSize(350, 100);
				alert.setHeaderText("CHYBA");
				alert.setContentText("Nepodarilo sa vytvorit noveho pouzivatela!");
				alert.showAndWait();

				Stage stage = (Stage) newEmpButton.getScene().getWindow();
				stage.close();
			}
		}
		catch (EmployeeException ex) {
			Alert alert = new Alert(Alert.AlertType.WARNING);
			alert.getDialogPane().setPrefSize(350, 100);
			alert.setHeaderText("CHYBA");
			alert.setContentText(ex.getMessage());
			alert.showAndWait();
		}
		
	}
	
	public void setData(Store store, ManagerController managerController) {
		this.store = store;
		this.manager = store.getManager();
		this.managerController = managerController;
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		this.workPosition.getItems().add("Montáž");
		this.workPosition.getItems().add("Diagnostika");
		this.workPosition.getItems().add("Software");
		this.workPosition.getItems().add("Konfigurácia");
		this.workPosition.getItems().add("Testovanie");
	}
	
}
