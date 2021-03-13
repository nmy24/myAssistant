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

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btnStart, btnStop;
    private static Context main_activity_context;
    private static final Integer RecordAudioRequestCode = 1;
    private static final String FILENAME = "MyPref";
    private static TTS_Manager tts;
    private static ArrayList<Integer> songs;
    private static Intent musicIntent;


    public static Context getContext() {
        return main_activity_context;
    }

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

        //store assistant name in Shared Preferences
        SharedPreferences pref = getApplicationContext().getSharedPreferences(FILENAME, 0); // 0 - for private mode
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("assistantName", "Siri"); // Storing string
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

    @Override
    public void onClick(View v) {
        if(v == btnStart)
        {
            //tts.talk("listening");
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
    //check if permission granted
    private void checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.RECORD_AUDIO, Manifest.permission.SEND_SMS, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_NETWORK_STATE},RecordAudioRequestCode);
        }
    }

    //ask for permission
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
