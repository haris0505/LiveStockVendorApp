package com.example.livestockvendorapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.livestockvendorapp.Interface.ItemClickListener;
import com.example.livestockvendorapp.Model.Orderlist;
import com.example.livestockvendorapp.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class HomeAdapter extends FirebaseRecyclerAdapter<Orderlist,HomeAdapter.OrderViewHolder> implements Filterable {


    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */

    Context context;
    HomeAdapter adapter;
    public HomeAdapter(@NonNull FirebaseRecyclerOptions<Orderlist> options, Context context) {
        super(options);
        this.context=context;
        adapter=this;


    }

    @Override
    protected void onBindViewHolder(@NonNull OrderViewHolder holder, int position, @NonNull Orderlist model) {

        holder.orderid.setText(adapter.getRef(position).getKey());
        holder.count.setText(model.getCount());
        holder.time.setText(model.getDate());


        holder.SetItemClickListner(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
                
            }
        });

    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.order_row,parent,false);
        return new OrderViewHolder(view);
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                return null;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {

            }
        };
    }

    class OrderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView orderid,count,time;
        ItemClickListener itemClickListener;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            orderid=itemView.findViewById(R.id.order_id);
            count=itemView.findViewById(R.id.order_count);
            time=itemView.findViewById(R.id.order_time);

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
