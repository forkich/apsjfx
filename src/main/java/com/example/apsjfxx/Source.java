package com.example.apsjfxx;

import java.util.ArrayList;

public class Source {

    private int numberOfSource;
    private int countOfBids;

    private int countOfDeniedBids;
    private double TUsing;
    private double TWaiting;
    private double TStaying;
    private double DOfTUsing;
    private double DOfTWaiting;
    private double pOfDeny;

    private ArrayList<Double> timeOfUsing = new ArrayList<>();
    private ArrayList<Double> timeOfWaiting = new ArrayList<>();

    public Source(int numberOfSource) {
        this.numberOfSource = numberOfSource;
        refresh();
    }
    public void refresh()
    {
        this.countOfBids = 0;
        this.countOfDeniedBids = 0;
        this.TUsing = 0;
        this.TWaiting = 0;
        this.DOfTUsing = 0;
        this.DOfTWaiting = 0;
        this.TStaying = 0;
        this.pOfDeny = 0;
    }

    Bid createBid(double tBX, double timeOfUsing)
    {
     countOfBids += 1;
     this.timeOfUsing.add(Utils.castFormat(timeOfUsing, 3));
     return new Bid(countOfBids, numberOfSource, tBX, timeOfUsing);
    }
    public void addDenied()
    {
        countOfDeniedBids++;
    }

    public void addWaitingTime(double timeOfWaiting)
    {
        this.timeOfWaiting.add(Utils.castFormat(timeOfWaiting, 3));
    }

    void countFullStatistic()
    {
        if(countOfBids != 0)
        {
            for (Double current:timeOfUsing)
            {
                TUsing = Utils.castFormat(current + TUsing, 3);
            }
            if(TUsing != 0)
            {
                TUsing = Utils.castFormat(TUsing/timeOfUsing.size(), 3);
                for (Double current:timeOfUsing)
                {
                    DOfTUsing = Utils.castFormat( DOfTUsing + Math.pow((current - TUsing), 2), 3);
                }

                double coef = timeOfUsing.size()-1;
                DOfTUsing = Utils.castFormat(DOfTUsing * (1 / coef), 3);
            }

            for (Double current:timeOfWaiting)
            {
                TWaiting = Utils.castFormat(current + TWaiting, 3);
            }
            if (TWaiting != 0)
            {
                TWaiting = Utils.castFormat(TWaiting / timeOfWaiting.size(), 3);
                for (Double current:timeOfWaiting)
                {
                    DOfTWaiting = Utils.castFormat( DOfTWaiting + Math.pow((current - TWaiting), 2), 3);
                }
                double coef = timeOfUsing.size()-1;
                DOfTWaiting = Utils.castFormat(DOfTWaiting * (1/coef), 3);
            }
            TStaying = Utils.castFormat(TWaiting + TUsing, 3);
            double tempCount = countOfBids;
            pOfDeny = Utils.castFormat(countOfDeniedBids / tempCount, 3);
        }
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

    public double getTStaying() {
        return TStaying;
    }

    public void setTStaying(double TStaying) {
        this.TStaying = TStaying;
    }

    public double getDOfTUsing() {
        return DOfTUsing;
    }

    public void setDOfTUsing(double DOfTUsing) {
        this.DOfTUsing = DOfTUsing;
    }

    public double getDOfTWaiting() {
        return DOfTWaiting;
    }

    public void setDOfTWaiting(double DOfTWaiting) {
        this.DOfTWaiting = DOfTWaiting;
    }

    public double getpOfDeny() {
        return pOfDeny;
    }

    public void setpOfDeny(double pOfDeny) {
        this.pOfDeny = pOfDeny;
    }

    public int getNumberOfSource() {
        return numberOfSource;
    }

    public void setNumberOfSource(int numberOfSource) {
        this.numberOfSource = numberOfSource;
    }

    public int getCountOfBids() {
        return countOfBids;
    }

    public void setCountOfBids(int countOfBids) {
        this.countOfBids = countOfBids;
    }

    @Override
    public String toString()
    {
        return "Source № " + numberOfSource +
                ", countOfBids=" + countOfBids +
                ", pOfDeny=" + pOfDeny +
                 ", TUsing=" + TUsing +
                ", TWaiting=" + TWaiting +
                ", TStaying=" + TStaying +
                ", DOfTUsing=" + DOfTUsing +
                ", DOfTWaiting=" + DOfTWaiting ;
    }
}
