package com.example.myassistant;

import android.app.SearchManager;
import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;

public class GoogleHandle {
    public static void searchGoogleQuery(String query)
    {
        Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);
        intent.putExtra(SearchManager.QUERY, query); // query contains search string
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        MainActivity.getContext().startActivity(intent);
    }

    public static void playFromYoutube(String query)
    {
        Intent intent = new Intent(Intent.ACTION_VIEW , Uri.parse("https://www.youtube.com/search?q="+query));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        MainActivity.getContext().startActivity(intent);
    }

    public static void sendEmail()
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
}
