package com.qwash.washer.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.qwash.washer.R;
import com.qwash.washer.api.model.InProgressResponse;

import java.util.List;

/**
 * Created by binderbyte on 06/01/17.
 */

public class InProgressAdapter extends RecyclerView.Adapter<InProgressAdapter.MyViewHolder>{

    private List<InProgressResponse> inProgressResponses;

    public InProgressAdapter(List<InProgressResponse> inProgressResponses) {
        this.inProgressResponses = inProgressResponses;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_in_progress, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.textView1.setText(inProgressResponses.get(position).getDetail().getDate());
        holder.textView2.setText(inProgressResponses.get(position).getDetail().getTime());
        holder.textView3.setText(inProgressResponses.get(position).getDetail().getBrand());
    }

    @Override
    public int getItemCount() {
        return inProgressResponses.size();
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
