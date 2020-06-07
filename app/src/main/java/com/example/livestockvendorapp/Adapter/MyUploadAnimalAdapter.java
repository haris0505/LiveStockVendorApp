package com.example.livestockvendorapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.livestockvendorapp.Interface.ItemClickListener;
import com.example.livestockvendorapp.Model.Animal;
import com.example.livestockvendorapp.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MyUploadAnimalAdapter extends RecyclerView.Adapter<MyUploadAnimalAdapter.AnimalViewHolder> {


    private List<Animal> list;
    private Context cx;

    public MyUploadAnimalAdapter(List<Animal> list, Context cx) {
        this.list = list;
        this.cx = cx;
    }

    @NonNull
    @Override
    public AnimalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.animal_row,parent,false);
        return  new AnimalViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull AnimalViewHolder holder, int position) {
        Picasso.get().load(list.get(position).getImage()).into(holder.imageView);
        holder.cost.setText("Rs:"+Double.toString(list.get(position).getCost()));
        holder.name.setText(list.get(position).getName());
        holder.weight.setText(list.get(position).getWeight());

        holder.SetItemClickListner(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
                Toast.makeText(cx,"",Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class AnimalViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView name,cost,weight;
        ImageView imageView;
        private ItemClickListener itemClickListener;


        public AnimalViewHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.animal_name);
            cost=itemView.findViewById(R.id.animal_cost);
            weight=itemView.findViewById(R.id.animal_weight);
            imageView=itemView.findViewById(R.id.animal_img);

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            itemClickListener.onClick(view,getAdapterPosition(),false);

        }

        private void SetItemClickListner(ItemClickListener itemClickListener){
            this.itemClickListener=itemClickListener;
        }

    }

}