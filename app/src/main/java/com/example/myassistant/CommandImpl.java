package com.example.myassistant;

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
    }
}
