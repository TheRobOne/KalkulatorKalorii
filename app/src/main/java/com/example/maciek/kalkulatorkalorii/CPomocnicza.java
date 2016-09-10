package com.example.maciek.kalkulatorkalorii;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Maciek on 09.09.2016.
 */
public class CPomocnicza {

    public static String getDate() {
        Date currentDate = new Date();
        String curDate = new SimpleDateFormat("yyyy-MM-dd").format(currentDate);
        return curDate;
    }


    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
    //
}
