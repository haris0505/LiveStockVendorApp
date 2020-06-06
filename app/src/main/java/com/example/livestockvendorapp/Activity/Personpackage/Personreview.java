package com.example.livestockvendorapp.Activity.Personpackage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.livestockvendorapp.Model.Common;
import com.example.livestockvendorapp.Model.Orderlist;
import com.example.livestockvendorapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class Personreview extends AppCompatActivity {

    FirebaseFirestore db;
    TextView username, userrating, userearning;
    TextView userordercount;
    ArrayList<Orderlist> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personreview);
        username = findViewById(R.id.user_name);
        userearning = findViewById(R.id.user_earning);
        userordercount = findViewById(R.id.user_ordercount);
        userrating = findViewById(R.id.user_rating);


        db = FirebaseFirestore.getInstance();
        set_Data();
    }


    public void set_Data() {

        username.setText(Common.currentuser.getName());
        db.collection("Orderlist").whereEqualTo("status", "Completed").whereEqualTo("sellerphone", "+923219281126")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            list.clear();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("Tag", document.getId() + " => " + document.getData());
                                Orderlist order = document.toObject(Orderlist.class);
                                order.setDocid(document.getId());
                                list.add(order);
                            }


                        } else {
                            Log.w("Tag", "Error getting documents.", task.getException());
                        }
                    }
                });

    }
}
