package org.mikel.dein_proyecto_2.dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.mikel.dein_proyecto_2.bbdd.ConexionBBDD;
import org.mikel.dein_proyecto_2.modelos.Alumno;
import org.mikel.dein_proyecto_2.modelos.Historico;
import org.mikel.dein_proyecto_2.modelos.Libro;
import org.mikel.dein_proyecto_2.modelos.Prestamo;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Clase DaoHistorico para gestionar el acceso a los datos del histórico de préstamos.
 */
public class DaoHistorico {

    /**
     * Obtiene todos los registros del histórico de préstamos.
     *
     * @return Una lista observable con todos los registros del histórico.
     */
    public static ObservableList<Historico> todosHistoricos() {
        ConexionBBDD connection;
        ObservableList<Historico> historicos = FXCollections.observableArrayList();
        try {
            connection = new ConexionBBDD();
            String consulta = "SELECT id_prestamo,dni_alumno,codigo_libro,fecha_prestamo,fecha_devolucion FROM Historico_prestamo";
            PreparedStatement pstmt = connection.getConnection().prepareStatement(consulta);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                int id_prestamo = rs.getInt(1);
                String dni_alumno = rs.getString(2);
                String codigo_libro = rs.getString(3);
                LocalDate fecha_prestamo = rs.getDate(4).toLocalDate();
                LocalDate fecha_devolucion = rs.getDate(5).toLocalDate();

                Alumno a= DaoAlumno.AlumnoID(dni_alumno);
                Libro l= DaoLibro.LibroID(codigo_libro);

                Historico h =new Historico(id_prestamo,a,l,fecha_prestamo,fecha_devolucion);
                historicos.add(h);
            }
            rs.close();
            connection.CloseConexion();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return historicos;
    }

    /**
     * Registra un préstamo en el histórico y lo elimina de la lista de préstamos activos.
     *
     * @param p El préstamo que se desea registrar en el histórico.
     * @return true si la operación fue exitosa, false en caso contrario.
     */
    public static boolean crearHistorico(Prestamo p) {
        ConexionBBDD connection;
        int resul = 0;
        try {
            connection = new ConexionBBDD();
            String consulta = "INSERT INTO Historico_prestamo (dni_alumno,codigo_libro,fecha_prestamo,fecha_devolucion) VALUES (?,?,?,?) ";
            PreparedStatement pstmt = connection.getConnection().prepareStatement(consulta);
            pstmt.setString(1, p.getAlumno().getDni());
            pstmt.setInt(2, p.getLibro().getCodigo());
            // Fecha LocalDate
            pstmt.setDate(3, Date.valueOf(p.getFecha_prestamo()));
            LocalDate fechaHoy = LocalDate.now();
            pstmt.setDate(4, Date.valueOf(fechaHoy));

            resul = pstmt.executeUpdate();
            pstmt.close();
            connection.CloseConexion();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        DaoPrestamo.eliminarPrestamo(p);
        return resul > 0;
    }
}
