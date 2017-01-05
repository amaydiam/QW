package com.ad.sample.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ad.sample.R;

/**
 * Created by binderbyte on 24/12/16.
 */

public class RecyclerAdapter5 extends RecyclerView.Adapter<RecyclerViewHolder> {

    String [] title = {"Promo 2015","Promo 2017","Promo 2018"};
    String [] deskripsi = {"Berlaku pada tgl 12 januari 2015","Berlaku pada tgl 12 januari 2017","Berlaku pada tgl 12 januari 2018"};

    Context context;
    LayoutInflater inflater;
    View view;
    public RecyclerAdapter5(Context context) {
        this.context=context;
        inflater=LayoutInflater.from(context);
    }
    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        view = inflater.inflate(R.layout.item_notification, parent, false);

        RecyclerViewHolder viewHolder = new RecyclerViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, final int position) {

        int j = 1;
        for(int i = 0; i < title.length; i++){

            i = i + j;
            if (position == i){
                holder.cardView5.setCardBackgroundColor(Color.parseColor("#E3F2FD"));
            }

        }

        holder.title5.setText(title[position]);
        holder.deskripsi1.setText(deskripsi[position]);
        holder.cardView5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Click "+position, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return title.length;
    }
}
