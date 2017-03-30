package com.qwash.washer.adapter;

import android.app.Activity;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.joanzapata.iconify.widget.IconTextView;
import com.qwash.washer.R;
import com.qwash.washer.model.register.VerifyTools;
import com.qwash.washer.ui.widget.RobotoRegularTextView;
import com.qwash.washer.ui.widget.SquareImageView;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

public class KategoriPostAdapter extends RecyclerView.Adapter<KategoriPostAdapter.ViewHolder> implements View.OnClickListener {


    public ArrayList<VerifyTools> data;
    private Activity activity;
    private GestureDetector gestureDetector;
    private int selected = -1;
    private OnKategoriPostItemClickListener OnKategoriPostItemClickListener;


    public KategoriPostAdapter(Activity a, ArrayList<VerifyTools> d) {
        activity = a;
        data = d;
        gestureDetector = new GestureDetector(activity, new SingleTapConfirm());

    }

    public long getItemId(int position) {
        return position;
    }

    public void setOnKategoriPostItemClickListener(OnKategoriPostItemClickListener onKategoriPostItemClickListener) {
        this.OnKategoriPostItemClickListener = onKategoriPostItemClickListener;
    }


    @Override
    public void onClick(View v) {
        if (OnKategoriPostItemClickListener != null) {
            OnKategoriPostItemClickListener.onRootClick(v, (Integer) v.getTag());
        }
    }

    public void setSelected(int position) {
        this.selected = position;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,
                                         int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_verfiy_tools, parent, false);
        ViewHolder holder = new ViewHolder(v);
        v.setOnClickListener(this);
        return holder;
    }

    public void onBindViewHolder(final ViewHolder holder, int position) {
        VerifyTools verifyTools = data.get(position);


        Glide.with(activity)
                .load(verifyTools.getResource())
                .asBitmap()
                .placeholder(R.drawable.placeholder)
                .into(holder.img);

        holder.desc.setText(verifyTools.getDes());
        holder.price.setText(verifyTools.getPrice());

        if (selected == position) {
            holder.checked.setVisibility(View.VISIBLE);
            holder.rootParent.setCardBackgroundColor(ContextCompat.getColor(activity, R.color.colorPrimary));
            holder.desc.setTextColor(ContextCompat.getColor(activity, R.color.white));
            holder.price.setTextColor(ContextCompat.getColor(activity, R.color.white));
        } else {
            holder.checked.setVisibility(View.GONE);
            holder.rootParent.setCardBackgroundColor(ContextCompat.getColor(activity, R.color.white));
            holder.desc.setTextColor(ContextCompat.getColor(activity, R.color.blue_2196F3));
            holder.price.setTextColor(ContextCompat.getColor(activity, R.color.textColorSecondary));
        }

        holder.rootParent.setTag(position);
        //  setAnimation(holder.rootParent, position);


    }

    public int getItemCount() {
        return data.size();
    }


    public interface OnKategoriPostItemClickListener {

        void onRootClick(View v, int position);
    }

    class ViewHolder extends RecyclerView.ViewHolder {


        @BindView(R.id.img)
        SquareImageView img;
        @BindView(R.id.desc)
        RobotoRegularTextView desc;
        @BindView(R.id.price)
        RobotoRegularTextView price;
        @BindView(R.id.checked)
        IconTextView checked;
        @BindView(R.id.root_parent)
        CardView rootParent;


        ViewHolder(View vi) {
            super(vi);
            ButterKnife.bind(this, vi);

        }
    }

    private class SingleTapConfirm extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onSingleTapUp(MotionEvent event) {
            return true;
        }


    }
}
