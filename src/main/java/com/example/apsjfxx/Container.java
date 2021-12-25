package com.example.apsjfxx;

import javafx.collections.ObservableList;

import java.util.ArrayList;

public class Container {
    private int countOfDevices;
    private int countOfSources;
    private int indexOfLastGenEvent;

    private ArrayList<Source> sources = new ArrayList<>();
    private ArrayList<SystemEvent> systemEvents = new ArrayList<>();
    private ArrayList<Device> devices = new ArrayList<>();

    public Container(Container container) {
        this.countOfDevices = container.getCountOfDevices();
        this.countOfSources = container.getCountOfSources();
        sources = container.getSources();
        devices = container.getDevices();
        systemEvents = container.getSystemEvents();
        this.indexOfLastGenEvent = container.getIndexOfLastGenEvent();
    }

    public void setOtherContainer(Container container)
    {
        this.countOfDevices = container.getCountOfDevices();
        this.countOfSources = container.getCountOfSources();
        sources = container.getSources();
        devices = container.getDevices();
        systemEvents = container.getSystemEvents();
        this.indexOfLastGenEvent = container.getIndexOfLastGenEvent();
    }
    public Container(int countOfDevices, int countOfSources) {

        this.countOfDevices = countOfDevices;
        this.countOfSources = countOfSources;
        indexOfLastGenEvent = 0;

        for (int i = 0; i < this.countOfSources; i++)
        {
            sources.add(new Source(i));
        }
        for (int i = 0; i < this.countOfDevices; i++)
        {
            devices.add(new Device(i));
        }
    }
    public void refresh()
    {
        systemEvents.clear();
        indexOfLastGenEvent = 0;
        for (Source currentSource: sources)
        {
            currentSource.refresh();
        }
        for (Device currentDevice: devices)
        {
            currentDevice.refresh();
        }
    }

    public void createNewBid(int numOfSource, double tBX, double timeOfUsing, Buffer buffer)
    {
       checkForUnreleased(buffer, tBX);
       Bid bid = sources.get(numOfSource).createBid(tBX, timeOfUsing);
       addEvent(EventType.BidGenerated, tBX, bid.getIdOfBid(), bid.getSourceNumber(), true);
       if(!setIfFreeBidOnDevice(bid))
       {
           if(buffer.isFull())
           {
               String str = buffer.denyBid(bid);
               sources.get(Integer.parseInt(str.substring(0, str.indexOf('.')))).addDenied();
               addEvent(EventType.BidDenied, tBX, Utils.cutIdBid(str), Utils.cutNumApp(str), true);
               addEvent(EventType.BidSetOnBuffer, tBX, bid.getIdOfBid(), buffer.addBid(bid), true);
           }
           else
           {
               addEvent(EventType.BidSetOnBuffer, tBX, bid.getIdOfBid(), buffer.addBid(bid), true);
           }
       }
    }

    public void handlingReleased(SystemEvent event, Buffer buffer)
    {
        event.setDone(true);
        if(!buffer.isEmpty())
        {
            Bid outBid = buffer.outBid();
            Device device =  devices.get(event.getNumOfApp());
            sources.get(outBid.getSourceNumber()).addWaitingTime(device.getEndTime() - outBid.getTBX());
            setBidOnDevice(outBid, device.getEndTime(), device);
        }
    }

    //if free devices
    public boolean setIfFreeBidOnDevice(Bid bid)
    {
        for (Device currentDevice:devices)
        {
            if(currentDevice.isFree(bid.getTBX()))
            {
                setBidOnDevice(bid, bid.getTBX(), currentDevice);
                return true;
            }
        }
        return false;
    }

    void setBidOnDevice(Bid bid, double startTime, Device device)
    {
        int numOfDevice = devices.indexOf(device);
        addEvent(EventType.BidSetOnDevice, startTime, bid.getIdOfBid(), numOfDevice, true);
        addEvent(EventType.DeviceReleased, device.addBid(bid, startTime), bid.getIdOfBid(), numOfDevice, false);
    }

    public void addEvent(EventType type, double startTime, String idBid, int numApp, boolean done)
    {
        int index;
        index = spotIndexEvent(startTime);

        if(type == EventType.BidGenerated)
        {
            indexOfLastGenEvent = index;
        }
        systemEvents.add(index, new SystemEvent(type, startTime, idBid, numApp, done));
    }

