package com.example.userr.remindme.services;

import android.app.Service;
import android.os.Handler;
import android.os.IBinder;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class FirstService extends Service {

    /** indicates how to behave if the service is killed */
    int mStartMode;

    /** interface for clients that bind */
    IBinder mBinder;

    /** indicates whether onRebind should be used */
    boolean mAllowRebind;
    int t = 0;
    Handler handler ;
    /** Called when the service is being created. */
    @Override
    public void onCreate() {

        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                t++;
                if(t<1000) {
                    handler.postDelayed(this, 1000);
                    Log.e("Tag",""+t);
                }
            }
        }, 1000);
    }

    /** The service is starting, due to a call to startService() */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return mStartMode;
    }

    /** A client is binding to the service with bindService() */
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    /** Called when all clients have unbound with unbindService() */
    @Override
    public boolean onUnbind(Intent intent) {
        return mAllowRebind;
    }

    /** Called when a client is binding to the service with bindService()*/
    @Override
    public void onRebind(Intent intent) {

    }

    /** Called when The service is no longer used and is being destroyed */
    @Override
    public void onDestroy() {

    }
}
