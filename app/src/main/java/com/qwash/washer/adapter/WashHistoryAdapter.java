package com.qwash.washer.adapter;

import android.app.Activity;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.qwash.washer.R;
import com.qwash.washer.Sample;
import com.qwash.washer.model.wash_history.WashHistory;
import com.qwash.washer.ui.widget.RobotoBoldTextView;
import com.qwash.washer.ui.widget.RobotoRegularTextView;
import com.qwash.washer.utils.TextUtils;

import java.util.ArrayList;

import agency.tango.android.avatarview.loader.PicassoLoader;
import agency.tango.android.avatarview.views.AvatarView;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.techery.properratingbar.ProperRatingBar;

public class WashHistoryAdapter extends RecyclerView.Adapter<WashHistoryAdapter.ViewHolder> implements View.OnTouchListener, View.OnClickListener {

    public final ArrayList<WashHistory> data;
    private final GestureDetector gestureDetector;

    private boolean isTablet = false;
    private Activity activity;
    private SparseBooleanArray mSelectedItemsIds;
    private int selected = -1;

    private OnWashHistoryItemClickListener OnWashHistoryItemClickListener;


    public WashHistoryAdapter(Activity activity, ArrayList<WashHistory> feedbackCustomerList) {
        this.activity = activity;
        this.data = feedbackCustomerList;
        mSelectedItemsIds = new SparseBooleanArray();
        gestureDetector = new GestureDetector(activity, new SingleTapConfirm());

    }

    public void setOnWashHistoryItemClickListener(OnWashHistoryItemClickListener onWashHistoryItemClickListener) {
        this.OnWashHistoryItemClickListener = onWashHistoryItemClickListener;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        return false;
    }

    @Override
    public void onClick(View v) {
        if (OnWashHistoryItemClickListener != null) {
            OnWashHistoryItemClickListener.onRootClick(v, (Integer) v.getTag());
        }
    }

