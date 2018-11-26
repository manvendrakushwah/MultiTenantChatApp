package com.example.kushwah_1.a1;

import java.text.SimpleDateFormat;
import java.util.Date;
public class DateTime {

    public String currentDate(){
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        String currDate = format.format(date);
        return currDate;
    }
    public String currentTime(){
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        String currTime = format.format(date);
        return currTime;
    }
}
