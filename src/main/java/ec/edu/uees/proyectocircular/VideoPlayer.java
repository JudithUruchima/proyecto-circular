/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.edu.uees.proyectocircular;

import Clases.DoublyCircular;
import Clases.Video;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.Clock;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ListIterator;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 *
 * @author judit
 */
public class VideoPlayer implements Initializable {

    @FXML
    private Button btnPlay;
    @FXML
    private Button btnPrevious;

    @FXML
    private Button btnNext;

    @FXML
    private Button btnTurno;

    @FXML
    private Label nombreVideo;

    @FXML
    private Label lblDuration;

    @FXML
    private MediaView mediaView;
    @FXML
    private Slider slider;

    private Media mediaActual;

    private MediaPlayer mediaPlayer;

    DoublyCircular<Video> videoList = new DoublyCircular<>();
    ListIterator<Video> listVideo;
    Video elementoActual;

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
    void btnPrevious(MouseEvent event) {
        btnPrevious.setText("<-");
        mediaPlayer.stop();
        playPreviousVideo();
    }

    @FXML
    void btnNext(MouseEvent event) {
        btnNext.setText("->");
        mediaPlayer.stop();
        playNextVideo();
    }

    /*public void selectMedia() {

        File archivo0 = new File("C:\\Users\\judit\\Videos\\Seenojolimón.mp4");
        Media player0 = new Media(archivo0.toURI().toString());
        Video video0 = new Video("Se enojó limón", player0, (int) player0.getDuration().toMillis(), LocalDateTime.now());

        File archivo1 = new File("C:\\Users\\judit\\Videos\\MECAIGOMELEVANTO.mp4");
        Media player1 = new Media(archivo1.toURI().toString());
        Video video1 = new Video("Me caigo, me levanto", player1, (int) player1.getDuration().toMillis(), LocalDateTime.now());

        File archivo2 = new File("C:\\Users\\judit\\Videos\\victorlediceajoel.mp4");
        Media player2 = new Media(archivo2.toURI().toString());
        Video video2 = new Video("Conversación entre Victor y Joel", player2, (int) player2.getDuration().toMillis(), LocalDateTime.now());

        File archivo3 = new File("C:\\Users\\judit\\Videos\\salsaypicante.mp4");
        Media player3 = new Media(archivo3.toURI().toString());
        Video video3 = new Video("Un video más mi gente", player3, (int) player3.getDuration().toMillis(), LocalDateTime.now());
        File archivo4 = new File("C:\\Users\\judit\\Videos\\monodandovueltas.mp4");
        Media player4 = new Media(archivo4.toURI().toString());
        Video video4 = new Video("Mono dando vueltas", player4, (int) player4.getDuration().toMillis(), LocalDateTime.now());
        File archivo5 = new File("C:\\Users\\judit\\Videos\\cancionamadre.mp4");
        Media player5 = new Media(archivo5.toURI().toString());
        Video video5 = new Video("Canción a la madre", player5, (int) player5.getDuration().toMillis(), LocalDateTime.now());
        File archivo6 = new File("C:\\Users\\judit\\Videos\\perroexplota.mp4");
        Media player6 = new Media(archivo6.toURI().toString());
        Video video6 = new Video("Perro persigue una botella", player6, (int) player6.getDuration().toMillis(), LocalDateTime.now());

        File archivo7 = new File("C:\\Users\\judit\\Videos\\Amor,ComprensionyTernura.mp4");
        Media player7 = new Media(archivo7.toURI().toString());
        Video video7 = new Video("Amor, Comprensión y Ternura", player7, (int) player7.getDuration().toMillis(), LocalDateTime.now());
        videoList.addLast(video0);
        videoList.addLast(video1);
        videoList.addLast(video2);
        videoList.addLast(video3);
        videoList.addLast(video4);
        videoList.addLast(video5);
        videoList.addLast(video6);
        videoList.addLast(video7);

        System.out.println(videoList);

        listVideo = videoList.listIterator2();
        System.out.println(listVideo);

        elementoActual = listVideo.next();

        mediaActual = elementoActual.getRuta();

        elementoActual.setReproducido(isPlayed);

        mediaPlayer = new MediaPlayer(mediaActual);

        mediaView.setMediaPlayer(mediaPlayer);

        obtenerDuracion(mediaActual);
        obtenerNombre(elementoActual);

        Scene scene = mediaView.getScene();
        mediaView.fitWidthProperty().bind(scene.widthProperty());
        mediaView.fitHeightProperty().bind(scene.heightProperty());

        playNextVideo();

    }*/
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
            lblDuration.setText("Duration: 00 / " + (int) player.getDuration().toSeconds());
        });
    }

    public void obtenerNombre(Video elemento) {

        nombreVideo.setText(elemento.getNombre());

    }

    /*private void playNextVideo() {
        if (!listVideo.hasNext()) {
            listVideo = videoList.listIterator2(); // No es necesario si es circular
        }

        elementoActual = listVideo.next(); // Avanza al siguiente (o vuelve al inicio si es circular)
        mediaActual = elementoActual.getRuta();
        elementoActual.setReproducido(true);

        mediaPlayer = new MediaPlayer(mediaActual);
        mediaView.setMediaPlayer(mediaPlayer);
        obtenerDuracion(mediaActual);
        obtenerNombre(elementoActual);

        // Configurar que al finalizar pase al siguiente
        mediaPlayer.setOnEndOfMedia(() -> playNextVideo());

        mediaPlayer.play(); // Iniciar reproducción
    }

    private void playPreviousVideo() {
        if (!listVideo.hasPrevious()) {
            listVideo = videoList.listIterator2(); // No es necesario si es circular
        }

        elementoActual = listVideo.previous(); // Avanza al siguiente (o vuelve al inicio si es circular)
        mediaActual = elementoActual.getRuta();
        elementoActual.setReproducido(true);

        mediaPlayer = new MediaPlayer(mediaActual);
        mediaView.setMediaPlayer(mediaPlayer);

        obtenerDuracion(mediaActual);
        obtenerNombre(elementoActual);
        // Configurar que al finalizar pase al siguiente

        mediaPlayer.setOnEndOfMedia(() -> playNextVideo());

        mediaPlayer.play(); // Iniciar reproducción
    }*/
    public void selectMedia() {
        if (!videoList.isEmpty()) {
            return; // Evita cargar los videos múltiples veces
        }

        cargarVideos(); // Nueva función para cargar solo una vez

        listVideo = videoList.listIterator2();
        elementoActual = listVideo.next();
        iniciarReproduccion(elementoActual);

    }

    private void cargarVideos() {
        String basePath = "C:\\Users\\judit\\Videos\\";
        String[][] archivos = {
            {"Seenojolimón.mp4", "Se enojó limón"},
            {"MECAIGOMELEVANTO.mp4", "Me caigo, me levanto"},
            {"victorlediceajoel.mp4", "Conversación entre Victor y Joel"},
            {"salsaypicante.mp4", "Un video más mi gente"},
            {"monodandovueltas.mp4", "Mono dando vueltas"},
            {"cancionamadre.mp4", "Canción a la madre"},
            {"perroexplota.mp4", "Perro persigue una botella"},
            {"Amor,ComprensionyTernura.mp4", "Amor, Comprensión y Ternura"}
        };

        for (String[] archivo : archivos) {
            File file = new File(basePath + archivo[0]);
            if (file.exists()) {
                Media media = new Media(file.toURI().toString());
                Video video = new Video(archivo[1], media, (int) media.getDuration().toMillis(), LocalDateTime.now());
                videoList.addLast(video);
            }
        }
    }

    private void iniciarReproduccion(Video video) {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.dispose(); // Libera el recurso anterior
        }

        mediaActual = video.getRuta();
        video.setReproducido(true);
        mediaPlayer = new MediaPlayer(mediaActual);
        mediaView.setMediaPlayer(mediaPlayer);

        obtenerDuracion(mediaActual);
        obtenerNombre(video);

        Scene scene = mediaView.getScene();
        if (scene != null) { // Verifica que la escena no sea null
            mediaView.fitWidthProperty().bind(scene.widthProperty());
            mediaView.fitHeightProperty().bind(scene.heightProperty());
        } else {
            mediaPlayer.setOnReady(() -> { // Esperar a que el video esté listo
                Scene readyScene = mediaView.getScene();
                if (readyScene != null) {
                    mediaView.fitWidthProperty().bind(readyScene.widthProperty());
                    mediaView.fitHeightProperty().bind(readyScene.heightProperty());
                }
            });
        }

        mediaPlayer.setOnEndOfMedia(() -> playNextVideo());

        mediaPlayer.play();

    }

// Métodos actualizados para avanzar o retroceder de video
    private void playNextVideo() {
        if (!listVideo.hasNext()) {
            listVideo = videoList.listIterator2(); // Reiniciar iterador si es necesario
        }
        iniciarReproduccion(listVideo.next());
    }

    private void playPreviousVideo() {
        if (!listVideo.hasPrevious()) {
            listVideo = videoList.listIterator2(); // Reiniciar iterador si es necesario
        }
        iniciarReproduccion(listVideo.previous());
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        selectMedia();
    }

    @FXML
    private void switchToSecondary() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("secondary.fxml"));

        Stage stage = new Stage();
        Scene scene = new Scene(root);

        stage.setTitle("Solicitar Turnos");
        stage.setScene(scene);
        stage.show();

    }

}
