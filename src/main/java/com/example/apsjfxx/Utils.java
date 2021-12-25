package com.example.apsjfxx;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Utils {

    public static double castFormat(double number, int scale)
    {
        BigDecimal bdUp= new BigDecimal(number).setScale(scale, RoundingMode.DOWN);
        return bdUp.doubleValue();
    }

    public static String cutIdBid(String str)
    {
        return str.substring(0, str.indexOf(' '));
    }

    public static int cutNumApp(String str)
    {
        return Integer.parseInt(str.substring(str.indexOf(' ') + 1));
    }

}
