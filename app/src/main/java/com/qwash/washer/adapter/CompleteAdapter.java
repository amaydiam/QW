package com.qwash.washer.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.qwash.washer.R;

/**
 * Created by binderbyte on 06/01/17.
 */

public class CompleteAdapter extends RecyclerView.Adapter<CompleteAdapter.MyViewHolder> {

    private String[] date = {"Today", "Monday", "Today", "Monday", "Saturday", "Today"};
    private String[] time = {"12:30 WIB", "12:30 WIB", "12:30 WIB", "12:30 WIB", "12:30 WIB", "12:30 WIB"};
    private String[] merk = {"Honda", "Yamaha", "Sedan", "Honda", "Jazz", "Gobri"};
    private String[] status = {"COMPLETE", "COMPLETE", "COMPLETE", "COMPLETE", "COMPLETE", "COMPLETE"};
    private Context context;

    public CompleteAdapter(Context context) {
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_complete, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.textView1.setText(date[position]);
        holder.textView2.setText(time[position]);
        holder.textView3.setText(merk[position]);
        holder.textView4.setText(status[position]);
    }

    @Override
    public int getItemCount() {
        return date.length;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView textView1, textView2, textView3, textView4;

        public MyViewHolder(View itemView) {
            super(itemView);

            textView1 = (TextView) itemView.findViewById(R.id.date_in_progress);
            textView2 = (TextView) itemView.findViewById(R.id.time_in_progress);
            textView3 = (TextView) itemView.findViewById(R.id.merk_in_progress);
            textView4 = (TextView) itemView.findViewById(R.id.status);

        }
    }
}
