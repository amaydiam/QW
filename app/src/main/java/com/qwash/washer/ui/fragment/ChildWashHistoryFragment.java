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
import com.qwash.washer.adapter.WashHistoryAdapter;
import com.qwash.washer.api.ApiUtils;
import com.qwash.washer.api.client.wash_history.WashHistoryService;
import com.qwash.washer.api.model.wash_history.WashHistoryListResponse;
import com.qwash.washer.model.wash_history.WashHistory;
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
public class ChildWashHistoryFragment extends Fragment implements WashHistoryAdapter.OnWashHistoryItemClickListener,
        SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG_MORE = "TAG_MORE";
    private static final String TAG_AWAL = "TAG_AWAL";
    private static final String TAG_NEW = "TAG_NEW";
    private static final String TAG_DELETE = "TAG_DELETE";

    private static final String TAG_TOP = "top";
    private static final String TAG_BOTTOM = "bottom";
    public WashHistoryAdapter adapter;
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
    private ArrayList<WashHistory> data = new ArrayList<>();
    private GridLayoutManager mLayoutManager;
    private String keyword = null;
    private int type_wash_history;
    private Integer position_delete;
    private ProgressDialog dialogProgress;
    private FragmentActivity activity;
    private Unbinder butterknife;
    private boolean isFinishLoadingAwalData = true;
    private boolean isLoadingMoreData = false;
    private boolean isFinishMoreData = false;
    private int page = 1;
    private boolean isRefresh = false;
    private int mPreviousVisibleItem;
    public ChildWashHistoryFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     *
     * @param type_wash_history
     */
    public static ChildWashHistoryFragment newInstance(int type_wash_history) {
        ChildWashHistoryFragment fragment = new ChildWashHistoryFragment();
        Bundle args = new Bundle();
        args.putInt(Sample.TYPE_WASH_HISTORY, type_wash_history);
        fragment.setArguments(args);
        return fragment;
    }
    //  private String session_key;

    @OnClick(R.id.scroll_up)
    void ScrollUp() {
        recyclerView.smoothScrollToPosition(0);
    }

    @OnClick(R.id.btn_search)
    void btn_search() {
        Search();
    }

    @OnClick(R.id.try_again)
    void TryAgain() {
        RefreshData();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = getActivity();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            type_wash_history = getArguments().getInt(Sample.TYPE_WASH_HISTORY);
        }
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
        adapter = new WashHistoryAdapter(activity, type_wash_history, data);
        adapter.setOnWashHistoryItemClickListener(this);

        //recyclerView
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);

        mLayoutManager = new GridLayoutManager(activity, Utils.getNumberOfColumns(getActivity(), isTablet));

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


        noData.setText(Html.fromHtml("<center><h1>{mdi-calendar}</h1></br> Tidak ada history ...</center>"));
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
          /*  Intent intent = new Intent(activity, CariWashHistoryActivity.class);
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

        WashHistoryService mService = ApiUtils.WashHistory(getActivity());
        mService.getListWashHistory(type_wash_history, Prefs.getUserId(getActivity()), page, Sample.LIMIT_DATA).enqueue(new Callback<WashHistoryListResponse>() {
            @Override
            public void onResponse(Call<WashHistoryListResponse> call, Response<WashHistoryListResponse> response) {

                if (response.isSuccessful()) {
                    onRetrofitSuccessResponse(TAG, response);
                } else {
                    int statusCode = response.code();
                    onRetrofitErrorResponse(TAG, statusCode);
                }
                onRetrofitEnd(TAG);
            }

            @Override
            public void onFailure(Call<WashHistoryListResponse> call, Throwable t) {
                onRetrofitErrorResponse(TAG, 0);
                onRetrofitEnd(TAG);
            }

        });
    }

    protected void DrawDataAllData(String position, String tag, Response<WashHistoryListResponse> response) {


        if (isRefresh) {
            adapter.delete_all();
        }

        Boolean isSuccess = response.body().getStatus();
        if (isSuccess) {
            List<WashHistory> h = response.body().getData();
            int jumlah_list_data = h.size();
            if (jumlah_list_data > 0) {
                for (int i = 0; i < jumlah_list_data; i++) {
                    WashHistory obj = h.get(i);
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


    private void ResponeDelete(Response<WashHistoryListResponse> response) {

        Boolean isSuccess = response.body().getStatus();
        String message = response.body().getMessage();
        if (isSuccess) {
            adapter.remove(position_delete);
            checkData();
        }
        Toast.makeText(activity, message, Toast.LENGTH_LONG).show();
    }

    private void setDataObject(String position, WashHistory washHistory) {
        String ordersId = washHistory.getOrdersId();
        String userIdFk = washHistory.getUserIdFk();
        String washerIdFk = washHistory.getWasherIdFk();
        String vCustomersIdFk = washHistory.getVCustomersIdFk();
        String createAt = washHistory.getCreateAt();
        String pickDate = washHistory.getPickDate();
        String pickTime = washHistory.getPickTime();
        String lat = washHistory.getLat();
        String lng = washHistory.getLng();
        String nameAddress = washHistory.getNameAddress();
        String address = washHistory.getAddress();
        String perfumed = washHistory.getPerfumed();
        String vacuum = washHistory.getVacuum();
        String price = washHistory.getPrice();
        String status = washHistory.getStatus();
        String description = washHistory.getDescription();
        String ordersRef = washHistory.getOrdersRef();
        String vCustomersId = washHistory.getOrdersRef();
        String vId = washHistory.getVId();
        String vBrandId = washHistory.getVBrandId();
        String vModelId = washHistory.getVModelId();
        String vTransId = washHistory.getVTransId();
        String vYearsId = washHistory.getVYearsId();
        String userId = washHistory.getUserId();
        String vIdFk = washHistory.getVIdFk();
        String vBrand = washHistory.getVBrand();

        //parse object

        //set map object
        AddAndSetMapData(
                position, ordersId,
                userIdFk,
                washerIdFk,
                vCustomersIdFk,
                createAt,
                pickDate,
                pickTime,
                lat,
                lng,
                nameAddress,
                address,
                price,
                perfumed,
                vacuum,
                status,
                description,
                ordersRef,
                vCustomersId,
                vId,
                vBrandId,
                vModelId,
                vTransId,
                vYearsId,
                userId,
                vIdFk,
                vBrand
        );

    }

    private void AddAndSetMapData(
            String position,
            String ordersId,
            String userIdFk,
            String washerIdFk,
            String vCustomersIdFk,
            String createAt,
            String pickDate,
            String pickTime,
            String lat,
            String lng,
            String nameAddress,
            String address,
            String price,
            String perfumed,
            String vacuum,
            String status,
            String description,
            String ordersRef,
            String vCustomersId,
            String vId,
            String vBrandId,
            String vModelId,
            String vTransId,
            String vYearsId,
            String userId,
            String vIdFk,
            String vBrand) {

        WashHistory history = new WashHistory(
                ordersId,
                userIdFk,
                washerIdFk,
                vCustomersIdFk,
                createAt,
                pickDate,
                pickTime,
                lat,
                lng,
                nameAddress,
                address,
                price,
                perfumed,
                vacuum,
                status,
                description,
                ordersRef,
                vCustomersId,
                vId,
                vBrandId,
                vModelId,
                vTransId,
                vYearsId,
                userId,
                vIdFk,
                vBrand);


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
            TAG = "Delete WashHistoryListResponse";
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

    public void onRetrofitSuccessResponse(String TAG, Response<WashHistoryListResponse> response) {
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
    public void onRootClick(View v, int position) {
/*

            Intent intent = new Intent(activity, WashHistoryDetailActivity.class);
            intent.putExtra(Sample.FEEDBACK_CUSTOMER_ID, adapter.data.get(position).getId());
            startActivity(intent);
*/

    }

}
