/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.edu.uees.proyectocircular;

import Clases.DoublyCircular;
import Clases.Video;
import java.io.File;
import java.time.Clock;
import java.time.LocalTime;
import java.util.ListIterator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;
import javafx.util.Duration;

/**
 *
 * @author judit
 */
public class VideoPlayer {

    @FXML
    private Button btnPlay;

    @FXML
    private Label lblDuration;

    @FXML
    private MediaView mediaView;
    @FXML
    private Slider slider;

    private Media media;
    private MediaPlayer mediaPlayer;

    DoublyCircular<Video> videoList = new DoublyCircular<>();

    File archivo = new File("C:/Videos/ejemplo.mp4");
    Media media = new Media(archivo.toURI().toString());

    String nombreArchivo = archivo.getName();

    System.out.println ("Nombre del archivo: " + nombreArchivo);

    videoList.add(new Video());
    
    ListIterator<Video> listVideo = videoList.listIterator(0);

    private boolean isPlayed = false;

    @FXML
    void btnPlay(MouseEvent event) {
        if (!isPlayed) {
            btnPlay.setText("Pause");
            mediaPlayer.play();
            isPlayed = true;
        } else {
            btnPlay.setText("Play");
            mediaPlayer.pause();
            isPlayed = false;
        }
    }

    @FXML
    void btnStop(MouseEvent event) {
        btnPlay.setText("Play");
        mediaPlayer.stop();
        isPlayed = false;
    }

    @FXML
    void selectMedia(ActionEvent event) {

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Media");
        File selectedFile = fileChooser.showOpenDialog(null);

        if (selectedFile != null) {
            String url = selectedFile.toURI().toString();
            // Video video = new Video(selectedFile.getName(), url,(int)slider.getValue(); //LocalTime.now(Clock.systemDefaultZone()));
            //videoList.add(video);

            /*  if (currentNode == null) {
                currentNode = videoList.getHead();
                loadMedia(currentNode.video.getPath());
            }
        }*/
            mediaPlayer.currentTimeProperty().addListener(((observableValue, oldValue, newValue) -> {
                slider.setValue(newValue.toSeconds());
                lblDuration.setText("Duration: " + (int) slider.getValue() + " / " + (int) media.getDuration().toSeconds());
            }));

            mediaPlayer.setOnReady(() -> {
                Duration totalDuration = media.getDuration();
                slider.setMax(totalDuration.toSeconds());
                lblDuration.setText("Duration: 00 / " + (int) media.getDuration().toSeconds());
            });

            Scene scene = mediaView.getScene();
            mediaView.fitWidthProperty().bind(scene.widthProperty());
            mediaView.fitHeightProperty().bind(scene.heightProperty());

            mediaPlayer.setAutoPlay(true);

        }

    }

    @FXML
    private void sliderPressed(MouseEvent event) {
        mediaPlayer.seek(Duration.seconds(slider.getValue()));
    }

}
