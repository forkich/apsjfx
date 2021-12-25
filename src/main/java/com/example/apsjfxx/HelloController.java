package com.example.apsjfxx;

import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;


public class HelloController {
    int countOfDevices = 0;
    int countOfSources = 0;
    int bufferSize = 0;
    double lambda = 0;
    int countOfBids = 0;
    double min = 0;
    double max = 0;
    Simulator simulator;

    @FXML
    private Button stepMode;

    @FXML
    private Button confMode;

    @FXML
    private Button autoMode;

    @FXML
    private TextField insBuffers;

    @FXML
    private TextField insDevices;

    @FXML
    private TextField insSources;

    @FXML
    private TextField insLambda;

    @FXML
    private TextField insTasks;

    @FXML
    private TextField insMin;

    @FXML
    private TextField insMax;

    @FXML
    void initialize() {
        stepMode.setOnAction(actionEvent ->
        {
            stepMode.getScene().getWindow().hide();
            countOfSources = Integer.parseInt(insSources.getText());
            bufferSize = Integer.parseInt(insBuffers.getText());
            countOfDevices = Integer.parseInt(insDevices.getText());
            lambda = Double.parseDouble(insLambda.getText());
            countOfBids = Integer.parseInt(insTasks.getText());
            min = Integer.parseInt(insMin.getText());
            max = Integer.parseInt(insMax.getText());
            simulator = new Simulator(countOfDevices, countOfSources, bufferSize, lambda, countOfBids, min, max);
            simulator.start(countOfBids);
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("step.fxml"));
            try {
                loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Parent root = loader.getRoot();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
            StepModeApp ap = new StepModeApp(simulator);
            try {
                ap.start(stage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });


        autoMode.setOnAction(actionEvent ->
        {
            autoMode.getScene().getWindow().hide();

            countOfSources = Integer.parseInt(insSources.getText());
            countOfDevices = Integer.parseInt(insDevices.getText());

            bufferSize = Integer.parseInt(insBuffers.getText());

            lambda = Double.parseDouble(insLambda.getText());
            countOfBids = Integer.parseInt(insTasks.getText());
            min = Integer.parseInt(insMin.getText());
            max = Integer.parseInt(insMax.getText());
            simulator = new Simulator(countOfDevices, countOfSources, bufferSize, lambda, countOfBids, min, max);
            simulator.start(countOfBids);
            Container container = simulator.getContainer();

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("step.fxml"));
            try {
                loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Parent root = loader.getRoot();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
            AutoModeApp ap = new AutoModeApp(container, simulator.getPreviousN());
            try {
                ap.start(stage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        confMode.setOnAction(actionEvent ->
        {
            confMode.getScene().getWindow().hide();
            countOfSources = Integer.parseInt(insSources.getText());
            bufferSize = Integer.parseInt(insBuffers.getText());
            countOfDevices = Integer.parseInt(insDevices.getText());
            lambda = Double.parseDouble(insLambda.getText());
            countOfBids = Integer.parseInt(insTasks.getText());
            min = Integer.parseInt(insMin.getText());
            max = Integer.parseInt(insMax.getText());
            simulator = new Simulator(countOfDevices, countOfSources, bufferSize, lambda, countOfBids, min, max);
            simulator.start(countOfBids);
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("step.fxml"));
            try {
                loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Parent root = loader.getRoot();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
             ConfigurationMode ap = new ConfigurationMode(simulator);
            try {
                ap.start(stage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

}
