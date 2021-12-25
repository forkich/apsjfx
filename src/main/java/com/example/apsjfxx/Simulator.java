package com.example.apsjfxx;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Simulator {

    private double tAlfa = 1.643;
    private double delta = 0.1;

    private double requiredPOfDeny = 0.1;
    private double requiredTStaying = 60;
    private double requiredDeviceUsing = 0.9;

    private Generator generator;
    private Container container;
    public Buffer buffer;
    private int previousN;
    private int currentN;
    private double previousP;
    private double currentP;
    ObservableList<ConfigurationStatistic> listConf = FXCollections.observableArrayList();

    public Simulator(int countOfDevices, int countOfSources, int bufferSize, double lambda, int countOfBids, double min, double max)
    {
        this.currentN = countOfBids;
        this.previousP = 0;
        container = new Container(countOfDevices, countOfSources);
        generator = new Generator(min, max, lambda);
        buffer = new Buffer(bufferSize);

    }
    public void refresh()
    {
        container.refresh();
        buffer.refresh();
    }
    public void start(int countOfBids)
    {
        generator.autoGenerateBids(buffer, container, countOfBids);
    }

    public Container autoProcessing()
    {
        start(currentN);
        currentP = container.getGeneralPOfDeny();
        Container prevContainer = new Container(container);
        while (Math.abs(previousP - currentP) >= previousP*delta)
        {
            previousP = currentP;
            previousN = currentN;
            currentN = getNByP(currentP);
            prevContainer.setOtherContainer(container);
            refresh();
            start(currentN);
            currentP = container.getGeneralPOfDeny();
            System.out.println("previous N " + previousN);
        }
        System.out.println("End of auto-simulation\n The sought count of Bids: " + previousN + "\n The Statistic: \n");
        container.setOtherContainer(prevContainer);
        return container;
    }

    public ObservableList<ConfigurationStatistic>  getConfiguration()
    {
        start(currentN);
        int countOfSources = container.getCountOfSources();
        int countOfDevices = container.getCountOfDevices();
        int bufferSize =  buffer.getSize();
        int additionalBuf = 0;
        int additionalDev = 0;
        int bufLimit = 3;
        int devLimit = 3;
        int count = 1;
        boolean containerAddFlag;
        listConf.add(new ConfigurationStatistic(count, countOfSources, countOfDevices, bufferSize ,
                container.getGeneralDeviceUsing(), container.getGeneralPOfDeny()));
        while (!checkConf() && count < 100)
        {
            containerAddFlag = false;
            refresh();
            if (additionalBuf < bufLimit)
            {
                additionalBuf++;
            }
            else if (additionalDev < devLimit)
            {
                additionalDev++;
                containerAddFlag = true;
            }
            else
            {
                bufLimit += 3;
                devLimit += 3;
                additionalBuf++;
            }
            buffer.setSize(bufferSize + additionalBuf);
            if (containerAddFlag)
            {
                container.setOtherContainer(new Container(countOfSources, countOfDevices + additionalDev));
            }
            start(currentN);
            count++;
            listConf.add(new ConfigurationStatistic(count, countOfSources, countOfDevices + additionalDev, bufferSize + additionalBuf ,
                    container.getGeneralDeviceUsing(), container.getGeneralPOfDeny()));
        }
       return listConf;
    }

    public int getPreviousN()
    {
        return previousN;
    }

    public void setPreviousN(int previousN)
    {
        this.previousN = previousN;
    }

    private boolean checkConf()
    {
        return container.getGeneralPOfDeny() < requiredPOfDeny && container.getGeneralDeviceUsing() > requiredDeviceUsing
                && container.checkForRequiredTStaying(requiredTStaying);
    }

    private int getNByP(double p)
    {
        return (int) ((1 - p)*tAlfa*tAlfa/(p*delta*delta));
    }

    public Generator getGenerator() {
        return generator;
    }

    public void setGenerator(Generator generator) {
        this.generator = generator;
    }

    public Container getContainer() {
        return container;
    }

    public void setContainer(Container container) {
        this.container = container;
    }

    public Buffer getBuffer() {
        return buffer;
    }

    public void setBuffer(Buffer buffer) {
        this.buffer = buffer;
    }

}
