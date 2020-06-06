package com.example.livestockvendorapp.BottomNavigationFragment;


import android.app.AlertDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.livestockvendorapp.Adapter.MyUploadAnimalAdapter;
import com.example.livestockvendorapp.Adapter.OrderAdapter;
import com.example.livestockvendorapp.Model.Animal;
import com.example.livestockvendorapp.Model.Common;
import com.example.livestockvendorapp.Model.Orderlist;
import com.example.livestockvendorapp.R;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
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
public class OrderFragment extends Fragment {


    RecyclerView recyclerView;
    MyUploadAnimalAdapter adapter;
    FirebaseFirestore db;
    ArrayList<Animal> list = new ArrayList();

    AlertDialog dialog;

    public OrderFragment() {
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
        View view = inflater.inflate(R.layout.fragment_order, container, false);
        recyclerView = view.findViewById(R.id.upload_list);
        db = FirebaseFirestore.getInstance();


        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        read_data();

        db.collection("Animal").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {

                read_data();
            }
        });

        return view;
    }


    public void read_data() {
        dialog.show();
        db.collection("Animal").whereEqualTo("Seller_id", Common.currentuser.getPhone()).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            list.clear();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("Tag", document.getId() + " => " + document.getData());
                                Animal order = document.toObject(Animal.class);
                                order.setDocid(document.getId());
                                list.add(order);
                            }
                            adapter = new MyUploadAnimalAdapter(list, getActivity());
                            recyclerView.setAdapter(adapter);
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });

        dialog.dismiss();
    }


}
