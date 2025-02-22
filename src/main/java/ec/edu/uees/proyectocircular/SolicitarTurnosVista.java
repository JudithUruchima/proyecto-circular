/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.edu.uees.proyectocircular;

import DBControlador.TurnoControlador;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import modelo.Enfermedad;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.util.StringConverter;
import modelo.Turno;

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

    @FXML
    private TextField txtapellido;

    @FXML
    private TextField txtnombre;

    @FXML
    private TextField txtcodigo;

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
        /*enfermedades1.add(null);
        enfermedades1.add(new Enfermedad("Paro cardiorrespiratorio o sospecha de infarto", 1));
        enfermedades1.add(new Enfermedad("Shock anafiláctico", 1));
        enfermedades1.add(new Enfermedad("Hemorragia masiva incontrolable", 1));
        enfermedades1.add(new Enfermedad("Accidente cerebrovascular en evolución", 1));
        enfermedades1.add(new Enfermedad("Quemaduras extensas y profundas", 1));

        //Prioridad 2 -> Muy urgente 
        enfermedades2.add(null);
        enfermedades2.add(new Enfermedad("Crisis asmática moderada", 2));
        enfermedades2.add(new Enfermedad("Alteración súbita del estado de conciencia", 2));
        enfermedades2.add(new Enfermedad("Vómitos persistentes con sangre", 2));
        enfermedades2.add(new Enfermedad("Fracturas expuestas o desplazadas", 2));
        enfermedades2.add(new Enfermedad("Dolor abdominal intenso y súbito", 2));

        //Prioridad 3 -> Urgente
        enfermedades3.add(null);
        enfermedades3.add(new Enfermedad("Dolor torácico leve sin signos de infarto", 3));
        enfermedades3.add(new Enfermedad("Crisis hipertensiva sin síntomas neurológicos", 3));
        enfermedades3.add(new Enfermedad("Vómitos y diarrea severos con signos de deshidratación", 3));
        enfermedades3.add(new Enfermedad("Cefalea intensa sin déficits neurológicos", 3));
        enfermedades3.add(new Enfermedad("Fiebre alta en niños sin convulsiones", 3));

        //Prioridad 4 -> Poco Urgente
        enfermedades4.add(null);
        enfermedades4.add(new Enfermedad("Fiebre moderada sin signos de alarma", 4));
        enfermedades4.add(new Enfermedad("Dolor de garganta intenso sin dificultad para respirar", 4));
        enfermedades4.add(new Enfermedad("Dolor de oído moderado", 4));
        enfermedades4.add(new Enfermedad("Pequeñas heridas sin signos de infección", 4));
        enfermedades4.add(new Enfermedad("Síntomas leves de resfriado", 4));

        //Prioridad 5 -> No Urgente
        enfermedades5.add(null);
        enfermedades5.add(new Enfermedad("Resfriado común sin fiebre", 5));
        enfermedades5.add(new Enfermedad("Dolor muscular leve", 5));
        enfermedades5.add(new Enfermedad("Picaduras de insectos sin reacción alérgica grave", 5));
        enfermedades5.add(new Enfermedad("Ansiedad o crisis emocional sin riesgo inmediato", 5));
        enfermedades5.add(new Enfermedad("Molestias gastrointestinales leves (gases, reflujo)", 5));
*/
        String filePath = "C:\\Users\\judit\\Documents\\NetBeansProjects\\proyectoCircular\\src\\main\\java\\ec\\edu\\uees\\proyectocircular\\dolencias.txt";
        //String filePath = getClass().getResource("/ec/edu/uees/proyectocircular/dolencias.txt").getPath();

         //Dolencias nulas para que tenga la oportunidad de cambiar su dolencia en el cbobox
         enfermedades1.add(null);
         enfermedades2.add(null);
         enfermedades3.add(null);
         enfermedades4.add(null);
         enfermedades5.add(null);

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String linea;
            while ((linea = br.readLine()) != null) { // Leer línea por línea
                String[] partes = linea.split(","); // Separar por coma
                if (partes.length == 2) { 
                    String nombre = partes[0].trim(); // Nombre de la enfermedad
                    int prioridad = Integer.parseInt(partes[1].trim()); // Prioridad

                    Enfermedad enfermedad = new Enfermedad(nombre, prioridad);
                    
                    // Agregar la enfermedad a la lista según su prioridad
                    switch (prioridad) {
                        case 1: 
                            enfermedades1.add(enfermedad);
                            break;
                        case 2: 
                            enfermedades2.add(enfermedad);
                            break;
                        case 3: 
                            enfermedades3.add(enfermedad);
                            break;
                        case 4: 
                            enfermedades4.add(enfermedad);
                            break;
                        case 5: 
                            enfermedades5.add(enfermedad);
                            break;
                        default:
                            System.out.println("Prioridad inválida en: " + linea);
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error al leer el archivo: " + e.getMessage());
        }
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

        agregarListeners(cbo1, cbo2, cbo3, cbo4, cbo5);
        agregarListeners(cbo2, cbo1, cbo3, cbo4, cbo5);
        agregarListeners(cbo3, cbo1, cbo2, cbo4, cbo5);
        agregarListeners(cbo4, cbo1, cbo2, cbo3, cbo5);
        agregarListeners(cbo5, cbo1, cbo2, cbo3, cbo4);
        txtcodigo.setText(Integer.toString(turnoCtrl.generarCodigo()));
    }

    private ComboBox<Enfermedad> comboSeleccionado = null;

    private void agregarListeners(ComboBox<Enfermedad> principal, ComboBox<Enfermedad>... otros) {
        principal.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Enfermedad>() {
            @Override
            public void changed(ObservableValue<? extends Enfermedad> observable, Enfermedad oldValue, Enfermedad newValue) {
                if (newValue != null) { // Se ha seleccionado algo
                    comboSeleccionado = principal; // Guardar el ComboBox seleccionado
                    System.out.println("Se ha seleccionado en: " + principal.getId());

                    // Deshabilitar los otros ComboBox
                    for (ComboBox<Enfermedad> combo : otros) {
                        combo.setDisable(true);
                    }
                } else { // Si se borra la selección, volver a habilitar todos
                    comboSeleccionado = null; // Ningún ComboBox está seleccionado
                    for (ComboBox<Enfermedad> combo : otros) {
                        combo.setDisable(false);
                    }
                }
            }
        });
    }

    TurnoControlador turnoCtrl = new TurnoControlador();

    @FXML
    void crearEdificio(ActionEvent event) {
        try {
            String nombre = txtnombre.getText();
            String apellido = txtapellido.getText();
            // Validaciones
            if (nombre.isEmpty() || apellido.isEmpty()) {
                mostrarAlerta(Alert.AlertType.WARNING, "Campos Vacíos", "Por favor, complete todos los campos.");
                return;
            }

            if (!nombre.matches("^[A-Za-zÁÉÍÓÚáéíóúÑñ ]{2,50}$")) {
                mostrarAlerta(Alert.AlertType.WARNING, "Nombre Inválido", "El nombre solo puede contener letras y espacios (2-50 caracteres).");
                return;
            }

            if (!apellido.matches("^[A-Za-zÁÉÍÓÚáéíóúÑñ ]{2,50}$")) {
                mostrarAlerta(Alert.AlertType.WARNING, "Apellido Inválido", "El apellido solo puede contener letras y espacios (2-50 caracteres).");
                return;
            }

            Enfermedad sintoma = getComboSeleccionado().getValue();
            if (sintoma == null) {
                mostrarAlerta(Alert.AlertType.WARNING, "Selección Inválida", "Debe seleccionar un síntoma.");
                return;
            }
            int prioridad = sintoma.getPrioridad();
            Boolean atendido = false;

            Turno turno = new Turno(nombre, apellido, sintoma, prioridad, atendido);
            txtcodigo.setText(Integer.toString(turno.getNumeroTicket()));

            int result = turnoCtrl.Create(turno);

            if (result > 0) {
                mostrarAlerta(Alert.AlertType.INFORMATION, "Registro Exitoso", "El registro ha sido agregado con éxito.");
                limpiarCampos();
            } else {
                mostrarAlerta(Alert.AlertType.ERROR, "Error en Registro", "No se pudo agregar el registro.");
            }

        } catch (Exception e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "Ocurrió un error. Por favor, revise los datos ingresados.");
        }

    }

    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensaje) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }

    // Método para obtener el ComboBox seleccionado en otras partes del código
    public ComboBox<Enfermedad> getComboSeleccionado() {
        if (comboSeleccionado != null) {
            return comboSeleccionado;
        } else {
            return null;
        }
    }

    private void limpiarCampos() {
        txtcodigo.setText(Integer.toString(turnoCtrl.generarCodigo()));
        txtnombre.clear();
        txtapellido.clear();
        getComboSeleccionado().setValue(null); // Limpia el ComboBox
    }

}
