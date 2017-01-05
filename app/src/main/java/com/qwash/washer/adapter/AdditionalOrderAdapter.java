package com.ad.sample.adapter;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.SoundEffectConstants;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.ad.sample.R;
import com.ad.sample.model.AdditionalOrder;
import com.ad.sample.ui.widget.RobotoRegularTextView;
import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.fonts.EntypoIcons;
import com.joanzapata.iconify.fonts.MaterialCommunityIcons;
import com.joanzapata.iconify.widget.IconButton;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AdditionalOrderAdapter extends RecyclerView.Adapter<AdditionalOrderAdapter.ViewHolder> implements View.OnClickListener {

    private final GestureDetector gestureDetector;
    public final ArrayList<AdditionalOrder> data;
    private Activity activity;
    private SparseBooleanArray mSelectedItemsIds;

    private OnAdditionalOrderItemClickListener OnAdditionalOrderItemClickListener;


    public void setOnAdditionalOrderItemClickListener(OnAdditionalOrderItemClickListener onAdditionalOrderItemClickListener) {
        this.OnAdditionalOrderItemClickListener = onAdditionalOrderItemClickListener;
    }


    @Override
    public void onClick(View v) {
        if (OnAdditionalOrderItemClickListener != null) {
            OnAdditionalOrderItemClickListener.onRootClick(v, (Integer) v.getTag());
        }
    }


    public interface OnAdditionalOrderItemClickListener {
        void onRootClick(View v, int position);

    }

    public AdditionalOrderAdapter(Activity activity, ArrayList<AdditionalOrder> additionalOrderList) {
        this.activity = activity;
        this.data = additionalOrderList;
        mSelectedItemsIds = new SparseBooleanArray();
        gestureDetector = new GestureDetector(activity, new SingleTapConfirm());

    }


    public void delete_all() {
        int count = getItemCount();
        if (count > 0) {
            data.clear();
            notifyDataSetChanged();
        }

    }

    public long getItemId(int position) {
        return position;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {


        @BindView(R.id.img_additional_order)
        ImageView imgAdditionalOrder;
        @BindView(R.id.additional_order)
        RobotoRegularTextView additionalOrder;
        @BindView(R.id.root_parent)
        LinearLayout rootParent;

        public ViewHolder(View vi) {
            super(vi);
            ButterKnife.bind(this, vi);

        }

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,
                                         int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_additional_order_list, parent, false);
        ViewHolder holder = new ViewHolder(v);
        holder.rootParent.setOnClickListener(this);
        return holder;
    }

    public void onBindViewHolder(final ViewHolder holder, int position) {
        AdditionalOrder additionalOrder = data.get(position);

        holder.additionalOrder.setText(additionalOrder.getAdditional_order());
       // holder.imgAdditionalOrder.setImageDrawable(additionalOrder.getImg_additional_order());

        holder.rootParent.setTag(position);

    }

    public int getItemCount() {
        return data.size();
    }

    /**
     * Here is the key method to apply the animation
     */

    public void remove(int position) {
        data.remove(data.get(position));
        notifyDataSetChanged();
    }

    public void toggleSelection(int position) {
        selectView(position, !mSelectedItemsIds.get(position));
    }

    public void removeSelection() {
        mSelectedItemsIds = new SparseBooleanArray();
        notifyDataSetChanged();
    }

    public void selectView(int position, boolean value) {
        if (value)
            mSelectedItemsIds.put(position, value);
        else
            mSelectedItemsIds.delete(position);
        notifyDataSetChanged();
    }

    public int getSelectedCount() {
        return mSelectedItemsIds.size();
    }

    public SparseBooleanArray getSelectedIds() {
        return mSelectedItemsIds;
    }

    private class SingleTapConfirm extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onSingleTapUp(MotionEvent event) {
            return true;
        }


    }

}
