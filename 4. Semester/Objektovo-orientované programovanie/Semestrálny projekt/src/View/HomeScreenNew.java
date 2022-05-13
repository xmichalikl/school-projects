package View;

import Controllers.HomeControllerNew;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;


public class HomeScreenNew extends VBox {
    private final VBox vBox = new VBox();
    private final Text text = new Text("CompExpress");

    private final ImageView image = new ImageView(new Image("/Resources/icon.png"));

    private final Button switchToLogInButton = new Button("Prihlasenie");
    private final Button switchToOrderButton = new Button("Objednavka");

    public HomeScreenNew(final HomeControllerNew homeControllerNew) {
        this.setPadding(new Insets(50, 0, 0, 0));
        this.setAlignment(Pos.TOP_CENTER);

        text.setFont(Font.font ("Verdana", 24));

        vBox.setPadding(new Insets(50, 0, 0, 0));
        vBox.setAlignment(Pos.CENTER);
        vBox.setPrefWidth(80);
        vBox.setSpacing(20);

        switchToLogInButton.setOnAction(homeControllerNew);
        switchToLogInButton.setMinWidth(vBox.getPrefWidth());

        switchToOrderButton.setOnAction(homeControllerNew);
        switchToOrderButton.setMinWidth(vBox.getPrefWidth());

        image.setFitHeight(100);
        image.setFitWidth(100);
        image.setPreserveRatio(true);

        vBox.getChildren().addAll(switchToLogInButton, switchToOrderButton);
        this.getChildren().addAll(image, text, vBox);
    }

    public Button getLogInButton() {
        return switchToLogInButton;
    }
    public Button getOrderButton() {
        return switchToOrderButton;
    }
}