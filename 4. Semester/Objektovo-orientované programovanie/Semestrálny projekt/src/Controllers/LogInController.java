package Controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import Persons.*;
import Store.Store;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LogInController implements Initializable {
	
	private Store store;
	
	@FXML private TextField usernameField;
	@FXML private PasswordField passwordField;
	
	@FXML private Button logInButton;
	@FXML private Button switchToHomeButton;

	public void logIn(ActionEvent event) throws IOException {
		String username = usernameField.getText();
		String password = passwordField.getText();
	
		if (username.equals("admin") && password.equals("admin")) {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/ManagerScreen.fxml"));
			Parent root = loader.load();

			ManagerController managerController = loader.getController();
			managerController.setData(this.store);
			
			Scene scene = new Scene(root);
			Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			appStage.setResizable(false);
			appStage.setScene(scene);
			appStage.show();
		}
		else {
			for (Employee emp : this.store.getEmployees()) {
				if (emp.getUserName().equals(username) && emp.getUserPassword().equals(password)) {

					if (emp instanceof DiagnosticTechnician) {
						FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/DiagnosticScreen.fxml"));
						Parent root = loader.load();

						DiagnosticController diagnosticTechnicianController = loader.getController();
						diagnosticTechnicianController.setData(this.store, emp);

						Scene scene = new Scene(root);
						Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
						appStage.setResizable(false);
						appStage.setScene(scene);
						appStage.show();
					}
					else if (emp instanceof ConfigurationTechnician) {
						FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/ConfigurationScreen.fxml"));
						Parent root = loader.load();

						ConfigurationController configurationController = loader.getController();
						configurationController.setData(this.store, emp);

						Scene scene = new Scene(root);
						Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
						appStage.setResizable(false);
						appStage.setScene(scene);
						appStage.show();
					}

					else if (emp instanceof AssemblyTechnician) {
						FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/AssemblyScreen.fxml"));
						Parent root = loader.load();

						AssemblyController assemblyController = loader.getController();
						assemblyController.setData(this.store, emp);

						Scene scene = new Scene(root);
						Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
						appStage.setResizable(false);
						appStage.setScene(scene);
						appStage.show();
					}

					else if (emp instanceof SoftwareTechnician) {
						FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/SoftwareScreen.fxml"));
						Parent root = loader.load();

						SoftwareController softwareController = loader.getController();
						softwareController.setData(this.store, emp);

						Scene scene = new Scene(root);
						Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
						appStage.setResizable(false);
						appStage.setScene(scene);
						appStage.show();
					}

					else if (emp instanceof TestingTechnician) {
						FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/TestingScreen.fxml"));
						Parent root = loader.load();

						TestingController testingController = loader.getController();
						testingController.setData(this.store, emp);

						Scene scene = new Scene(root);
						Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
						appStage.setResizable(false);
						appStage.setScene(scene);
						appStage.show();
					}
					//System.out.println("LOGIN");
				}	
			}
		}
	}

	public void switchToHome(ActionEvent event) throws IOException {

		Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

		HomeControllerNew homeControllerNew = new HomeControllerNew(appStage, this.store);
		Scene scene = new Scene(homeControllerNew.getView(), 400, 400);

		appStage.setResizable(false);
		appStage.setScene(scene);
		appStage.show();
	}

	public void setData(Store store) {
		this.store = store;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}

}
