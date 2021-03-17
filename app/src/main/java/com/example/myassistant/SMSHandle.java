package com.example.myassistant;

import android.content.ContentResolver;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Toast;

/**
 * this class handles all the functions related to sending sms.
 * @author Noa Fatael
 */
public class SMSHandle {
    SmsManager smsManager;
    String pNumber;

    /**
     * SMSHandle constructor
     */
    public SMSHandle() {
        smsManager = SmsManager.getDefault();
        pNumber = "";
    }

    /**
     * This function will send an sms.
     *
     * @param
     * @return
     */
    public void sendSMS(String command) {
        String contIn = getMSGCont(command);
        pNumber = extractNumber(command);
        String name = getContactNameFromCommand(command);
        if (pNumber == "")//there was no number ===> contact
        {
            getContactpNumber(name);
        }
        if(contIn != "" && pNumber != "")
            smsManager.sendTextMessage(pNumber,null,contIn,null,null);
    }
    /**
     * This function return if this command fits this class.
     *
     * @param
     * @return return if this command fits this class.
     */
    public static boolean isCammand(String command) {
        return (command.contains("send") && command.contains("sms"));
    }
    /**
     * This function will extract the phone number from the command if was given.
     *
     * @param
     * @return returns phone number from the command or ""
     */
    private String extractNumber(String str) {
        if (str == null || str.isEmpty()) return "";
        StringBuilder sb = new StringBuilder();
        boolean found = false;
        for (char c : str.toCharArray()) {
            if (Character.isDigit(c)) {
                sb.append(c);
                found = true;
            } else if (found) {
                // If we already found a digit before and this char is not a digit, stop looping
                break;
            }
        }
        return sb.toString();
    }
    /**
     * This function will get the phone number of a contact in the phone.
     *
     * @param
     * @return
     */
    private void getContactpNumber(String nameIn) {
        ContentResolver cr = MainActivity.getContext().getContentResolver();
        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,
                null, null, null, null);
        if ((cur != null ? cur.getCount() : 0) > 0) {
            while (cur != null && cur.moveToNext()) {
                String id = cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID));
                String name = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                if (cur.getInt(cur.getColumnIndex(
                        ContactsContract.Contacts.HAS_PHONE_NUMBER)) > 0) {
                    Cursor pCur = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?", new String[]{id}, null);
                    while (pCur.moveToNext()) {
                        String phoneNo = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                        if(name.equals(nameIn))
                        {
                            pNumber = phoneNo;
                        }
                    }
                    pCur.close();
                }
            }
        }
    }
    /**
     * This function will get from command to who to send the sms.
     *
     * @param
     * @return who to send the sms.
     */
    private String getContactNameFromCommand(String command)
    {
        String name = "";
        try{
            name = command.substring(command.indexOf("to ")+3);
            name = name.substring(0, name.indexOf(" "));
        }
        catch (Exception e)
        {
            Toast.makeText(MainActivity.getContext(),"invalid command", Toast.LENGTH_SHORT).show();
        }
        return name;
    }
    /**
     * This function will get from command the content to send.
     *
     * @param
     * @return the content to send.
     */
    private String getMSGCont(String command) {
        {
            String cont = "";
            try{
                cont = command.substring(command.indexOf("says ")+"says ".length());
            }
            catch (Exception e)
            {
                Toast.makeText(MainActivity.getContext(),"invalid command", Toast.LENGTH_SHORT).show();
            }
            return cont;
        }
    }

}
