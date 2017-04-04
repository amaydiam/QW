package com.qwash.washer.adapter;

import android.app.Activity;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.qwash.washer.R;
import com.qwash.washer.Sample;
import com.qwash.washer.model.feedback_customer.FeedbackCustomer;
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

public class FeedbackCustomerAdapter extends RecyclerView.Adapter<FeedbackCustomerAdapter.ViewHolder> implements View.OnClickListener {

    public final ArrayList<FeedbackCustomer> data;

    private boolean isTablet = false;
    private Activity activity;
    private SparseBooleanArray mSelectedItemsIds;
    private int selected = -1;

    private OnFeedbackCustomerItemClickListener OnFeedbackCustomerItemClickListener;


    public FeedbackCustomerAdapter(Activity activity, ArrayList<FeedbackCustomer> feedbackCustomerList) {
        this.activity = activity;
        this.data = feedbackCustomerList;
        mSelectedItemsIds = new SparseBooleanArray();

    }

    public void setOnFeedbackCustomerItemClickListener(OnFeedbackCustomerItemClickListener onFeedbackCustomerItemClickListener) {
        this.OnFeedbackCustomerItemClickListener = onFeedbackCustomerItemClickListener;
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
                .inflate(R.layout.item_feedback, parent, false);
        ViewHolder holder = new ViewHolder(v);
        holder.rootParent.setOnClickListener(this);
        return holder;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void onBindViewHolder(final ViewHolder holder, int position) {
        FeedbackCustomer feedbackCustomer = data.get(position);

        try {
            String[] n = feedbackCustomer.getCreateAt().split(" ");
            String[] d = n[1].split(":");
            String h = TextUtils.getTodayYestFromMilli(activity, n[0], TextUtils.getDate(feedbackCustomer.getCreateAt()).getTime());

            try {
                FeedbackCustomer feedbackCustomer_s = data.get(position - 1);
                String[] s = feedbackCustomer_s.getCreateAt().split(" ");
                if (n[0].equalsIgnoreCase(s[0])) {
                    holder.header.setVisibility(View.GONE);
                } else {
                    holder.header.setVisibility(View.VISIBLE);
                    holder.header.setText(h);
                }

            } catch (Exception e) {
                holder.header.setVisibility(View.VISIBLE);
                holder.header.setText(h);
            }

            holder.dateFeedback.setText(d[0] + ":" + d[1]);
        } catch (Exception x) {
            holder.header.setVisibility(View.VISIBLE);
            holder.header.setText(feedbackCustomer.getCreateAt());
            holder.dateFeedback.setText(feedbackCustomer.getCreateAt());
        }


        final int sdk = Build.VERSION.SDK_INT;
        int rate = Integer.parseInt(feedbackCustomer.getRate());
        if (rate == 5) {
            if (sdk < Build.VERSION_CODES.JELLY_BEAN) {
                holder.content.setBackgroundDrawable(activity.getResources().getDrawable(R.drawable.border_set_rate_5));

            } else {
                holder.content.setBackground(activity.getResources().getDrawable(R.drawable.border_set_rate_5));
            }

        } else if (rate == 4) {
            if (sdk < Build.VERSION_CODES.JELLY_BEAN) {
                holder.content.setBackgroundDrawable(activity.getResources().getDrawable(R.drawable.border_set_rate_4));

            } else {
                holder.content.setBackground(activity.getResources().getDrawable(R.drawable.border_set_rate_4));
            }

        } else if (rate == 3) {
            if (sdk < Build.VERSION_CODES.JELLY_BEAN) {
                holder.content.setBackgroundDrawable(activity.getResources().getDrawable(R.drawable.border_set_rate_3));

            } else {
                holder.content.setBackground(activity.getResources().getDrawable(R.drawable.border_set_rate_3));
            }

        } else if (rate == 2) {
            if (sdk < Build.VERSION_CODES.JELLY_BEAN) {
                holder.content.setBackgroundDrawable(activity.getResources().getDrawable(R.drawable.border_set_rate_2));

            } else {
                holder.content.setBackground(activity.getResources().getDrawable(R.drawable.border_set_rate_2));
            }

        } else if (rate == 1) {
            if (sdk < Build.VERSION_CODES.JELLY_BEAN) {
                holder.content.setBackgroundDrawable(activity.getResources().getDrawable(R.drawable.border_set_rate_1));

            } else {
                holder.content.setBackground(activity.getResources().getDrawable(R.drawable.border_set_rate_1));
            }

        } else {
            if (sdk < Build.VERSION_CODES.JELLY_BEAN) {
                holder.content.setBackgroundDrawable(activity.getResources().getDrawable(R.drawable.border_set_default));

            } else {
                holder.content.setBackground(activity.getResources().getDrawable(R.drawable.border_set_default));
            }

        }


        PicassoLoader imageLoader = new PicassoLoader();
        imageLoader.loadImage(holder.customerPhoto, Sample.BASE_URL_QWASH_PUBLIC + "", feedbackCustomer.getFullName());
        holder.customerName.setText(feedbackCustomer.getFullName());

        holder.deskripsiFeedback.setText(TextUtils.isNullOrEmpty(feedbackCustomer.getComments()) ? "-" : feedbackCustomer.getComments());
        holder.rating.setRating(Integer.parseInt(feedbackCustomer.getRate()));

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

    public interface OnFeedbackCustomerItemClickListener {
        void onActionClick(View v, int position);

        void onRootClick(View v, int position);

    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        @BindView(R.id.header)
        RobotoBoldTextView header;
        @BindView(R.id.customer_photo)
        AvatarView customerPhoto;
        @BindView(R.id.date_feedback)
        RobotoRegularTextView dateFeedback;
        @BindView(R.id.customer_name)
        RobotoRegularTextView customerName;
        @BindView(R.id.deskripsi_feedback)
        RobotoRegularTextView deskripsiFeedback;
        @BindView(R.id.rating)
        ProperRatingBar rating;
        @BindView(R.id.content)
        LinearLayout content;
        @BindView(R.id.root_parent)
        LinearLayout rootParent;

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
