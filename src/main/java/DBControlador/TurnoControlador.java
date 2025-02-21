/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DBControlador;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import modelo.DBConnection;
import modelo.Enfermedad;
import modelo.Turno;

/**
 *
 * @author judit
 */
public class TurnoControlador {

    private DBConnection connection = new DBConnection();

    public int Create(Turno turno) {
        int result = 0;
        String req = "INSERT INTO turno(id_turno, nombre, apellido, sintoma, prioridad)" + "VALUES (?, ?, ?, ?, ?);"; //Consulta para crear un perfil en la BD
        try {
            if (this.connection.conectar()) {
                PreparedStatement statement = this.connection.getConnection().prepareStatement(req); //Prepara la sentencia que vamos a envia
                statement.setInt(1, turno.getNumeroTicket());
                statement.setString(2, turno.getNombre()); //El indice, es del parametro 
                statement.setString(3, turno.getApellido()); //El indice, es del parametro 
                statement.setString(4, turno.getSintoma().getNombre());
                statement.setInt(5, turno.getSintoma().getPrioridad());

                result = statement.executeUpdate(); //Se ejecuta la sentencia, el result me devuelve el numero de filas afectadas
            } else { //Si no se logro conectar la base de datos
                System.out.println("No conecta");
            }
        } catch (SQLException e) { //Controla la excepcion sql por si algo sale mal
            throw new RuntimeException(e);
        } finally {
            if (this.connection != null) {
                connection.desconectar();
            }
        }
        return result;
    }

    public int generarCodigo() {
        int codigo = 1; // Por defecto, si la tabla está vacía, el primer código será 1.
        String req = "SELECT MAX(id_turno) AS MaxTurno FROM turno";

        try {
            this.connection.conectar();
            PreparedStatement statement = this.connection.getConnection().prepareStatement(req);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) { // Verificamos si hay un resultado
                codigo = resultSet.getInt("MaxTurno") + 1;
            }

            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            throw new RuntimeException("Error al generar el código", e);
        } finally {
            if (this.connection != null) {
                this.connection.desconectar();
            }
        }

        return codigo;
    }

    //Esto para la cola
    public Queue<Turno> ListarTurnos() { //Consulta todos los usuarios del DB
        Queue<Turno> turnos = new LinkedList<>();

        String req = "SELECT * FROM turno"; // Unimos usuario con perfil;
        try {
            this.connection.conectar();
            PreparedStatement statement = this.connection.getConnection().prepareStatement(req);

            ResultSet resultSet = statement.executeQuery(); //Ejecuta la consulta, que dara un resultado
            while (resultSet.next()) { //Recorre hasta que ya no hay un siguiente
                int codigo = resultSet.getInt("id_turno");
                String nombre = resultSet.getString("nombre");
                String apellido = resultSet.getString("direccion");
                String sintoma = resultSet.getString("sintoma");
                int prioridad = resultSet.getInt("prioridad");

                Enfermedad enfermedad = new Enfermedad(sintoma, prioridad);

                turnos.add(new Turno(codigo, nombre, apellido, enfermedad, prioridad));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (this.connection != null) {
                this.connection.desconectar();
            }
        }
        return turnos;

    }

}
