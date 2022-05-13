package Controllers;

import Store.Store;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HomeController implements Initializable {

    private Store store;

    @FXML private Button switchToLogInButton;
    @FXML private Button switchToOrderButton;

    public void switchToLogIn(ActionEvent event) throws IOException {
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

    public void switchToOrder(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/CreateOrderScreen.fxml"));
        Parent root = loader.load();

        CreateOrderController createOrderController = loader.getController();
        createOrderController.setData(this.store);

        Scene scene = new Scene(root);
        Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
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
