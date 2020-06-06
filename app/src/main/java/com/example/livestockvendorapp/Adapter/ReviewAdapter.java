package com.example.livestockvendorapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.livestockvendorapp.Interface.ItemClickListener;
import com.example.livestockvendorapp.Model.Orderlist;
import com.example.livestockvendorapp.R;

import java.util.List;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewViewholder> {

    List<Orderlist> list;
    Context cx;

    public ReviewAdapter(List<Orderlist> list, Context cx) {
        this.list = list;
        this.cx = cx;
    }

    @NonNull
    @Override
    public ReviewViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.review_row, parent, false);
        return new ReviewViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewViewholder holder, int position) {
        holder.username.setText(list.get(position).getPhone());
        holder.date.setText(list.get(position).getDate());
        holder.comment.setText(list.get(position).getReview());
        holder.ratingbar.setRating(list.get(position).getRating());

        holder.SetItemClickListner(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ReviewViewholder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView username, date, comment;
        RatingBar ratingbar;

        ItemClickListener itemClickListener;

        public ReviewViewholder(@NonNull View itemView) {
            super(itemView);
            username = itemView.findViewById(R.id.review_id);
            date = itemView.findViewById(R.id.review_date);
            comment = itemView.findViewById(R.id.review_comment);
            itemView.setOnClickListener(this);
            ratingbar = itemView.findViewById(R.id.review_rating);
            ratingbar.setEnabled(false);


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
