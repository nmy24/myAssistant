package com.example.myassistant;

import android.content.Intent;
import android.os.Build;

import androidx.annotation.RequiresApi;

/**
 * this class call commandes via user wish.
 * @author Noa Fatael
 */
public class CommandImpl {
    /**
     * This function will call the wanted command.
     * @param command
     * @return
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static void getCommandDone(String command)
    {
        command = command.toLowerCase();
        if(BatteryHandle.isCammand(command))
        {
            float battery = BatteryHandle.getBatteryLevel();
            MainActivity.tts.talk("you have " + battery + " precents");
        }
        else if(CameraHandle.isCammand(command))
        {
            CameraHandle.openCamera();
        }
        else if(FlashlightHandle.isCammand(command))
        {
            FlashlightHandle fl= new FlashlightHandle();
            if(command.contains("open"))
            {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    fl.switchFlashLight(true);//turn on
                }
            }
            else if(command.contains("close"))
            {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    fl.switchFlashLight(false);//turn off
                }
            }
        }
        else if(GoogleHandle.isCammandSearchGoogleQuery(command))
        {
            MainActivity.tts.talk("searching");
            GoogleHandle.searchGoogleQuery(command.substring(("search in google ").length(), command.length()));
        }
        else if(GoogleHandle.isCammandPlayFromYoutube(command))
        {
            GoogleHandle.playFromYoutube(command.substring(("search in youtube ").length(), command.length()));
        }
        else if(GoogleHandle.isCammandSendEmail(command))
        {
            GoogleHandle.sendEmail();
        }
        else if(command.contains("music") && command.contains("play"))
        {
            //start sount track in background:
            MainActivity.musicIntent = new Intent(MainActivity.getContext(),MusicService.class);
            MainActivity.getContext().startService(MainActivity.musicIntent);

        }
        else if(command.contains("music") && command.contains("stop"))
        {
            MainActivity.getContext().stopService(MainActivity.musicIntent);
        }
        else if (CommunicationHandle.isCammandSendSms(command))
        {
            CommunicationHandle smsHandle = new CommunicationHandle();
            smsHandle.sendSMS(command);
        }
        else if(CommunicationHandle.isCammandCall(command))
        {
            CommunicationHandle ch = new CommunicationHandle();
            ch.callContact(command);
        }
        else if(SettingsHandle.isCammandBluetoothOn(command))
        {
            SettingsHandle.bluetoothOn();
        }
        else if(SettingsHandle.isCammandBluetoothOff(command))
        {
            SettingsHandle.bluetoothOff();
        }
        else if(SettingsHandle.isCammandWifiOff(command))
        {
            SettingsHandle.switchWiFi(SettingsHandle.WIFI_OFF);
        }
        else if(SettingsHandle.isCammandWifiOn(command))
        {
            SettingsHandle.switchWiFi(SettingsHandle.WIFI_ON);
        }
        else if(TimeHandle.isCammandGetTime(command))
        {
            MainActivity.tts.talk(TimeHandle.getTimeSent());
        }

    }
}
