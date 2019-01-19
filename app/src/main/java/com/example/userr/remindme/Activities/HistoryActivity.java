package com.example.userr.remindme.Activities;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.example.userr.remindme.Adapters.HistoryActivityViewPagerAdapter;
import com.example.userr.remindme.R;
import butterknife.BindView;
import butterknife.ButterKnife;

public class HistoryActivity extends AppCompatActivity {
    @BindView(R.id.activity_history_viewPager)
    ViewPager historyViewPager;
    @BindView(R.id.activity_history_tabLayout)
    TabLayout historyTablayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        ButterKnife.bind(this);
        HistoryActivityViewPagerAdapter adapter = new HistoryActivityViewPagerAdapter(getSupportFragmentManager());

        historyViewPager.setAdapter(adapter);
        historyTablayout.setupWithViewPager(historyViewPager);
    }
}
