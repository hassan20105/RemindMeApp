package com.example.userr.remindme.services;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;
import com.example.userr.remindme.Activities.MapActivity;
import com.example.userr.remindme.R;

public class AlarmService extends Service {
    @Override
    public void onCreate() {
       // Toast.makeText(this, "create", Toast.LENGTH_SHORT).show();
        Log.e("tag","test");

    }
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Intent notificationIntent = new Intent(getApplicationContext(),MapActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(getApplicationContext(), 0, notificationIntent, 0);
          NotificationManager notificationManager =
                (NotificationManager) getSystemService(Service.NOTIFICATION_SERVICE);
        Notification notification =
                new NotificationCompat.Builder(getBaseContext(),"notification_id")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Reminder Of Today")
                .setContentText(intent.getStringExtra("address"))
                .setDefaults(NotificationCompat.DEFAULT_SOUND).setContentIntent(contentIntent)
                .build();
        notificationManager.notify(0, notification);

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Toast.makeText(this, "Destroy", Toast.LENGTH_SHORT).show();
    }
}
