package com.example.apsjfxx;

import java.util.*;

public class Buffer {

    private ArrayList<Bid> arrayBid = new ArrayList<>();
    private int size;
    private int index;
    private int occupied_size;
    private int priorityPackage;

     //<NumSource, count of that num>
    private  Map<Integer, Integer> countPackages = new HashMap<>();

    public Buffer(int new_size)
    {
        size = new_size;
        refresh();
    }

    public void refresh()
    {
        index = 0;
        occupied_size = 0;
        priorityPackage = -1;
    }

    boolean isFull()
    {
        return occupied_size == size;
    }

    boolean isEmpty()
    {
        return occupied_size == 0;
    }

    HashMap<Integer, Integer> bidsBySource(int numberSource)
    {
        //<id, index>
        HashMap<Integer, Integer> map = new HashMap<>();
        Bid bid;
        for (int i = 0; i < arrayBid.size(); i++)
        {
           bid = arrayBid.get(i);
           if(bid != null && bid.getSourceNumber() == numberSource)
           {
               map.put(bid.getNumOfBid(), i);
           }
        }
        return map;
    }

    public void getPackagePriority()
    {
        Collection<Integer> values = this.countPackages.values();
        Collection<Integer> sources = new ArrayList<>();
        int max = Collections.max(values);
        for (int k : this.countPackages.keySet())
        {
            if (this.countPackages.get(k).equals(max) )
            {
                sources.add(k);
            }
        }
        priorityPackage = Collections.min(sources);
    }

    public int getIndexToDeny()
    {
       return getIndexBySourceNum(countPackages.keySet()
                .stream()
                .max(Integer::compareTo)
                .orElse(0),
                "max");
    }

    public int getIndexToDevice()
    {
        if(!countPackages.containsKey(priorityPackage))
        {
            getPackagePriority();
        }
        return getIndexBySourceNum(priorityPackage, "min");
    }

    public int getIndexBySourceNum(int sourceNum, String compare)
    {
        //<id, index>
        HashMap<Integer, Integer> producers = bidsBySource(sourceNum);
        if (producers.size() == 1)
        {
            return (int) producers.values().toArray()[0];
        }
        else if(Objects.equals(compare, "min"))
        {
            return producers.get(producers.keySet().stream()
                    .min(Integer::compare)
                    .get());
        }
        else if(Objects.equals(compare, "max"))
        {
            return producers.get(producers.keySet().stream()
                    .max(Integer::compare)
                    .get());
        }
        else
        {
            //ToDO: add check return value
            return -1;
        }
    }

    public int addBid(Bid bid) {
        for(int i = index; i < size; i++)
        {
            try {
                if (arrayBid.get(i) == null)
                {
                    index = i;
                    break;
                }
            } catch ( IndexOutOfBoundsException e ) {
                arrayBid.add(i, null);
                index = i;
                break;
            }
            if(i + 1== size)
            {
                i = -1;
            }

        }
        arrayBid.set(index, bid);
        int indexResult = index;
        index = (index == size - 1) ? 0 : index + 1;
        occupied_size ++;
        int word = bid.getSourceNumber();
        plusCountPackage(word);
        return indexResult;
    }

    private void plusCountPackage(int word)
    {
        int count = countPackages.getOrDefault(word, 0);
        countPackages.put(word, count + 1);
    }

    private void minusCountPackage(int word)
    {
        int count = countPackages.getOrDefault(word, 0);
        if(count - 1 == 0)
        {
            countPackages.remove(word);
        }
        else
        {
            countPackages.put(word, count - 1);
        }
    }

    public String denyBid(Bid bid)
    {
        int index = getIndexToDeny();
        Bid knockOutBid = this.arrayBid.set(index, null);
        minusCountPackage(knockOutBid.getSourceNumber());
        occupied_size -= 1;
        return knockOutBid.getIdOfBid() + " " + index;
    }

    public Bid outBid()
    {
        int index = getIndexToDevice();
        Bid knockOutBid = this.arrayBid.get(index);
        this.arrayBid.set(index, null);
        minusCountPackage(knockOutBid.getSourceNumber());
        occupied_size -= 1;
        return knockOutBid;
    }

    @Override
    public String toString() {
        return "Buffer{" +
                "\narrayBid=" + arrayBid +
                ",\nsize=" + size +
                ",\nindex=" + index +
                ",\noccupied_size=" + occupied_size +
                ",\ncountPackages=" + countPackages +
                '}';
    }

    public ArrayList<Bid> getArrayProducer()
    {
        return arrayBid;
    }

    public void setArrayProducer(ArrayList<Bid> arrayBid)
    {
        this.arrayBid = arrayBid;
    }

    public int getSize()
    {
        return size;
    }

    public void setSize(int size)
    {
        this.size = size;
    }

    public int getIndex()
    {
        return index;
    }

    public void setIndex(int index)
    {
        this.index = index;
    }

    public int getOccupied_size()
    {
        return occupied_size;
    }

    public void setOccupied_size(int occupied_size)
    {
        this.occupied_size = occupied_size;
    }

    public void setCountPackages(Map<Integer, Integer> countPackages)
    {
        this.countPackages = countPackages;
    }

    public Map<Integer, Integer> getCountPackages()
    {
        return countPackages;
    }
}
