package com.example.livestockvendorapp.Adapter;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.livestockvendorapp.Tabfragments.CompletedOrderFragment;
import com.example.livestockvendorapp.Tabfragments.DeliveryOrderFragment;
import com.example.livestockvendorapp.Tabfragments.HomeFragment;

public class OrderTabAdapter extends FragmentPagerAdapter {

    private Context context;
    int totaltabs;

    public OrderTabAdapter(Context context, @NonNull FragmentManager fm, int totaltabs) {
        super(fm);
        this.context = context;
        this.totaltabs = totaltabs;
    }


    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                HomeFragment homeFragment = new HomeFragment();
                return homeFragment;
            case 1:
                DeliveryOrderFragment orderFragment = new DeliveryOrderFragment();
                return orderFragment;

            case 2:
                CompletedOrderFragment completedOrderFragment = new CompletedOrderFragment();
                return completedOrderFragment;
            default:
                return null;

        }
    }

    @Override
    public int getCount() {
        return totaltabs;
    }
}
