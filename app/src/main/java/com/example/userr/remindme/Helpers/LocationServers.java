package com.example.userr.remindme.Helpers;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.userr.remindme.Activities.RegisterActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

public class LocationServers {
    public static  void saveLocation_to_Server(String url , Map map , final Context context) {
        RequestQueue mRequestQueue = Volley.newRequestQueue(context);
        final Dialog loadingDialog = Dailogs.createLoadingBar(context);
        loadingDialog.show();
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(map), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String dbStatus = (String) response.get("DBStatus");
                    Toast.makeText(context, ""+dbStatus, Toast.LENGTH_SHORT).show();
                    loadingDialog.dismiss();

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loadingDialog.dismiss();
            }
        });
        mRequestQueue.add(request);

    }


    public static  void remove_Location_From_Server(String url , Map map , final Context context) {
        RequestQueue mRequestQueue = Volley.newRequestQueue(context);
        final Dialog loadingDialog = Dailogs.createLoadingBar(context);
        loadingDialog.show();
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(map), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String dbStatus = (String) response.get("DBStatus");
                    Toast.makeText(context, ""+dbStatus, Toast.LENGTH_SHORT).show();
                    loadingDialog.dismiss();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loadingDialog.dismiss();
            }
        });
        mRequestQueue.add(request);

    }










}
