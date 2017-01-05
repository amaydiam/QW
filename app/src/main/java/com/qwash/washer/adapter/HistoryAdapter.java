package com.ad.sample.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.GradientDrawable;
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

import com.ad.sample.R;
import com.ad.sample.model.History;
import com.ad.sample.ui.widget.RobotoRegularTextView;
import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.fonts.EntypoIcons;
import com.joanzapata.iconify.fonts.MaterialCommunityIcons;
import com.joanzapata.iconify.widget.IconButton;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> implements View.OnTouchListener, View.OnClickListener {

    private final GestureDetector gestureDetector;
    public final ArrayList<History> data;
    private boolean isTablet = false;
    private Activity activity;
    private SparseBooleanArray mSelectedItemsIds;
    private int selected = -1;

    private OnHistoryItemClickListener OnHistoryItemClickListener;


    public void setOnHistoryItemClickListener(OnHistoryItemClickListener onHistoryItemClickListener) {
        this.OnHistoryItemClickListener = onHistoryItemClickListener;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        final int viewId = v.getId();
        if (viewId == R.id.btn_action) {
            if (gestureDetector.onTouchEvent(event)) {
                if (OnHistoryItemClickListener != null) {
                    AudioManager audioManager = (AudioManager) activity.getSystemService(Context.AUDIO_SERVICE);
                    audioManager.playSoundEffect(SoundEffectConstants.CLICK);
                    OnHistoryItemClickListener.onActionClick(v, (Integer) v.getTag());
                }
            }
        }

        return false;
    }

    @Override
    public void onClick(View v) {
        if (OnHistoryItemClickListener != null) {
            OnHistoryItemClickListener.onRootClick(v, (Integer) v.getTag());
        }
    }

    public void setSelected(int selected) {
        this.selected = selected;
        notifyDataSetChanged();
    }


    public interface OnHistoryItemClickListener {
        void onActionClick(View v, int position);

        void onRootClick(View v, int position);

    }

    public HistoryAdapter(Activity activity, ArrayList<History> mustahiqList, boolean isTable) {
        this.activity = activity;
        this.data = mustahiqList;
        mSelectedItemsIds = new SparseBooleanArray();
        gestureDetector = new GestureDetector(activity, new SingleTapConfirm());
        this.isTablet = isTable;

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

        @BindView(R.id.history_time)
        RobotoRegularTextView historyTime;
        @BindView(R.id.img_address)
        ImageView imgAddress;
        @BindView(R.id.address)
        RobotoRegularTextView address;
        @BindView(R.id.img_vehicle_model)
        ImageView imgVehicleModel;
        @BindView(R.id.vehicle_model)
        RobotoRegularTextView vehicleModel;
        @BindView(R.id.btn_action)
        IconButton btnAction;
        @BindView(R.id.root_parent)
        CardView rootParent;

        public ViewHolder(View vi) {
            super(vi);
            ButterKnife.bind(this, vi);

        }

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,
                                         int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_history_list, parent, false);
        ViewHolder holder = new ViewHolder(v);
        holder.rootParent.setOnClickListener(this);
        holder.btnAction.setOnTouchListener(this);
        return holder;
    }

    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.btnAction.setVisibility(View.GONE);
        History history = data.get(position);

        holder.historyTime.setText(history.history_time);
        holder.address.setText(history.address);
        holder.vehicleModel.setText(history.vehicle_model);

        if (isTablet) {
            if (selected == position) {
                holder.rootParent.setBackgroundColor(ContextCompat.getColor(activity, R.color.blue_1E87DA));
                holder.historyTime.setTextColor(ContextCompat.getColor(activity,R.color.white));
                holder.address.setTextColor(ContextCompat.getColor(activity,R.color.white));
                holder.imgAddress.setImageDrawable(new IconDrawable(activity, EntypoIcons.entypo_location_pin).colorRes(R.color.white).actionBarSize());
                holder.imgVehicleModel.setImageDrawable(new IconDrawable(activity, MaterialCommunityIcons.mdi_car).colorRes(R.color.white).actionBarSize());
            } else {
                holder.rootParent.setBackgroundColor(ContextCompat.getColor(activity, R.color.white));
                holder.historyTime.setTextColor(ContextCompat.getColor(activity,R.color.black_424242));
                holder.address.setTextColor(ContextCompat.getColor(activity,R.color.black_424242));
                holder.imgAddress.setImageDrawable(new IconDrawable(activity, EntypoIcons.entypo_location_pin).colorRes(R.color.blue_1E87DA).actionBarSize());
                holder.imgVehicleModel.setImageDrawable(new IconDrawable(activity, MaterialCommunityIcons.mdi_car).colorRes(R.color.blue_1E87DA).actionBarSize());
            }
        } else {
            holder.rootParent.setBackgroundColor(ContextCompat.getColor(activity, R.color.white));
            holder.historyTime.setTextColor(ContextCompat.getColor(activity,R.color.black_424242));
            holder.address.setTextColor(ContextCompat.getColor(activity,R.color.black_424242));
            holder.imgAddress.setImageDrawable(new IconDrawable(activity, EntypoIcons.entypo_location_pin).colorRes(R.color.blue_1E87DA).actionBarSize());
            holder.imgVehicleModel.setImageDrawable(new IconDrawable(activity, MaterialCommunityIcons.mdi_car).colorRes(R.color.blue_1E87DA).actionBarSize());
        }

        holder.btnAction.setTag(position);
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
