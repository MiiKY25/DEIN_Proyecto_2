package org.mikel.dein_proyecto_2.dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.mikel.dein_proyecto_2.bbdd.ConexionBBDD;
import org.mikel.dein_proyecto_2.modelos.Alumno;
import org.mikel.dein_proyecto_2.modelos.Libro;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;

public class DaoLibro {


    public static ObservableList<Libro> todosLibros() {
        ConexionBBDD connection;
        ObservableList<Libro> libros = FXCollections.observableArrayList();
        try {
            connection = new ConexionBBDD();
            String consulta = "SELECT codigo,titulo,autor,editorial,estado,baja,imagen FROM Libro";
            PreparedStatement pstmt = connection.getConnection().prepareStatement(consulta);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                int codigo = rs.getInt(1);
                String titulo = rs.getString(2);
                String autor = rs.getString(3);
                String editorial = rs.getString(4);
                String estado = rs.getString(5);
                int baja = rs.getInt(6);
                Blob foto = rs.getBlob(7);
                Libro l=new Libro(codigo,titulo,autor,editorial,estado,baja,foto);
                libros.add(l);
            }
            rs.close();
            connection.CloseConexion();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return libros;
    }

    public static Libro LibroID(String codigo_libro) {
        ConexionBBDD connection;
        Libro libro = null;
        try {
            connection = new ConexionBBDD();
            String consulta = "SELECT codigo,titulo,autor,editorial,estado,baja,imagen FROM Libro WHERE codigo = ?";
            PreparedStatement pstmt = connection.getConnection().prepareStatement(consulta);
            pstmt.setString(1, codigo_libro);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                int codigo = rs.getInt(1);
                String titulo = rs.getString(2);
                String autor = rs.getString(3);
                String editorial = rs.getString(4);
                String estado = rs.getString(5);
                int baja = rs.getInt(6);
                Blob foto = rs.getBlob(7);
                libro = new Libro(codigo,titulo,autor,editorial,estado,baja,foto);
            }
            rs.close();
            connection.CloseConexion();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return libro;
    }

    public static boolean crearLibro(Libro l) {
        ConexionBBDD connection;
        int resul = 0;
        try {
            connection = new ConexionBBDD();
            String consulta = "INSERT INTO Libro (titulo,autor,editorial,estado,baja,imagen) VALUES (?,?,?,?,?,?) ";
            PreparedStatement pstmt = connection.getConnection().prepareStatement(consulta);
            pstmt.setString(1, l.getTitulo());
            pstmt.setString(2, l.getAutor());
            pstmt.setString(3, l.getEditorial());
            pstmt.setString(4, l.getEstado());
            pstmt.setInt(5, l.getBaja());
            pstmt.setBlob(6, l.getFoto());

            resul = pstmt.executeUpdate();
            pstmt.close();
            connection.CloseConexion();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resul > 0;
    }

    public static boolean editarLibro(Libro l) {
        ConexionBBDD connection;
        int resul = 0;
        try {
            connection = new ConexionBBDD();
            String consulta = "UPDATE Libro SET titulo = ?,autor = ?,editorial = ?,estado = ?,baja = ?,imagen = ? WHERE codigo = ?";
            PreparedStatement pstmt = connection.getConnection().prepareStatement(consulta);
            pstmt.setString(1, l.getTitulo());
            pstmt.setString(2, l.getAutor());
            pstmt.setString(3, l.getEditorial());
            pstmt.setString(4, l.getEstado());
            pstmt.setInt(5, l.getBaja());
            pstmt.setBlob(6, l.getFoto());
            pstmt.setInt(7, l.getCodigo());

            resul = pstmt.executeUpdate();
            pstmt.close();
            connection.CloseConexion();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resul > 0;
    }

    public static boolean darDeBajaLibro(int codigo) {
        ConexionBBDD connection;
        int resul = 0;
        try {
            connection = new ConexionBBDD();
            // Consulta para actualizar el estado del libro a 'baja = 0'
            String consulta = "UPDATE Libro SET baja = 0 WHERE codigo = ?";
            PreparedStatement pstmt = connection.getConnection().prepareStatement(consulta);
            pstmt.setInt(1, codigo);

            resul = pstmt.executeUpdate();
            pstmt.close();
            connection.CloseConexion();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resul > 0;
    }

    public static ObservableList<Libro> todosLibrosActivos() {
        ConexionBBDD connection;
        ObservableList<Libro> libros = FXCollections.observableArrayList();
        try {
            connection = new ConexionBBDD();
            String consulta = "SELECT codigo,titulo,autor,editorial,estado,baja,imagen FROM Libro WHERE baja=1";
            PreparedStatement pstmt = connection.getConnection().prepareStatement(consulta);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                int codigo = rs.getInt(1);
                String titulo = rs.getString(2);
                String autor = rs.getString(3);
                String editorial = rs.getString(4);
                String estado = rs.getString(5);
                int baja = rs.getInt(6);
                Blob foto = rs.getBlob(7);
                Libro l=new Libro(codigo,titulo,autor,editorial,estado,baja,foto);
                libros.add(l);
            }
            rs.close();
            connection.CloseConexion();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return libros;
    }

    public static ObservableList<Libro> todosLibrosParaPrestar() {
        ConexionBBDD connection;
        ObservableList<Libro> libros = FXCollections.observableArrayList();
        try {
            connection = new ConexionBBDD();
            String consulta = """
            SELECT l.codigo, l.titulo, l.autor, l.editorial, l.estado, l.baja, l.imagen
            FROM Libro l
            LEFT JOIN Prestamo p ON l.codigo = p.codigo_libro
            WHERE l.baja = 1 AND p.id_prestamo IS NULL
        """;
            PreparedStatement pstmt = connection.getConnection().prepareStatement(consulta);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                int codigo = rs.getInt(1);
                String titulo = rs.getString(2);
                String autor = rs.getString(3);
                String editorial = rs.getString(4);
                String estado = rs.getString(5);
                int baja = rs.getInt(6);
                Blob foto = rs.getBlob(7);
                Libro l = new Libro(codigo, titulo, autor, editorial, estado, baja, foto);
                libros.add(l);
            }
            rs.close();
            pstmt.close();
            connection.CloseConexion();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return libros;
    }


    /**
     * Convierte un archivo en un objeto Blob que puede ser almacenado en la base de datos.
     *
     * @param file El archivo a convertir.
     * @return Un objeto Blob con los datos del archivo.
     * @throws SQLException Si ocurre un error al crear el Blob.
     * @throws IOException Si ocurre un error al leer el archivo.
     */
    public static Blob convertirFicheroABlob(File file) throws SQLException, IOException {
        ConexionBBDD connection = new ConexionBBDD();

        try (Connection conn = connection.getConnection();
             FileInputStream inputStream = new FileInputStream(file)) {
            Blob blob = conn.createBlob();

            byte[] buffer = new byte[1024];
            int bytesRead;

            try (var outputStream = blob.setBinaryStream(1)) {
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }
            }
            return blob;
        }
    }
}
