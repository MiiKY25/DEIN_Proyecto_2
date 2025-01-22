package org.mikel.dein_proyecto_2.controladores;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import org.mikel.dein_proyecto_2.modelos.Alumno;
import org.mikel.dein_proyecto_2.modelos.Libro;

public class ControllerPrestamo {

    @FXML
    private ComboBox<Alumno> comboAlumnos;

    @FXML
    private ComboBox<Libro> comboLibros;

    @FXML
    private DatePicker fecha;

    @FXML
    void accionCancelar(ActionEvent event) {

    }

    @FXML
    void accionGuardar(ActionEvent event) {

    }

}
