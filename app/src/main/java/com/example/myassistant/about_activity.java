package com.example.myassistant;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
/**
 * This class shows the info about the app screen.
 * @author Noa Fatael
 */
public class about_activity extends AppCompatActivity implements View.OnClickListener {
    /**
     * This is onCreate function.
     *
     * @param
     * @return
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_activity);

        getSupportActionBar().hide();

    }
    /**
     * This is onClick function.
     * Will open the main screen after tapping on the screen.
     * @param v- the view
     * @return
     */
    @Override
    public void onClick(View v) {
        startActivity(new Intent(this, MainActivity.class));
    }
}