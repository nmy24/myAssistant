package com.example.myassistant;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.BreakIterator;
import java.util.ArrayList;

/**
 * This is the MainActivity class.
 * @author Noa Fatael
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener, NameDialog.NameDialogListener {
    private Button btnStart, btnStop;
    private static Context main_activity_context;
    private static final Integer RecordAudioRequestCode = 1;
    public static final String FILENAME = "MyPref";
    public static TTS_Manager tts;
    public static ArrayList<Integer> songs;
    public static Intent musicIntent;
    public final static BluetoothAdapter bAdapter = BluetoothAdapter.getDefaultAdapter();
    private static TextView tvOut;

    /**
     * This is onCreate function.
     *
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
        tvOut = (TextView) findViewById(R.id.tvOut);
        btnStart.setOnClickListener(this);
        btnStop.setOnClickListener(this);

        CommunicationHandle.isInCall = false;

        main_activity_context = this;

        initMusic();

        tts = new TTS_Manager();

        //ask for permission if it didn't already
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            checkPermission();
        } else if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            checkPermission();
        } else if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            checkPermission();
        } else if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            checkPermission();
        } else if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_NETWORK_STATE) != PackageManager.PERMISSION_GRANTED) {
            checkPermission();
        } else if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            checkPermission();
        } else if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            checkPermission();
        }
    }
    /**
     * This function init the music array list
     *
     * @param
     * @return
     */
    private void initMusic()
    {
        //init songs
        songs = new ArrayList<Integer>();
        songs.add(R.raw.a);
        songs.add(R.raw.b);
        songs.add(R.raw.c);
        songs.add(R.raw.d);
        songs.add(R.raw.e);
        songs.add(R.raw.f);
        songs.add(R.raw.g);
    }

    /**
     * This is onClick function.
     * btnStart => start to listen
     * btnStop => stop to listen
     *
     * @param
     * @return
     */
    @Override
    public void onClick(View v) {
        if (v == btnStart) {
            SttService.sttOn();
            Intent intent = new Intent(getApplicationContext(), SttService.class);
            startService(intent);
        } else if (v == btnStop) {
            SttService.sttOff();
            tts.talk("stop listening");
            Intent intent = new Intent(getApplicationContext(), SttService.class);
            stopService(intent);

        }
    }

    /**
     * This function will return the context.
     *
     * @param
     * @return context
     */
    public static Context getContext() {
        return main_activity_context;
    }

    /**
     * This function will check if permission granted.
     *
     * @param
     * @return
     */
    private void checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO, Manifest.permission.SEND_SMS, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_NETWORK_STATE, Manifest.permission.READ_CONTACTS, Manifest.permission.CALL_PHONE}, RecordAudioRequestCode);
        }
    }

    /**
     * This function will ask for permission.
     *
     * @param requestCode, permissions, grantResults
     * @return
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    /**
     * This will handle a situation when the user taps back and will ask if really wants to leave.
     *
     * @param
     * @return
     */
    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        MainActivity.super.onBackPressed();
                    }
                })

                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        AlertDialog ad = builder.create();
        ad.show();
    }
    /**
     * This will open the dialog on the top right screen.
     *
     * @param
     * @return
     */
    public void openDialog() {
        NameDialog exampleDialog = new NameDialog();
        exampleDialog.show(getSupportFragmentManager(), "example dialog");
    }
    /**
     * This will change the assistant name in the Shared Preferences
     *
     * @param
     * @return
     */
    @Override
    public void applyTexts(String name) {
        SharedPreferences pref = getApplicationContext().getSharedPreferences(FILENAME, 0); // 0 - for private mode
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("assistantName", name); // Storing string
        editor.commit(); // commit changes

    }
    /**
     * This will create the option menu on the top right screen
     *
     * @param
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;

    }
    /**
     * The function will handle which item that was selected
     *
     * @param item
     * @return true
     */
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();
        View v = this.getWindow().getDecorView();
        switch (id)
        {
            case R.id.about:
                //goto about screen
                startActivity(new Intent(this, about_activity.class));
                break;
            case R.id.assistant_name:
                openDialog();
                break;
        }
        return true;
    }
    /**
     * This will change the output log on the screen to what the user says.
     *
     * @param sayed - what the user sayed.
     * @return
     */
    public static void changeOutput(String sayed)
    {
        tvOut.setText(sayed);
    }
}