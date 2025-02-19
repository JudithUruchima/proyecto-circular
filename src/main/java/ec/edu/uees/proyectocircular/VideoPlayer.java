/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.edu.uees.proyectocircular;

import Clases.DoublyCircular;
import Clases.Video;
import java.io.File;
import java.time.Clock;
import java.time.LocalDateTime;
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

        File archivo0 = new File("C:\\Users\\judit\\Videos\\perroexplota.mp4");
        Media player0 = new Media(archivo0.toURI().toString());
        Video video0 = new Video(archivo0.getName(), player0, (int) player0.getDuration().toMillis(), LocalDateTime.now());

        File archivo1 = new File("C:\\Users\\judit\\Videos\\Seenojolimón.mp4");
        Media player1 = new Media(archivo1.toURI().toString());
        Video video1 = new Video(archivo1.getName(), player1, (int) player1.getDuration().toMillis(), LocalDateTime.now());

        File archivo2 = new File("C:\\Users\\judit\\Videos\\victorlediceajoel.mp4");
        Media player2 = new Media(archivo2.toURI().toString());
        Video video2 = new Video(archivo2.getName(), player2, (int) player2.getDuration().toMillis(), LocalDateTime.now());

        File archivo3 = new File("C:\\Users\\judit\\Videos\\salsaypicante.mp4");
        Media player3 = new Media(archivo3.toURI().toString());
        Video video3 = new Video(archivo3.getName(), player3, (int) player3.getDuration().toMillis(), LocalDateTime.now());
        File archivo4 = new File("C:\\Users\\judit\\Videos\\monodandovueltas.mp4");
        Media player4 = new Media(archivo4.toURI().toString());
        Video video4 = new Video(archivo4.getName(), player4, (int) player4.getDuration().toMillis(), LocalDateTime.now());

        videoList.addLast(video0);
        videoList.addLast(video1);
        videoList.addLast(video2);
        videoList.addLast(video3);
        videoList.addLast(video4);

        System.out.println(videoList);

        ListIterator<Video> listVideo = videoList.listIterator2();
        System.out.println(listVideo);

        // ListIterator<Video> listVideo = videoList.listIterator(0);
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Media");
        File selectedFile = fileChooser.showOpenDialog(null);

        //if (selectedFile != null) {
        // String url = selectedFile.toURI().toString();
        // Video video = new Video(selectedFile.getName(), url,(int)slider.getValue(); //LocalTime.now(Clock.systemDefaultZone()));
        //videoList.add(video);

        /*  if (currentNode == null) {
                currentNode = videoList.getHead();
                loadMedia(currentNode.video.getPath());
            }
        }*/
        while (listVideo.hasNext()) {
            Video elementoActual = listVideo.next();
            Media mediaActual = elementoActual.getRuta();
            
            mediaPlayer = new MediaPlayer(mediaActual);
            mediaView.setMediaPlayer(mediaPlayer);

            obtenerDuracion(mediaActual);

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

    public void obtenerDuracion(Media player) {
        mediaPlayer.currentTimeProperty().addListener(((observableValue, oldValue, newValue) -> {
            slider.setValue(newValue.toSeconds());
            lblDuration.setText("Duration: " + (int) slider.getValue() + " / " + (int) player.getDuration().toSeconds());
        }));
        mediaPlayer.setOnReady(() -> {
            Duration totalDuration = player.getDuration();
            slider.setMax(totalDuration.toSeconds());
            lblDuration.setText("Duration: 00 / " + (int) media.getDuration().toSeconds());
        });
    }

}
