package org.mikel.dein_proyecto_2.dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.mikel.dein_proyecto_2.bbdd.ConexionBBDD;
import org.mikel.dein_proyecto_2.modelos.Alumno;
import org.mikel.dein_proyecto_2.modelos.Libro;
import org.mikel.dein_proyecto_2.modelos.Prestamo;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class DaoPrestamo {

    public static ObservableList<Prestamo> todosPrestamos() {
        ConexionBBDD connection;
        ObservableList<Prestamo> prestamos = FXCollections.observableArrayList();
        try {
            connection = new ConexionBBDD();
            String consulta = "SELECT id_prestamo,dni_alumno,codigo_libro,fecha_prestamo FROM Prestamo";
            PreparedStatement pstmt = connection.getConnection().prepareStatement(consulta);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                int id_prestamo = rs.getInt(1);
                String dni_alumno = rs.getString(2);
                String codigo_libro = rs.getString(3);
                LocalDate fecha_prestamo = rs.getDate(4).toLocalDate();

                Alumno a= DaoAlumno.AlumnoID(dni_alumno);
                Libro l= DaoLibro.LibroID(codigo_libro);

                Prestamo p =new Prestamo(id_prestamo,a,l,fecha_prestamo);
                prestamos.add(p);
            }
            rs.close();
            connection.CloseConexion();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return prestamos;
    }

    public static boolean crearPrestamo(Prestamo p) {
        ConexionBBDD connection;
        int resul = 0;
        try {
            connection = new ConexionBBDD();
            String consulta = "INSERT INTO Prestamo (dni_alumno,codigo_libro,fecha_prestamo) VALUES (?,?,?) ";
            PreparedStatement pstmt = connection.getConnection().prepareStatement(consulta);
            pstmt.setString(1, p.getAlumno().getDni());
            pstmt.setInt(2, p.getLibro().getCodigo());
            // Fecha LocalDate
            pstmt.setDate(3, Date.valueOf(p.getFecha_prestamo()));

            resul = pstmt.executeUpdate();
            pstmt.close();
            connection.CloseConexion();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resul > 0;
    }

    public static boolean eliminarPrestamo(Prestamo p) {
        ConexionBBDD connection;
        int resul = 0;
        try {
            connection = new ConexionBBDD();
            String consulta = "DELETE FROM Prestamo WHERE id_prestamo = ?";
            PreparedStatement pstmt = connection.getConnection().prepareStatement(consulta);
            pstmt.setInt(1, p.getId());

            resul = pstmt.executeUpdate();
            pstmt.close();
            connection.CloseConexion();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return resul > 0;
    }
}
