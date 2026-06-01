package org.example.proyecto_santiago;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        FXMLLoader fxmlLoader = new FXMLLoader(
                Main.class.getResource("hello-view.fxml")
        );

        Scene scene = new Scene(fxmlLoader.load());

        scene.getStylesheets().add(
                Main.class.getResource("style.css").toExternalForm()
        );

        stage.setTitle("Paint Studio FX");

        stage.setScene(scene);

        stage.setMaximized(true);

        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}