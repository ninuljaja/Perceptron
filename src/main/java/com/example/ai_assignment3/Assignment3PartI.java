package com.example.ai_assignment3;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Assignment3PartI extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        // Create a FXMLLoader to load the user interface layout from an FXML file
        FXMLLoader fxmlLoader = new FXMLLoader(Assignment3PartI.class.getResource("GUI.fxml"));
        // Create a Scene using the loaded FXML layout, with dimensions 600x500
        Scene scene = new Scene(fxmlLoader.load(), 600, 500);
        stage.setTitle("Perceptron");
        stage.setScene(scene);
        // Display the application window
        stage.show();
    }
    /**
     * the main method that launches the JavaFX application
     * @param args
     */
    public static void main(String[] args) {
        launch();
    }
}