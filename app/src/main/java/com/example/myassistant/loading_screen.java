package com.example.myassistant;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
/**
 * This class shows the loading screen to the app.
 * @author Noa Fatael
 */
public class loading_screen extends AppCompatActivity {
    int SPLASH_TIME = 3000; //display for 3 seconds
/*
    On create function
    will open the main screen after 3 seconds.
 */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_screen);

        getSupportActionBar().hide();//hides the top part

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent i=new Intent(loading_screen.this,MainActivity.class);
                startActivity(i);
            }
        }, SPLASH_TIME);
    }
}