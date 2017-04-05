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
import android.widget.LinearLayout;

import com.qwash.washer.R;
import com.qwash.washer.model.wallet.Wallet;
import com.qwash.washer.ui.widget.RobotoBoldTextView;
import com.qwash.washer.ui.widget.RobotoRegularTextView;
import com.qwash.washer.utils.TextUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WalletAdapter extends RecyclerView.Adapter<WalletAdapter.ViewHolder> implements View.OnClickListener {

    public final ArrayList<Wallet> data;
   

    private boolean isTablet = false;
    private Activity activity;
    private SparseBooleanArray mSelectedItemsIds;
    private int selected = -1;

    private OnWalletItemClickListener OnWalletItemClickListener;


    public WalletAdapter(Activity activity, ArrayList<Wallet> walletList) {
        this.activity = activity;
        this.data = walletList;
        mSelectedItemsIds = new SparseBooleanArray();

    }

    public void setOnWalletItemClickListener(OnWalletItemClickListener onWalletItemClickListener) {
        this.OnWalletItemClickListener = onWalletItemClickListener;
    }


    @Override
    public void onClick(View v) {
        if (OnWalletItemClickListener != null) {
            OnWalletItemClickListener.onRootClick(v, (Integer) v.getTag());
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
                .inflate(R.layout.item_wallet, parent, false);
        ViewHolder holder = new ViewHolder(v);
        holder.rootParent.setOnClickListener(this);
        return holder;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void onBindViewHolder(final WalletAdapter.ViewHolder holder, int position) {
        Wallet wallet = data.get(position);
        try {
            String[] n = wallet.getCreatedAt().split(" ");
            String[] d = n[1].split(":");
            String h = TextUtils.getTodayYestFromMilli(activity, n[0], TextUtils.getDate(wallet.getCreatedAt()).getTime());

            try {
                Wallet wallet_s = data.get(position - 1);
                String[] s = wallet_s.getCreatedAt().split(" ");
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

            holder.dateInProgress.setText(d[0] + ":" + d[1]);

        } catch (Exception x) {
            holder.header.setVisibility(View.VISIBLE);
            holder.header.setText(wallet.getCreatedAt());
            holder.dateInProgress.setText(wallet.getCreatedAt());
        }

        final int sdk = Build.VERSION.SDK_INT;
        if (wallet.getKredit() !=0) {

            if (sdk < Build.VERSION_CODES.JELLY_BEAN) {
                holder.content.setBackgroundDrawable(activity.getResources().getDrawable(R.drawable.border_set_green));
                holder.status.setBackgroundDrawable(activity.getResources().getDrawable(R.drawable.shape_complete));

            } else {
                holder.content.setBackground(activity.getResources().getDrawable(R.drawable.border_set_green));
                holder.status.setBackground(activity.getResources().getDrawable(R.drawable.shape_complete));
            }
            holder.status.setTextColor(ContextCompat.getColor(activity, R.color.green_cc09891b));
            holder.estimatedPrice.setText(wallet.getKreditRupiah());
            holder.status.setText(activity.getString(R.string.kredit));

        } else if (wallet.getDebit()!=0) {

            if (sdk < Build.VERSION_CODES.JELLY_BEAN) {
                holder.content.setBackgroundDrawable(activity.getResources().getDrawable(R.drawable.border_set_red));
                holder.status.setBackgroundDrawable(activity.getResources().getDrawable(R.drawable.shape_cancel));

            } else {
                holder.content.setBackground(activity.getResources().getDrawable(R.drawable.border_set_red));
                holder.status.setBackground(activity.getResources().getDrawable(R.drawable.shape_cancel));
            }
            holder.status.setTextColor(ContextCompat.getColor(activity, R.color.red_light));

            holder.estimatedPrice.setText(wallet.getDebitRupiah());
            holder.status.setText(activity.getString(R.string.debit));

        } else {
            if (sdk < Build.VERSION_CODES.JELLY_BEAN) {
                holder.content.setBackgroundDrawable(activity.getResources().getDrawable(R.drawable.border_set_default));
                holder.status.setBackgroundDrawable(activity.getResources().getDrawable(R.drawable.shape_default));

            } else {
                holder.content.setBackground(activity.getResources().getDrawable(R.drawable.border_set_default));
                holder.status.setBackground(activity.getResources().getDrawable(R.drawable.shape_default));
            }
            holder.status.setTextColor(ContextCompat.getColor(activity, R.color.divider));

            holder.estimatedPrice.setText("-");
            holder.status.setText("-");
        }


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

    public interface OnWalletItemClickListener {
        void onActionClick(View v, int position);

        void onRootClick(View v, int position);

    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        @BindView(R.id.header)
        RobotoBoldTextView header;
        @BindView(R.id.date_in_progress)
        RobotoRegularTextView dateInProgress;
        @BindView(R.id.estimated_price)
        RobotoBoldTextView estimatedPrice;
        @BindView(R.id.status)
        RobotoRegularTextView status;
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
