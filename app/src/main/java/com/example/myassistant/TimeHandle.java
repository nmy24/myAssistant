package com.example.myassistant;

import android.util.Log;

import java.util.Calendar;
import java.util.Date;

public class TimeHandle {
    public static String getTimeSent()
    {
        String timeIn = "";
        Date currentTime = Calendar.getInstance().getTime();
        String currentTime2 = Calendar.getInstance().getTimeZone().getDisplayName();
        Log.d("aaaaa", currentTime.toString());
        Log.d("aaaaa", currentTime2);
        return currentTime.toString();
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
