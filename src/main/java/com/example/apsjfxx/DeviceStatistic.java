package com.example.apsjfxx;

public class DeviceStatistic {

    private int numberOfDevice;
    private double CoefOfUsing;

    public DeviceStatistic(int numberOfDevice, double coefOfUsing)
    {
        this.numberOfDevice = numberOfDevice;
        CoefOfUsing = coefOfUsing;
    }

    public int getNumberOfDevice()
    {
        return numberOfDevice;
    }

    public void setNumberOfDevice(int numberOfDevice)
    {
        this.numberOfDevice = numberOfDevice;
    }

    public double getCoefOfUsing()
    {
        return CoefOfUsing;
    }

    public void setCoefOfUsing(double coefOfUsing)
    {
        CoefOfUsing = coefOfUsing;
    }
}
