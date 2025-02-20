/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.edu.uees.proyectocircular;

import Clases.Enfermedad;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.util.StringConverter;

/**
 *
 * @author judit
 */
public class SolicitarTurnosVista implements Initializable {

    @FXML
    private ComboBox<Enfermedad> cbo1;

    @FXML
    private ComboBox<Enfermedad> cbo2;

    @FXML
    private ComboBox<Enfermedad> cbo3;

    @FXML
    private ComboBox<Enfermedad> cbo4;

    @FXML
    private ComboBox<Enfermedad> cbo5;


    /* El siguiente proyecto deben realizarlo en grupos de 2 estudiantes.
El proyecto consiste en realizar un sistema de atención de turnos por prioridad para un centro de salud. El paciente debe ingresar sus nombres y seleccionar la dolencia que tiene. Una vez que ingresa esta información será ingresado a la cola de prioridad. La dolencia establece la prioridad que tiene su atención. Estas dolencias deben estar con su prioridad en un archivo de texto plano.
Para atender un paciente debe existir un botón que diga "Atención" y deberá mostrar los datos del paciente que debe ser atendido.
El sistema debe mostrar entretenimiento continuo durante la espera de los pacientes para ello debe utilizar una lista doblemente enlazada circular que está reproduciendo continuamente videos. La interfaz gráfica debe ofrecer botones para ir al siguiente video, al previo, pausar y reanudar la reproducción del video. Los nombres de los archivos deben estar en texto plano.*/
      
    ArrayList<Enfermedad> enfermedades1 = new ArrayList<>();
    ArrayList<Enfermedad> enfermedades2 = new ArrayList<>();
    ArrayList<Enfermedad> enfermedades3 = new ArrayList<>();
    ArrayList<Enfermedad> enfermedades4 = new ArrayList<>();
    ArrayList<Enfermedad> enfermedades5 = new ArrayList<>();

    public void aniadirEnfermedades() {
        //Priodidades basadas en el triaje de manchester
        //Prioridad 1 -> Emergencia
        enfermedades1.add(new Enfermedad("Paro cardiorrespiratorio o sospecha de infarto", 1));
        enfermedades1.add(new Enfermedad("Shock anafiláctico", 1));
        enfermedades1.add(new Enfermedad("Hemorragia masiva incontrolable", 1));
        enfermedades1.add(new Enfermedad("Accidente cerebrovascular en evolución", 1));
        enfermedades1.add(new Enfermedad("Quemaduras extensas y profundas", 1));
        
        //Prioridad 2 -> Muy urgente 
        enfermedades2.add(new Enfermedad("Crisis asmática moderada", 2));
        enfermedades2.add(new Enfermedad("Alteración súbita del estado de conciencia", 2));
        enfermedades2.add(new Enfermedad("Vómitos persistentes con sangre", 2));
        enfermedades2.add(new Enfermedad("Fracturas expuestas o desplazadas", 2));
        enfermedades2.add(new Enfermedad("Dolor abdominal intenso y súbito", 2));
        
        //Prioridad 3 -> Urgente
        enfermedades3.add(new Enfermedad("Dolor torácico leve sin signos de infarto", 3));
        enfermedades3.add(new Enfermedad("Crisis hipertensiva sin síntomas neurológicos", 3));
        enfermedades3.add(new Enfermedad("Vómitos y diarrea severos con signos de deshidratación", 3));
        enfermedades3.add(new Enfermedad("Cefalea intensa sin déficits neurológicos", 3));
        enfermedades3.add(new Enfermedad("Fiebre alta en niños sin convulsiones", 3));

         //Prioridad 4 -> Poco Urgente
        enfermedades4.add(new Enfermedad("Fiebre moderada sin signos de alarma", 4));
        enfermedades4.add(new Enfermedad("Dolor de garganta intenso sin dificultad para respirar", 4));
        enfermedades4.add(new Enfermedad("Dolor de oído moderado", 4));
        enfermedades4.add(new Enfermedad("Pequeñas heridas sin signos de infección", 4));
        enfermedades4.add(new Enfermedad("Síntomas leves de resfriado", 4));
        
        //Prioridad 5 -> No Urgente
        enfermedades5.add(new Enfermedad("Resfriado común sin fiebre", 5));
        enfermedades5.add(new Enfermedad("Dolor muscular leve", 5));
        enfermedades5.add(new Enfermedad("Picaduras de insectos sin reacción alérgica grave", 5));
        enfermedades5.add(new Enfermedad("Ansiedad o crisis emocional sin riesgo inmediato", 5));
        enfermedades5.add(new Enfermedad("Molestias gastrointestinales leves (gases, reflujo)", 5));

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        new Thread(() -> {
            aniadirEnfermedades();

            // Convertir listas a ObservableList
            ObservableList<Enfermedad> lista1 = FXCollections.observableArrayList(enfermedades1);
            ObservableList<Enfermedad> lista2 = FXCollections.observableArrayList(enfermedades2);
            ObservableList<Enfermedad> lista3 = FXCollections.observableArrayList(enfermedades3);
            ObservableList<Enfermedad> lista4 = FXCollections.observableArrayList(enfermedades4);
            ObservableList<Enfermedad> lista5 = FXCollections.observableArrayList(enfermedades5);

            // Actualizar UI en el hilo principal
            Platform.runLater(() -> {
                cbo1.setItems(lista1);
                cbo2.setItems(lista2);
                cbo3.setItems(lista3);
                cbo4.setItems(lista4);
                cbo5.setItems(lista5);
            });
        }).start();
    }

}
