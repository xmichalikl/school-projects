package App.Controller;

import App.Model.Model;
import App.Model.MovieData;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import java.util.Collections;

public class Controller {
    private int pageNum = 1;
    private boolean firstPage = true;

    private Stage stage;
    private Model model;
    private Thread loadThread;
    private final ObservableList<MovieData> moviesList = FXCollections.observableArrayList();

    @FXML private Button searchButton;
    @FXML private Button nextButton;
    @FXML private Button prevButton;
    @FXML private TextField searchTextField;
    @FXML private ProgressIndicator progressIndicator;

    @FXML private TableView<MovieData> moviesTable;
    @FXML private TableColumn<MovieData, ImageView> movieImage;
    @FXML private TableColumn<MovieData, String> movieName;
    @FXML private TableColumn<MovieData, String> movieQuality;
    @FXML private TableColumn<MovieData, Button> moviePlayButton;


    public void setModel(Model model, Stage stage) {
        this.model = model;
        this.stage = stage;
        this.movieImage.setCellValueFactory(new PropertyValueFactory<>("MovieImage"));
        this.movieName.setCellValueFactory(new PropertyValueFactory<>("MovieName"));
        this.movieQuality.setCellValueFactory(new PropertyValueFactory<>("MovieQuality"));
        this.moviePlayButton.setCellValueFactory(new PropertyValueFactory<>("MoviePlayButton"));
        this.moviesTable.setItems(moviesList);
        setListeners();
    }

    public void setListeners() {
        // Auto-resize preview images
        this.movieImage.widthProperty().addListener((observableValue, number, t1) -> {
            for (MovieData data : moviesList) {
                data.movieImage.setFitWidth((double)t1-1);
            }
        });
        // Auto-resize play buttons
        this.moviePlayButton.widthProperty().addListener((observableValue, number, t1) -> {
            //System.out.println(t1);
            String buttonText = "Play movie!";
            if ((double)t1 <= 145) buttonText = "Play!";
            if ((double)t1 <= 82) buttonText = "►";

            for (MovieData data : moviesList) {
                data.moviePlayButton.setText(buttonText);
                data.moviePlayButton.setStyle("-fx-pref-width:"+(double)t1/1.44+"px");
            }
        });
    }

    public void next() {
        if (!this.loadThread.isAlive()) {
            if (this.pageNum + 1 <= this.model.pagesCount) {
                this.firstPage = false;
                this.pageNum++;
                load();
            }
        }
    }

    public void prev() {
        if (!this.loadThread.isAlive()) {
            if (this.pageNum - 1 >= 1) {
                this.firstPage = false;
                this.pageNum--;
                load();
            }
        }
    }

    public void load() {
        //System.out.println("Page: "+this.pageNum);
        if (this.loadThread == null || !this.loadThread.isAlive()) {
            if (!searchTextField.getText().isEmpty()) {
                this.progressIndicator.setProgress(-1);
                this.model.moviesArray.clear();
                this.moviesList.clear();

                if (this.firstPage) {
                    this.pageNum = 1;
                }

                this.loadThread = new Thread(() -> {
                    if (this.model.start(searchTextField.getText(), this.pageNum)) {
                        Collections.sort(this.model.moviesArray);
                        this.moviesList.addAll(this.model.moviesArray);
                        Platform.runLater(() -> updateProgressIndicator(1));

                        this.nextButton.setDisable(this.pageNum + 1 > this.model.pagesCount);
                        this.prevButton.setDisable(this.pageNum - 1 < 1);
                        this.firstPage = true;
                    }
                });
                this.loadThread.start();
            }
        }
    }
    public void updateProgressIndicator(int value) {
        this.progressIndicator.setProgress(value);
    }
}
