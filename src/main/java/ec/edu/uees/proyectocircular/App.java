package ec.edu.uees.proyectocircular;

import modelo.DoublyCircular;
import java.io.File;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.LinkedList;
import java.util.ListIterator;
import static javafx.application.Application.launch;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.StageStyle;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("NuevInterfaz.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Sistema de turnos médicos");
      
        stage.setScene(scene);
        stage.getIcons().add(new Image(App.class.getResourceAsStream("icono.png")));
        stage.setMaximized(true); // Se abre maximizada pero con la barra de tareas visible
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}
