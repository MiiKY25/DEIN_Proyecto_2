package org.mikel.dein_proyecto_2.controladores;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;
import org.mikel.dein_proyecto_2.dao.DaoAlumno;
import org.mikel.dein_proyecto_2.dao.DaoLibro;
import org.mikel.dein_proyecto_2.dao.DaoPrestamo;
import org.mikel.dein_proyecto_2.modelos.Alumno;
import org.mikel.dein_proyecto_2.modelos.Libro;
import org.mikel.dein_proyecto_2.modelos.Prestamo;

import java.time.LocalDate;

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
