package com.example.livestockvendorapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.livestockvendorapp.Activity.OrderdetailActivity;
import com.example.livestockvendorapp.Interface.ItemClickListener;
import com.example.livestockvendorapp.Model.Animal;
import com.example.livestockvendorapp.Model.Orderlist;
import com.example.livestockvendorapp.R;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.List;

public class OrderdetailAdapter extends  RecyclerView.Adapter<OrderdetailAdapter.OrderViewHolder>{


    List<Animal> orderlists;

    Context cx;


    public OrderdetailAdapter(Context context, List<Animal> order) {
        cx=context;
        orderlists=order;



    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.orderdetail_row,parent,false);
        return new OrderdetailAdapter.OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        holder.time.setText(Double.toString(orderlists.get(position).getCost()));
        holder.orderid.setText(orderlists.get(position).getName());
        holder.count.setText(orderlists.get(position).getType());
        Picasso.get().load(orderlists.get(position).getImage()).into(holder.img);

        holder.SetItemClickListner(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {


            }
        });

    }

    @Override
    public int getItemCount() {
        return orderlists.size();
    }

    class OrderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView img;
        TextView orderid,count,time;
        ItemClickListener itemClickListener;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            img=itemView.findViewById(R.id.orderdetail_img);
            orderid=itemView.findViewById(R.id.orderdetail_id);
            count=itemView.findViewById(R.id.orderdetail_count);
            time=itemView.findViewById(R.id.orderdetail_time);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            itemClickListener.onClick(view,getAdapterPosition(),false);

        }

        public void SetItemClickListner(ItemClickListener itemClickListener){
            this.itemClickListener=itemClickListener;
        }
    }
}
