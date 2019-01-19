package com.example.userr.remindme.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.userr.remindme.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MenuActivity extends AppCompatActivity implements View.OnClickListener{
    @BindView(R.id.about_menu_btn)
    Button aboutBTN;
    @BindView(R.id.contact_menu_btn)
    Button contactBTN;
    @BindView(R.id.location_menu_btn)
    Button locationBTN;
    @BindView(R.id.history_menu_btn)
    Button historyBTN;
    @BindView(R.id.logout_menu_btn)
    Button logoutBTN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        ButterKnife.bind(this);
        locationBTN.setOnClickListener(this);
        aboutBTN.setOnClickListener(this);
        historyBTN.setOnClickListener(this);
        logoutBTN.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.location_menu_btn:
            {
                startActivity(new Intent(this,AddLocationActivity.class));
                break;
            }
            case R.id.history_menu_btn:
            {
                startActivity(new Intent(this,HistoryActivity.class));
                break;
            }
            case R.id.about_menu_btn:
            {
                //startActivity(new Intent(this,AddLocationActivity.class));
                break;
            }
            case R.id.contact_menu_btn:
            {
                //startActivity(new Intent(this,AddLocationActivity.class));
                break;
            }





        }
    }
}
