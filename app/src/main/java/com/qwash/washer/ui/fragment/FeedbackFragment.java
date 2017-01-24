package com.qwash.washer.ui.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.fonts.MaterialCommunityIcons;
import com.joanzapata.iconify.widget.IconButton;
import com.mugen.Mugen;
import com.mugen.MugenCallbacks;
import com.qwash.washer.R;
import com.qwash.washer.Sample;
import com.qwash.washer.adapter.FeedbackCustomerAdapter;
import com.qwash.washer.api.ApiUtils;
import com.qwash.washer.api.client.feedbackcustomer.FeedbackCutomerService;
import com.qwash.washer.api.model.FeedbackCustomer.FeedbackCustomerListResponse;
import com.qwash.washer.model.FeedbackCustomer;
import com.qwash.washer.utils.Prefs;
import com.qwash.washer.utils.TextUtils;
import com.qwash.washer.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindBool;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by binderbyte on 10/01/17.
 */
public class FeedbackFragment extends Fragment implements FeedbackCustomerAdapter.OnFeedbackCustomerItemClickListener,
        SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG_MORE = "TAG_MORE";
    private static final String TAG_AWAL = "TAG_AWAL";
    private static final String TAG_NEW = "TAG_NEW";
    private static final String TAG_DELETE = "TAG_DELETE";

    private static final String TAG_TOP = "top";
    private static final String TAG_BOTTOM = "bottom";

    @BindBool(R.bool.is_tablet)
    boolean isTablet;
    @BindView(R.id.btn_search)
    IconButton btnSearch;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;
    @BindView(R.id.progress_more_data)
    ProgressBar progressMoreData;
    @BindView(R.id.no_data)
    IconButton noData;
    @BindView(R.id.scroll_up)
    ImageView scrollUp;
    @BindView(R.id.swipe_container)
    SwipeRefreshLayout swipeContainer;
    @BindView(R.id.fab_action)
    FloatingActionButton fabAction;

    private ArrayList<FeedbackCustomer> data = new ArrayList<>();
    private GridLayoutManager mLayoutManager;
    private String keyword = null;

    @OnClick(R.id.scroll_up)
    void ScrollUp() {
        recyclerView.smoothScrollToPosition(0);
    }


    //error
    @BindView(R.id.error_message)
    View errorMessage;
    @BindView(R.id.loading)
    ProgressBar loading;
    @BindView(R.id.text_error)
    TextView textError;
    @BindView(R.id.try_again)
    TextView tryAgain;


    @BindView(R.id.search)
    EditText search;
    @BindView(R.id.parent_search)
    CardView parentSearch;

    @OnClick(R.id.btn_search)
    void btn_search() {
        Search();
    }

    @OnClick(R.id.try_again)
    void TryAgain() {
        RefreshData();
    }


    private Integer position_delete;
    private ProgressDialog dialogProgress;
    private FragmentActivity activity;
    private Unbinder butterknife;
    public FeedbackCustomerAdapter adapter;
    private boolean isFinishLoadingAwalData = true;
    private boolean isLoadingMoreData = false;
    private boolean isFinishMoreData = false;
    private int page = 1;
    private boolean isRefresh = false;
    //  private String session_key;

    private int mPreviousVisibleItem;

    public FeedbackFragment() {
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = getActivity();
    }


    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static FeedbackFragment newInstance() {
        return new FeedbackFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_list, container, false);
        butterknife = ButterKnife.bind(this, rootView);


        try {
            keyword = getArguments().getString(Sample.KEYWORD);
        } catch (Exception e) {

        }


        // Configure the swipe refresh layout
        swipeContainer.setOnRefreshListener(this);
        swipeContainer.setColorSchemeResources(R.color.blue_light,
                R.color.green_light, R.color.orange_light, R.color.red_light);
        TypedValue typed_value = new TypedValue();
        activity.getTheme().resolveAttribute(android.support.v7.appcompat.R.attr.actionBarSize, typed_value, true);
        swipeContainer.setProgressViewOffset(false, 0, getResources().getDimensionPixelSize(typed_value.resourceId));

        //search
        parentSearch.setVisibility(View.GONE);
        //search.setHint("Cari alamat...");
        search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                String val_search = search.getText().toString().trim();
                Search();
                return false;
            }
        });

        Utils.hideSoftKeyboard(getActivity());

        //inisial adapter
        adapter = new FeedbackCustomerAdapter(activity, data);
        adapter.setOnFeedbackCustomerItemClickListener(this);

        //recyclerView
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);

        mLayoutManager = new GridLayoutManager(activity, Utils.getNumberOfColumns(getActivity(),isTablet));

        // set layout manager
        recyclerView.setLayoutManager(mLayoutManager);

        // set adapter
        recyclerView.setAdapter(adapter);

        //handle ringkas data
        Mugen.with(recyclerView, new MugenCallbacks() {
            @Override
            public void onLoadMore() {
                if (isFinishLoadingAwalData
                        && !isFinishMoreData
                        && adapter.getItemCount() > 0) {
                    getDataFromServer(TAG_MORE);
                }
            }

            @Override
            public boolean isLoading() {
                return isLoadingMoreData;
            }

            @Override
            public boolean hasLoadedAllItems() {
                return false;
            }
        }).start();


        fabAction.setVisibility(View.GONE);

        scrollUp.setImageDrawable(
                new IconDrawable(getActivity(), MaterialCommunityIcons.mdi_arrow_up)
                        .colorRes(R.color.blue_1E87DA));


        noData.setText(Html.fromHtml("<center><h1>{mdi-calendar}</h1></br> Tidak ada feedback customer ...</center>"));
        showNoData(false);

        if (savedInstanceState == null || !savedInstanceState.containsKey(Sample.DATA)) {
            getDataFromServer(TAG_AWAL);
        } else {
            data = savedInstanceState.getParcelableArrayList(Sample.DATA);
            page = savedInstanceState.getInt(Sample.PAGE);
            isLoadingMoreData = savedInstanceState.getBoolean(Sample.IS_LOADING_MORE_DATA);
            isFinishLoadingAwalData = savedInstanceState.getBoolean(Sample.IS_FINISH_LOADING_AWAL_DATA);

            if (!isFinishLoadingAwalData) {
                getDataFromServer(TAG_AWAL);
            } else if (isLoadingMoreData) {
                adapter.notifyDataSetChanged();
                checkData();
                getDataFromServer(TAG_MORE);
            } else {
                adapter.notifyDataSetChanged();
                checkData();
            }
        }

        return rootView;
    }

    private void Search() {
        String val_search = search.getText().toString().trim();
        if (!TextUtils.isNullOrEmpty(val_search)) {
            search.setText("");
          /*  Intent intent = new Intent(activity, CariFeedbackCustomerActivity.class);
            intent.putExtra(Sample.KEYWORD, val_search);
            startActivity(intent);*/
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mLayoutManager != null && adapter != null) {
            outState.putBoolean(Sample.IS_FINISH_LOADING_AWAL_DATA, isFinishLoadingAwalData);
            outState.putBoolean(Sample.IS_LOADING_MORE_DATA, isLoadingMoreData);
            outState.putInt(Sample.PAGE, page);
            outState.putParcelableArrayList(Sample.DATA, data);
        }
    }

    private void showProgresMore(boolean show) {
        if (show) {
            progressMoreData.setVisibility(View.VISIBLE);
        } else {
            progressMoreData.setVisibility(View.GONE);
        }
    }

    private void showNoData(boolean show) {
        if (show) {
            noData.setVisibility(View.VISIBLE);
        } else {
            noData.setVisibility(View.GONE);
        }
    }

    private void ProgresRefresh(boolean show) {
        if (show) {
            swipeContainer.setRefreshing(true);
            swipeContainer.setEnabled(false);
        } else {
            swipeContainer.setEnabled(true);
            swipeContainer.setRefreshing(false);
        }
    }

    private void getDataFromServer(final String TAG) {

        onRetrofitStart(TAG);

        FeedbackCutomerService mService = ApiUtils.getFeedbackCutomer(getActivity());
        mService.getListFeedbackCustomer(Prefs.getUserId(getActivity()),page, Sample.LIMIT_DATA).enqueue(new Callback<FeedbackCustomerListResponse>() {
            @Override
            public void onResponse(Call<FeedbackCustomerListResponse> call, Response<FeedbackCustomerListResponse> response) {

                if (response.isSuccessful()) {
                    onRetrofitSuccessResponse(TAG, response);
                } else {
                    int statusCode = response.code();
                    onRetrofitErrorResponse(TAG, statusCode);
                }
                onRetrofitEnd(TAG);
            }

            @Override
            public void onFailure(Call<FeedbackCustomerListResponse> call, Throwable t) {
                onRetrofitErrorResponse(TAG, 0);
                onRetrofitEnd(TAG);
            }

        });
    }

    protected void DrawDataAllData(String position, String tag, Response<FeedbackCustomerListResponse> response) {


        if (isRefresh) {
            adapter.delete_all();
        }

        Boolean isSuccess = response.body().getStatus();
        if (isSuccess) {
            List<FeedbackCustomer> h = response.body().getData();
            int jumlah_list_data = h.size();
            if (jumlah_list_data > 0) {
                for (int i = 0; i < jumlah_list_data; i++) {
                    FeedbackCustomer obj = h.get(i);
                    setDataObject(position, obj);
                }
                adapter.notifyDataSetChanged();
            } else {
                switch (tag) {
                    case TAG_MORE:
                        isFinishMoreData = true;
                        //       Toast.makeText(activity, "tidak ada data lama...", Toast.LENGTH_LONG, Toast.INFO);
                        break;
                    case TAG_AWAL:
                        //      Toast.makeText(activity, "tidak ada data...", Toast.LENGTH_LONG, Toast.INFO);
                        break;
                    case TAG_NEW:
                        //     Toast.makeText(activity, "tidak ada data baru...", Toast.LENGTH_LONG, Toast.INFO);
                        break;
                }
            }


            page = page + 1;
        }
        checkData();


    }

    private void checkData() {
        if (adapter.getItemCount() > 0) {

            showNoData(false);
        } else {
            showNoData(true);
        }
    }


    private void ResponeDelete(Response<FeedbackCustomerListResponse> response) {

        Boolean isSuccess = response.body().getStatus();
        String message = response.body().getMessage();
        if (isSuccess) {
            adapter.remove(position_delete);
            checkData();
        }
            Toast.makeText(activity,message, Toast.LENGTH_LONG).show();
    }

    private void setDataObject(String position, FeedbackCustomer obj) {
         String id = obj.getId();
         String rate= obj.getRate();
         String createAt= obj.getCreateAt();
         String comments= obj.getComments();
         String ordersRef= obj.getOrdersRef();

        //parse object
        
        //set map object
        AddAndSetMapData(
                position,
                id,
                rate,
                createAt,
                comments,
                ordersRef
        );

    }

    private void AddAndSetMapData(
            String position, String id, String rate, String createAt, String comments, String ordersRef) {

        FeedbackCustomer history = new FeedbackCustomer( id,  rate,  createAt,  comments,  ordersRef);


        if (position.equals(TAG_BOTTOM)) {
            data.add(history);
        } else {
            data.add(0, history);
        }
    }


    @Override
    public void onRefresh() {
        RefreshData();
    }

    public void RefreshData() {
        // if (adapter.getItemCount() > 1) {
        isRefresh = true;
        isLoadingMoreData = false;
        isFinishLoadingAwalData = true;
        isFinishMoreData = false;
        page = 1;
        showNoData(false);
       /* } else {
            isRefresh = false;
        }*/
        getDataFromServer(TAG_AWAL);
    }


    private void startProgress(String TAG) {
        if (TAG.equals(TAG_DELETE)) {
            TAG = "Delete FeedbackCustomerListResponse";
        }
        dialogProgress = ProgressDialog.show(getActivity(), TAG,
                "Please wait...", true);
    }

    private void stopProgress(String TAG) {
        if (dialogProgress != null)
            dialogProgress.dismiss();
    }

    public void onRetrofitStart(String TAG) {
        if (TAG.equals(TAG_DELETE)) {
            startProgress(TAG_DELETE);
        } else {
            showProgresMore(false);
            if (TAG.equals(TAG_AWAL)) {
                ProgresRefresh(true);
                isFinishLoadingAwalData = false;
                errorMessage.setVisibility(View.GONE);
                if (adapter.getItemCount() == 0) {
                    loading.setVisibility(View.VISIBLE);
                }

            } else {
                if (TAG.equals(TAG_MORE)) {
                    isLoadingMoreData = true;
                    isFinishMoreData = false;
                    showProgresMore(true);
                }
            }
        }
    }

    public void onRetrofitEnd(String TAG) {
        if (TAG.equals(TAG_DELETE)) {
            stopProgress(TAG_DELETE);
        } else {
            ProgresRefresh(false);
            if (TAG.equals(TAG_AWAL)) {
                loading.setVisibility(View.GONE);
            }
        }
    }

    public void onRetrofitSuccessResponse(String TAG, Response<FeedbackCustomerListResponse> response) {
        if (TAG.equals(TAG_DELETE)) {
            ResponeDelete(response);

        } else {

            if (TAG.equals(TAG_AWAL)) {
                errorMessage.setVisibility(View.GONE);
                DrawDataAllData(TAG_BOTTOM, TAG, response);
                isFinishLoadingAwalData = true;
            }
            if (TAG.equals(TAG_MORE)) {
                DrawDataAllData(TAG_BOTTOM, TAG, response);
                isLoadingMoreData = false;
            }
            if (TAG.equals(TAG_NEW)) {
                DrawDataAllData(TAG_TOP, TAG, response);
            }

            isRefresh = false;
            showProgresMore(false);
        }
    }


    void onRetrofitErrorResponse(String TAG, int statusCode) {
        if (TAG.equals(TAG_DELETE)) {
            Toast.makeText(activity, "Error hapus history...", Toast.LENGTH_LONG).show();
        } else {
            if (TAG.equals(TAG_AWAL)) {
                isFinishLoadingAwalData = false;
                if (adapter.getItemCount() == 0) {
                    errorMessage.setVisibility(View.VISIBLE);
                } else {
                    errorMessage.setVisibility(View.GONE);
                }
            }
            if (TAG.equals(TAG_MORE)) {
                isFinishMoreData = false;
                isLoadingMoreData = false;
                showProgresMore(false);
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        butterknife.unbind();

    }

    @Override
    public void onActionClick(View v, int position) {
        int viewId = v.getId();
       /* if (viewId == R.id.btn_action) {
            OpenAtion(v, position);
        }*/
    }

    @Override
    public void onRootClick(View v, int position) {
/*

            Intent intent = new Intent(activity, FeedbackCustomerDetailActivity.class);
            intent.putExtra(Sample.FEEDBACK_CUSTOMER_ID, adapter.data.get(position).getId());
            startActivity(intent);
*/

    }
/*
    public void OpenAtion(View v, final int position) {

        final String id_history = adapter.data.get(position).id_history

        PopupMenu popup = new PopupMenu(activity, v, Gravity.RIGHT);
        popup.getMenuInflater().inflate(R.menu.action_manage, popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                int which = item.getItemId();
                if (which == R.id.action_edit) {
                    Intent myIntent = new Intent(getActivity(), actionEditActivity.class);
                    activity.startActivityForResult(myIntent, 1);
                }
                if (which == R.id.action_delete) {
                    new AlertDialog.Builder(getActivity())
                            .setIcon(
                                    new IconDrawable(getActivity(), MaterialCommunityIcons.mdi_alert_octagon)
                                            .colorRes(R.color.primary)
                                            .actionBarSize())
                            .setTitle("Hapus FeedbackCustomer")
                            .setMessage("Apa anda yakin akan menghapus history ini?")
                            .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    position_delete = position;
                                    queue = customRetrofit.Rest(Request.Method.GET, ApiHelper.getDeleteFeedbackCustomerLink(getActivity(), idgambar), null, TAG_DELETE);
                                }
                            })
                            .setNegativeButton("Tidak", null)
                            .show();
                }
                return true;
            }
        });

        // Force icons to show
        try {
            Field mFieldPopup = popup.getClass().getDeclaredField("mPopup");
            mFieldPopup.setAccessible(true);

            MenuPopupHelper mPopup = (MenuPopupHelper) mFieldPopup.get(popup);
            mPopup.setForceShowIcon(true);

        } catch (Exception e) {
            Log.w("TAG", "error forcing menu icons to show", e);
            return;
        }

        popup.show();
    }*/



}
