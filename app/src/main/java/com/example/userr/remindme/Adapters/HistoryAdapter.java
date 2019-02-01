package com.example.userr.remindme.Adapters;

import android.content.Context;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.userr.remindme.Helpers.LocationResponse;
import com.example.userr.remindme.Helpers.LocationServers;
import com.example.userr.remindme.Helpers.URLS;
import com.example.userr.remindme.R;
import com.example.userr.remindme.models.LocationItem;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryHolder>  {
    String id ="";
    private Context mContext;
    private ArrayList<LocationItem> mlocationItems;
    private SwipeRefreshLayout swiper;
    public HistoryAdapter(Context mContext, ArrayList<LocationItem> mlocationItems, SwipeRefreshLayout swiper) {
        this.mContext = mContext;
        this.mlocationItems = mlocationItems;
        this.swiper = swiper;
    }
    @NonNull
    @Override
    public HistoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.history_items,parent,false);
        return new HistoryHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryHolder historyHolder, final int position) {
        final LocationItem locationItems = mlocationItems.get(position);
        historyHolder.dateTV.setText(locationItems.getDate());
        historyHolder.timeTV.setText(locationItems.getTime());
        historyHolder.addressTV.setText(locationItems.getAddress());
        historyHolder.remove_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringRequest stringRequest = new StringRequest(Request.Method.GET, URLS.GET_LOCATION_URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            JSONObject jsonObject = jsonArray.getJSONObject(position);
                            id = jsonObject.getString("id");
                            Map<String,String> map = new HashMap<String, String>();
                            map.put("id",id);
                            LocationServers.remove_Location_From_Server(URLS.REMOVE_LOCATION_URL,map,mContext);
                            mlocationItems.remove(position);
                            notifyDataSetChanged();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
                RequestQueue requestQueue = Volley.newRequestQueue(mContext);
                requestQueue.add(stringRequest);

            }
        });
        swiper.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
               // refresh();
            }
        });
    }
    public void refresh()
    {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                HistoryAdapter.this.notifyDataSetChanged();
                swiper.setRefreshing(false);
            }
        },3000);
    }
    @Override
    public int getItemCount() {
        return mlocationItems.size();
    }

    public class HistoryHolder extends RecyclerView.ViewHolder {
        TextView dateTV,timeTV,addressTV;
        ImageView remove_img;
        public HistoryHolder(@NonNull View itemView) {
            super(itemView);
            dateTV = itemView.findViewById(R.id.date_TV);
            timeTV = itemView.findViewById(R.id.time_TV);
            addressTV = itemView.findViewById(R.id.address_TV);
            remove_img = itemView.findViewById(R.id.remove_img);
        }
    }
}
