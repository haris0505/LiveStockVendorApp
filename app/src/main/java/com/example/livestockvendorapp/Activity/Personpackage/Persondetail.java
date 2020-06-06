package com.example.livestockvendorapp.Activity.Personpackage;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.livestockvendorapp.Activity.MapsActivity;
import com.example.livestockvendorapp.Model.Common;
import com.example.livestockvendorapp.R;
import com.google.firebase.firestore.FirebaseFirestore;

public class Persondetail extends AppCompatActivity {

    FirebaseFirestore db;
    TextView username, usernumber, userrating, userearning;
    TextView userordercount, userlocation;
    ImageButton btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personadetail);


        username = findViewById(R.id.user_name);
        usernumber = findViewById(R.id.user_number);
        userlocation = findViewById(R.id.user_location);
        userearning = findViewById(R.id.user_earning);
        userordercount = findViewById(R.id.user_ordercount);
        userrating = findViewById(R.id.user_rating);

        btn = findViewById(R.id.location_btn);
        db = FirebaseFirestore.getInstance();


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),MapsActivity.class);
                startActivityForResult(intent,100);
            }
        });

        set_Data();
    }


    public void set_Data() {

        usernumber.setText(Common.currentuser.getPhone());
        userlocation.setText(Common.currentuser.getLocation());
        username.setText(Common.currentuser.getName());

        userrating.setText(Float.toString(Common.totalrating));
        userearning.setText(Float.toString(Common.totalcost));
        userordercount.setText(Integer.toString(Common.totalcount));


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==100){
            if(resultCode==RESULT_OK){

            }else if(resultCode==RESULT_CANCELED){

            }
        }
    }
}
