package com.example.apsjfxx;

import java.util.Random;

//all that connect with random functions
public class Generator {

   private double minTimeDevice;
   private double maxTimeDevice;
   private double lambda;

   public Generator(double min, double max, double lambda)
   {
      this.minTimeDevice = min;
      this.maxTimeDevice = max;
      this.lambda = lambda;
   }

   public void autoGenerateBids(Buffer buffer, Container container, int countOfBids)
   {
        double delta_t = 0;
        double currentTime = 0;
        double coef = -1/lambda;
        int currentCount = 0;
        while (currentCount < countOfBids)
        {
            delta_t = coef*Math.log(Math.random());
            currentTime += delta_t;
            container.createNewBid(generateSourceNum(container), Utils.castFormat(currentTime, 3), Utils.castFormat(generateTimeOfUsing(), 3), buffer);
            currentCount++;
        }
        SystemEvent lastEvent = container.getSystemEvents().get(container.getSystemEvents().size() - 1);
        if(!lastEvent.isDone())
        {
            container.checkForUnreleased(buffer,(lastEvent.getTime() + minTimeDevice + 1) * buffer.getSize());
        }
        container.countSourceStatistic();
        container.countDeviceStatistic(container.getSystemEvents().get(container.getSystemEvents().size() - 1).getTime());
   }

    //ToDo change func
   private int generateSourceNum(Container container)
   {
      Random random = new Random();
      return random.nextInt(container.getCountOfSources());

   }
   public double generateTimeOfUsing()
   {
      return Math.random() * maxTimeDevice + minTimeDevice;
   }
}
