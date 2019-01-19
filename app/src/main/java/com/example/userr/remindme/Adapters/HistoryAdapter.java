package com.example.userr.remindme.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.userr.remindme.Helpers.LocationResponse;
import com.example.userr.remindme.R;

import java.util.ArrayList;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryHolder>  {
    private Context mContext;
    private ArrayList<LocationResponse> mlocationItems;
    public HistoryAdapter(Context mContext, ArrayList<LocationResponse> mlocationItems) {
        this.mContext = mContext;
        this.mlocationItems = mlocationItems;
    }

    @NonNull
    @Override
    public HistoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.history_items,parent,false);
        return new HistoryHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryHolder historyHolder, int position) {
        LocationResponse locationItems = mlocationItems.get(position);
        historyHolder.dateTV.setText(locationItems.getDate());
        historyHolder.timeTV.setText(locationItems.getTime());
        historyHolder.addressTV.setText(locationItems.getAddress());
    }

    @Override
    public int getItemCount() {
        return mlocationItems.size();
    }


    public class HistoryHolder extends RecyclerView.ViewHolder
    {
        TextView dateTV,timeTV,addressTV;
        public HistoryHolder(@NonNull View itemView) {
            super(itemView);
            dateTV = itemView.findViewById(R.id.date_TV);
            timeTV = itemView.findViewById(R.id.time_TV);
            addressTV = itemView.findViewById(R.id.address_TV);
        }
    }
}
