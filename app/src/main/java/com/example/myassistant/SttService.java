package com.example.myassistant;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class SttService extends Service {
    public SttService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
