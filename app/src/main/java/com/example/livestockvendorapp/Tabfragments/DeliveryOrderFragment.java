package com.example.livestockvendorapp.Tabfragments;


import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.livestockvendorapp.Adapter.OrderAdapter;
import com.example.livestockvendorapp.Model.Common;
import com.example.livestockvendorapp.Model.Orderlist;
import com.example.livestockvendorapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import dmax.dialog.SpotsDialog;

/**
 * A simple {@link Fragment} subclass.
 */
public class DeliveryOrderFragment extends Fragment {

    RecyclerView recyclerView;


    OrderAdapter adapter1;

    FirebaseFirestore db;
    CollectionReference reference;
    ArrayList<Orderlist> list = new ArrayList<>();
    AlertDialog dialog;

    public DeliveryOrderFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dialog = new SpotsDialog.Builder().setContext(getContext()).setCancelable(true).build();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_delivery_order, container, false);
        recyclerView = view.findViewById(R.id.deliverorder_list);
        // database = FirebaseDatabase.getInstance();
        //ref = database.getReference().child("Orderlist");

        db = FirebaseFirestore.getInstance();
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

//
        readdata();


        db.collection("Orderlist").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {

                readdata();

            }
        });
        return view;
    }

    public void readdata() {
        dialog.show();
        db.collection("Orderlist").whereEqualTo("status", "Delivered").whereEqualTo("sellerphone", Common.currentuser.getPhone())
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
//                                if (order.getStatus().equalsIgnoreCase("Deliver")) {
//                                    list.add(order);
//                                }

                            }
                            adapter1 = new OrderAdapter(getActivity(), list, 1);
                            recyclerView.setAdapter(adapter1);

                        } else {
                            Log.w("Tag", "Error getting documents.", task.getException());
                        }
                    }
                });
        dialog.dismiss();
    }
}
