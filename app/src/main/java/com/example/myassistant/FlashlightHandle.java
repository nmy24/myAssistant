package com.example.myassistant;

import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
/**
 * This class handles all the functions related to flashlight.
 * @author Noa Fatael
 */
public class FlashlightHandle {
    private static CameraManager mCameraManager;
    private static String mCameraId;
    /**
     * FlashlightHandle constructor
     */
    private static void setFlashlightHandleVars()
    {
        boolean isFlashAvailable = MainActivity.getContext().getApplicationContext().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
        if (!isFlashAvailable) {
            showNoFlashError();
        }
        mCameraManager = (CameraManager) MainActivity.getContext().getSystemService(Context.CAMERA_SERVICE);
        try {
            mCameraId = mCameraManager.getCameraIdList()[0];
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }

    }
    /**
     * This function will alert the user if there is no flashlight supported
     * @param
     * @return
     */
    private static void showNoFlashError() {
        AlertDialog alert = new AlertDialog.Builder(MainActivity.getContext())
                .create();
        alert.setTitle("Oops!");
        alert.setMessage("Flash not available in this device...");
        alert.show();
    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    /**
     * This function will switch the flashLight mode
     * @param status
     * @return
     */
    public static void switchFlashLight(String status) {
        boolean st = status.contains("on") || status.contains("open");
        setFlashlightHandleVars();
        try {
            mCameraManager.setTorchMode(mCameraId, st);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }
    /**
     * This function return if this command fits this class.
     * @param command
     * @return return if this command fits this class.
     */
    public static boolean isCammand(String command){
        return command.contains("flashlight");
    }

}