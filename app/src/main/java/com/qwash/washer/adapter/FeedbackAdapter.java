package com.qwash.washer.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qwash.washer.R;

/**
 * Created by binderbyte on 10/01/17.
 */

public class FeedbackAdapter extends RecyclerView.Adapter<FeedbackAdapter.MyRecyclerHolder> {

    private String[] date = {"Wed, 02 January 2017", "Monday, 02 January 2017", "Today, 02 January 2017", "Monday, 02 January 2017", "Saturday, 02 January 2017", "Today, 02 January 2017"};
    private String[] deskripsi = {"Lorem ipsum doler asmet soler, moler doler asmet lorem asmet, loter, asmet", "Lorem ipsum doler asmet soler, moler doler asmet lorem asmet, loter, asmet", "Lorem ipsum doler asmet soler, moler doler asmet lorem asmet, loter, asmet", "Lorem ipsum doler asmet soler, moler doler asmet lorem asmet, loter, asmet", "Lorem ipsum doler asmet soler, moler doler asmet lorem asmet, loter, asmet", "Lorem ipsum doler asmet soler, moler doler asmet lorem asmet, loter, asmet"};

    private Context context;

    public FeedbackAdapter(Context context) {
        this.context = context;
    }

    @Override
    public MyRecyclerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_feedback, parent, false);

        return new MyRecyclerHolder(view);
    }

    @Override
    public void onBindViewHolder(MyRecyclerHolder holder, int position) {
        holder.textView1.setText(date[position]);
        holder.textView2.setText(deskripsi[position]);
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  Intent intent = new Intent(v.getContext(), RatingActivity.class);
             //   v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return date.length;
    }

    class MyRecyclerHolder extends RecyclerView.ViewHolder {

        private TextView textView1, textView2;
        private LinearLayout linearLayout;

        public MyRecyclerHolder(View itemView) {
            super(itemView);

            linearLayout = (LinearLayout) itemView.findViewById(R.id.klik_feedback);
            textView1 = (TextView) itemView.findViewById(R.id.date_feedback);
            textView2 = (TextView) itemView.findViewById(R.id.deskripsi_feedback);

        }
    }
}
