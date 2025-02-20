/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.edu.uees.proyectocircular;

import modelo.DoublyCircular;
import modelo.Video;
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

    private static Stage secondaryStage = null; // Almacena la instancia de la ventana

    @FXML
    private void switchToSecondary() throws IOException {
        if (secondaryStage == null) { // Si no está abierta, la creamos
            Parent root = FXMLLoader.load(getClass().getResource("secondary.fxml"));

            secondaryStage = new Stage();
            Scene scene = new Scene(root);

            secondaryStage.setTitle("Solicitar Turnos");
            secondaryStage.setScene(scene);
            secondaryStage.setOnCloseRequest(event -> secondaryStage = null); // Cuando se cierra, restablecer variable
            secondaryStage.show();
        } else {
            secondaryStage.toFront(); // Si ya está abierta, la trae al frente
        }
    }

}
