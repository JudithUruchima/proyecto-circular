/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.edu.uees.proyectocircular;

import java.io.BufferedReader;
import modelo.DoublyCircular;
import modelo.Video;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Clock;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ListIterator;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;
import javafx.stage.Screen;
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
    private ImageView btnPlayIcon;
    @FXML
    private Slider slider;

    private Media mediaActual;

    private MediaPlayer mediaPlayer;

    DoublyCircular<Video> videoList = new DoublyCircular<>();
    ListIterator<Video> listVideo;
    Video elementoActual;
    private boolean previousPressed = false;

    private boolean isPlayed = true;

    private enum Navigation {
        NONE, NEXT, PREVIOUS
    }

    private Navigation lastNav = Navigation.NEXT;

// Constantes para los textos de los botones
    private final Image playImage = new Image(getClass().getResourceAsStream("right_13928849.png"));
    private final Image pauseImage = new Image(getClass().getResourceAsStream("pause.png"));

    @FXML
    void btnPlay(MouseEvent event) {
        if (isPlayed) {
            actualizarEstadoReproduccion(false);
            mediaPlayer.pause();
        } else {
            actualizarEstadoReproduccion(true);
            mediaPlayer.play();
        }
    }

    @FXML
    void btnStop(MouseEvent event) {
        if (isPlayed) {
            mediaPlayer.stop();
            mediaPlayer.play();
            actualizarEstadoReproduccion(true);
        }
    }

    @FXML
    void btnPrevious(MouseEvent event) {
        mediaPlayer.stop();
        playPreviousVideo();
        actualizarEstadoReproduccion(true);
    }

    @FXML
    void btnNext(MouseEvent event) {
        mediaPlayer.stop();
        playNextVideo();
        actualizarEstadoReproduccion(true);
    }

    /**
     * Método para actualizar la UI cuando cambia el estado de reproducción.
     */
    private void actualizarEstadoReproduccion(boolean enReproduccion) {
        isPlayed = enReproduccion;
        //btnPlay.setText(enReproduccion ? PAUSE_TEXT : PLAY_TEXT);
        btnPlayIcon.setImage(enReproduccion ? pauseImage : playImage);
    }

    @FXML
    private void sliderPressed(MouseEvent event) {
        mediaPlayer.seek(Duration.seconds(slider.getValue()));
    }

    public void obtenerDuracion(Media player) {
        mediaPlayer.currentTimeProperty().addListener((observableValue, oldValue, newValue) -> {
            double seconds = newValue.toSeconds();
            slider.setValue(seconds);
            Duration totalDuration = player.getDuration();
            if (totalDuration != null && !totalDuration.equals(Duration.UNKNOWN) && totalDuration.toSeconds() > 0) {
                lblDuration.setText("Duración: " + (int) seconds + " / " + (int) totalDuration.toSeconds());
            } else {
                lblDuration.setText("Duración: " + (int) seconds + " / 0");
            }
        });

        mediaPlayer.setOnReady(() -> {
            Duration totalDuration = player.getDuration();
            if (totalDuration != null && !totalDuration.equals(Duration.UNKNOWN) && totalDuration.toSeconds() > 0) {
                slider.setMax(totalDuration.toSeconds());
            } else {
                System.out.println("Duración no válida: " + totalDuration);
            }
            lblDuration.setText("Duración: 0 / " + (int) totalDuration.toSeconds());
        });
    }

    public void obtenerNombre(Video elemento) {

        nombreVideo.setText(elemento.getNombre());

    }

    public void selectMedia() {
        if (!videoList.isEmpty()) {
            return;
        }

        cargarVideos();

        listVideo = videoList.listIterator2();
        elementoActual = listVideo.next();
        iniciarReproduccion(elementoActual);

    }

    private void cargarVideos() {
        InputStream inputStream = App.class.getResourceAsStream("videos.txt");

        // Verificar si el archivo existe en los recursos
        if (inputStream == null) {
            System.out.println("Archivo videos.txt no encontrado en los recursos.");
            return;
        }

        String basePath = "C:\\Users\\judit\\Videos\\"; // Ruta base de los videos, ya que no se van a mandar en si

        try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(",", 2); // Separar por la primera coma
                if (partes.length < 2) {
                    System.out.println("Línea inválida en videos.txt: " + linea);
                    continue;
                }

                String nombreArchivo = partes[0].trim();
                String titulo = partes[1].trim();

                File file = new File(basePath + nombreArchivo);
                if (file.exists()) {
                    Media media = new Media(file.toURI().toString());
                    Video video = new Video(titulo, media, (int) media.getDuration().toMillis(), LocalDateTime.now());
                    videoList.addLast(video);
                } else {
                    System.out.println("Archivo de video no encontrado: " + nombreArchivo);
                }
            }
        } catch (IOException e) {
            System.out.println("Error al leer videos.txt: " + e.getMessage());
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
        mediaView.setPreserveRatio(true); // Mantener la relación de aspecto del video

        if (scene != null) {
            mediaView.fitWidthProperty().bind(scene.widthProperty().multiply(0.9));  // Ajustar al 80% del ancho de la ventana
            mediaView.fitHeightProperty().bind(scene.heightProperty().multiply(0.7)); // Ajustar al 80% del alto de la ventana

        } else {
            mediaPlayer.setOnReady(() -> {
                Scene readyScene = mediaView.getScene();
                if (readyScene != null) {
                    mediaView.fitWidthProperty().bind(readyScene.widthProperty().multiply(0.9));
                    mediaView.fitHeightProperty().bind(readyScene.heightProperty().multiply(0.7));
                }
            });

        }
        mediaPlayer.setOnEndOfMedia(() -> playNextVideo());

        mediaPlayer.play();
    }

    private void playNextVideo() {
        if (!videoList.isEmpty()) {
            // Si la última acción fue PREVIOUS, avanzar una posición extra para no repetir el video actual
            if (lastNav == Navigation.PREVIOUS) {
                listVideo.next();
            }
            elementoActual = listVideo.next();
            iniciarReproduccion(elementoActual);
            lastNav = Navigation.NEXT;
        }
    }

    private void playPreviousVideo() {
        if (!videoList.isEmpty()) {
            // Si la última acción fue NEXT, retroceder una posición extra para evitar repetir el video actual
            if (lastNav == Navigation.NEXT) {
                listVideo.previous();
            }
            elementoActual = listVideo.previous();
            iniciarReproduccion(elementoActual);
            lastNav = Navigation.PREVIOUS;
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        selectMedia();

        Platform.runLater(() -> {
            Stage stage = (Stage) mediaView.getScene().getWindow();
            stage.setOnCloseRequest(event -> {
                if (mediaPlayer != null) {
                    mediaPlayer.pause(); // Pausar el video si la ventana se cierra
                }

                if (secondaryStage != null && secondaryStage.isShowing()) {
                    secondaryStage.close();
                }

                if (tertiaryStage != null && tertiaryStage.isShowing()) {
                    tertiaryStage.close();
                }

            });

        });
    }

    private static Stage secondaryStage = null; // Almacena la instancia de la ventana

    @FXML
    private void switchToSecondary() throws IOException {
        /*if (secondaryStage == null) { // Si no está abierta, la creamos
            Parent root = FXMLLoader.load(getClass().getResource("secondary.fxml"));

            secondaryStage = new Stage();
            Scene scene = new Scene(root);

            secondaryStage.setTitle("Solicitar Turnos");
            secondaryStage.setScene(scene);
            secondaryStage.setOnCloseRequest(event -> secondaryStage = null); // Cuando se cierra, restablecer variable
            secondaryStage.show();

            Platform.runLater(() -> {
                Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
                double screenWidth = screenBounds.getWidth();

                // Posicionar en la parte superior derecha
                secondaryStage.setX(screenWidth - scene.getWidth() - 5); // 10px de margen
                secondaryStage.setY(0); // 10px de margen desde arriba
            });
        } else {
            if (secondaryStage.isIconified()) { // Si está minimizada
                secondaryStage.setIconified(false); // Restaurar la ventana
            }
            secondaryStage.toFront(); // Traerla al frente
        }*/
        if (secondaryStage == null) {
            Task<Parent> task = new Task<>() {
                @Override
                protected Parent call() throws Exception {
                    return FXMLLoader.load(getClass().getResource("secondaryNueva.fxml"));
                }
            };

            task.setOnSucceeded(event -> {
                Parent root = task.getValue();
                secondaryStage = new Stage();
                Scene scene = new Scene(root);

                secondaryStage.setTitle("Solicitar Turnos");
                secondaryStage.setScene(scene);
                secondaryStage.getIcons().add(new Image(App.class.getResourceAsStream("turno.png")));
                secondaryStage.setOnCloseRequest(e -> secondaryStage = null);
                secondaryStage.show();

                Platform.runLater(() -> {
                    Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
                    double screenWidth = screenBounds.getWidth();
                    secondaryStage.setX(screenWidth - scene.getWidth() - 5);
                    secondaryStage.setY(0);
                });
            });

            task.setOnFailed(event -> task.getException().printStackTrace());

            new Thread(task).start();
        } else {
            if (secondaryStage.isIconified()) {
                secondaryStage.setIconified(false);
            }
            secondaryStage.toFront();
        }
    }

    private static Stage tertiaryStage = null; // Almacena la instancia de la ventana

    @FXML
    private void switchToTertiary() throws IOException {

        //Sin hilos -> no tarda pero el video se congela
        /*if (tertiaryStage == null) { // Si no está abierta, la creamos
            Parent root = FXMLLoader.load(getClass().getResource("tertiary.fxml"));

            tertiaryStage = new Stage();
            Scene scene = new Scene(root);

            tertiaryStage.setTitle("Atención de Turnos");
            tertiaryStage.setScene(scene);
            tertiaryStage.setOnCloseRequest(event -> tertiaryStage = null); // Cuando se cierra, restablecer variable
            tertiaryStage.show();

            // Usar Platform.runLater para asegurarnos de que la ventana tenga tamaño
            Platform.runLater(() -> {
                Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
                double screenWidth = screenBounds.getWidth();
                double screenHeight = screenBounds.getHeight();

                // Posicionar en la parte inferior derecha
                tertiaryStage.setX(screenWidth - scene.getWidth() - 5); // 10px de margen
                tertiaryStage.setY(screenHeight - scene.getHeight() - 31); // 10px de margen desde abajo
            });
        } else {
            if (tertiaryStage.isIconified()) { // Si está minimizada
                tertiaryStage.setIconified(false); // Restaurar la ventana
            }
            tertiaryStage.toFront(); // Traerla al frente
        }
    }*/
        //Con hilos -> tarda mas
        if (tertiaryStage == null) {
            Task<Parent> task = new Task<>() {
                @Override
                protected Parent call() throws Exception {
                    return FXMLLoader.load(getClass().getResource("tertiaryNueva.fxml"));
                }
            };

            task.setOnSucceeded(event -> {
                Parent root = task.getValue();
                tertiaryStage = new Stage();
                Scene scene = new Scene(root);

                tertiaryStage.setTitle("Atención de Turnos");
                tertiaryStage.setScene(scene);
                tertiaryStage.getIcons().add(new Image(App.class.getResourceAsStream("atencion.png")));
                tertiaryStage.setOnCloseRequest(e -> tertiaryStage = null);
                tertiaryStage.show();

                Platform.runLater(() -> {
                    Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
                    double screenWidth = screenBounds.getWidth();
                    double screenHeight = screenBounds.getHeight();
                    tertiaryStage.setX(screenWidth - scene.getWidth() - 5);
                    tertiaryStage.setY(screenHeight - scene.getHeight() - 31);
                });
            });

            task.setOnFailed(event -> task.getException().printStackTrace());

            new Thread(task).start();
        } else {
            if (tertiaryStage.isIconified()) {
                tertiaryStage.setIconified(false);
            }
            tertiaryStage.toFront();
        }
    }
}
