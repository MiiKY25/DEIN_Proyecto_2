package org.mikel.dein_proyecto_2.controladores;

import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.mikel.dein_proyecto_2.bbdd.ConexionBBDD;
import org.mikel.dein_proyecto_2.dao.DaoAlumno;
import org.mikel.dein_proyecto_2.dao.DaoHistorico;
import org.mikel.dein_proyecto_2.dao.DaoLibro;
import org.mikel.dein_proyecto_2.dao.DaoPrestamo;
import org.mikel.dein_proyecto_2.modelos.Alumno;
import org.mikel.dein_proyecto_2.modelos.Historico;
import org.mikel.dein_proyecto_2.modelos.Libro;
import org.mikel.dein_proyecto_2.modelos.Prestamo;

import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;

public class ControllerBiblioteca {

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
    private TableColumn<Prestamo, LocalDate> colPrestamoFecha;

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
    private Button btnBajaLibro;

    @FXML
    private Button btnEditarAlumno;

    @FXML
    private Button btnEditarLibro;

    @FXML
    private Button btnEliminarAlumno;

    @FXML
    void accionAniadirLibro(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/Libro.fxml"));
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("Añadir Libro");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.initModality(javafx.stage.Modality.APPLICATION_MODAL);

            // Establecer un evento que se ejecute cuando se cierre la ventana
            stage.setOnHidden(windowEvent -> cargarLibros());
            stage.show();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    void accionModificarLibro(ActionEvent event) {
        Libro libro=tablaLibro.getSelectionModel().getSelectedItem();
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/Libro.fxml"));
            Parent root = fxmlLoader.load();

            ControllerLibro controller=fxmlLoader.getController();
            controller.setLibro(libro);

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("Editar Libro");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.initModality(javafx.stage.Modality.APPLICATION_MODAL);

            // Establecer un evento que se ejecute cuando se cierre la ventana
            stage.setOnHidden(windowEvent -> cargarLibros());
            stage.show();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    void accionBajaLibro(ActionEvent event) {
        Libro libro=tablaLibro.getSelectionModel().getSelectedItem();
        if (DaoLibro.darDeBajaLibro(libro.getCodigo())){
            mostrarInfo("Libro dado de baja correctamente");
            cargarLibros();
        }else {
            mostrarInfo("Error al dar de baja el libro");
        }
    }

    @FXML
    void accionAniadirAlumno(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/Alumno.fxml"));
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("Añadir Alumno");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.initModality(javafx.stage.Modality.APPLICATION_MODAL);

            // Establecer un evento que se ejecute cuando se cierre la ventana
            stage.setOnHidden(windowEvent -> cargarAlumnos());
            stage.show();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    void accionModificarAlumno(ActionEvent event) {
        Alumno alumno=tablaAlumno.getSelectionModel().getSelectedItem();
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/Alumno.fxml"));
            Parent root = fxmlLoader.load();

            ControllerAlumno controller=fxmlLoader.getController();
            controller.setAlumno(alumno);

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("Editar Alumno");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.initModality(javafx.stage.Modality.APPLICATION_MODAL);

            // Establecer un evento que se ejecute cuando se cierre la ventana
            stage.setOnHidden(windowEvent -> cargarAlumnos());
            stage.show();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    void accionNuevoPrestamo(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/Prestamo.fxml"));
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("Crear Prestamo");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.initModality(javafx.stage.Modality.APPLICATION_MODAL);

            // Establecer un evento que se ejecute cuando se cierre la ventana
            stage.setOnHidden(windowEvent -> cargarPrestamos());
            stage.show();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    void accionDevolver(ActionEvent event) {

    }

    @FXML
    void accionEliminarAlumno(ActionEvent event) {

    }

    @FXML
    void accionAñadirHistoricoPrestamos(ActionEvent event) {

    }

    @FXML
    void accionAcercaDe(ActionEvent event) {

    }

    @FXML
    void accionAyuda(ActionEvent event) {

    }

    void cargarLibros() {
        ObservableList<Libro> listaLibros = DaoLibro.todosLibrosActivos();
        tablaLibro.setItems(listaLibros);
    }

    void cargarAlumnos() {
        ObservableList<Alumno> listaAlumnos = DaoAlumno.todosAlumnos();
        tablaAlumno.setItems(listaAlumnos);
    }

    void cargarPrestamos() {
        ObservableList<Prestamo> listaPrestamos = DaoPrestamo.todosPrestamos();
        tablaPrestamo.setItems(listaPrestamos);
    }

    void cargarHistorico() {
        ObservableList<Historico> listaHistoricos = DaoHistorico.todosHistoricos();
        tablaHistorico.setItems(listaHistoricos);
    }

    void cargarTodasTablas() {
       cargarLibros();
       cargarAlumnos();
       cargarPrestamos();
       cargarHistorico();
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

    /**
     * Metodo que muestra un mensaje de información en una ventana emergente.
     *
     * @param info El mensaje de información que se mostrará en la ventana emergente.
     */
    void mostrarInfo(String info) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setTitle("Info");
        alert.setContentText(info);
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
        colLibroImagen.setCellValueFactory(new PropertyValueFactory<>("foto"));

        // Configuración del cell factory para mostrar las imágenes en la columna 'colLibroImagen'
        colLibroImagen.setCellFactory(column -> new TableCell<Libro, Blob>() {
            private final ImageView imageView = new ImageView();
            private final Image imagenPorDefecto = new Image(getClass().getResourceAsStream("/imagenes/iconoLibro.png"));

            @Override
            protected void updateItem(Blob item, boolean empty) {
                super.updateItem(item, empty);

                // Asegúrate de limpiar el gráfico si la celda está vacía
                if (empty) {
                    setGraphic(null);
                } else {
                    if (item == null) {
                        // No hay imagen, usar imagen por defecto
                        imageView.setImage(imagenPorDefecto);
                    } else {
                        try {
                            // Convierte el Blob a un Image
                            Image image = new Image(item.getBinaryStream());
                            imageView.setImage(image);
                        } catch (SQLException e) {
                            e.printStackTrace();
                            // En caso de error, establecer la imagen por defecto
                            imageView.setImage(imagenPorDefecto);
                        }
                    }

                    // Ajusta el tamaño de la imagen y mantiene su proporción
                    imageView.setFitWidth(50); // Establece el ancho de la imagen
                    imageView.setFitHeight(50); // Establece la altura de la imagen
                    imageView.setPreserveRatio(true); // Mantiene la proporción de la imagen

                    // Establecer la gráfica de la celda
                    setGraphic(imageView); // Muestra la imagen en la celda
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

        // Configurar la columna de fecha de préstamo
        colPrestamoFecha.setCellValueFactory(new PropertyValueFactory<>("fecha_prestamo"));

        // Aplicar un formato de fecha
        colPrestamoFecha.setCellFactory(column -> new TableCell<Prestamo, LocalDate>() {
            private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

            @Override
            protected void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    // Formatear la fecha
                    String formattedDate = item.format(formatter);
                    setText(formattedDate);
                }
            }
        });

        //Cargar datos a las tablas
        cargarTodasTablas();


        // Deshabilitar los botones al inicio
        btnEditarLibro.setDisable(true);
        btnBajaLibro.setDisable(true);
        btnEditarAlumno.setDisable(true);
        btnEliminarAlumno.setDisable(true);

        // Agregar un listener a la tabla para habilitar/deshabilitar los botones
        tablaLibro.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            boolean itemSeleccionado = newSelection != null;
            btnEditarLibro.setDisable(!itemSeleccionado);
            btnBajaLibro.setDisable(!itemSeleccionado);
        });
        tablaAlumno.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            boolean itemSeleccionado = newSelection != null;
            btnEditarAlumno.setDisable(!itemSeleccionado);
            btnEliminarAlumno.setDisable(!itemSeleccionado);
        });
    }

}
