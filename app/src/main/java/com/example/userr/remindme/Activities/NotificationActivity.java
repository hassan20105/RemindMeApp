package com.example.userr.remindme.Activities;

import android.app.Dialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.userr.remindme.Helpers.Dailogs;
import com.example.userr.remindme.Helpers.ValidationFields;
import com.example.userr.remindme.R;
import com.example.userr.remindme.services.FirstService;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NotificationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_notificaiton);
        startService(new Intent(this, FirstService.class));
    }


    public void showNotificaiton(View view){
        notificationSound();
    }
    public void notificationSound( ) {
        Intent intent = new Intent(this, RegisterActivity.class);

        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent contentIntent = PendingIntent.getActivity(this, 1315, intent,0);

        NotificationManager mNotificationManager;
        mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);




        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            NotificationChannel notificationChannel = new NotificationChannel("1231",
                    getString(R.string.app_name), NotificationManager.IMPORTANCE_DEFAULT);

            mNotificationManager.createNotificationChannel(notificationChannel);

        }

        NotificationCompat.Builder mBuilder = new  NotificationCompat.Builder(this,"12135")
                .setContentTitle("Content Title")
                .setContentText("Content Text")
                .setContentInfo("Content Info").setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setSubText("Sub Text").setSmallIcon(R.drawable.places_ic_clear)
                .setStyle(new NotificationCompat.BigTextStyle()
                        .setBigContentTitle("Big Content Title")
                        .bigText("Big Text").setBigContentTitle("Big Title").setSummaryText("Text Summary"))
                .setContentIntent(contentIntent).setAutoCancel(true);

        mNotificationManager.notify(150, mBuilder.build());
    }


}
