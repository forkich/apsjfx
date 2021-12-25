package com.example.apsjfxx;

public class SystemEvent {

    private EventType type;
    private double time;
    private String idOfBid;
    private int numOfApp;
    private boolean done;

    public SystemEvent(EventType type, double time, String idOfBid, int numOfApp, boolean done)
    {
        this.type = type;
        this.time = time;
        this.idOfBid = idOfBid;
        this.numOfApp = numOfApp;
        this.done = done;
    }

    @Override
    public String toString() {
        return "SystemEvent{" +
                "type=" + type +
                ", time=" + time +
                ", idOfBid='" + idOfBid + '\'' +
                ", numOfApp=" + numOfApp +
                ", done=" + done +
                '}';
    }

    public EventType getType() {
        return type;
    }

    public void setType(EventType type) {
        this.type = type;
    }

    public double getTime() {
        return time;
    }

    public void setTime(double time) {
        this.time = time;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public String getIdOfBid() {
        return idOfBid;
    }

    public void setIdOfBid(String idOfBid) {
        this.idOfBid = idOfBid;
    }

    public int getNumOfApp() {
        return numOfApp;
    }

    public void setNumOfApp(int numOfApp) {
        this.numOfApp = numOfApp;
    }
}
