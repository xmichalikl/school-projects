package App;

import App.Controller.Controller;
import App.Model.Model;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.awt.*;

public class Main extends Application {
    public Model model;
    public static final double HEIGHT_RATIO = 0.64814;
    public static final double WIDTH_RATIO = 0.46875;

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.model = new Model(2); // 2 for GUI

        Image icon = new Image("App/Resources/icon.png");
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("View/sample.fxml"));
        Parent root = loader.load();

        Controller controller = loader.getController();
        controller.setModel(this.model, primaryStage);
        Scene scene = new Scene(root);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double screenWidth = screenSize.getWidth();
        double screenHeight = screenSize.getHeight();

        primaryStage.setWidth((int)(screenWidth*WIDTH_RATIO));
        primaryStage.setHeight((int)(screenHeight*HEIGHT_RATIO));
        primaryStage.setMinWidth(390);
        primaryStage.setMinHeight(500);

        primaryStage.getIcons().add(icon);
        primaryStage.setTitle("BorrowIT v1.1");
        primaryStage.setResizable(true);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
