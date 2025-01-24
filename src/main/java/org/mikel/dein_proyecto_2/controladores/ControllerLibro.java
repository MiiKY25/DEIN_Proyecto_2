package org.mikel.dein_proyecto_2.controladores;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.mikel.dein_proyecto_2.dao.DaoLibro;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.SQLException;

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
    private ImageView imgFoto;

    @FXML
    private Button btnBorrarFoto;

    private Blob imagen;


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
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Elige la portada del libro");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Imagenes", "*.png", "*.jpg", "*.jpeg"));
        fileChooser.setInitialDirectory(new File("."));
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            try {
                double kbs = (double) file.length() / 1024;
                if (kbs > 64) {
                    mostrarError("La imagen no puede pesar mas de 64kb");
                } else {
                    InputStream imagenInput = new FileInputStream(file);
                    Blob imagenBlob = DaoLibro.convertirFicheroABlob(file);
                    imagen = imagenBlob;
                    imgFoto.setImage(new Image(imagenInput));
                    btnBorrarFoto.setDisable(false);
                }
            } catch (IOException | NullPointerException | SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    void accionBorrarImagen(ActionEvent event) {
        imgFoto.setImage(new Image(getClass().getResourceAsStream("/imagenes/iconoLibro.png")));
        btnBorrarFoto.setDisable(true);
        imagen = null;
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

        // Verificar si el ComboBox tiene un valor seleccionado
        if (comboEstado.getValue() == null || comboEstado.getValue().isEmpty()) {
            error += "Debe seleccionar un estado en el campo 'Estado'\n";
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
        btnBorrarFoto.setDisable(true);
    }



}
