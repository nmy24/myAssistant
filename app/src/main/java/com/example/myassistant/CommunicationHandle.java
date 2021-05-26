package com.example.myassistant;

import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Toast;

/**
 * This class handles all the functions related to sending SMS && WhatsApp and calling.
 * @author Noa Fatael
 */
public class CommunicationHandle {
    public static boolean isInCall;
    private static SmsManager smsManager;
    private static String pNumber;
    /**
     * This function will init the class vars.
     *
     * @param
     * @return
     */
    public static void setCommunicationHandleVars() {
        smsManager = SmsManager.getDefault();
        pNumber = "";
    }
    /**
     * This function will send an SMS.
     *
     * @param command
     * @return
     */
    public static void sendSMS(String command) {
        setCommunicationHandleVars();
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
     * @param command
     * @return return if this command fits this command.
     */
    public static boolean isCammandSendSms(String command) {
        return (command.contains("send") && command.contains("sms"));
    }
    /**
     * This function will extract the phone number from the command if was given.
     *
     * @param str -> command
     * @return returns phone number from the command or ""
     */
    private static String extractNumber(String str) {
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
     * @param nameIn
     * @return
     */
    private static void getContactpNumber(String nameIn) {
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
     * @param command
     * @return who to send the sms.
     */
    private static String getContactNameFromCommand(String command)
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
     * @param command
     * @return the content to send.
     */
    private static String getMSGCont(String command) {
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
    /**
     * This function will call the contact asked.
     *
     * @param command
     * @return
     */
    public static void callContact(String command)
    {
        setCommunicationHandleVars();
        getContactpNumber(command.substring(("call ").length()));
        makePhoneCall();
    }
    /**
     * This function will call the contact via phone.
     *
     * @param
     * @return
     */
    private static void makePhoneCall()
    {
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:"+pNumber));//change the number
        MainActivity.getContext().startActivity(callIntent);
    }
    /**
     * This function return if this command fits this class.
     *
     * @param
     * @return return if this command fits this class.
     */
    public static boolean isCammandCall(String command) {
        return (command.contains("call"));
    }
    /**
     * This function will send WhatsApp msg
     *
     * @param command
     * @return
     */
    public static void sendWhatsapp(String command)
    {
        String name = getContactNameFromCommand(command);
        String cont = getMSGCont(command);
        getContactpNumber(name);
        if(isAppInstalled("com.whatsapp") && !name.equals("") && !cont.equals("") && !pNumber.equals("")){
            MainActivity.getContext().startActivity(
                    new Intent(Intent.ACTION_VIEW,
                            Uri.parse(
                                    String.format("https://api.whatsapp.com/send?phone=%s&text=%s", "+972" + pNumber, cont)// for only Israeli phone numbers.
                            )
                    )
            );
        }

    }
    /**
     * This function return if this command fits this class.
     *
     * @param
     * @return return if this command fits this class.
     */
    public static boolean isCommandSendWhatsapp(String command)
    {
        return command.contains("send") && command.contains("whatsapp");
    }
    /**
     * This function will check if a app is installed on the phone
     *
     * @param uri
     * @return
     */
    private static boolean isAppInstalled(String uri) {
        PackageManager pm = MainActivity.getContext().getPackageManager();
        boolean app_installed = false;
        try {
            pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
            app_installed = true;
        } catch (PackageManager.NameNotFoundException e) {
            app_installed = false;
        }
        return app_installed;
    }
}
