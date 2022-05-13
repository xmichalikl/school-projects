package Controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import Exceptions.OrderException;
import Pc.BrokenPc;
import Pc.NewPc;
import Persons.ConfigurationTechnician;
import Persons.DiagnosticTechnician;
import Persons.Employee;
import Persons.Manager;
import Store.Order;
import Store.Store;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ManagerController implements Initializable {

	private Manager manager;
	private Store store;

	private ObservableList<Employee> obsEmployees = FXCollections.observableArrayList();
	private ObservableList<Order> obsOrders = FXCollections.observableArrayList();

	@FXML private Label loggedEmpText;

	@FXML private ComboBox<Order> ordersComboBox;
	@FXML private ComboBox<Employee> employeesComboBox;

	@FXML private Button confirmButton;
	@FXML private Button backButton;
	@FXML private Button createEmployeeButton;

	@FXML private CheckBox simulateCheck;

	public void confirm() {
		try {
			if (ordersComboBox.getSelectionModel().getSelectedItem() == null) {
				throw new OrderException("Treba vybrat objednavku");
			}
			else {
				Order activeOrder = ordersComboBox.getSelectionModel().getSelectedItem();
				int orderStatus;

				if (simulateCheck.isSelected()) {
					orderStatus = this.manager.startProduction(activeOrder);

					if (orderStatus == 0) {
						this.employeesComboBox.setDisable(true);
						this.confirmButton.setDisable(true);
						this.simulateCheck.setSelected(false);
						this.simulateCheck.setDisable(true);
						updateActiveOrder(activeOrder, orderStatus);
						Alert alert = new Alert(Alert.AlertType.INFORMATION);
						alert.getDialogPane().setPrefSize(350, 100);
						alert.setHeaderText("INFO");
						alert.setContentText(activeOrder.toString() + " bola vybavena");
						alert.showAndWait();
					}
					else if (orderStatus == 1) {
						this.employeesComboBox.setDisable(true);
						this.confirmButton.setDisable(true);
						this.simulateCheck.setSelected(false);
						this.simulateCheck.setDisable(true);
						updateActiveOrder(activeOrder, orderStatus);
						Alert alert = new Alert(Alert.AlertType.INFORMATION);
						alert.getDialogPane().setPrefSize(350, 100);
						alert.setHeaderText("INFO");
						alert.setContentText(activeOrder.toString() + " bola pozastavena");
						alert.showAndWait();
					}
					else {
						Alert alert = new Alert(Alert.AlertType.WARNING);
						alert.getDialogPane().setPrefSize(350, 100);
						alert.setHeaderText("INFO");
						alert.setContentText(activeOrder.toString() + " nemoze byt spracovana");
						alert.showAndWait();
					}
				}
				else if (!simulateCheck.isSelected()) {

					if (employeesComboBox.getSelectionModel().getSelectedItem() == null) {
						throw new OrderException("Treba vybrat zamestnanca");
					}

					else {
						Employee activeEmp = employeesComboBox.getSelectionModel().getSelectedItem();

						this.manager.movePc(this.manager, activeEmp, activeOrder.getPc());

						updateActiveEmp(activeEmp);
						updateActiveOrder(activeOrder, 1);
						this.employeesComboBox.setDisable(true);
						this.ordersComboBox.getSelectionModel().select(null);
						this.confirmButton.setDisable(true);
						this.simulateCheck.setSelected(false);
						this.simulateCheck.setDisable(true);

						Alert alert = new Alert(Alert.AlertType.INFORMATION);
						alert.getDialogPane().setPrefSize(450, 100);
						alert.setHeaderText("INFO");
						alert.setContentText(activeOrder.toString() + " bola posunuta zamestnancovi (" + activeEmp);
						alert.showAndWait();
					}
				}
			}
		}
		catch (OrderException ex) {
			Alert alert = new Alert(Alert.AlertType.WARNING);
			alert.getDialogPane().setPrefSize(350, 100);
			alert.setHeaderText("CHYBA");
			alert.setContentText(ex.getMessage());
			alert.showAndWait();
		}
	}

	public void createEmployee(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/EmpCreateScreen.fxml"));
		Parent root = loader.load();
		
		EmpCreateController empCreateController = loader.getController();
		empCreateController.setData(this.store, this);
		
		Scene scene = new Scene(root);
		Stage Stage = new Stage();
		Stage.setResizable(false);
		Stage.initModality(Modality.APPLICATION_MODAL);
		Stage.setScene(scene);
		Stage.show();
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

	public void orderSelection() {
		if (ordersComboBox.getSelectionModel().getSelectedItem() != null) {
			Order selectedOrder = ordersComboBox.getSelectionModel().getSelectedItem();
			this.obsEmployees.clear();

			if (selectedOrder.getPc() instanceof NewPc) {
				for (Employee emp : this.store.getEmployees()) {
					if (emp instanceof ConfigurationTechnician && emp.getIsAvailable()) {
						this.obsEmployees.add(emp);
					}
				}
			}
			else if (selectedOrder.getPc() instanceof BrokenPc) {
				for (Employee emp : this.store.getEmployees()) {
					if (emp instanceof DiagnosticTechnician && emp.getIsAvailable()) {
						this.obsEmployees.add(emp);
					}
				}
			}
			this.simulateCheck.setDisable(false);
			this.employeesComboBox.setDisable(false);
			this.employeesComboBox.setItems(this.obsEmployees);
		}
	}

	public void empSelection() {
		Employee activeEmp = employeesComboBox.getSelectionModel().getSelectedItem();
		if (activeEmp != null) {
			this.confirmButton.setDisable(false);
		}
	}

	public void simulateSelection() {
		if (simulateCheck.isSelected()) {
			this.employeesComboBox.setDisable(true);
			this.confirmButton.setDisable(false);
		}
		if (!simulateCheck.isSelected()) {
			this.employeesComboBox.setDisable(false);
			if (employeesComboBox.getSelectionModel().getSelectedItem() == null)
				this.confirmButton.setDisable(true);
		}
	}

	public void updateActiveOrder(Order activeOrder, int orderStatus) {
		activeOrder.setOrderStatus(orderStatus);
		this.obsOrders.remove(activeOrder);
	}

	public void updateActiveEmp(Employee activeEmp) {
		this.obsEmployees.remove(activeEmp);
	}

	public void updateEmp(Employee newEmp) {
		this.obsEmployees.add(newEmp);
	}

	public void updateEmp() {
		this.obsEmployees.clear();
		this.obsEmployees.addAll(this.store.getEmployees());
	}

	public void setData(Store store) {
		this.store = store;
		this.manager = store.getManager();
		this.employeesComboBox.setDisable(true);
		this.confirmButton.setDisable(true);
		this.simulateCheck.setDisable(true);
		this.loggedEmpText.setText("PRIHLASENY:\t" + this.manager.getName() + " " + this.manager.getSurname() + " (" + this.manager.getWorkPosition() + ")");
		this.loggedEmpText.setUnderline(true);

		for (Order ord : store.getOrders()) {
			if (ord.getOrderStatus() == 2) {
				this.obsOrders.add(ord);
			}
		}
		this.ordersComboBox.setItems(this.obsOrders);
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
	}

}
