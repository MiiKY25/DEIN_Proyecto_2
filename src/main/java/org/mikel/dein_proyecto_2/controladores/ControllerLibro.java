package org.mikel.dein_proyecto_2.controladores;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class ControllerLibro {

    @FXML
    private ComboBox<String> comboEstado;

    @FXML
    private TextField txtAutor;

    @FXML
    private TextField txtEditorial;

    @FXML
    private TextField txtTitulo;

    @FXML
    void accionGuardar(ActionEvent event) {
        String error=validadDatos();
        if (error.isEmpty()){

        }else {
            mostrarError(error);
        }

    }

    @FXML
    void accionCancelar(ActionEvent event) {
        cerrarVentana();
    }

    @FXML
    void accionSeleccionarImagen(ActionEvent event) {

    }

    @FXML
    void accionBorrarImagen(ActionEvent event) {

    }

    private void cerrarVentana() {
        Stage stage = (Stage) txtAutor.getScene().getWindow();
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

        // Verificar si cada campo está vacío o cumple con las restricciones de formato
        if (txtTitulo.getText().isEmpty()) {
            error += "El campo 'Titulo' no puede estar vacio\n";
        }

        if (txtEditorial.getText().isEmpty()) {
            error += "El campo 'Editorial' no puede estar vacio\n";
        }

        if (txtAutor.getText().isEmpty()) {
            error += "El campo 'Autor' no puede estar vacio\n";
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

    @FXML
    public void initialize() {
        // Cargar los valores en el ComboBox
        comboEstado.getItems().addAll(
                "Nuevo",
                "Usado nuevo",
                "Usado seminuevo",
                "Usado estropeado",
                "Restaurado"
        );
        comboEstado.setValue("Nuevo");
    }



}
