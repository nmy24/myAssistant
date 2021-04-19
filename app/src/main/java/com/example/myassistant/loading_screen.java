package com.example.myassistant;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class loading_screen extends AppCompatActivity {
    int SPLASH_TIME = 3000; //This is 3 seconds

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_screen);

        getSupportActionBar().hide();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent i=new Intent(loading_screen.this,MainActivity.class);
                startActivity(i);
            }
        }, 5000);
    }

}