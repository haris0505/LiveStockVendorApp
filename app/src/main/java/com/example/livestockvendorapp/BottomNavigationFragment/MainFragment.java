package com.example.livestockvendorapp.BottomNavigationFragment;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.livestockvendorapp.Adapter.OrderTabAdapter;
import com.example.livestockvendorapp.R;
import com.google.android.material.tabs.TabLayout;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {


    TabLayout tablayout;
    ViewPager viewPager;


    public MainFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_main, container, false);
        tablayout=view.findViewById(R.id.order_tablayout);
        viewPager=view.findViewById(R.id.order_viewpager);


        setuptab();
        final OrderTabAdapter orderTabAdapter=new OrderTabAdapter(getActivity(),getChildFragmentManager(),tablayout.getTabCount());

        viewPager.setAdapter(orderTabAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tablayout));

        tablayout.addOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        return view;
    }

    private void setuptab() {
     tablayout.addTab(tablayout.newTab().setText("New Order"));
     tablayout.addTab(tablayout.newTab().setText("Deliver Order"));
     tablayout.addTab(tablayout.newTab().setText("Completed Order"));
     tablayout.setTabGravity(TabLayout.GRAVITY_FILL);
    }

}
