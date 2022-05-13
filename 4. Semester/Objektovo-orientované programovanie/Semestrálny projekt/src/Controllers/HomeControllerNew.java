package Controllers;

import Store.Store;
import View.HomeScreenNew;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HomeControllerNew implements EventHandler {
    private final Store store;
    private final Stage primaryStage;
    private final HomeScreenNew view = new HomeScreenNew(this);

    public HomeControllerNew(final Stage primaryStage, Store store) {
        this.primaryStage = primaryStage;
        this.store = store;
    }

    @Override
    public void handle(final Event event) {
        final Object source = event.getSource();

        //Handlovanie eventov podla stlaceneho tlacidla a
        //prechod na dalsiu scenu
        if (source.equals(view.getLogInButton())) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/LogInScreen.fxml"));
                Parent root = loader.load();

                LogInController logInController = loader.getController();
                logInController.setData(this.store);

                Scene scene = new Scene(root);
                primaryStage.setResizable(false);
                primaryStage.setScene(scene);
                primaryStage.show();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if (source.equals(view.getOrderButton())) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/CreateOrderScreen.fxml"));
                Parent root = loader.load();

                CreateOrderController createOrderController = loader.getController();
                createOrderController.setData(this.store);

                Scene scene = new Scene(root);
                primaryStage.setResizable(false);
                primaryStage.setScene(scene);
                primaryStage.show();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public HomeScreenNew getView() {
        return view;
    }
}