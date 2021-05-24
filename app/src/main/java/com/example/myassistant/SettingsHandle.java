package com.example.myassistant;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
/**
 * This class handles all the functions settings in the phone: bluetooth.
 * @author Noa Fatael
 */
public class SettingsHandle {
    public static final boolean WIFI_ON = true;
    public static final boolean WIFI_OFF = false;
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    /**
     * This function will open bluetooth
     * @param command
     * @return
     */
    public static void bluetoothOn(String command)
    {
        if(MainActivity.bAdapter == null)
        {
            Toast.makeText(MainActivity.getContext(),"Bluetooth Not Supported",Toast.LENGTH_SHORT).show();
        }
        else{
            if(!MainActivity.bAdapter.isEnabled()){
                MainActivity.getContext().startActivity(new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE));
                Toast.makeText(MainActivity.getContext(),"Bluetooth Turned ON",Toast.LENGTH_SHORT).show();
            }
        }
    }
    /**
     * This function will stop bluetooth
     * @param
     * @return
     */
    public static void bluetoothOff(String command)
    {
        MainActivity.bAdapter.disable();
    }
    /**
     * This function return if this command fits this class.
     * @param command
     * @return return if this command fits this class.
     */
    public static boolean isCammandBluetoothOn(String command){
        return (command.contains("bluetooth") && command.contains("on"));
    }
    /**
     * This function return if this command fits this class.
     * @param command
     * @return return if this command fits this class.
     */
    public static boolean isCammandBluetoothOff(String command){
        return (command.contains("bluetooth") && command.contains("off"));
    }
    public static void openSettings(String command)
    {
        MainActivity.getContext().startActivity(new Intent(Settings.ACTION_SETTINGS));
    }
}
