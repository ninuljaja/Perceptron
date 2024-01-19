/**
 * Perceptron Class
 */
package com.example.ai_assignment3;



public class Perceptron {
    private double[] weights;
    private double bias;
    private double learningRate;
    private boolean trainingCompleted;

    /**
     * Constructor that creates Perceptron object
     * @param inputSize an integer that holds number of input values
     * @param learningRate an integer that holds learning rate
     */
    public Perceptron(int inputSize, double learningRate) {
        this.weights = new double[inputSize];
        this.bias = Math.random()/2;
        this.learningRate = learningRate;

        // Initialize weights with random values

        for (int i = 0; i < weights.length; i++) {
            weights[i] = Math.random()/2;
        }
    }

    /**
     * Method that initiates training
     * @param trainingData 2D array og integers that holds training input data
     * @param targets array of integers that holds expected output for each sample of training input data
     */
    public void training(int[][] trainingData, int[] targets){
        // run training until predictions for all training samples are correct
        do {
            trainingCompleted = true;
            for (int i = 0; i < trainingData.length; i++) {
                train(trainingData[i], targets[i]);
            }
        }while (!trainingCompleted);
    }

    /**
     * Method that adjust weights if the predicted output is not equals to expected one
     * @param input array of integers that holds one sample of training input data
     * @param target an integer that holds expected output fot the sample
     */
    public void train(int[] input, int target) {
        // find predicted output
        int prediction = predict(input);
        // calculate an error
        int error = target - prediction;
        if(error != 0){
            // if predicted output is not equals to expected one, update weights and continue training
            for (int i = 0; i < weights.length; i++) {
                weights[i] += learningRate * error * input[i];
            }
            bias += learningRate*error;
            trainingCompleted = false;
        }
    }

    /**
     * Method that calculates and returns the predicted output for the sample data
     * @param input array of integers that holds weights of each input data
     * @return an integer that represents the predicted output
     */
    public int predict(int[] input) {
        // Step function
        double sum = bias;
        for (int i = 0; i < weights.length; i++) {
            sum += weights[i] * input[i];
        }
        return (sum > 0.5) ? 1 : 0;
    }
}