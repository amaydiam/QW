package com.qwash.washer.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.qwash.washer.R;
import com.qwash.washer.api.model.InProgressResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by binderbyte on 06/01/17.
 */

public class InProgressAdapter extends RecyclerView.Adapter<InProgressAdapter.MyViewHolder>{

    private String[] date = {"Today", "Monday", "Today", "Monday", "Saturday", "Today"};
    private String[] time = {"12:30 WIB", "12:30 WIB", "12:30 WIB", "12:30 WIB", "12:30 WIB", "12:30 WIB"};
    private String[] merk = {"Honda", "Yamaha", "Sedan", "Honda", "Jazz", "Gobri"};
    private Context context;

    public InProgressAdapter(Context context) {
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_in_progress, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.textView1.setText(date[position]);
        holder.textView2.setText(time[position]);
        holder.textView3.setText(merk[position]);
    }

    @Override
    public int getItemCount() {
        return date.length;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView textView1, textView2, textView3;

        public MyViewHolder(View itemView) {
            super(itemView);

            textView1 = (TextView) itemView.findViewById(R.id.date_in_progress);
            textView2 = (TextView) itemView.findViewById(R.id.time_in_progress);
            textView3 = (TextView) itemView.findViewById(R.id.merk_in_progress);

        }
    }
}
