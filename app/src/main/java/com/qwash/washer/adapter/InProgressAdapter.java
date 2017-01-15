package com.qwash.washer.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.qwash.washer.R;
import com.qwash.washer.api.model.InProgressResponse;
import com.qwash.washer.ui.activity.DetailHistoryActivity;
import com.qwash.washer.ui.activity.DetailInProgressActivity;

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
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), DetailInProgressActivity.class);
                intent.putExtra("date", "");
                intent.putExtra("time", "");
                intent.putExtra("totalharga", "");
                intent.putExtra("addselect1", "");
                intent.putExtra("addselect2", "");
                intent.putExtra("lokasi", "");
                intent.putExtra("merk", "");
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return date.length;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView textView1, textView2, textView3;
        private LinearLayout linearLayout;

        public MyViewHolder(View itemView) {
            super(itemView);

            textView1 = (TextView) itemView.findViewById(R.id.date_in_progress);
            textView2 = (TextView) itemView.findViewById(R.id.time_in_progress);
            textView3 = (TextView) itemView.findViewById(R.id.merk_in_progress);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.klik_in_progress);

        }
    }
}
