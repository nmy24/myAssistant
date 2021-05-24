package com.example.myassistant;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.Random;

/**
 * This is the music service.
 * @author Noa Fatael
 */
public class MusicService extends Service {
    MediaPlayer player;
    Random rand = new Random();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    /**
     * This function creates the music service.
     * @param
     * @return
     */
    public void onCreate() {
        super.onCreate();
        player = MediaPlayer.create(this, MainActivity.songs.get(rand.nextInt(MainActivity.songs.size())));
        player.setLooping(false); // Set not looping
        player.setVolume(100, 100);
    }
    @Override
    /**
     * This function start the service.
     * @param intent, flags, startId
     * @return
     */
    public int onStartCommand(Intent intent, int flags, int startId) {
        player.start();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        player.stop();
    }

    /**
     * This function return if this command fits this class.
     * @param command
     * @return return if this command fits this class.
     */
    public static boolean isCammandPlay(String command){
        return (command.contains("music") && command.contains("play"));
    }
    /**
     * This function return if this command fits this class.
     * @param command
     * @return return if this command fits this class.
     */
    public static boolean isCammandStopPlay(String command){
        return command.contains("music") && command.contains("stop");
    }
    /**
     * This function will start the music sevice.
     * @param command
     * @return
     */
    public static void startMusicSer(String command)
    {
        //start sount track in background:
        MainActivity.musicIntent = new Intent(MainActivity.getContext(),MusicService.class);
        MainActivity.getContext().startService(MainActivity.musicIntent);
    }
    /**
     * This function will stop the music sevice.
     * @param command
     * @return
     */
    public static void stopMusicSer(String command)
    {
        MainActivity.getContext().stopService(MainActivity.musicIntent);
    }

}
