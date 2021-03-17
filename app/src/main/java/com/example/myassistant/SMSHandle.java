package com.example.myassistant;

import android.telephony.SmsManager;

/**
 * this class handles all the functions related to sending sms.
 * @author Noa Fatael
 */
public class SMSHandle {
    SmsManager smsManager;
    String sendToPhone;
    /**
     * SMSHandle constructor
     */
    public SMSHandle()
    {
        smsManager = SmsManager.getDefault();
        sendToPhone = "";
    }
    /**
     * This function will send an sms.
     * @param
     * @return
     */
    public void sendSMS(String pNumber, String cont)
    {
        smsManager.sendTextMessage(pNumber,null,cont,null,null);

    }
    /**
     * This function return if this command fits this class.
     * @param command
     * @return return if this command fits this class.
     */
    public static boolean isCammand(String command){
        return (command.contains("send") && command.contains("sms"));
    }
}
