package com.example.livestockvendorapp.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;


import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.example.livestockvendorapp.BottomNavigationFragment.MainFragment;
import com.example.livestockvendorapp.BottomNavigationFragment.OrderFragment;
import com.example.livestockvendorapp.BottomNavigationFragment.PersonFragment;
import com.example.livestockvendorapp.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    FloatingActionButton upload_btn;
    BottomNavigationView bottomNavigationView;
    FrameLayout frameLayout;
    ActionBar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = getSupportActionBar();
        upload_btn = findViewById(R.id.upload_fb);
        frameLayout = findViewById(R.id.frame_container);
        bottomNavigationView = findViewById(R.id.navigation);


        toolbar.setTitle("Home");
        loadfragment(new MainFragment());

        upload_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), UploadActivity.class);
                startActivity(intent);
            }
        });

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                Fragment fragment;
                switch (menuItem.getItemId()) {

                    case R.id.home:
                        toolbar.setTitle("Home");
                        fragment = new MainFragment();
                        loadfragment(fragment);
                        return true;


                    case R.id.order_history:
                        toolbar.setTitle("Orderlist History");
                        fragment = new OrderFragment();
                        loadfragment(fragment);
                        return true;

                    case R.id.Person:
                        toolbar.setTitle("Contact");
                        fragment = new PersonFragment();
                        loadfragment(fragment);
                        return true;
                }

                return false;
            }
        });


    }

    private void loadfragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        //transaction.addToBackStack(null);
        transaction.commit();

    }


}
