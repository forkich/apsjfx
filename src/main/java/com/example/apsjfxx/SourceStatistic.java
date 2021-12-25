package com.example.apsjfxx;

public class SourceStatistic {

    private int numOfSource;
    private int countOfBids;
    private double POfDeny;
    private double TStaying;
    private double TUsing;
    private double TWaiting;
    private double DUsing;
    private double DWaiting;

    public SourceStatistic(Source source) {
        this.numOfSource = source.getNumberOfSource();
        this.countOfBids = source.getCountOfBids();
        this.POfDeny = source.getpOfDeny();
        this.TStaying = source.getTStaying();
        this.TUsing = source.getTUsing();
        this.TWaiting = source.getTWaiting();
        this.DUsing = source.getDOfTUsing();
        this.DWaiting = source.getDOfTWaiting();
    }

    public int getNumOfSource() {
        return numOfSource;
    }

    public void setNumOfSource(int numOfSource) {
        this.numOfSource = numOfSource;
    }

    public int getCountOfBids() {
        return countOfBids;
    }

    public void setCountOfBids(int countOfBids) {
        this.countOfBids = countOfBids;
    }

    public double getPOfDeny() {
        return POfDeny;
    }

    public void setPOfDeny(double POfDeny) {
        this.POfDeny = POfDeny;
    }

    public double getTStaying() {
        return TStaying;
    }

    public void setTStaying(double TStaying) {
        this.TStaying = TStaying;
    }

    public double getTUsing() {
        return TUsing;
    }

    public void setTUsing(double TUsing) {
        this.TUsing = TUsing;
    }

    public double getTWaiting() {
        return TWaiting;
    }

    public void setTWaiting(double TWaiting) {
        this.TWaiting = TWaiting;
    }

    public double getDUsing() {
        return DUsing;
    }

    public void setDUsing(double DUsing) {
        this.DUsing = DUsing;
    }

    public double getDWaiting() {
        return DWaiting;
    }

    public void setDWaiting(double DWaiting) {
        this.DWaiting = DWaiting;
    }
}
