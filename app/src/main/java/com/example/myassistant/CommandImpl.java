package com.example.myassistant;

import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import java.util.HashMap;

/**
 * This class will call the commands via user wish.
 * @author Noa Fatael
 */
public class CommandImpl {
    private HashMap<HandleCheckFunction, CommandHandler> myMap;//this hash map will contain all the command checkers and handles.
    /**
     * This C'tor will init the hash map that contains all the command checkers and handles.
     * @param
     * @return
     */
    CommandImpl()
    {
        HandleCheckFunction sayCheck = command -> command.contains("say");
        HandleCheckFunction isOpenSettings = command -> command.contains("open") && command.contains("setting");


        myMap = new HashMap<>();

        myMap.put(BatteryHandle::isCammand, BatteryHandle::handleCommand);
        myMap.put(CameraHandle::isCammand, CameraHandle::openCamera);
        myMap.put(GoogleHandle::isCammandSearchGoogleQuery, GoogleHandle::searchGoogleQuery);
        myMap.put(GoogleHandle::isCammandPlayFromYoutube, GoogleHandle::playFromYoutube);
        myMap.put(GoogleHandle::isCammandSendEmail, GoogleHandle::sendEmail);
        myMap.put(CommunicationHandle::isCammandCall, CommunicationHandle::callContact);
        myMap.put(CommunicationHandle::isCammandSendSms, CommunicationHandle::sendSMS);
        myMap.put(CommunicationHandle::isCommandSendWhatsapp, CommunicationHandle::sendWhatsapp);
        myMap.put(SettingsHandle::isCammandBluetoothOn, SettingsHandle::bluetoothOn);
        myMap.put(SettingsHandle::isCammandBluetoothOff, SettingsHandle::bluetoothOff);
        myMap.put(FlashlightHandle::isCammand, FlashlightHandle::switchFlashLight);
        myMap.put(MusicService::isCammandPlay, MusicService::startMusicSer);
        myMap.put(MusicService::isCammandStopPlay, MusicService::stopMusicSer);
        myMap.put(TimeHandle::isCammandGetTime, TimeHandle::sayTime);
        myMap.put(TimeHandle::isCammandGetDay, TimeHandle::sayDay);
        myMap.put(TimeHandle::isCammandGetDate, TimeHandle::sayDate);
        myMap.put(TimeHandle::isCammandGetYear, TimeHandle::sayYear);
        myMap.put(isOpenSettings, SettingsHandle::openSettings);

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

    }
}
