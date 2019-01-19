package com.example.userr.remindme.Activities;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import com.example.userr.remindme.Helpers.LocationServers;
import com.example.userr.remindme.Helpers.URLS;
import com.example.userr.remindme.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddLocationActivity extends AppCompatActivity implements View.OnClickListener {
    Calendar c;
    int year, month, day, hour, minute;
    @BindView(R.id.activity_add_location_locationET)
    EditText locationET;
    @BindView(R.id.activity_add_location_detailsET)
    EditText detailsET;
    @BindView(R.id.activity_add_location_dateET)
    EditText dateET;
    @BindView(R.id.activity_add_location_timeET)
    EditText timeET;
    @BindView(R.id.activity_add_location_saveBTN)
    Button saveBTN;
    public static int REQUEST_CODE_Location_ACTIVITY =123;
    String lat = "";
    String log ="";
    String address="";
    String date="";
    String time="";
    String details="";
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_location);
        ButterKnife.bind(this);
        locationET.setFocusable(false);
        dateET.setFocusable(false);
        timeET.setFocusable(false);
        callCalender();
        locationET.setOnClickListener(this);
        dateET.setOnClickListener(this);
        timeET.setOnClickListener(this);
        saveBTN.setOnClickListener(this);
        details = detailsET.getText().toString();
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    public void callCalender() {

        c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);
        hour = c.get(Calendar.HOUR_OF_DAY);
        minute = c.get(Calendar.MINUTE);
    }
    public void pick_date() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int y, int m, int d) {
                int month = datePicker.getMonth()+1;
                dateET.setText(y+"-"+month+"-"+d);

            }
        },year,month,day);
        datePickerDialog.setTitle("Select Date");
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis()-1000);
        datePickerDialog.show();
    }
        public void pick_time() {
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int h, int m) {
                timeET.setText(h+":"+m);
            }
        },hour,minute,true);
        timePickerDialog.setTitle("Select Time");
        timePickerDialog.show();

    }


    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.activity_add_location_locationET:
                Intent intent = new Intent(AddLocationActivity.this,MapActivity.class);
                startActivityForResult(intent, REQUEST_CODE_Location_ACTIVITY);
                break;
            case R.id.activity_add_location_dateET:
                pick_date();
                break;
            case R.id.activity_add_location_timeET:
                pick_time();
                break;
            case R.id.activity_add_location_saveBTN:
            {
                LocationServers.saveLocation_to_Server(URLS.ADD_LOCATION_URL,getMapData(),this);
                break;
            }
        }

    }

    public Map<String, String> getMapData() {
        callData();
        Map<String, String> postParam = new HashMap<String, String>();
        postParam.put("lat", lat);
        postParam.put("log", log);
        postParam.put("address", address);
        postParam.put("date", date);
        postParam.put("time", time);
        postParam.put("details", details);
        return postParam;
    }

    private void callData() {
        date = dateET.getText().toString();
        time = timeET.getText().toString();
        details = detailsET.getText().toString();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ArrayList list = data.getStringArrayListExtra("map_list");
        lat = list.get(0).toString();
        log = list.get(1).toString();
        address = list.get(2).toString();
        locationET.setText(lat +"\n"+log+"\n"+address);

}


}
