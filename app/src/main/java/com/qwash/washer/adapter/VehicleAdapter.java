package com.ad.sample.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.media.AudioManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.SparseBooleanArray;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.SoundEffectConstants;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ad.sample.R;
import com.ad.sample.model.Vehicle;
import com.ad.sample.ui.widget.RobotoBoldTextView;
import com.bumptech.glide.Glide;
import com.joanzapata.iconify.widget.IconButton;
import com.joanzapata.iconify.widget.IconTextView;

import java.util.ArrayList;

import agency.tango.android.avatarview.loader.PicassoLoader;
import butterknife.BindView;
import butterknife.ButterKnife;

public class VehicleAdapter extends RecyclerView.Adapter<VehicleAdapter.ViewHolder> implements View.OnTouchListener, View.OnClickListener {

    private final GestureDetector gestureDetector;
    public final ArrayList<Vehicle> data;
    private Activity activity;
    private SparseBooleanArray medItemsIds;
    private int selected = -1;


    private OnVehicleItemClickListener OnVehicleItemClickListener;


    public void setOnVehicleItemClickListener(OnVehicleItemClickListener onVehicleItemClickListener) {
        this.OnVehicleItemClickListener = onVehicleItemClickListener;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        final int viewId = v.getId();
        if (viewId == R.id.btn_action) {
            if (gestureDetector.onTouchEvent(event)) {
                if (OnVehicleItemClickListener != null) {
                    AudioManager audioManager = (AudioManager) activity.getSystemService(Context.AUDIO_SERVICE);
                    audioManager.playSoundEffect(SoundEffectConstants.CLICK);
                    OnVehicleItemClickListener.onActionClick(v, (Integer) v.getTag());
                }
            }
        }

        return false;
    }

    @Override
    public void onClick(View v) {
        if (OnVehicleItemClickListener != null) {
            OnVehicleItemClickListener.onRootClick(v, (Integer) v.getTag());
        }
    }


    public interface OnVehicleItemClickListener {
        void onActionClick(View v, int position);

        void onRootClick(View v, int position);

    }

    public VehicleAdapter(Activity activity, ArrayList<Vehicle> vehicleList) {
        this.activity = activity;
        this.data = vehicleList;
        medItemsIds = new SparseBooleanArray();
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

        @BindView(R.id.vehicle_image)
        ImageView vehicleImage;
        @BindView(R.id.vehicle_description)
        RobotoBoldTextView vehicleDescription;
        @BindView(R.id.btn_action)
        IconButton btnAction;
        @BindView(R.id.indicator_item)
        IconTextView indicatorItem;
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
                .inflate(R.layout.item_select_vehicle, parent, false);
        ViewHolder holder = new ViewHolder(v);
        holder.rootParent.setOnClickListener(this);
        holder.btnAction.setOnTouchListener(this);
        return holder;
    }

    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.btnAction.setVisibility(View.GONE);
        Vehicle vehicle = data.get(position);
        Glide
                .with(activity)
                .load("")
                .centerCrop()
                .placeholder(vehicle.type == 1 ? R.drawable.mobil : R.drawable.motor)
                .crossFade()
                .into(holder.vehicleImage);

        holder.vehicleDescription.setText(vehicle.brand + "\n" + vehicle.model + " " + vehicle.transmission + " " + vehicle.year);
        if (selected == position) {
            holder.rootParent.setBackgroundColor(ContextCompat.getColor(activity, R.color.blue_2196F3));
            holder.vehicleDescription.setTextColor(ContextCompat.getColor(activity, R.color.white));
            holder.indicatorItem.setVisibility(View.VISIBLE);
        } else {
            holder.rootParent.setBackgroundColor(ContextCompat.getColor(activity, R.color.white));
            holder.vehicleDescription.setTextColor(ContextCompat.getColor(activity, R.color.black_424242));
            holder.indicatorItem.setVisibility(View.GONE);
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

    public void toggleion(int position) {
        selectView(position, !medItemsIds.get(position));
    }

    public void removeion() {
        medItemsIds = new SparseBooleanArray();
        notifyDataSetChanged();
    }

    public void setSelectionByIdVehicle(String id_vehicle) {
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i).id_vehicle.equalsIgnoreCase(id_vehicle)) {
                setSelection(i);
                break;
            }
        }
    }

    public void setSelection(int position) {
        this.selected = position;
        notifyDataSetChanged();
    }


    public int getSelection() {
        return this.selected;
    }


    public void selectView(int position, boolean value) {
        if (value)
            medItemsIds.put(position, value);
        else
            medItemsIds.delete(position);
        notifyDataSetChanged();
    }

    public int getedCount() {
        return medItemsIds.size();
    }

    public SparseBooleanArray getedIds() {
        return medItemsIds;
    }

    private class SingleTapConfirm extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onSingleTapUp(MotionEvent event) {
            return true;
        }


    }


}