    private int spotIndexEvent(double startTime)
    {
        if (!(systemEvents.size() == 0))
        {
            for (int i = indexOfLastGenEvent; i < systemEvents.size(); i++)
            {
                if (startTime < systemEvents.get(i).getTime())
                {
                    return i;
                }
                else if (i + 1 == systemEvents.size())
                {
                    return i + 1;
                }
                else if (startTime == systemEvents.get(i).getTime())
                {
                    if(startTime == systemEvents.get(i + 1).getTime())
                    {
                        return i + 2;
                    }
                    return i + 1;
                }
            }
            //ToDo:check return value
            return -1;
        }
        else
        {
            return 0;
        }
    }

    public void checkForUnreleased(Buffer buffer, double tBX)
    {
        for(int i = indexOfLastGenEvent + 1; i < systemEvents.size() && systemEvents.get(i).getTime() < tBX; i++)
        {
            if(systemEvents.get(i).getType() == EventType.DeviceReleased)
            {
                handlingReleased(systemEvents.get(i), buffer);
            }
        }
    }
    public void countSourceStatistic()
    {
        for (Source currentSource: sources)
        {
            currentSource.countFullStatistic();
        }
    }

    public void countDeviceStatistic(double timeOfSimulation)
    {
        for (Device currentDevice:devices)
        {
            currentDevice.setCoefOfUsing(timeOfSimulation);
        }
    }

    public ArrayList<String> getFullStat(double timeOfSimulation)
    {
        ArrayList<String> stat = new ArrayList<>();
        stat.add("-------Sources-------");
        for (Source currentSource:sources)
        {
            stat.add(currentSource.toString());
        }
        stat.add("-------Devices-------");
        for (Device currentDevice:devices)
        {
            currentDevice.setCoefOfUsing(timeOfSimulation);
            stat.add(currentDevice.toString());
        }
        return stat;
    }

    public void printFullStatistic(double timeOfSimulation)
    {
        System.out.println("-------Sources-------\n");
        for (Source currentSource:sources)
        {
            System.out.println(currentSource);
           //list.add(currentSource.getStat());
        }
        System.out.println("-------Devices-------\n");
        for (Device currentDevice:devices)
        {
            currentDevice.setCoefOfUsing(timeOfSimulation);
            System.out.println(currentDevice);
        }
    }

    public double getGeneralDeviceUsing()
    {
        double generalUsing = 0;
        for (Device currentDevice : devices)
        {
            generalUsing += currentDevice.getCoefOfUsing();
        }
        return Utils.castFormat(generalUsing / devices.size(), 3);
    }

    public boolean checkForRequiredTStaying(double time)
    {
        for (Source currentSource:sources)
        {
            if (currentSource.getTStaying() > time)
            {
                System.out.println("too mush time of staying :(");
                return false;
            }
        }
        return true;
    }

    public double getGeneralPOfDeny()
    {
        double generalP = 0;
        for (Source currentSource:sources)
        {
            generalP += currentSource.getpOfDeny();
        }
        return Utils.castFormat(generalP / sources.size(), 3);
    }

    public int getCountOfSources() {
        return countOfSources;
    }

    public ArrayList<Source> getSources() {
        return sources;
    }

    public void setSources(ArrayList<Source> sources) {
        this.sources = sources;
    }

    public ArrayList<SystemEvent> getSystemEvents() {
        return systemEvents;
    }

    public void setSystemEvents(ArrayList<SystemEvent> systemEvents) {
        this.systemEvents = systemEvents;
    }

    public ArrayList<Device> getDevices() {
        return devices;
    }

    public void setDevices(ArrayList<Device> devices) {
        this.devices = devices;
    }

    public int getCountOfDevices() {
        return countOfDevices;
    }

    public void setCountOfDevices(int countOfDevices) {
        this.countOfDevices = countOfDevices;
    }

    public void setCountOfSources(int countOfSources) {
        this.countOfSources = countOfSources;
    }

    public int getIndexOfLastGenEvent() {
        return indexOfLastGenEvent;
    }

    public void setIndexOfLastGenEvent(int indexOfLastGenEvent) {
        this.indexOfLastGenEvent = indexOfLastGenEvent;
    }
}
