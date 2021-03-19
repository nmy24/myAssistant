package com.example.myassistant;

import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import java.util.HashMap;

/**
 * this class call commandes via user wish.
 * @author Noa Fatael
 */
public class CommandImpl {
    private HashMap<HandleCheckFunction, CommandHandler> myMap;
    CommandImpl()
    {
        HandleCheckFunction sayCheck = command -> command.contains("say");
        CommandHandler sayCommandHandler = command -> MainActivity.tts.talk(command.substring("say ".length()));

        myMap = new HashMap<>();

        myMap.put(BatteryHandle::isCammand, BatteryHandle::handleCommand);
        myMap.put(CameraHandle::isCammand, CameraHandle::openCamera);
        myMap.put(GoogleHandle::isCammandSearchGoogleQuery, GoogleHandle::searchGoogleQuery);
        myMap.put(GoogleHandle::isCammandPlayFromYoutube, GoogleHandle::playFromYoutube);
        myMap.put(GoogleHandle::isCammandSendEmail, GoogleHandle::sendEmail);
        myMap.put(CommunicationHandle::isCammandCall, CommunicationHandle::callContact);
        myMap.put(CommunicationHandle::isCammandSendSms, CommunicationHandle::sendSMS);
        myMap.put(SettingsHandle::isCammandBluetoothOn, SettingsHandle::bluetoothOn);
        myMap.put(SettingsHandle::isCammandBluetoothOff, SettingsHandle::bluetoothOff);
        myMap.put(FlashlightHandle::isCammand, FlashlightHandle::switchFlashLight);
        myMap.put(MusicService::isCammandPlay, MusicService::startMusicSer);
        myMap.put(MusicService::isCammandStopPlay, MusicService::stopMusicSer);
        myMap.put(TimeHandle::isCammandGetTime, TimeHandle::sayTime);
        myMap.put(sayCheck, sayCommandHandler);

    }
    /**
     * This function will call the wanted command.
     * @param command
     * @return
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void getCommandDone(String command)
    {
        command = command.toLowerCase();

        for (HandleCheckFunction handleCheckFunction : myMap.keySet()) {
            if(handleCheckFunction.shouldHandleCommand(command)) {
                myMap.get(handleCheckFunction).handleCommand(command);
            }
        }
        /*
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
        }*/

    }
}
