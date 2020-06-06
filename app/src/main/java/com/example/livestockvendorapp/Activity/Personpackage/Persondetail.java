package com.example.livestockvendorapp.Activity.Personpackage;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.livestockvendorapp.Model.Common;
import com.example.livestockvendorapp.R;
import com.google.firebase.firestore.FirebaseFirestore;

public class Persondetail extends AppCompatActivity {

    FirebaseFirestore db;
    TextView username,usernumber,userrating,userearning;
    TextView userordercount ,userlocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personadetail);


        username=findViewById(R.id.user_name);
        usernumber=findViewById(R.id.user_number);
        userlocation=findViewById(R.id.user_location);
        userearning=findViewById(R.id.user_earning);
        userordercount=findViewById(R.id.user_ordercount);
        userrating=findViewById(R.id.user_rating);


        db=FirebaseFirestore.getInstance();


        set_Data();
    }


    public void set_Data(){

        usernumber.setText(Common.currentuser.getPhone());
        userlocation.setText(Common.currentuser.getLocation());
        username.setText(Common.currentuser.getName());



    }

}
