package com.example.myassistant;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * this is the MainActivity class.
 * @author Noa Fatael
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btnStart, btnStop;
    private static Context main_activity_context;
    private static final Integer RecordAudioRequestCode = 1;
    public static final String FILENAME = "MyPref";
    public static TTS_Manager tts;
    public static ArrayList<Integer> songs;
    public static Intent musicIntent;

    /**
     * This is onCreate function.
     * @param
     * @return
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //init buttons
        btnStart = (Button) findViewById(R.id.btnStart);
        btnStop = (Button) findViewById(R.id.btnStop);
        btnStart.setOnClickListener(this);
        btnStop.setOnClickListener(this);

        main_activity_context = this;

        //init songs
        songs = new ArrayList<Integer>();
        songs.add(R.raw.a);
        songs.add(R.raw.b);
        songs.add(R.raw.c);
        songs.add(R.raw.d);
        songs.add(R.raw.e);
        songs.add(R.raw.f);
        songs.add(R.raw.g);

        //store assistant name in Shared Preferences
        SharedPreferences pref = getApplicationContext().getSharedPreferences(FILENAME, 0); // 0 - for private mode
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("assistantName", "Maya"); // Storing string
        editor.commit(); // commit changes
        tts = new TTS_Manager();

        //ask for permission if it didn't already
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED){
            checkPermission();
        }
        else if(ContextCompat.checkSelfPermission(this,Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED){
            checkPermission();
        }
        else if(ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            checkPermission();
        }
        else if(ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            checkPermission();
        }
        else if(ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_NETWORK_STATE) != PackageManager.PERMISSION_GRANTED){
            checkPermission();
        }

    }
    /**
     * This is onClick function.
     * btnStart => start to listen
     * btnStop => stop to listen
     * @param
     * @return
     */
    @Override
    public void onClick(View v) {
        if(v == btnStart)
        {
            Intent intent = new Intent(getApplicationContext(),SttService.class);
            startService(intent);
        }
        else if(v == btnStop)
        {
            tts.talk("stop listening");
            Intent intent = new Intent(getApplicationContext(),SttService.class);
            stopService(intent);

        }
    }
    /**
     * This function will return the context.
     * @param
     * @return context
     */
    public static Context getContext() {
        return main_activity_context;
    }
    /**
     * This function will check if permission granted.
     * @param
     * @return
     */
    private void checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.RECORD_AUDIO, Manifest.permission.SEND_SMS, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_NETWORK_STATE},RecordAudioRequestCode);
        }
    }
    /**
     * This function will ask for permission.
     * @param requestCode, permissions, grantResults
     * @return
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == RecordAudioRequestCode && grantResults.length > 0 ){
            for(int i = 0; i < grantResults.length; i++)
            {
                if(grantResults[i] == PackageManager.PERMISSION_GRANTED)
                    Toast.makeText(this,"Permission Granted",Toast.LENGTH_SHORT).show();
            }
        }
    }
}
