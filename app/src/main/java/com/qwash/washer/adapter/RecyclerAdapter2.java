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

public class RecyclerAdapter2 extends RecyclerView.Adapter<RecyclerViewHolder> {

    String [] model = {"Ayla", "Ceria", "Espass"};

    Context context;
    LayoutInflater inflater;
    View view;
    public RecyclerAdapter2(Context context) {
        this.context=context;
        inflater=LayoutInflater.from(context);
    }
    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        view = inflater.inflate(R.layout.item_select_model, parent, false);

        RecyclerViewHolder viewHolder = new RecyclerViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, final int position) {

        int j = 1;
        for(int i = 0; i < model.length; i++){

            i = i + j;
            if (position == i){
                holder.cardView2.setCardBackgroundColor(Color.parseColor("#E3F2FD"));
            }

        }

        holder.title2.setText(model[position]);
        holder.cardView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Click "+position, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return model.length;
    }
}
