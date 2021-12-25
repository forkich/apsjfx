package com.example.apsjfxx;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;

public class ConfigurationMode extends Application {
    Simulator simulator;

    public ConfigurationMode(Simulator simulator)
    {
        this.simulator = simulator;
    }

    @Override
    public void start(Stage stage) throws IOException {
        final HBox hb = new HBox();
        Scene scene = new Scene(new Group());

        hb.setSpacing(5);
        hb.setPadding(new Insets(20, 0, 0, 10));
        ((Group) scene.getRoot()).getChildren().addAll(hb);
        stage.setTitle("Statistic of AutoMode");
        stage.setWidth(680);
        stage.setHeight(780);

        Simulator simulator = new Simulator(11, 6, 5, 2, 1400, 2 , 3);
        //Container container = simulator.autoProcessing();
        ObservableList<ConfigurationStatistic> listConf = simulator.getConfiguration();

        TableView<ConfigurationStatistic> tableConf = new TableView<ConfigurationStatistic>(listConf);
        TableColumn<ConfigurationStatistic, Integer> countConf = new TableColumn<ConfigurationStatistic, Integer>("#");
        countConf.setCellValueFactory(new PropertyValueFactory<ConfigurationStatistic, Integer>("num"));

        TableColumn<ConfigurationStatistic, Integer> sourceCount = new TableColumn<ConfigurationStatistic, Integer>("Sources");
        sourceCount.setCellValueFactory(new PropertyValueFactory<ConfigurationStatistic, Integer>("sources"));

        TableColumn<ConfigurationStatistic, Integer> deviceCount = new TableColumn<ConfigurationStatistic, Integer>("Devices");
        deviceCount.setCellValueFactory(new PropertyValueFactory<ConfigurationStatistic, Integer>("devices"));

        TableColumn<ConfigurationStatistic, Double> buf = new TableColumn<ConfigurationStatistic, Double>("Buffer");
        buf.setCellValueFactory(new PropertyValueFactory<ConfigurationStatistic, Double>("buffer"));

        TableColumn<ConfigurationStatistic, Double> using = new TableColumn<ConfigurationStatistic, Double>("Using devices");
        using.setCellValueFactory(new PropertyValueFactory<ConfigurationStatistic, Double>("usingDevices"));

        TableColumn<ConfigurationStatistic, Double> deny = new TableColumn<ConfigurationStatistic, Double>("P of deny");
        deny.setCellValueFactory(new PropertyValueFactory<ConfigurationStatistic, Double>("denyP"));
        tableConf.getColumns().addAll(countConf, sourceCount, deviceCount, buf, using, deny);

        hb.getChildren().addAll(tableConf);
        stage.setScene(scene);
        stage.show();
    }


}
