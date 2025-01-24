package org.mikel.dein_proyecto_2.controladores;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.mikel.dein_proyecto_2.dao.DaoAlumno;
import org.mikel.dein_proyecto_2.dao.DaoLibro;
import org.mikel.dein_proyecto_2.modelos.Alumno;
import org.mikel.dein_proyecto_2.modelos.Libro;

public class ControllerAlumno {

    @FXML
    private TextField txtApellido1;

    @FXML
    private TextField txtApellido2;

    @FXML
    private TextField txtDNI;

    @FXML
    private TextField txtNombre;

    private Alumno alumno;

    @FXML
    void accionGuardar(ActionEvent event) {
        String error=validadDatos();
        if (error.isEmpty()){
            if (alumno==null){
                //Crear Alumno
                Alumno a =new Alumno(txtDNI.getText(),txtNombre.getText(),txtApellido1.getText(),txtApellido2.getText());
                if (DaoAlumno.crearAlumno(a)){
                    mostrarInfo("Alumno creado correctamente");
                    cerrarVentana();
                }else {
                    mostrarInfo("Error al crear el Alumno");
                }
            }else {
                //Modificar Alumno
                Alumno a =new Alumno(txtDNI.getText(),txtNombre.getText(),txtApellido1.getText(),txtApellido2.getText());
                if (DaoAlumno.editarAlumno(a)){
                    mostrarInfo("Alumno editado correctamente");
                    cerrarVentana();
                }else {
                    mostrarInfo("Error al editar el Alumno");
                }
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
        Stage stage = (Stage) txtNombre.getScene().getWindow();
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
        if (txtNombre.getText().isEmpty()) {
            error += "El campo 'Nombre' no puede estar vacio\n";
        }

        if (txtDNI.getText().isEmpty()) {
            error += "El campo 'DNI' no puede estar vacio\n";
        }

        if (txtApellido1.getText().isEmpty()) {
            error += "El campo 'Apellido 1' no puede estar vacio\n";
        }

        if (txtApellido2.getText().isEmpty()) {
            error += "El campo 'Apellido 2' no puede estar vacio\n";
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

    public void setAlumno(Alumno a) {
        this.alumno=a;

        //Cargar datos
        txtDNI.setText(alumno.getDni());
        txtNombre.setText(alumno.getNombre());
        txtApellido1.setText(alumno.getApellido1());
        txtApellido2.setText(alumno.getApellido2());

        // Si se edita no se puede cambiar el DNI
        txtDNI.setDisable(true);
    }

}
