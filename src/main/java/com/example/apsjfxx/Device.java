package com.example.apsjfxx;

public class Device {

    private int numberOfDevice;
    private double startTime;
    private double endTime;
    private double usingTime;
    private double coefOfUsing;

    public Device(int numberOfDevice)
    {
        this.numberOfDevice = numberOfDevice;
        refresh();
    }

    public double addBid(Bid bid, double currentTime)
    {
        startTime = currentTime;
        usingTime = Utils.castFormat(usingTime + bid.getTimeOfUsing(), 3);
        endTime = Utils.castFormat(startTime + bid.getTimeOfUsing(), 3);
        return endTime;
    }

    public void refresh()
    {
        this.usingTime = 0;
        this.coefOfUsing = 0;
        this.endTime = 0;
    }
    boolean isFree(double currentTime)
    {
        return currentTime > endTime;
    }

    public double getCoefOfUsing() {
        return coefOfUsing;
    }

    public void setCoefOfUsing(double timeOfSimulation)
    {
        this.coefOfUsing = Utils.castFormat(usingTime / timeOfSimulation , 3);
    }

    public int getNumberOfDevice() {
        return numberOfDevice;
    }

    public void setNumberOfDevice(int numberOfDevice) {
        this.numberOfDevice = numberOfDevice;
    }

    public double getStartTime() {
        return startTime;
    }

    public void setStartTime(double startTime) {
        this.startTime = startTime;
    }

    public double getEndTime() {
        return endTime;
    }

    public void setEndTime(double endTime) {
        this.endTime = endTime;
    }

    @Override
    public String toString()
    {
        return "Device № " + numberOfDevice +
                ", coefficient of using = " + coefOfUsing;
    }
}
