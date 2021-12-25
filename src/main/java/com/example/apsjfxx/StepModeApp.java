package com.example.apsjfxx;

import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class StepModeApp extends Application {
    int countOfSources;
    int countOfDevices;
    int bufferSize;
    ArrayList<SystemEvent> events;
    int numStep = 0;
    int numCell = 1;
    int nextNumCellStep = 1;
    int additionalNext = 0;
    boolean afterRelease = false;

    ArrayList<SystemEvent> buffersBid = new ArrayList<>();
    ArrayList<SystemEvent> sourceBid = new ArrayList<>();
    public StepModeApp(Simulator simulator) {
        Container container = simulator.getContainer();
        bufferSize = simulator.getBuffer().getSize();
        countOfDevices = container.getCountOfDevices();
        countOfSources = container.getCountOfSources();
        events = container.getSystemEvents();
    }

    @Override
    public void start(Stage stage) throws IOException {
        ScrollPane root = new ScrollPane();
        GridPane gridPane = new GridPane();
        Button next = new Button("next");
        next.setOnAction(actionEvent -> {
            next(gridPane);
        });
        next.setMaxWidth(100);
        gridPane.add(next, 1, 0);
        root.setContent(gridPane);
        root.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        root.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        stage.setTitle("Step Mode");
        gridPane.setGridLinesVisible(true);
        makeTable(gridPane);
        Scene scene = new Scene(root, 1500, 900);
        stage.setScene(scene);
        stage.show();
    }

    private SystemEvent checkForBufferRelease(SystemEvent event)
    {
        for (SystemEvent current:buffersBid)
        {
            if (Objects.equals(current.getIdOfBid(), event.getIdOfBid()))
            {
                int index = events.indexOf(current);
                return events.remove(index);
            }
        }
        return null;
    }

    private void next(GridPane gridPane)
    {
        SystemEvent event = events.get(numStep);
        String text = "  " + event.getIdOfBid() + "  ";
        Button button = new Button();
        button.setText(text);
        if (event.getType() == EventType.BidGenerated)
        {
            gridPane.add(button, numCell, event.getNumOfApp() + 1);
        }
        else if (event.getType() == EventType.BidSetOnDevice)
        {
            sourceBid.add(event);
            SystemEvent res = checkForBufferRelease(event);
            if (res != null)
            {
                Button bufButton = new Button();
                bufButton.setText("  " + res.getIdOfBid() + "  ");
                bufButton.setStyle("-fx-background-color: #ffd700");
                gridPane.add(bufButton, numCell, res.getNumOfApp() + countOfSources + countOfDevices + 3);
                gridPane.add(button, numCell, event.getNumOfApp() + countOfSources + 2);
            }
            else
            {
                gridPane.add(button, numCell, event.getNumOfApp() + countOfSources + 2);
            }

        }
        else if (event.getType() == EventType.BidSetOnBuffer)
        {
            buffersBid.add(event);
            gridPane.add(button, numCell, event.getNumOfApp() + countOfSources + countOfDevices + 3);
        }
        else if (event.getType() == EventType.BidDenied)
        {
            //пунктир
//            int len = 30 * (countOfDevices + bufferSize + 3 - countOfSources + event.getNumOfApp() + 1);
//            Line line = new Line(0, 0, 0, len + 3);
//            line.setStroke(Color.BLACK);
//            line.getStrokeDashArray().addAll(25.0, 10.0);
//
//            GridPane.setHalignment(line, HPos.CENTER);
//            GridPane.setValignment(line, VPos.TOP);
           //gridPane.add(line, numCell, event.getNumOfApp() + 1);
            gridPane.add(button, numCell, countOfDevices + bufferSize + 4 + countOfSources);

        }
        else if (event.getType() == EventType.DeviceReleased)
        {
            release(event);
            button.setStyle("-fx-background-color: #ffd700");
            gridPane.add(button, numCell - 1, countOfSources + event.getNumOfApp() + 2);
            //gridPane.add(line, numCell, countOfSources + event.getNumOfApp() + 2);
        }

        if (numStep == nextNumCellStep)
        {
            numCell++;
            getNextNumCell(numStep + 1);
        }
        numStep++;
        makePart(gridPane);
    }

    private void setBlock(GridPane grid, int num)
    {
        Button button = new Button();
        SystemEvent current;
        for (int i = 0; i < buffersBid.size(); i ++)
        {
            if(buffersBid.get(i) != null)
            {
                current = buffersBid.get(i);
                button.setText("  " + current.getIdOfBid() + "  ");
                grid.add(button, numCell, current.getNumOfApp() + countOfSources + countOfDevices + 3 );
            }
        }
        for (SystemEvent systemEvent : sourceBid)
        {
            if ((systemEvent != null))
            {
                current = systemEvent;
                button.setText("  " + current.getIdOfBid() + "  ");
                grid.add(button, numCell, current.getNumOfApp() + countOfSources + 2);
            }
        }
    }

    private void release(SystemEvent event)
    {
        sourceBid.removeIf(current -> current.getIdOfBid().equals(event.getIdOfBid()));
    }

    private void getNextNumCell(int step)
    {
        double currentTime = events.get(step).getTime();
        int count = 1;
        for (int i = step + 1; i < events.size() ; i++)
        {
            if (currentTime < events.get(i).getTime() )
            {
                break;
            }
            else
            {
                count++;
            }
        }
        this.nextNumCellStep += count;
    }

    public void makePart(GridPane gridPane) {
        Rectangle rect = new Rectangle(40, 30, Color.GRAY);
        GridPane.setHalignment(rect, HPos.CENTER);
        GridPane.setValignment(rect, VPos.CENTER);
        for (int i = 0; i < countOfSources + countOfDevices + bufferSize + 5; i++)
        {
           if (i == 0 && numCell != 1)
           {
                gridPane.add(rect, numCell, 0);
           }
           else if (i == countOfSources + 1)
           {
                rect = new Rectangle(40, 30, Color.GRAY);
                gridPane.add(rect, numCell, i);
           }
           else if (i == countOfSources + countOfDevices + 2)
           {
                rect = new Rectangle(40, 30, Color.GRAY);
                gridPane.add(rect, numCell, i);
           }
           else if (i == countOfSources + countOfDevices + bufferSize + 3)
           {
                rect = new Rectangle(40, 30, Color.GRAY);
                gridPane.add(rect, numCell, i);
           }
        }
    }

    public void makeTable(GridPane gridPane) {
        gridPane.getColumnConstraints().add(new ColumnConstraints(50));
        for (int i = 0; i < countOfSources + countOfDevices + bufferSize + 5; i++) {
            gridPane.getRowConstraints().add(new RowConstraints(30));
            if (i == 0) {
                Label label = new Label(" Sources");
                gridPane.add(label, 0, 0);
            } else if (i == countOfSources + 1) {
                Label label = new Label("  Devises");
                gridPane.add(label, 0, i);
            } else if (i == countOfSources + countOfDevices + 2) {
                Label label = new Label(" Buffer");
                gridPane.add(label, 0, i);
            } else if (i == countOfSources + countOfDevices + bufferSize + 3) {
                Label label = new Label("  Deny");
                gridPane.add(label, 0, i);
            } else {
                if (i <= countOfSources) {
                    Label label = new Label("   " + Integer.toString(i - 1));
                    gridPane.add(label, 0, i);
                } else if (i - countOfSources - 1 <= bufferSize) {
                    Label label = new Label("   " + Integer.toString(i - countOfSources - 2));
                    gridPane.add(label, 0, i);
                } else if (i - countOfSources - bufferSize - 2 <= countOfDevices) {
                    Label label = new Label("   " + Integer.toString(i - countOfSources - bufferSize - 2));
                    gridPane.add(label, 0, i);
                }
            }
        }
    }
}