package com.example.myassistant;

import android.util.Log;

import java.sql.Time;
import java.util.Calendar;
import java.util.Date;
/**
 * this class handles all the functions related to time.
 * @author Noa Fatael
 */
public class TimeHandle {
    private static String[] currentTimeArr;
    private static final int DAY_IN_WEEK = 0;
    private static final int MONTH = 1;
    private static final int MONTH_DAY = 2;
    private static final int TIME = 3;
    private static final int YEAR = 5;
    /**
     * This function will get the command and say the time.
     * @param
     * @return
     */
    public static void sayTime(String command)
    {
        MainActivity.tts.talk(getTime());
    }
    /**
     * This function will return the time RN.
     * @param
     * @return
     */
    private static String getTime()
    {
        updateTime();
        return currentTimeArr[TIME];
    }
    /**
     * This function will get the command and say the day.
     * @param
     * @return
     */
    public static void sayDay(String command)
    {
        MainActivity.tts.talk(getDay());
    }
    /**
     * This function will return the day RN.
     * @param
     * @return
     */
    private static String getDay()
    {
        updateTime();
        return currentTimeArr[DAY_IN_WEEK];
    }
    /**
     * This function will get the command and say the date.
     * @param
     * @return
     */
    public static void sayDate(String s) {
        updateTime();
        MainActivity.tts.talk(currentTimeArr[MONTH_DAY] + " of " + currentTimeArr[MONTH]);
    }
    /**
     * This function will get the command and say the year.
     * @param
     * @return
     */
    public static void sayYear(String command)
    {
        MainActivity.tts.talk(getYear());
    }
    /**
     * This function will return the year.
     * @param
     * @return
     */
    private static String getYear()
    {
        updateTime();
        return currentTimeArr[YEAR];
    }
    /**
     * This function will update the date object to a array of strings.
     * @param
     * @return
     */
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
    /**
     * This function return if this command fits this class.
     * @param command
     * @return return if this command fits this class.
     */
    public static boolean isCammandGetDay(String command){
        return (command.contains("day"));
    }
    /**
     * This function return if this command fits this class.
     * @param command
     * @return return if this command fits this class.
     */
    public static boolean isCammandGetDate(String command){
        return (command.contains("date"));
    }
    /**
     * This function return if this command fits this class.
     * @param
     * @return return if this command fits this class.
     */
    public static boolean isCammandGetYear(String command){
        return (command.contains("year"));
    }
}
