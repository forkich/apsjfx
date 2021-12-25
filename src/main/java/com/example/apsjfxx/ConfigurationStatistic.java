package com.example.apsjfxx;

public class ConfigurationStatistic {

    private int num;
    private int sources;
    private int devices;
    private int buffer;
    private double usingDevices;
    private double denyP;

    public ConfigurationStatistic(int num, int sources, int devices, int buffer, double usingDevices, double denyP) {
        this.num = num;
        this.sources = sources;
        this.devices = devices;
        this.buffer = buffer;
        this.usingDevices = usingDevices;
        this.denyP = denyP;
    }

    public int getNum()
    {
        return num;
    }

    public void setNum(int num)
    {
        this.num = num;
    }

    public int getSources() {
        return sources;
    }

    public void setSources(int sources) {
        this.sources = sources;
    }

    public int getDevices() {
        return devices;
    }

    public void setDevices(int devices) {
        this.devices = devices;
    }

    public int getBuffer() {
        return buffer;
    }

    public void setBuffer(int buffer) {
        this.buffer = buffer;
    }

    public double getUsingDevices() {
        return usingDevices;
    }

    public void setUsingDevices(double usingDevices) {
        this.usingDevices = usingDevices;
    }

    public double getDenyP() {
        return denyP;
    }

    public void setDenyP(double denyP) {
        this.denyP = denyP;
    }

    @Override
    public String toString()
    {
        return "ConfigurationStatistic{" +
                "sources=" + sources +
                ", devices=" + devices +
                ", buffer=" + buffer +
                ", usingDevices=" + usingDevices +
                ", pOfDeny=" + denyP +
                '}';
    }
}