    public void setSelected(int selected) {
        this.selected = selected;
        notifyDataSetChanged();
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

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,
                                         int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_history_wash, parent, false);
        ViewHolder holder = new ViewHolder(v);
        holder.rootParent.setOnClickListener(this);
        return holder;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void onBindViewHolder(final ViewHolder holder, int position) {
        WashHistory WashHistory = data.get(position);

        if (WashHistory.getVehicles().equalsIgnoreCase("1")) {
            holder.vehiclePhoto.setImageDrawable(ContextCompat.getDrawable(activity, R.drawable.big_citycar));
            holder.vehicle.setText("SMALL");
        } else if (WashHistory.getVehicles().equalsIgnoreCase("2")) {
            holder.vehiclePhoto.setImageDrawable(ContextCompat.getDrawable(activity, R.drawable.big_minivan));
            holder.vehicle.setText("MEDIUM");
        } else if (WashHistory.getVehicles().equalsIgnoreCase("3")) {
            holder.vehiclePhoto.setImageDrawable(ContextCompat.getDrawable(activity, R.drawable.big_suv));
            holder.vehicle.setText("BIG");

        } else if (WashHistory.getVehicles().equalsIgnoreCase("4")) {
            holder.vehiclePhoto.setImageDrawable(ContextCompat.getDrawable(activity, R.drawable.big_under_srp_cc));
            holder.vehicle.setText("SMALL");

        } else if (WashHistory.getVehicles().equalsIgnoreCase("5")) {
            holder.vehiclePhoto.setImageDrawable(ContextCompat.getDrawable(activity, R.drawable.big_srp_cc));
            holder.vehicle.setText("MEDIUM");

        } else if (WashHistory.getVehicles().equalsIgnoreCase("6")) {
            holder.vehiclePhoto.setImageDrawable(ContextCompat.getDrawable(activity, R.drawable.big_above_srp_cc));
            holder.vehicle.setText("BIG");

        } else {
            holder.vehiclePhoto.setImageDrawable(ContextCompat.getDrawable(activity, R.mipmap.ic_launcher));
            holder.vehicle.setText("-");
        }


        final int sdk = Build.VERSION.SDK_INT;
        if (WashHistory.getStatus().equalsIgnoreCase("3")) {

            if (sdk < Build.VERSION_CODES.JELLY_BEAN) {
                holder.rootParent.setBackgroundDrawable(activity.getResources().getDrawable(R.drawable.border_set_green));
                holder.status.setBackgroundDrawable(activity.getResources().getDrawable(R.drawable.shape_complete));

            } else {
                holder.rootParent.setBackground(activity.getResources().getDrawable(R.drawable.border_set_green));
                holder.status.setBackground(activity.getResources().getDrawable(R.drawable.shape_complete));
            }
            holder.status.setTextColor(ContextCompat.getColor(activity, R.color.green_cc09891b));

        } else if (WashHistory.getStatus().equalsIgnoreCase("4") || WashHistory.getStatus().equalsIgnoreCase("5")) {

            if (sdk < Build.VERSION_CODES.JELLY_BEAN) {
                holder.rootParent.setBackgroundDrawable(activity.getResources().getDrawable(R.drawable.border_set_red));
                holder.status.setBackgroundDrawable(activity.getResources().getDrawable(R.drawable.shape_cancel));

            } else {
                holder.rootParent.setBackground(activity.getResources().getDrawable(R.drawable.border_set_red));
                holder.status.setBackground(activity.getResources().getDrawable(R.drawable.shape_cancel));
            }
            holder.status.setTextColor(ContextCompat.getColor(activity, R.color.red_light));

        } else {
            if (sdk < Build.VERSION_CODES.JELLY_BEAN) {
                holder.rootParent.setBackgroundDrawable(activity.getResources().getDrawable(R.drawable.border_set_default));
                holder.status.setBackgroundDrawable(activity.getResources().getDrawable(R.drawable.shape_default));

            } else {
                holder.rootParent.setBackground(activity.getResources().getDrawable(R.drawable.border_set_default));
                holder.status.setBackground(activity.getResources().getDrawable(R.drawable.shape_default));
            }
            holder.status.setTextColor(ContextCompat.getColor(activity, R.color.divider));

        }


        if (WashHistory.getStatus().equalsIgnoreCase("3") && !TextUtils.isNullOrEmpty(WashHistory.getRate())) {
            holder.layoutRating.setVisibility(View.VISIBLE);
            holder.rating.setRating(Integer.parseInt(WashHistory.getRate()));
            holder.estimatedPrice.setTextColor(ContextCompat.getColor(activity, R.color.blue_2196F3));
        } else {
            holder.layoutRating.setVisibility(View.GONE);
            holder.estimatedPrice.setTextColor(ContextCompat.getColor(activity, R.color.orange));
        }

        holder.status.setText(WashHistory.getDescription());

        holder.dateInProgress.setText(WashHistory.getCreateAt());

        holder.estimatedPrice.setText(WashHistory.getPriceOnRupiah());

        PicassoLoader imageLoader = new PicassoLoader();
        holder.address.setText(WashHistory.getAddress());

        imageLoader.loadImage(holder.customerPhoto, Sample.BASE_URL_IMAGE + "", WashHistory.getName());
        holder.customerName.setText(WashHistory.getName());

        holder.status.setVisibility(View.VISIBLE);
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

    public interface OnWashHistoryItemClickListener {

        void onRootClick(View v, int position);

    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        @BindView(R.id.vehicle_photo)
        ImageView vehiclePhoto;
        @BindView(R.id.date_in_progress)
        RobotoRegularTextView dateInProgress;
        @BindView(R.id.vehicle)
        RobotoRegularTextView vehicle;
        @BindView(R.id.customer_photo)
        AvatarView customerPhoto;
        @BindView(R.id.customer_name)
        RobotoRegularTextView customerName;
        @BindView(R.id.address)
        RobotoRegularTextView address;
        @BindView(R.id.rating)
        ProperRatingBar rating;
        @BindView(R.id.estimated_price)
        RobotoBoldTextView estimatedPrice;
        @BindView(R.id.status)
        RobotoRegularTextView status;
        @BindView(R.id.layout_rating)
        LinearLayout layoutRating;
        @BindView(R.id.root_parent)
        RelativeLayout rootParent;

        public ViewHolder(View vi) {
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
