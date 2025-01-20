package org.mikel.dein_proyecto_2.dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.mikel.dein_proyecto_2.bbdd.ConexionBBDD;
import org.mikel.dein_proyecto_2.modelos.Alumno;
import org.mikel.dein_proyecto_2.modelos.Historico;
import org.mikel.dein_proyecto_2.modelos.Libro;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class DaoHistorico {

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
                LocalDateTime fecha_prestamo = rs.getTimestamp(4).toLocalDateTime();
                LocalDateTime fecha_devolucion = rs.getTimestamp(5).toLocalDateTime();

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
}
