package org.mikel.dein_proyecto_2.controladores;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.util.JRLoader;
import org.mikel.dein_proyecto_2.bbdd.ConexionBBDD;
import org.mikel.dein_proyecto_2.dao.DaoAlumno;
import org.mikel.dein_proyecto_2.dao.DaoLibro;
import org.mikel.dein_proyecto_2.dao.DaoPrestamo;
import org.mikel.dein_proyecto_2.modelos.Alumno;
import org.mikel.dein_proyecto_2.modelos.Libro;
import org.mikel.dein_proyecto_2.modelos.Prestamo;

import java.awt.*;
import java.io.File;
import java.io.InputStream;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class ControllerPrestamo {

    @FXML
    private ComboBox<Alumno> comboAlumnos;

    @FXML
    private ComboBox<Libro> comboLibros;

    @FXML
    private DatePicker fecha;

    @FXML
    void accionGuardar(ActionEvent event) {
        String error=validadDatos();
        if (error.isEmpty()){
            Prestamo p=new Prestamo(0,comboAlumnos.getValue(),comboLibros.getValue(),fecha.getValue());
            if (DaoPrestamo.crearPrestamo(p)){
                mostrarInfo("Prestamo creado correctamente");
                //generarInformePDF("Informe1.jasper", p.getId());
                cerrarVentana();
            }else {
                mostrarInfo("Error al crear el Prestamo");
            }
        }else {
            mostrarError(error);
        }
    }

    @FXML
    void accionCancelar(ActionEvent event) {
        cerrarVentana();
    }

    private void cerrarVentana() {
        Stage stage = (Stage) fecha.getScene().getWindow();
        stage.close();
    }

    private void generarInformePDF(String archivoJasper, int idPrestamo) {
        ConexionBBDD db;
        try {
            // Crear una nueva conexión a la base de datos
            db = new ConexionBBDD();

            // Cargar el archivo Jasper del informe
            InputStream reportStream = db.getClass().getResourceAsStream("/jasper/" + archivoJasper);

            // Verificar si el archivo fue encontrado
            if (reportStream == null) {
                mostrarError("Archivo Jasper no encontrado: " + archivoJasper);
                return;
            }

            // Cargar el informe Jasper
            JasperReport report = (JasperReport) JRLoader.loadObject(reportStream);

            // Parámetros del informe
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("ID_PRESTAMO", idPrestamo); // Pasar el ID del préstamo como parámetro

            // Añadir el parámetro para la ruta de imágenes
            String imagePath = db.getClass().getResource("/imagenes/").toString(); // Ruta de la carpeta de imágenes
            parameters.put("IMAGE_PATH", imagePath);

            // Llenar el informe con datos
            JasperPrint jprint = JasperFillManager.fillReport(report, parameters, db.getConnection());

            // Ruta para guardar el PDF
            String outputPath = System.getProperty("user.home") + "/prestamo_" + idPrestamo + ".pdf";

            // Exportar a PDF
            JasperExportManager.exportReportToPdfFile(jprint, outputPath);

            // Notificar al usuario que el PDF fue generado
            mostrarInfo("Informe generado correctamente: " + outputPath);

        } catch (SQLException e) {
            mostrarError("No se ha podido establecer conexión con la Base de Datos");
            e.printStackTrace();
        } catch (JRException e) {
            mostrarError("Error al procesar el informe Jasper");
            e.printStackTrace();
        }
    }



    /**
     * Metodo que valida los datos ingresados en los campos del formulario.
     * Verifica si los campos están vacíos o si el formato de los valores es incorrecto.
     *
     * @return Un mensaje de error si los datos son inválidos, o una cadena vacía si los datos son válidos.
     */
    public String validadDatos() {
        String error = "";

        // Verificar si el ComboBox tiene un valor seleccionado
        if (comboLibros.getValue() == null) {
            error += "Debe seleccionar un Libro en el campo 'Libro'\n";
        }

        if (comboAlumnos.getValue() == null) {
            error += "Debe seleccionar un alumno en el campo 'Alumno'\n";
        }

        // Verificar si la fecha es nula o posterior al día de hoy
        if (fecha.getValue() == null) {  // Asumiendo que fechaPrestamo es tu DatePicker
            error += "Debe seleccionar una fecha en el campo 'Fecha'\n";
        } else if (fecha.getValue().isAfter(LocalDate.now())) {
            error += "La fecha no puede ser posterior al día de hoy\n";
        }

        return error;
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
    public void initialize() {
        // Cargar los valores a los ComboBox
        comboAlumnos.getItems().addAll(DaoAlumno.todosAlumnos());
        comboLibros.getItems().addAll(DaoLibro.todosLibrosParaPrestar());
        //Elegir el primer item
        comboAlumnos.getSelectionModel().selectFirst();
        comboLibros.getSelectionModel().selectFirst();

    }

}
