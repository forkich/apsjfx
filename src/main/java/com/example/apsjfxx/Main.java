package com.example.apsjfxx;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import java.util.Random;
import java.util.Scanner;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;


public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        final HBox hb = new HBox();
        Scene scene = new Scene(new Group());

        hb.setSpacing(5);
        hb.setPadding(new Insets(20, 0, 0, 10));
        ((Group) scene.getRoot()).getChildren().addAll(hb);
        Simulator simulator = new Simulator(11, 13, 15, 2, 1400, 2 , 3);
        Container container = simulator.autoProcessing();

        ObservableList<SourceStatistic> listSource = FXCollections.observableArrayList();
        for (Source currentSource: container.getSources())
        {
            listSource.add(new SourceStatistic(currentSource));
        }
        TableView<SourceStatistic> tableSource = new TableView<SourceStatistic>(listSource);
        TableColumn<SourceStatistic, Integer> sourceNum = new TableColumn<SourceStatistic, Integer>("Source #");
        sourceNum.setCellValueFactory(new PropertyValueFactory<SourceStatistic, Integer>("numOfSource"));

        TableColumn<SourceStatistic, Integer> countOfBids = new TableColumn<SourceStatistic, Integer>("Count of bids");
        countOfBids.setCellValueFactory(new PropertyValueFactory<SourceStatistic, Integer>("countOfBids"));

        TableColumn<SourceStatistic, Double> pOfDeny = new TableColumn<SourceStatistic, Double>("P of deny");
        pOfDeny.setCellValueFactory(new PropertyValueFactory<SourceStatistic, Double>("POfDeny"));

        TableColumn<SourceStatistic, Double> TStaying = new TableColumn<SourceStatistic, Double>("T of Staying");
        TStaying.setCellValueFactory(new PropertyValueFactory<SourceStatistic, Double>("TStaying"));

        TableColumn<SourceStatistic, Double> TUsing = new TableColumn<SourceStatistic, Double>("T of Using");
        TUsing.setCellValueFactory(new PropertyValueFactory<SourceStatistic, Double>("TUsing"));

        TableColumn<SourceStatistic, Double> TWaiting = new TableColumn<SourceStatistic, Double>("T of Waiting");
        TWaiting.setCellValueFactory(new PropertyValueFactory<SourceStatistic, Double>("TWaiting"));

        TableColumn<SourceStatistic, Double> DUsing = new TableColumn<SourceStatistic, Double>("D of Using");
        DUsing.setCellValueFactory(new PropertyValueFactory<SourceStatistic, Double>("DUsing"));

        TableColumn<SourceStatistic, Double> DWaiting = new TableColumn<SourceStatistic, Double>("D of Waiting");
        DWaiting.setCellValueFactory(new PropertyValueFactory<SourceStatistic, Double>("DWaiting"));

        tableSource.getColumns().addAll(sourceNum, countOfBids, pOfDeny, TStaying, TUsing, TWaiting, DUsing, DWaiting);


        ObservableList<DeviceStatistic> listDevice = FXCollections.observableArrayList();
        for (Device currentDevice: container.getDevices())
        {
            listDevice.add(new DeviceStatistic(currentDevice.getNumberOfDevice(), currentDevice.getCoefOfUsing()));
        }

        TableView<DeviceStatistic> tableDevice = new TableView<DeviceStatistic>(listDevice);

        TableColumn<DeviceStatistic, Integer> deviceNum = new TableColumn<DeviceStatistic, Integer>("Device #");
        deviceNum.setCellValueFactory(new PropertyValueFactory<DeviceStatistic, Integer>("numberOfDevice"));

        TableColumn<DeviceStatistic, Double> coef = new TableColumn<DeviceStatistic, Double>("coef of using");
        coef.setCellValueFactory(new PropertyValueFactory<DeviceStatistic, Double>("CoefOfUsing"));
        tableDevice.getColumns().addAll(deviceNum, coef);

        hb.setSpacing(5);
        hb.setPadding(new Insets(10, 0, 0, 10));
        hb.getChildren().addAll(tableSource, tableDevice);
        ((Group) scene.getRoot()).getChildren().addAll(hb);
        stage.setScene(scene);
        stage.show();
        stage.setTitle("Statistic of AutoMode");
        stage.setWidth(680);
        stage.setHeight(780);
    }

    @Override
    public void stop() throws Exception
    {
        super.stop();
    }

    @Override
    public void init() throws Exception
    {
        super.init();
    }
}
