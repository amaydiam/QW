package com.qwash.washer.adapter;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.SoundEffectConstants;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.fonts.EntypoIcons;
import com.joanzapata.iconify.fonts.MaterialCommunityIcons;
import com.joanzapata.iconify.widget.IconButton;
import com.joanzapata.iconify.widget.IconTextView;
import com.qwash.washer.R;
import com.qwash.washer.model.FeedbackCustomer;
import com.qwash.washer.ui.widget.RobotoLightTextView;
import com.qwash.washer.ui.widget.RobotoRegularTextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FeedbackCustomerAdapter extends RecyclerView.Adapter<FeedbackCustomerAdapter.ViewHolder> implements View.OnTouchListener, View.OnClickListener {

    private final GestureDetector gestureDetector;
    public final ArrayList<FeedbackCustomer> data;
    private boolean isTablet = false;
    private Activity activity;
    private SparseBooleanArray mSelectedItemsIds;
    private int selected = -1;

    private OnFeedbackCustomerItemClickListener OnFeedbackCustomerItemClickListener;


    public void setOnFeedbackCustomerItemClickListener(OnFeedbackCustomerItemClickListener onFeedbackCustomerItemClickListener) {
        this.OnFeedbackCustomerItemClickListener = onFeedbackCustomerItemClickListener;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        final int viewId = v.getId();
        if (viewId == R.id.btn_action) {
            if (gestureDetector.onTouchEvent(event)) {
                if (OnFeedbackCustomerItemClickListener != null) {
                    AudioManager audioManager = (AudioManager) activity.getSystemService(Context.AUDIO_SERVICE);
                    audioManager.playSoundEffect(SoundEffectConstants.CLICK);
                    OnFeedbackCustomerItemClickListener.onActionClick(v, (Integer) v.getTag());
                }
            }
        }

        return false;
    }

    @Override
    public void onClick(View v) {
        if (OnFeedbackCustomerItemClickListener != null) {
            OnFeedbackCustomerItemClickListener.onRootClick(v, (Integer) v.getTag());
        }
    }

    public void setSelected(int selected) {
        this.selected = selected;
        notifyDataSetChanged();
    }


    public interface OnFeedbackCustomerItemClickListener {
        void onActionClick(View v, int position);

        void onRootClick(View v, int position);

    }

    public FeedbackCustomerAdapter(Activity activity, ArrayList<FeedbackCustomer> feedbackCustomerList) {
        this.activity = activity;
        this.data = feedbackCustomerList;
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

        @BindView(R.id.icon_datetime)
        IconTextView iconDatetime;
        @BindView(R.id.date_feedback)
        RobotoRegularTextView dateFeedback;
        @BindView(R.id.deskripsi_feedback)
        RobotoLightTextView deskripsiFeedback;
        @BindView(R.id.btn_action)
        IconButton btnAction;
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
                .inflate(R.layout.item_feedback, parent, false);
        ViewHolder holder = new ViewHolder(v);
        holder.rootParent.setOnClickListener(this);
        holder.btnAction.setOnTouchListener(this);
        return holder;
    }

    public void onBindViewHolder(final ViewHolder holder, int position) {
        FeedbackCustomer FeedbackCustomer = data.get(position);

        holder.dateFeedback.setText(FeedbackCustomer.getCreateAt());
        holder.deskripsiFeedback.setText(FeedbackCustomer.getComments());

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
