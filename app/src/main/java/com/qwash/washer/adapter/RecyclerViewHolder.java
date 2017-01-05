package com.qwash.washer.adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.qwash.washer.R;

/**
 * Created by binderbyte on 24/12/16.
 */
public class RecyclerViewHolder extends RecyclerView.ViewHolder {

    TextView title1, title2, title3, title4, title5, deskripsi1;
    CardView cardView1, cardView2, cardView3, cardView4, cardView5;
    ImageView image1;

    public RecyclerViewHolder(View itemView) {
        super(itemView);

        title1 = (TextView) itemView.findViewById(R.id.list_brand);
        cardView1 =(CardView) itemView.findViewById(R.id.card_view_brand);

        title2 = (TextView) itemView.findViewById(R.id.list_model);
        cardView2 =(CardView) itemView.findViewById(R.id.card_view_model);

        title3 = (TextView) itemView.findViewById(R.id.list_transmission);
        cardView3 =(CardView) itemView.findViewById(R.id.card_view_transmission);

        title4 = (TextView) itemView.findViewById(R.id.list_year);
        cardView4 =(CardView) itemView.findViewById(R.id.card_view_year);

        title5 = (TextView) itemView.findViewById(R.id.title_notification);
        deskripsi1 = (TextView) itemView.findViewById(R.id.deskripsi_notification);
        cardView5 = (CardView) itemView.findViewById(R.id.card_view_notification);
        image1 = (ImageView) itemView.findViewById(R.id.image_notification);

    }
}
