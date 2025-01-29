package org.mikel.dein_proyecto_2;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.mikel.dein_proyecto_2.bbdd.ConexionBBDD;
import java.io.IOException;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;

public class BibliotecaApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        // Cargar las propiedades de la base de datos
        Properties properties = ConexionBBDD.cargarIdioma();
        String lang = properties.getProperty("language");

        // Cargar el recurso de idioma adecuado utilizando el archivo de propiedades
        Locale locale = new Locale(lang);
        ResourceBundle bundle = ResourceBundle.getBundle("idiomas/lang", locale);


        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/Biblioteca.fxml"), bundle);
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("LECTORIKA");
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/imagenes/logo.png")));
        stage.setMinWidth(800);
        stage.setMinHeight(500);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}