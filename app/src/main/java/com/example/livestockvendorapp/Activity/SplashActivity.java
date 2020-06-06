package com.example.livestockvendorapp.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.WindowManager;

import com.example.livestockvendorapp.Model.Common;
import com.example.livestockvendorapp.R;

import io.paperdb.Paper;

public class SplashActivity extends AppCompatActivity {


    private static int SPLASH_TIME_OUT = 2000;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Paper.init(this);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //This method is used so that your splash activity
        //can cover the entire screen.
        setContentView(R.layout.activity_splash);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {


               String value = Paper.book().read(Common.checklogin);
                if (TextUtils.isEmpty(value)) {
                    intent = new Intent(SplashActivity.this,
                            LoginActivity.class);
                    //Intent is used to switch from one activity to another.


                } else {

                    Common.currentuser=Paper.book().read(Common.login);
                    intent=new Intent(SplashActivity.this,MainActivity.class);
                }

                startActivity(intent);
                //invoke the SecondActivity.

                finish();
                //the current activity will get finished.
            }
        }, SPLASH_TIME_OUT);
    }
}
