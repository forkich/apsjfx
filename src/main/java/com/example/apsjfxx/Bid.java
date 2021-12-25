package com.example.apsjfxx;

public class Bid {

    private String idOfBid;
    private double tBX;
    private double timeOfUsing;
    private int sourceNumber;


    public Bid(int numberOfBid, int sourceNumber, double tBX, double timeOfUsing)
    {
        this.idOfBid = Integer.toString(sourceNumber) + '.' + numberOfBid;
        this.sourceNumber = sourceNumber;
        this.tBX = tBX;
        this.timeOfUsing = timeOfUsing;
    }

    int getNumOfBid()
    {
        return Integer.parseInt(idOfBid.substring(idOfBid.indexOf(".")+1));
    }

    public String getIdOfBid() {
        return idOfBid;
    }

    public void setIdOfBid(String idOfBid) {
        this.idOfBid = idOfBid;
    }

    public double getTBX() {
        return tBX;
    }

    public void setTBX(int tBX) {
        this.tBX = tBX;
    }

    public int getSourceNumber() {
        return sourceNumber;
    }

    public void setSourceNumber(int sourceNumber) {
        this.sourceNumber = sourceNumber;
    }

    public double getTimeOfUsing() {
        return timeOfUsing;
    }

    public void setTimeOfUsing(double timeOfUsing) {
        this.timeOfUsing = timeOfUsing;
    }

    @Override
    public String toString()
    {
        return "Bid{" +
                "idOfBid='" + idOfBid + '\'' +
                ", tBX=" + tBX +
                ", sourceNumber=" + sourceNumber +
                '}';
    }
}
