package com.example.myassistant;

import android.os.Build;

import androidx.annotation.RequiresApi;

/**
 * this class call commandes via user wish.
 * @author Noa Fatael
 */
public class CommandImpl {

    /**
     * This function will call the wanted command
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

    }
}
