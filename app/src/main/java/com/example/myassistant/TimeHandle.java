package com.example.myassistant;

import android.util.Log;

import java.sql.Time;
import java.util.Calendar;
import java.util.Date;

public class TimeHandle {
    private static String[] currentTimeArr;
    private static final int DAY_IN_WEEK = 0;
    private static final int MONTH = 1;
    private static final int MONTH_DAY = 2;
    private static final int TIME = 3;
    private static final int YEAR = 5;

    public static void sayTime(String command)
    {
        MainActivity.tts.talk("the time is " + getTime());
    }
    private static String getTime()
    {
        updateTime();
        return currentTimeArr[TIME];
    }
    public static void sayDay(String command)
    {
        MainActivity.tts.talk("the day is " + getDay());
    }
    private static String getDay()
    {
        updateTime();
        return currentTimeArr[DAY_IN_WEEK];
    }
    public static String getDate()
    {
        updateTime();
        return "the date is the "+ currentTimeArr[MONTH_DAY] + " of " + currentTimeArr[MONTH];
    }
    public static String getYear()
    {
        updateTime();
        return "the year is "+ currentTimeArr[YEAR];
    }
    private static void updateTime()
    {
        Date currentTime = Calendar.getInstance().getTime();
        currentTimeArr = currentTime.toString().split(" ");
    }
    /**
     * This function return if this command fits this class.
     * @param command
     * @return return if this command fits this class.
     */
    public static boolean isCammandGetTime(String command){
        return (command.contains("time"));
    }
}
