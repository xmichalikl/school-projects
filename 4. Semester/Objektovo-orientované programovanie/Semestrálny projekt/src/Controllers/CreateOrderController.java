package Controllers;

import Enums.Performance;
import Exceptions.OrderException;
import Store.Store;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CreateOrderController implements Initializable {

    private Store store;

    @FXML private Button createOrderButton;
    @FXML private Button switchToHomeButton;

    @FXML private ComboBox<String> pcType;
    @FXML private ComboBox<Performance> pcPerformance;

    @FXML private CheckBox pcCleanup;
    @FXML private CheckBox pcBackup;

    public void createOrder() {
        try {
            if (pcType.getValue() == null) {
                throw new OrderException();
            }
            else if (pcType.getValue().equals("Novy PC") && pcPerformance.getValue() == null) {
                throw new OrderException("Treba zvolit typ pocitaca");
            }
            else if (pcType.getValue().equals("Novy PC")) {
                this.store.createOrder(this.store, pcPerformance.getValue());
            }
            else if (pcType.getValue().equals("Pokazeny PC")) {
                this.store.createOrder(this.store, pcCleanup.isSelected(), pcBackup.isSelected());
            }

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Objednavka vytvorena");
            alert.setContentText(pcType.getValue());
            alert.showAndWait();
        }
        catch (OrderException ex) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.getDialogPane().setPrefSize(350, 100);
            alert.setHeaderText("CHYBA");
            alert.setContentText(ex.getMessage());
            alert.showAndWait();
        }
    }


    public void switchSelectionPc() {
        if (pcType.getValue().equals("Novy PC")) {
            this.pcPerformance.setDisable(false);
            this.pcCleanup.setDisable(true);
            this.pcBackup.setDisable(true);
        }
        else if (pcType.getValue().equals("Pokazeny PC")) {
            this.pcPerformance.setDisable(true);
            this.pcCleanup.setDisable(false);
            this.pcBackup.setDisable(true);
        }
    }

    public void switchSelectionCleanup() {
        if ( pcCleanup.isSelected() ) {
            this.pcBackup.setDisable(false);
        }
        else if ( !pcCleanup.isSelected() ) {
            this.pcBackup.setSelected(false);
            this.pcBackup.setDisable(true);
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
        this.pcPerformance.setDisable(true);
        this.pcCleanup.setDisable(true);
        this.pcBackup.setDisable(true);

        this.pcType.getItems().add("Novy PC");
        this.pcType.getItems().add("Pokazeny PC");

        this.pcPerformance.getItems().add(Performance.HIGH);
        this.pcPerformance.getItems().add(Performance.MEDIUM);
        this.pcPerformance.getItems().add(Performance.LOW);
    }
}
