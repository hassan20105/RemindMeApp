package com.example.userr.remindme.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class NewHistoryFragment extends Fragment{

    private RecyclerView mRecyclerView;
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
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for(int i=0;i<=jsonArray.length();i++)
                    {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String date = jsonObject.getString("date");
                        String[] address = jsonObject.getString("address").split(Pattern.quote(","));
                        String time = jsonObject.getString("time");
                        mLocationList.add(new LocationResponse(date,address[0]+"\n"+address[1]+"\n"+address[2],time));
                        adapter = new HistoryAdapter(getContext(), mLocationList);
                        mRecyclerView.setAdapter(adapter);

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
