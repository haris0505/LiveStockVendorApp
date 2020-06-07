package com.example.livestockvendorapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.livestockvendorapp.Activity.CompleteStatusActivity;
import com.example.livestockvendorapp.Activity.DeliveryStatusActivity;
import com.example.livestockvendorapp.Activity.OrderdetailActivity;
import com.example.livestockvendorapp.Interface.ItemClickListener;
import com.example.livestockvendorapp.Model.Orderlist;
import com.example.livestockvendorapp.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {


    List<Orderlist> orderlists;
    List<String> list;
    Context cx;
    OrderAdapter adapter;
    int tab = 0;
    Intent intent;
    public OrderAdapter(Context context, List<Orderlist> order,int tab) {
        cx = context;
        orderlists = order;
        adapter = this;
        this.tab=tab;


    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_row, parent, false);
        return new OrderAdapter.OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        holder.time.setText(orderlists.get(position).getDate());
        holder.orderid.setText(orderlists.get(position).getDocid());
        holder.count.setText(orderlists.get(position).getCount());

        holder.SetItemClickListner(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {

                if(tab==0){
                    intent = new Intent(cx, OrderdetailActivity.class);
                }else if(tab==1){
                    intent = new Intent(cx, DeliveryStatusActivity.class);
                }else if(tab==2){
                    intent = new Intent(cx, CompleteStatusActivity.class);
                }

                //Intent intent = new Intent(cx, OrderdetailActivity.class);
                list = orderlists.get(position).getOrder();
                intent.putExtra("list", (Serializable) list);
                intent.putExtra("docid", orderlists.get(position).getDocid());
                cx.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return orderlists.size();
    }

    class OrderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView orderid, count, time;
        ItemClickListener itemClickListener;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            orderid = itemView.findViewById(R.id.order_id);
            count = itemView.findViewById(R.id.order_count);
            time = itemView.findViewById(R.id.order_time);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            itemClickListener.onClick(view, getAdapterPosition(), false);

        }

        public void SetItemClickListner(ItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
        }
    }
}
