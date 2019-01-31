package com.example.userr.remindme.Fragments;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.userr.remindme.Adapters.HistoryAdapter;
import com.example.userr.remindme.Helpers.LocationResponse;
import com.example.userr.remindme.R;
import com.example.userr.remindme.models.LocationItem;
import com.example.userr.remindme.services.AlarmService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import static android.content.Context.ALARM_SERVICE;

public class NewHistoryFragment extends Fragment{

    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout swiper;
    private HistoryAdapter adapter;
    private ArrayList<LocationResponse> mLocationList;
    private RequestQueue requestQueue;
    public static NewHistoryFragment newInstance(){return new NewHistoryFragment(); }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_new_history,container,false);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView =  view.findViewById(R.id.rec);
        swiper = view.findViewById(R.id.swiper);
        LinearLayoutManager layout = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(layout);
        requestQueue = Volley.newRequestQueue(getContext());
          mLocationList = new ArrayList<>();
          getLocations();

    }

    private void getLocations() {
        String url = "https://hassancs.000webhostapp.com/getlocation.php";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                ArrayList<LocationItem> locationItems = new Gson().fromJson(response,new TypeToken<ArrayList<LocationItem>>(){}.getType());
                adapter = new HistoryAdapter(getContext(), locationItems,swiper);
                mRecyclerView.setAdapter(adapter);

                try {
                    Intent intent = new Intent(getActivity(),AlarmService.class);
                    AlarmManager alarmManager = (AlarmManager)getActivity(). getSystemService(ALARM_SERVICE);
                    JSONArray jsonArray = new JSONArray(response);
                    Calendar calendar = Calendar.getInstance();
                    for(int i=0;i<=jsonArray.length();i++)
                    {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String time = jsonObject.getString("time");
                        String date= jsonObject.getString("date");
                        String address=jsonObject.getString("address");
                        intent.putExtra("address",address);
                        PendingIntent pendingIntent  = PendingIntent.getService(getActivity(),0,intent,0);
                        String date_splitter[]= date.split("-");
                        String[]time_split = time.split(Pattern.quote(":"));
                      /* calendar.set(Calendar.YEAR, Integer.parseInt(date_splitter[0]));
                        calendar.set(Calendar.MONTH, Integer.parseInt(date_splitter[1]));
                        calendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(date_splitter[2]));*/
                        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(time_split[0]));
                        calendar.set(Calendar.MINUTE, Integer.parseInt(time_split[1]));
                     /*  int year = Integer.parseInt(date_splitter[0]);
                        int month = Integer.parseInt(date_splitter[1]);
                        int day = Integer.parseInt(date_splitter[2]);
                        int h = Integer.parseInt(time_split[0]);
                        int m = Integer.parseInt(time_split[1]);
                        calendar.set(year,month,day,h,m,0);*/
                        alarmManager.set(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),pendingIntent);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
    }


}
