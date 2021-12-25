package com.example.apsjfxx;


import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class AutoModeApp extends Application {
    Container container;
    int soughtN;

    public AutoModeApp(Container container, int N)
    {
        this.container = container;
        soughtN = N;
    }

    @Override
    public void start(Stage stage){

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

        final Label label = new Label("The count of N = " + this.soughtN);
        Button button = new Button("The count of N = " + this.soughtN);
        button.setFont(new Font("Arial", 13));
        label.setFont(new Font("Arial", 10));

        Scene scene = new Scene(new Group());
        final HBox hb = new HBox();
        hb.setPadding(new Insets(10, 0, 0, 10));
        hb.setSpacing(5);
        hb.getChildren().addAll(button, tableSource, tableDevice);
        ((Group) scene.getRoot()).getChildren().addAll(hb);

        stage.setScene(scene);
        stage.show();
        stage.setTitle("Statistic of AutoMode");
        stage.setWidth(1000);
        stage.setHeight(600);
    }
}