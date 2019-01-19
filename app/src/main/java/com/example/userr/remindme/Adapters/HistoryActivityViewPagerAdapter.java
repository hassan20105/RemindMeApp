package com.example.userr.remindme.Adapters;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.userr.remindme.Fragments.NewHistoryFragment;
import com.example.userr.remindme.Fragments.OldHistoryFragment;

public class HistoryActivityViewPagerAdapter extends FragmentStatePagerAdapter{
    public HistoryActivityViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment returnFragment;
        switch (position)
        {
            case 0:
                returnFragment = NewHistoryFragment.newInstance();
                break;
            case 1:
                returnFragment = OldHistoryFragment.newInstance();
                break;
                default:
                    return null;
        }
        return returnFragment;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        CharSequence title;
        switch (position)
        {
            case 0:
                title = "New History";
                break;
            case 1:
                title = "Old History";
                break;
            default:
                return null;
        }
        return title;





    }
}
