package org.mikel.dein_proyecto_2.controladores;

import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.mikel.dein_proyecto_2.bbdd.ConexionBBDD;
import org.mikel.dein_proyecto_2.modelos.Alumno;
import org.mikel.dein_proyecto_2.modelos.Historico;
import org.mikel.dein_proyecto_2.modelos.Libro;
import org.mikel.dein_proyecto_2.modelos.Prestamo;

import java.sql.Blob;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class bibliotecaController {

    @FXML
    private TableColumn<Alumno, String> colAlumnoApellido1;

    @FXML
    private TableColumn<Alumno, String> colAlumnoApellido2;

    @FXML
    private TableColumn<Alumno, Integer> colAlumnoDni;

    @FXML
    private TableColumn<Alumno, String> colAlumnoNombre;

    @FXML
    private TableColumn<Historico, String> colHistoricoAlumno;

    @FXML
    private TableColumn<Historico, LocalDateTime> colHistoricoDevolucion;

    @FXML
    private TableColumn<Historico, LocalDateTime> colHistoricoFecha;

    @FXML
    private TableColumn<Historico, Integer> colHistoricoID;

    @FXML
    private TableColumn<Historico, Integer> colHistoricoLibro;

    @FXML
    private TableColumn<Libro, String> colLibroAutor;

    @FXML
    private TableColumn<Libro, Integer> colLibroBaja;

    @FXML
    private TableColumn<Libro, Integer> colLibroCodigo;

    @FXML
    private TableColumn<Libro, String> colLibroEditorial;

    @FXML
    private TableColumn<Libro, String> colLibroEstado;

    @FXML
    private TableColumn<Libro, Blob> colLibroImagen;

    @FXML
    private TableColumn<Libro, String> colLibroTitulo;

    @FXML
    private TableColumn<Prestamo, String> colPrestamoAlumno;

    @FXML
    private TableColumn<Prestamo, LocalDateTime> colPrestamoFecha;

    @FXML
    private TableColumn<Prestamo, Integer> colPrestamoID;

    @FXML
    private TableColumn<Prestamo, Integer> colPrestamoLibro;

    @FXML
    private TableView<Alumno> tablaAlumno;

    @FXML
    private TableView<Historico> tablaHistorico;

    @FXML
    private TableView<Libro> tablaLibro;

    @FXML
    private TableView<Prestamo> tablaPrestamo;

    @FXML
    void accionAcercaDe(ActionEvent event) {

    }

    @FXML
    void accionAniadirAlumno(ActionEvent event) {

    }

    @FXML
    void accionAniadirLibro(ActionEvent event) {

    }

    @FXML
    void accionAyuda(ActionEvent event) {

    }

    @FXML
    void accionAñadirHistoricoPrestamos(ActionEvent event) {

    }

    @FXML
    void accionAñadirPrestamos(ActionEvent event) {

    }

    @FXML
    void accionBajaLibro(ActionEvent event) {

    }

    @FXML
    void accionEliminarAlumno(ActionEvent event) {

    }

    @FXML
    void accionModificarAlumno(ActionEvent event) {

    }

    @FXML
    void accionModificarLibro(ActionEvent event) {

    }

    /**
     * Muestra un mensaje de error en una ventana de alerta.
     *
     * @param error El mensaje de error a mostrar en el cuadro de diálogo.
     */
    void mostrarError(String error) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(error);
        alert.showAndWait();
    }

    @FXML
    void initialize() {
        // Controlar acceso a la base de datos
        try {
            new ConexionBBDD();
        } catch (SQLException e) {
            mostrarError("Conexion Erronea a la Base de Datos");
            Platform.exit(); // Cierra la aplicación
            return;
        }

        // Configuración de columnas para Alumnos
        colAlumnoDni.setCellValueFactory(new PropertyValueFactory<>("dni"));
        colAlumnoNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colAlumnoApellido1.setCellValueFactory(new PropertyValueFactory<>("apellido1"));
        colAlumnoApellido2.setCellValueFactory(new PropertyValueFactory<>("apellido2"));

        // Configuración de columnas para Libros
        colLibroCodigo.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        colLibroTitulo.setCellValueFactory(new PropertyValueFactory<>("titulo"));
        colLibroAutor.setCellValueFactory(new PropertyValueFactory<>("autor"));
        colLibroEditorial.setCellValueFactory(new PropertyValueFactory<>("editorial"));
        colLibroEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));
        colLibroBaja.setCellValueFactory(new PropertyValueFactory<>("baja"));

        // Configuración del cell factory para mostrar las imágenes en la columna 'colLibroImagen'
        colLibroImagen.setCellFactory(column -> new TableCell<Libro, Blob>() {
            private final ImageView imageView = new ImageView();
            private final Image imagenPorDefecto = new Image(getClass().getResourceAsStream("/imagenes/iconoLibro.png.png"));

            @Override
            protected void updateItem(Blob item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null) {
                    setGraphic(null); // Limpia el gráfico si la celda está vacía o no tiene imagen
                } else {
                    try {
                        // Convierte el Blob a un flujo binario y luego a una imagen
                        Image image = new Image(item.getBinaryStream());
                        imageView.setImage(image); // Establece la imagen en el ImageView
                    } catch (SQLException e) {
                        e.printStackTrace();
                        imageView.setImage(imagenPorDefecto); // En caso de error, muestra la imagen por defecto
                    }

                    // Configura el tamaño del ImageView y preserva las proporciones
                    imageView.setFitWidth(50); // Ancho de la imagen
                    imageView.setFitHeight(50); // Altura de la imagen
                    imageView.setPreserveRatio(true); // Mantiene las proporciones de la imagen

                    setGraphic(imageView); // Muestra el ImageView en la celda
                }
            }
        });

        // Configuración de columnas para Historico
        colHistoricoID.setCellValueFactory(new PropertyValueFactory<>("id"));
        colHistoricoAlumno.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getAlumno().getDni()));
        colHistoricoLibro.setCellValueFactory(cellData ->
                new SimpleObjectProperty<>(cellData.getValue().getLibro().getCodigo()));
        colHistoricoFecha.setCellValueFactory(cellData ->
                new SimpleObjectProperty<>(cellData.getValue().getFecha_prestamo()));
        colHistoricoDevolucion.setCellValueFactory(cellData ->
                new SimpleObjectProperty<>(cellData.getValue().getFecha_devolucion()));

        // Configuración de columnas para Préstamos
        colPrestamoID.setCellValueFactory(new PropertyValueFactory<>("id"));
        colPrestamoAlumno.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getAlumno().getDni()));
        colPrestamoLibro.setCellValueFactory(cellData ->
                new SimpleObjectProperty<>(cellData.getValue().getLibro().getCodigo()));
        colPrestamoFecha.setCellValueFactory(cellData ->
                new SimpleObjectProperty<>(cellData.getValue().getFecha_prestamo()));

    }

}
