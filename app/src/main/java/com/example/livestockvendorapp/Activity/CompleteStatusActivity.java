package com.example.livestockvendorapp.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.livestockvendorapp.Adapter.OrderdetailAdapter;
import com.example.livestockvendorapp.Model.Animal;
import com.example.livestockvendorapp.Model.Orderlist;
import com.example.livestockvendorapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class CompleteStatusActivity extends AppCompatActivity {


    TextView number, price, payment, date;
    FirebaseFirestore db;
    private String TAG = "tag";
    RecyclerView recyclerView;
    List<String> list;
    List<Animal> animalList;
    OrderdetailAdapter adapter;
    public double cost = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_status);

        recyclerView = findViewById(R.id.orderdetail_list);
        animalList = new ArrayList<>();

        number = findViewById(R.id.order_number);
        date = findViewById(R.id.order_date);
        payment = findViewById(R.id.order_paymentmethod);
        price = findViewById(R.id.order_cost);
        db = FirebaseFirestore.getInstance();
        animalList.clear();
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        Intent intent = getIntent();
        list = (List<String>) intent.getSerializableExtra("list");
        final String docid = intent.getStringExtra("docid");
        // Toast.makeText(getApplicationContext(), list.get(0), Toast.LENGTH_SHORT).show();
        get_order_details(docid);
        for (String i : list) {
            getdata(i.trim());
        }

    }


    public void getdata(String element) {

        db.collection("Animal").document(element).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                        Animal animal = document.toObject(Animal.class);
                        cost = cost + animal.getCost();
                        //Log.d(TAG, "cost: " + cost);
                        price.setText(Double.toString(cost));
                        animalList.add(animal);
                        adapter = new OrderdetailAdapter(getApplicationContext(), animalList);
                        recyclerView.setAdapter(adapter);
                    } else {
                        Log.d(TAG, "No such document");
                    }


                }
            }
        });


    }


    public void get_order_details(String docid) {
        db.collection("Orderlist").document(docid).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                        Orderlist order = document.toObject(Orderlist.class);
                        number.setText(order.getPhone());
                        date.setText(order.getDate());
                        payment.setText(order.getPayment());

                    } else {
                        Log.d(TAG, "No such document");
                    }


                }
            }
        });
    }
}
