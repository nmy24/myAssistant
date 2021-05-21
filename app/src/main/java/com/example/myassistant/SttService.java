package com.example.myassistant;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.util.Log;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.Locale;

/**
 * this the speech to text service.
 * @author Noa Fatael
 */
public class SttService extends Service {

    public static SpeechRecognizer speechRecognizer;
    static Intent speechRecognizerIntent = null;
    private CommandImpl commandHandle;
    private static boolean isOn;

    /**
     * This function will create the service.
     * @param
     * @return
     */
    @Override
    public void onCreate() {
        super.onCreate();
        isOn = true;
        speechRecognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        speechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        speechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(MainActivity.getContext());
        speechRecognizer.startListening(speechRecognizerIntent);
        commandHandle = new CommandImpl();
    }
    /**
     * This function will start the service.
     * @param intent, flag, startId
     * @return integer
     */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        speechRecognizer.setRecognitionListener(new RecognitionListener() {
            @Override
            public void onReadyForSpeech(Bundle bundle) {}
            @Override
            public void onBeginningOfSpeech() {}
            @Override
            public void onRmsChanged(float v) {}
            @Override
            public void onBufferReceived(byte[] bytes) {}
            @Override
            public void onEndOfSpeech() {}
            @Override
            public void onError(int i) {
                Log.d("aaaaErrorFunction","error number is "+i); //https://developer.android.com/reference/android/speech/SpeechRecognizer for getting wats wrong
                Intent intent = new Intent(getApplicationContext(),SttService.class);
                stopService(intent);
                startService(intent);
            }
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onResults(Bundle bundle) {

                if(CommunicationHandle.isInCall) {
                    isOn = false;
                    stopSelf();
                }

                if(isOn)
                {
                    ArrayList<String> data = bundle.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
                    Log.d("aaaaInputIn", data.get(0)); //prints what the user says

                    SharedPreferences pref = getApplicationContext().getSharedPreferences(MainActivity.FILENAME, 0); // 0 - for private mode
                    String name = pref.getString("assistantName", ""); // getting String
                    String comm = data.get(0).toString();
                    comm = comm.toLowerCase();
                    name = name.toLowerCase();

                    MainActivity.changeOutput(comm);
                    if(comm.contains("hey " + name))
                    {
                        comm.substring(0, ("hey " + name).length());
                        commandHandle.getCommandDone(data.get(0).toString());

                    }
                    speechRecognizer.startListening(speechRecognizerIntent);
                }

            }
            @Override
            public void onPartialResults(Bundle partialResults) {}
            @Override
            public void onEvent(int eventType, Bundle params) {}
        });
        return START_STICKY;
    }
    /**
     * This function will destroy the sevice.
     * @param
     * @return
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public static void sttOn()
    {
        isOn = true;
    }
    public static void sttOff()
    {
        isOn = false;
    }
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

}