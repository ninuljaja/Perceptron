/**
 * PartIController Class
 */
package com.example.ai_assignment3;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.util.Arrays;

public class PartIController {
    @FXML
    private Button runTestingDataBtn;
    @FXML
    private TextFlow finalResult;
    @FXML
    private Label resultLbl;
    @FXML
    private ImageView imageView;
    Image image;
    Perceptron perceptron;
    // Training data
    int[][] trainingData = {
            {1, 0, 1, 0} ,  // Bright
            {0, 1, 1, 0} ,  // Bright
            {0, 0, 0, 1},   // Dark
            {0, 0, 1, 1} ,  // Bright
            {1, 1, 0, 0} ,  // Bright
            {0, 0, 1, 0},   // Dark
            {1, 0, 0, 0},   // Dark
            {1, 0, 1, 1},   // Bright
            {1, 1, 1, 0}    // Bright
    };


    // Target labels for training data
    int[] targets = {1, 1, 0, 1, 1,0, 0, 1, 1}; // 0 for Dark, 1 for Bright

    // Testing data set
    int[][] testingData = {
            {1, 1, 1, 1},   // Bright
            {0, 0, 0, 0},   // Dark
            {1, 0, 0, 1} ,  // Bright
            {0, 1, 1, 1},   // Bright
            {1, 1, 0, 1},   // Bright
            {0, 1, 0, 1},   // Bright
            {0, 1, 0, 0}    // Dark
    };
    // Expected output for testing data
    int[] expected = {1,0,1,1,1,1,0};
    int sampleNumber;
    float accuracy;
    int correct;

    /**
     * Method that resets GUI
     */
    public void initialize(){
        // reset GUI
        image = null;
        imageView.setImage(null);
        perceptron = null;
        sampleNumber = 0;
        runTestingDataBtn.setText("Start testing");
        runTestingDataBtn.setDisable(true);
        resultLbl.setText("");
        imageView.setVisible(true);
        finalResult.setVisible(true);
        correct = 0;
        // Display training data
        finalResult.getChildren().setAll(new Text("Training data\n"));
        for (int i = 0; i < trainingData.length; i++) {
            Text text1=new Text("\nSample " + (i + 1) + ": " + Arrays.toString(trainingData[i]));
            finalResult.getChildren().addAll(text1);
        }
    }

    /**
     * Event handler for the "Run training data" button. Starts training
     */
    @FXML
    protected void onTrainingBtn() {
        initialize();
        int inputSize = trainingData[0].length;
        // Create s perceptron object with learning rate 0.1
        perceptron = new Perceptron(inputSize, 0.1);

        // Training the perceptron
        perceptron.training(trainingData, targets);
        resultLbl.setText("Training completed");
        runTestingDataBtn.setDisable(false);

        // Display testing data
        finalResult.getChildren().setAll(new Text("Testing data\n"));
        for (int i = 0; i < testingData.length; i++) {
            Text text1=new Text("\nSample " + (i + 1) + ": " + Arrays.toString(testingData[i]));
            finalResult.getChildren().addAll(text1);
        }
    }

    /**
     * Event handler for the "Start testing" button.
     */
    @FXML
    protected void onTestingBtn() {
        // reset TextFlow
        if(sampleNumber == 0){
            finalResult.getChildren().setAll(new Text("Result:"));
            finalResult.setVisible(false);
        }
        // check if all testing data samples processed
        if(sampleNumber < testingData.length) {
            // find predicted output
            int prediction = perceptron.predict(testingData[sampleNumber]);
            if(prediction == expected[sampleNumber]){
                // increment the number of correct predictions
                correct++;
            }
            // create and display image that represents sample testing data
            image = createImage(testingData[sampleNumber]);
            imageView.setImage(image);
            // display the result of testing
            resultLbl.setText("Sample " + (sampleNumber + 1) + ": " + Arrays.toString(testingData[sampleNumber]) + "\n" +
                    "Prediction:\t" + (prediction == 1 ? "Bright" : "Dark"));

            // add the result of the sample testing to the finalResult TextFlow
            Text text1=new Text("\n\nSample " + (sampleNumber + 1) + ": " + Arrays.toString(testingData[sampleNumber]) +
                    "\tPrediction:\t");
            Text text2=new Text(prediction == 1 ? "Bright" : "Dark");
            text2.setStyle("-fx-font-weight: bold");
            if(prediction != expected[sampleNumber]){
                text2.setFill(Color.RED); // setting color of the text to red
                text2.setStyle("-fx-font-weight: bold");
            }
            finalResult.getChildren().addAll(text1, text2);
            sampleNumber++; // move to the next sample data
        }else{
            // if testing completes, calculate accuracy of predictions, and display the results
            accuracy = 100.0f*correct/expected.length;
            Text text=new Text("\n\nAccuracy is: " + accuracy + "%.");
            text.setStyle("-fx-font-weight: bold");
            finalResult.getChildren().addAll(text);
            finalResult.setVisible(true);
            imageView.setVisible(false);
            resultLbl.setText("Test completed");
            runTestingDataBtn.setDisable(true);
        }
        // change the text on the button if testing is already started
        if(runTestingDataBtn.getText().equalsIgnoreCase("Start testing")){
            runTestingDataBtn.setText("Next testing data");
        }
    }

    /**
     * Method that creates and returns an Image
     * @param data array of integers that holds a sample of input data
     * @return an Image that represents a sample input data (4 black or white squares)
     */
    private Image createImage(int[] data){
        // set the size of the picture
        int width = 100;
        int height = 100;
        WritableImage image = new WritableImage(width, height);
        PixelWriter pw = image.getPixelWriter();
        for(int i = 0; i < height/2; i++){
            // create a block that represents the first pixel in input data
            for(int j = 0; j < width/2; j++){
                int color = (data[0] == 0) ? 0xFF000000 : 0xFFFFFFFF;
                pw.setArgb(j,i,color);
            }
            // create a block that represents the second pixel in input data
            for(int j = width/2; j < width; j++){
                int color = (data[1] == 0) ? 0xFF000000 : 0xFFFFFFFF;
                pw.setArgb(j,i,color);
            }
        }

        for(int i = height/2; i < height; i++){
            // create a block that represents the third pixel in input data
            for(int j = 0; j < width/2; j++){
                int color = (data[2] == 0) ? 0xFF000000 : 0xFFFFFFFF;
                pw.setArgb(j,i,color);
            }
            for(int j = width/2; j < width; j++){
                // create a block that represents the fourth pixel in input data
                int color = (data[3] == 0) ? 0xFF000000 : 0xFFFFFFFF;
                pw.setArgb(j,i,color);
            }
        }
        return image;
    }

    /**
     * Event handler for the "Exit" button. Terminates the program after confirmation
     */
    @FXML
    protected void onExitBtn() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Are you sure you want to exit?", ButtonType.YES, ButtonType.NO);
        alert.setHeaderText("");
        alert.showAndWait();
        if (alert.getResult() == ButtonType.YES){
            System.exit(0);
        }
    }

}