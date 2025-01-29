package org.mikel.dein_proyecto_2.controladores;

import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
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
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
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
import java.io.InputStream;
import java.sql.Blob;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

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
    private TableColumn<Historico, LocalDate> colHistoricoDevolucion;

    @FXML
    private TableColumn<Historico, LocalDate> colHistoricoFecha;

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
    private ComboBox<String> comboFiltroHistorico;

    @FXML
    private TextField txtFiltroHistorico;

    private ObservableList<Historico> historicos = FXCollections.observableArrayList();

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
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/Devolver.fxml"));
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("Devolver Libro");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.initModality(javafx.stage.Modality.APPLICATION_MODAL);

            // Establecer un evento que se ejecute cuando se cierre la ventana
            stage.setOnHidden(windowEvent -> cargarTodasTablas());
            stage.show();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    void accionFiltrarHistorico(ActionEvent event) {
        // Obtener el texto ingresado y el filtro seleccionado
        String filtro = comboFiltroHistorico.getValue();
        String textoFiltro = txtFiltroHistorico.getText().toLowerCase().trim();

        // Si el campo de texto está vacío, cargar todos los datos
        if (textoFiltro.isEmpty()) {
            cargarHistorico();
            return;
        }

        ObservableList<Historico> todosHistoricos = DaoHistorico.todosHistoricos();

        if (filtro.equals("ID")) {
            tablaHistorico.setItems(todosHistoricos.filtered(historico ->
                    String.valueOf(historico.getId()).contains(textoFiltro)));
        } else if (filtro.equals("DNI")) {
            tablaHistorico.setItems(todosHistoricos.filtered(historico ->
                    historico.getAlumno().getDni().toLowerCase().contains(textoFiltro)));
        } else if (filtro.equals("Libro")) {
            tablaHistorico.setItems(todosHistoricos.filtered(historico ->
                    String.valueOf(historico.getLibro().getCodigo()).contains(textoFiltro)));
        }

    }

    @FXML
    void accionInformeLibros(ActionEvent event) {
        Map<String, Object> parameters = new HashMap<>();

        // Añadir la ruta de las imágenes
        String imagePath = getClass().getResource("/imagenes/").toString(); // Ruta de la carpeta de imágenes
        parameters.put("IMAGE_PATH", imagePath);
        String resourcePath = getClass().getResource("/jasper/").toString();
        parameters.put("Resource_PATH", resourcePath);
        generarInforme("Informe2.jasper",parameters);
    }

    @FXML
    void accionInformeGraficos(ActionEvent event) {
        Map<String, Object> parameters = new HashMap<>();

        // Añadir la ruta de las imágenes
        String imagePath = getClass().getResource("/imagenes/").toString(); // Ruta de la carpeta de imágenes
        parameters.put("IMAGE_PATH", imagePath);
        String resourcePath = getClass().getResource("/jasper/").toString();
        parameters.put("Resource_PATH", resourcePath);
        generarInforme("Informe3.jasper",parameters);
    }

    @FXML
    void accionInformeAlumnos(ActionEvent event) {
        Map<String, Object> parameters = new HashMap<>();

        // Añadir la ruta de las imágenes
        String imagePath = getClass().getResource("/imagenes/").toString(); // Ruta de la carpeta de imágenes
        parameters.put("IMAGE_PATH", imagePath);
        generarInforme("Informe4.jasper",parameters);
    }

    @FXML
    void accionAcercaDe(ActionEvent event) {

    }

    @FXML
    void accionAyuda(ActionEvent event) {

    }

    /**
     * Cambia el idioma de la aplicación a español y actualiza la ventana.
     * Este metodo guarda la configuración del idioma en la base de datos y luego
     * actualiza la ventana para reflejar los cambios en el idioma.
     *
     * @param event El evento de acción que dispara este metodo. No se usa explícitamente
     *              pero es necesario para la firma del metodo.
     */
    @FXML
    void cambiarEspanol(ActionEvent event) {
        ConexionBBDD.guardarIdioma("es");
        Stage stage = (Stage) txtFiltroHistorico.getScene().getWindow();
        actualizarVentana(stage);
    }

    /**
     * Cambia el idioma de la aplicación a inglés y actualiza la ventana.
     * Este metodo guarda la configuración del idioma en la base de datos y luego
     * actualiza la ventana para reflejar los cambios en el idioma.
     *
     * @param event El evento de acción que dispara este metodo. No se usa explícitamente
     *              pero es necesario para la firma del metodo.
     */
    @FXML
    void cambiarIngles(ActionEvent event) {
        ConexionBBDD.guardarIdioma("en");
        Stage stage = (Stage) txtFiltroHistorico.getScene().getWindow();
        actualizarVentana(stage);
    }

    /**
     * Actualiza la ventana con el nuevo idioma establecido en el sistema.
     * Este metodo carga las propiedades de idioma desde la base de datos,
     * crea un nuevo {@link Locale} según el idioma seleccionado, y luego carga
     * el archivo FXML de la ventana principal con el nuevo {@link ResourceBundle}.
     * Finalmente, cambia la raíz de la escena de la ventana principal para reflejar
     * los cambios de idioma.
     *
     * @param stage El {@link Stage} de la ventana principal que se actualizará.
     *              No debe ser nulo, ya que se usará para cambiar la escena.
     */
    public void actualizarVentana(Stage stage) {
        try {
            // Cargar las propiedades de idioma y establecer el nuevo locale
            Properties properties = ConexionBBDD.cargarIdioma();
            String lang = properties.getProperty("language");
            Locale locale = new Locale(lang);
            ResourceBundle bundle = ResourceBundle.getBundle("idiomas/lang", locale);

            // Cargar el archivo FXML de la ventana principal con el nuevo ResourceBundle
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/Biblioteca.fxml"), bundle);
            Parent root = fxmlLoader.load();

            // Verificar que el Stage no sea nulo antes de cambiar la raíz de la escena
            if (stage != null) {
                stage.getScene().setRoot(root);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void generarInforme(String archivoJasper, Map<String, Object> parameters) {
        ConexionBBDD db;
        try {
            // Crear una nueva conexión a la base de datos
            db = new ConexionBBDD();

            // Cargar el archivo Jasper del informe
            InputStream reportStream = db.getClass().getResourceAsStream("/jasper/" + archivoJasper);

            // Verificar si el archivo fue encontrado
            if (reportStream == null) {
                System.out.println("Archivo NO encontrado");
                return;
            }

            // Cargar el informe Jasper
            JasperReport report = (JasperReport) JRLoader.loadObject(reportStream);

            // Verificar si el mapa de parámetros es nulo o vacío
            if (parameters == null) {
                parameters = new HashMap<>();
            }

            // Añadir la ruta de las imágenes si no está ya en los parámetros
            if (!parameters.containsKey("IMAGE_PATH")) {
                String imagePath = db.getClass().getResource("/imagenes/").toString(); // Ruta de la carpeta de imágenes
                parameters.put("IMAGE_PATH", imagePath);
            }

            // Llenar el informe con datos
            JasperPrint jprint = JasperFillManager.fillReport(report, parameters, db.getConnection());

            // Mostrar el informe
            JasperViewer viewer = new JasperViewer(jprint, false);
            viewer.setVisible(true);

        } catch (SQLException e) {
            mostrarError("No se ha podido establecer conexión con la Base de Datos");
            e.printStackTrace();
        } catch (JRException e) {
            mostrarError("Error al procesar el informe Jasper");
            e.printStackTrace();
        }
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

        // Configuración de columnas para Histórico
        colHistoricoID.setCellValueFactory(new PropertyValueFactory<>("id"));
        colHistoricoAlumno.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getAlumno().getDni()));
        colHistoricoLibro.setCellValueFactory(cellData ->
                new SimpleObjectProperty<>(cellData.getValue().getLibro().getCodigo()));

        // Configurar la columna de fecha de préstamo
        colHistoricoFecha.setCellValueFactory(cellData ->
                new SimpleObjectProperty<>(cellData.getValue().getFecha_prestamo()));

        // Aplicar un formato de fecha
        colHistoricoFecha.setCellFactory(column -> new TableCell<Historico, LocalDate>() {
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

        // Configurar la columna de fecha de devolución
        colHistoricoDevolucion.setCellValueFactory(cellData ->
                new SimpleObjectProperty<>(cellData.getValue().getFecha_devolucion()));

        // Aplicar un formato de fecha
        colHistoricoDevolucion.setCellFactory(column -> new TableCell<Historico, LocalDate>() {
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

        // Agregar un listener a la tabla para habilitar/deshabilitar los botones
        tablaLibro.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            boolean itemSeleccionado = newSelection != null;
            btnEditarLibro.setDisable(!itemSeleccionado);
            btnBajaLibro.setDisable(!itemSeleccionado);
        });
        tablaAlumno.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            boolean itemSeleccionado = newSelection != null;
            btnEditarAlumno.setDisable(!itemSeleccionado);
        });

        //Cargar opciones al combobox del filtro historico
        comboFiltroHistorico.getItems().addAll(
                "ID",
                "DNI",
                "Libro"
        );
        //Elegir el primer item
        comboFiltroHistorico.getSelectionModel().selectFirst();

    }

}
