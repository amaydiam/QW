package com.qwash.washer.ui.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
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
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.qwash.washer.R;
import com.qwash.washer.Sample;
import com.qwash.washer.adapter.HistoryAdapter;
import com.qwash.washer.api.ApiUtils;
import com.qwash.washer.api.client.HistoryService;
import com.qwash.washer.api.model.HistoryListResponse;
import com.qwash.washer.model.History;
import com.qwash.washer.ui.activity.HistoryActivity;
import com.qwash.washer.ui.activity.HistoryDetailActivity;
import com.qwash.washer.utils.TextUtils;
import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.fonts.MaterialCommunityIcons;
import com.joanzapata.iconify.widget.IconButton;
import com.mugen.Mugen;
import com.mugen.MugenCallbacks;

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

import static android.content.Context.INPUT_METHOD_SERVICE;


public class HistoryListFragment extends Fragment implements HistoryAdapter.OnHistoryItemClickListener,
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

    private ArrayList<History> data = new ArrayList<>();
    private GridLayoutManager mLayoutManager;
    private String keyword = null;
    private HistoryService mService;

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
    public HistoryAdapter adapter;
    private boolean isFinishLoadingAwalData = true;
    private boolean isLoadingMoreData = false;
    private boolean isFinishMoreData = false;
    private int page = 1;
    private boolean isRefresh = false;
    //  private String session_key;

    private int mPreviousVisibleItem;

    public HistoryListFragment() {
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof HistoryActivity) {
            // activity = (HistoryActivity) context;
        }
        activity = getActivity();
    }


    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static HistoryListFragment newInstance() {
        return new HistoryListFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_list, container, false);
        butterknife = ButterKnife.bind(this, rootView);

        mService = ApiUtils.getHistory();

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

        hideSoftKeyboard();

        //inisial adapter
        adapter = new HistoryAdapter(activity, data, isTablet);
        adapter.setOnHistoryItemClickListener(this);

        //recyclerView
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);

        //inisial layout manager
       /* int grid_column_count = getResources().getInteger(R.integer.grid_column_count);
        StaggeredGridLayoutManager mLayoutManager = new StaggeredGridLayoutManager(grid_column_count, StaggeredGridLayoutManager.VERTICAL);
*/

        //   final LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        //  mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        mLayoutManager = new GridLayoutManager(activity, getNumberOfColumns());

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


        noData.setText(Html.fromHtml("<center><h1>{mdi-calendar}</h1></br> Tidak ada donasi ...</center>"));
        showNoData(false);

          /* =========================================================================================
        ==================== Get Data List (HistoryListResponse) ================================================
        ============================================================================================*/
        if (savedInstanceState == null || !savedInstanceState.containsKey(Sample.HISTORY_ID)) {
            getDataFromServer(TAG_AWAL);
        } else {
            data = savedInstanceState.getParcelableArrayList(Sample.HISTORY_ID);
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
        /* =========================================================================================
        ==================== End Get Data List (HistoryListResponse) ============================================
        ============================================================================================*/

        return rootView;
    }

    private void Search() {
        String val_search = search.getText().toString().trim();
        if (!TextUtils.isNullOrEmpty(val_search)) {
            search.setText("");
          /*  Intent intent = new Intent(activity, CariHistoryActivity.class);
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
        mService.getListHistory(page, keyword).enqueue(new Callback<HistoryListResponse>() {
            @Override
            public void onResponse(Call<HistoryListResponse> call, Response<HistoryListResponse> response) {

                if (response.isSuccessful()) {
                    onRetrofitSuccessResponse(TAG, response);
                } else {
                    int statusCode = response.code();
                    onRetrofitErrorResponse(TAG, statusCode);
                }
                onRetrofitEnd(TAG);
            }

            @Override
            public void onFailure(Call<HistoryListResponse> call, Throwable t) {
                onRetrofitErrorResponse(TAG, 0);
                onRetrofitEnd(TAG);
            }

        });
    }

    protected void DrawDataAllData(String position, String tag, Response<HistoryListResponse> response) {


        if (isRefresh) {
            adapter.delete_all();
        }

        Boolean isSuccess = Boolean.parseBoolean(response.body().getIsSuccess());
        String message = response.body().getMessage();
        if (isSuccess) {
            List<History> h = response.body().getHistory();
            int jumlah_list_data = h.size();
            if (jumlah_list_data > 0) {
                for (int i = 0; i < jumlah_list_data; i++) {
                    History obj = h.get(i);
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

            if (isTablet && page == 1 && adapter.data.size() > 0) {
                adapter.setSelected(0);
                if (activity instanceof HistoryActivity) {
                    ((HistoryActivity) getActivity()).loadDetailHistoryFragmentWith(adapter.data.get(0).id_history);
                }
            }

            page = page + 1;
        } else {
            Toast.makeText(activity, message, Toast.LENGTH_LONG).show();
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


    private void ResponeDelete(Response<HistoryListResponse> response) {

        Boolean isSuccess = Boolean.parseBoolean(response.body().getIsSuccess());
        String message = response.body().getMessage();
        if (isSuccess) {
            adapter.remove(position_delete);
            checkData();
        } else {
            Toast.makeText(activity, message, Toast.LENGTH_LONG).show();
        }
    }

    private void setDataObject(String position, History obj) {
        //parse object
        String id_history = obj.getId_history();
        String history_time = obj.getHistory_time();
        String address = obj.getAddress();
        String vehicle_model = obj.getVehicle_model();
        //set map object
        AddAndSetMapData(
                position,
                id_history,
                history_time,
                address,
                vehicle_model
        );

    }

    private void AddAndSetMapData(
            String position,
            String id_history,
            String history_time,
            String address,
            String vehicle_model) {

        History history = new History(
                id_history,
                history_time,
                address,
                vehicle_model);


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
            TAG = "Delete HistoryListResponse";
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

    public void onRetrofitSuccessResponse(String TAG, Response<HistoryListResponse> response) {
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

        if (isTablet) {
            adapter.setSelected(position);

            if (activity instanceof HistoryActivity) {
                ((HistoryActivity) getActivity()).loadDetailHistoryFragmentWith(adapter.data.get(0).getId_history());
            }
        } else {
            Intent intent = new Intent(activity, HistoryDetailActivity.class);
            intent.putExtra(Sample.HISTORY_ID, adapter.data.get(position).getId_history());
            startActivity(intent);
        }

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
                            .setTitle("Hapus History")
                            .setMessage("Apa anda yakin akan menghapus history ini?")
                            .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    position_delete = position;
                                    queue = customRetrofit.Rest(Request.Method.GET, ApiHelper.getDeleteHistoryLink(getActivity(), idgambar), null, TAG_DELETE);
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


    public int getNumberOfColumns() {
        // Get screen width
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        float widthPx = displayMetrics.widthPixels;
        if (isTablet) {
            widthPx = widthPx / 3;
        }
        // Calculate desired width

        float desiredPx = getResources().getDimensionPixelSize(R.dimen.list_card_width);
        int columns = Math.round(widthPx / desiredPx);
        return columns > 1 ? columns : 1;
    }

    public void hideSoftKeyboard() {
        if (getActivity().getCurrentFocus() != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
        }
    }

}
