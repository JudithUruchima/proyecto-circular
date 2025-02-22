/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.edu.uees.proyectocircular;

import DBControlador.TurnoControlador;
import java.net.URL;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import modelo.Turno;

/**
 *
 * @author judit
 */
public class AtencionVista implements Initializable {

    @FXML
    private Label lblactual;

    @FXML
    private Label lblsiguiente;

    TurnoControlador turnoCtrl = new TurnoControlador();

    public void mostrarTurnoActual(Queue<Turno> colaTurnos) {
        if (colaTurnos == null || colaTurnos.isEmpty()) {
            lblactual.setText("No hay turnos pendientes");
            lblsiguiente.setText("Esperando nuevos turnos");
            return;
        }

        // Extraer y mostrar el turno actual
        Turno turnoActual = colaTurnos.poll();
        if (turnoActual != null) {
            lblactual.setText(turnoActual.toString());
            turnoCtrl.ActualizarAtendido(turnoActual.getNumeroTicket());
        } else {
            lblactual.setText("No hay turnos pendientes");
        }

        // Mostrar el siguiente turno si existe
        Turno turnoSiguiente = colaTurnos.peek();
        if (turnoSiguiente != null) {
            lblsiguiente.setText(turnoSiguiente.toString());
        } else {
            lblsiguiente.setText("No hay más turnos");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        mostrarTurnoActual(turnoCtrl.ListarTurnos());
    }

}
