package com.qwash.washer.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.qwash.washer.R;
import com.qwash.washer.Sample;
import com.qwash.washer.model.wash_history.WashHistory;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WashHistoryAdapter extends RecyclerView.Adapter<WashHistoryAdapter.ViewHolder> implements View.OnTouchListener, View.OnClickListener {

    public final ArrayList<WashHistory> data;
    private final GestureDetector gestureDetector;
    private final int type_wash_history;

    private boolean isTablet = false;
    private Activity activity;
    private SparseBooleanArray mSelectedItemsIds;
    private int selected = -1;

    private OnWashHistoryItemClickListener OnWashHistoryItemClickListener;


    public WashHistoryAdapter(Activity activity, int type_wash_history, ArrayList<WashHistory> feedbackCustomerList) {
        this.activity = activity;
        this.type_wash_history = type_wash_history;
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

    public void onBindViewHolder(final ViewHolder holder, int position) {
        WashHistory WashHistory = data.get(position);

        holder.dateInProgress.setText(WashHistory.getPickDate());
        holder.timeInProgress.setText(WashHistory.getPickTime());
        holder.merkInProgress.setText(WashHistory.getVBrand());
        if (type_wash_history == Sample.WASH_HISTORY_COMPLETE)
            holder.status.setVisibility(View.VISIBLE);
        else
            holder.status.setVisibility(View.GONE);

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

        @BindView(R.id.date_in_progress)
        TextView dateInProgress;
        @BindView(R.id.time_in_progress)
        TextView timeInProgress;
        @BindView(R.id.merk_in_progress)
        TextView merkInProgress;
        @BindView(R.id.status)
        TextView status;
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
