package org.mikel.dein_proyecto_2.dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.mikel.dein_proyecto_2.bbdd.ConexionBBDD;
import org.mikel.dein_proyecto_2.modelos.Alumno;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DaoAlumno {

    public static ObservableList<Alumno> todosAlumnos() {
        ConexionBBDD connection;
        ObservableList<Alumno> alumnos = FXCollections.observableArrayList();
        try {
            connection = new ConexionBBDD();
            String consulta = "SELECT dni,nombre,apellido1,apellido2 FROM Alumno";
            PreparedStatement pstmt = connection.getConnection().prepareStatement(consulta);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                String dni = rs.getString(1);
                String nombre = rs.getString(2);
                String apellido1 = rs.getString(3);
                String apellido2 = rs.getString(4);
                Alumno a =new Alumno(dni,nombre,apellido1,apellido2);
                alumnos.add(a);
            }
            rs.close();
            connection.CloseConexion();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return alumnos;
    }

    public static Alumno AlumnoID(String dni_alumno) {
        ConexionBBDD connection;
        Alumno alumno = null;
        try {
            connection = new ConexionBBDD();
            String consulta = "SELECT dni,nombre,apellido1,apellido2 FROM Alumno WHERE dni = ?";
            PreparedStatement pstmt = connection.getConnection().prepareStatement(consulta);
            pstmt.setString(1, dni_alumno);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                String dni = rs.getString(1);
                String nombre = rs.getString(2);
                String apellido1 = rs.getString(3);
                String apellido2 = rs.getString(4);
                alumno = new Alumno(dni,nombre,apellido1,apellido2);
            }
            rs.close();
            connection.CloseConexion();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return alumno;
    }

    public static boolean crearAlumno(Alumno a) {
        ConexionBBDD connection;
        int resul = 0;
        try {
            connection = new ConexionBBDD();
            String consulta = "INSERT INTO Alumno (dni,nombre,apellido1,apellido2) VALUES (?,?,?,?) ";
            PreparedStatement pstmt = connection.getConnection().prepareStatement(consulta);
            pstmt.setString(1, a.getDni());
            pstmt.setString(2, a.getNombre());
            pstmt.setString(3, a.getApellido1());
            pstmt.setString(4, a.getApellido2());

            resul = pstmt.executeUpdate();
            pstmt.close();
            connection.CloseConexion();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resul > 0;
    }

    public static boolean editarAlumno(Alumno a) {
        ConexionBBDD connection;
        int resul = 0;
        try {
            connection = new ConexionBBDD();
            String consulta = "UPDATE Alumno SET nombre = ?,apellido1 = ?,apellido2 = ? WHERE dni = ?";
            PreparedStatement pstmt = connection.getConnection().prepareStatement(consulta);
            pstmt.setString(1, a.getNombre());
            pstmt.setString(2, a.getApellido1());
            pstmt.setString(3, a.getApellido2());
            pstmt.setString(4, a.getDni());

            resul = pstmt.executeUpdate();
            pstmt.close();
            connection.CloseConexion();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resul > 0;
    }

    public static boolean existeDNI(String dni) {
        ConexionBBDD connection;
        boolean existe = false;
        try {
            connection = new ConexionBBDD();
            String consulta = "SELECT COUNT(*) FROM Alumno WHERE dni = ?";
            PreparedStatement pstmt = connection.getConnection().prepareStatement(consulta);
            pstmt.setString(1, dni);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                existe = rs.getInt(1) > 0;
            }
            rs.close();
            connection.CloseConexion();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return existe;
    }



}
