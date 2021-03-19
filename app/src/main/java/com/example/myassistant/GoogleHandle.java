package com.example.myassistant;

import android.app.SearchManager;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

/**
 * this class handles all the functions related the use of google.
 * @author Noa Fatael
 */
public class GoogleHandle {
    /**
     * This function will search a Google Query.
     * @param
     * @return
     */
    public static void searchGoogleQuery(String query)
    {
        query = query.substring(("search in google ").length(), query.length());
        MainActivity.tts.talk("searching");
        Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);
        intent.putExtra(SearchManager.QUERY, query); // query contains search string
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        MainActivity.getContext().startActivity(intent);
    }
    /**
     * This function will play a song from youtube.
     * @param
     * @return
     */
    public static void playFromYoutube(String query)
    {
        query = query.substring(("search in youtube ").length(), query.length());
        MainActivity.tts.talk("searching");
        Intent intent = new Intent(Intent.ACTION_VIEW , Uri.parse("https://www.youtube.com/search?q="+query));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        MainActivity.getContext().startActivity(intent);
    }
    /**
     * This function will send a mail.
     * @param
     * @return
     */
    public static void sendEmail(String command)
    {
        String[] to = {""};
        String[] cc = {""};
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, to);
        emailIntent.putExtra(Intent.EXTRA_CC, cc);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "");//the subject
        emailIntent.putExtra(Intent.EXTRA_TEXT, "");//the message
        try {
            MainActivity.getContext().startActivity(Intent.createChooser(emailIntent, "Send mail..."));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(MainActivity.getContext(), "There is no email client installed.", Toast.LENGTH_SHORT).show();
        }
    }
    /**
     * This function return if this command fits this class.
     * @param command
     * @return return if this command fits this class.
     */
    public static boolean isCammandSearchGoogleQuery(String command){
        return (command.contains("search") && command.contains("google"));
    }
    /**
     * This function return if this command fits this class.
     * @param command
     * @return return if this command fits this class.
     */
    public static boolean isCammandPlayFromYoutube(String command){
        return (command.contains("play") && command.contains("youtube"));
    }
    /**
     * This function return if this command fits this class.
     * @param command
     * @return return if this command fits this class.
     */
    public static boolean isCammandSendEmail(String command){
        return (command.contains("send") && command.contains("email"));
    }
}
