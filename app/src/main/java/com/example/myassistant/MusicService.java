package com.example.myassistant;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

import androidx.annotation.Nullable;

import java.util.Random;

/**
 * this is the music service.
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
     * This function create the music service.
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
     * @param
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
}
