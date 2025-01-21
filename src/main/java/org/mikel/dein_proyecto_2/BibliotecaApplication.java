package org.mikel.dein_proyecto_2;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class BibliotecaApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/Biblioteca.fxml"));
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