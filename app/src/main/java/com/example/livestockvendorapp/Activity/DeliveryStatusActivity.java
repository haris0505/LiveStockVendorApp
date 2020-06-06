package com.example.livestockvendorapp.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.livestockvendorapp.Adapter.OrderdetailAdapter;
import com.example.livestockvendorapp.Model.Animal;
import com.example.livestockvendorapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DeliveryStatusActivity extends AppCompatActivity {

    FirebaseFirestore db;
    private String TAG = "tag";
    Button accept_btn;
    TextView cost_text;
    RecyclerView recyclerView;
    List<String> list;
    List<Animal> animalList;
    OrderdetailAdapter adapter;
    public  double cost=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_status);

        accept_btn = findViewById(R.id.completed_btn);
        cost_text = findViewById(R.id.cost_label);
        recyclerView = findViewById(R.id.orderdetail_list);
        animalList = new ArrayList<>();

        db = FirebaseFirestore.getInstance();
        animalList.clear();
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        Intent intent = getIntent();
        list = (List<String>) intent.getSerializableExtra("list");
        final String docid=intent.getStringExtra("docid");
        // Toast.makeText(getApplicationContext(), list.get(0), Toast.LENGTH_SHORT).show();

        for (String i : list) {
            getdata(i.trim());
        }

        // cost_text.setText(Double.toString(cost));
        accept_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Map<String,Object> map=new HashMap<>();
                map.put("status","Completed");
                db.collection("Orderlist").document(docid).update(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(DeliveryStatusActivity.this,"Update",Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });

            }
        });
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
                        cost= cost+Double.parseDouble(animal.getCost());
                        //Log.d(TAG, "cost: " + cost);
                        cost_text.setText( Double.toString(cost));
                        animalList.add(animal);
                        adapter=new OrderdetailAdapter(getApplicationContext(),animalList);
                        recyclerView.setAdapter(adapter);
                    } else {
                        Log.d(TAG, "No such document");
                    }


                }
            }
        });


    }
}
