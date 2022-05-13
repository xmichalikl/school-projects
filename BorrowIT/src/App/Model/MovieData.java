package App.Model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.awt.*;
import java.net.URI;
import java.net.URISyntaxException;

public class MovieData implements Comparable<MovieData> {
    public SimpleStringProperty movieName;
    public SimpleStringProperty movieLink;
    public SimpleIntegerProperty movieQuality;
    public Button moviePlayButton;
    public ImageView movieImage;
    public int movieId;

    public MovieData(int id, String name, String link, int quality, String imgURL, int mode) {
        this.movieId = id;
        this.movieName = new SimpleStringProperty(name);
        this.movieLink = new SimpleStringProperty(link);
        this.movieQuality = new SimpleIntegerProperty(quality);

        if (mode == 2) {
            Image img = new Image(imgURL);
            this.movieImage = new ImageView(img);
            //this.movieImage.setFitHeight(95);
            //this.movieImage.setFitWidth(175);
            this.movieImage.setPreserveRatio(true);

            this.moviePlayButton = new Button();
            this.moviePlayButton.setPrefWidth(50);
            this.moviePlayButton.setText("Play movie!");
            this.moviePlayButton.setOnAction(actionEvent -> {
                try {
                    openWebpage(new URI(link));
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    public void openWebpage(URI uri) {
        Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
        if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
            try {
                desktop.browse(uri);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public String getMovieName() {
        return movieName.get();
    }

    public void setMovieName(String movieName) {
        this.movieName.set(movieName);
    }

    public String getMovieLink() {
        return movieLink.get();
    }

    public void setMovieLink(String movieLink) {
        this.movieLink.set(movieLink);
    }

    public int getMovieQuality() {
        return movieQuality.get();
    }

    public void setMovieQuality(int movieQuality) {
        this.movieQuality.set(movieQuality);
    }

    public Button getMoviePlayButton() {
        return moviePlayButton;
    }

    public void setMoviePlayButton(Button moviePlayButton) {
        this.moviePlayButton = moviePlayButton;
    }

    public ImageView getMovieImage() {
        return movieImage;
    }

    public String toString() {
        return this.movieName + " - " + this.movieQuality + "p: " + this.movieLink;
    }

    @Override
    public int compareTo(MovieData movieData) {
        if (this.movieId < movieData.movieId) {
            return -1;
        }
        else if (movieData.movieId < this.movieId) {
            return 1;
        }
        return 0;
    }
}
