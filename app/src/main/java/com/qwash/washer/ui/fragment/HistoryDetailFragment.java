package com.qwash.washer.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.qwash.washer.R;
import com.qwash.washer.Sample;
import com.qwash.washer.adapter.AdditionalOrderAdapter;
import com.qwash.washer.adapter.HistoryAdapter;
import com.qwash.washer.api.ApiUtils;
import com.qwash.washer.api.client.HistoryService;
import com.qwash.washer.api.model.HistoryDetailResponse;
import com.qwash.washer.model.AdditionalOrder;
import com.qwash.washer.model.History;
import com.qwash.washer.ui.widget.RobotoBoldTextView;
import com.qwash.washer.ui.widget.RobotoLightTextView;
import com.qwash.washer.ui.widget.RobotoRegularTextView;
import com.qwash.washer.utils.TextUtils;
import com.qwash.washer.utils.Utils;
import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.fonts.EntypoIcons;
import com.joanzapata.iconify.fonts.MaterialCommunityIcons;
import com.joanzapata.iconify.fonts.MaterialIcons;
import com.joanzapata.iconify.widget.IconTextView;

import org.json.JSONObject;

import java.util.ArrayList;

import agency.tango.android.avatarview.loader.PicassoLoader;
import agency.tango.android.avatarview.views.AvatarView;
import butterknife.BindBool;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HistoryDetailFragment extends Fragment implements AdditionalOrderAdapter.OnAdditionalOrderItemClickListener {

    @BindBool(R.bool.is_tablet)
    boolean isTablet;

    // Toolbar
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;

    // Main views
    @BindView(R.id.progress_circle)
    View progressCircle;
    @BindView(R.id.error_message)
    View errorMessage;
    @BindView(R.id.text_error)
    RobotoLightTextView textError;
    @BindView(R.id.try_again)
    RobotoBoldTextView tryAgain;
    @BindView(R.id.content_holder)
    NestedScrollView contentHolder;
    @BindView(R.id.fab_action)
    FloatingActionButton fabAction;

    // Basic info
    @BindView(R.id.image_washer)
    AvatarView imageWasher;
    @BindView(R.id.whaser_name)
    RobotoBoldTextView whaserName;
    @BindView(R.id.rating_whaser)
    IconTextView ratingWhaser;
    @BindView(R.id.book_date)
    IconTextView bookDate;
    @BindView(R.id.book_time)
    IconTextView bookTime;
    @BindView(R.id.bill_total)
    RobotoBoldTextView billTotal;

    //additional
    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;

    //
    @BindView(R.id.img_address)
    ImageView imgAddress;
    @BindView(R.id.address)
    RobotoRegularTextView address;
    @BindView(R.id.img_vehicle_model)
    ImageView imgVehicleModel;
    @BindView(R.id.vehicle_model)
    RobotoRegularTextView vehicleModel;


    private HistoryService mService;


    private Unbinder unbinder;
    private String id;
    private History history;
    private PicassoLoader imageLoader;
    private static final String TAG_DETAIL = "TAG_DETAIL";

    private ArrayList<AdditionalOrder> data = new ArrayList<>();
    private AdditionalOrderAdapter adapter;

    // Fragment lifecycle
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_history_detail, container, false);
        unbinder = ButterKnife.bind(this, v);
        mService = ApiUtils.getHistory();
        imageLoader = new PicassoLoader();


        // Setup toolbar
        toolbar.setTitle("");
        if (!isTablet) {
            toolbar.setNavigationIcon(
                    new IconDrawable(getActivity(), MaterialIcons.md_arrow_back)
                            .colorRes(R.color.black_424242)
                            .actionBarSize());
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    getActivity().finish();
                }
            });
        }

        //setup fab
        fabAction.setImageDrawable(
                new IconDrawable(getActivity(), MaterialIcons.md_attach_money)
                        .colorRes(R.color.white)
                        .actionBarSize());

        adapter = new AdditionalOrderAdapter(getActivity(), data);
        adapter.setOnAdditionalOrderItemClickListener(this);
        //recyclerView
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);

        // set layout manager
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));

        // set adapter
        recyclerView.setAdapter(adapter);

        // Download history details if new instance, else restore from saved instance
        if (savedInstanceState == null || !(savedInstanceState.containsKey(Sample.HISTORY_ID)
                && savedInstanceState.containsKey(Sample.HISTORY_OBJECT))) {
            id = getArguments().getString(Sample.HISTORY_ID);
            if (TextUtils.isNullOrEmpty(id)) {
                progressCircle.setVisibility(View.GONE);
            } else {
                downloadLokasiDetails(id);
            }
        } else {
            id = savedInstanceState.getString(Sample.HISTORY_ID);
            history = savedInstanceState.getParcelable(Sample.HISTORY_OBJECT);
            onDownloadSuccessful();
        }

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        // Send screen id_user_kru to analytics
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (history != null && id != null) {
            outState.putString(Sample.HISTORY_ID, id);
            outState.putParcelable(Sample.HISTORY_OBJECT, history);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();

    }


    // JSON parsing and display
    private void downloadLokasiDetails(String id) {
        onRetrofitStart(TAG_DETAIL);
        mService.getDetailHistory(id).enqueue(new Callback<HistoryDetailResponse>() {
            @Override
            public void onResponse(Call<HistoryDetailResponse> call, Response<HistoryDetailResponse> response) {

                if (response.isSuccessful()) {
                    onRetrofitSuccessResponse(TAG_DETAIL, response);
                } else {
                    int statusCode = response.code();
                    onRetrofitErrorResponse(TAG_DETAIL, statusCode);
                }
                onRetrofitEnd(TAG_DETAIL);
            }

            @Override
            public void onFailure(Call<HistoryDetailResponse> call, Throwable t) {
                onRetrofitErrorResponse(TAG_DETAIL, 0);
                onRetrofitEnd(TAG_DETAIL);
            }

        });

    }

    private void onDownloadSuccessful() {

        toolbarTitle.setText(R.string.title_activity_detail_history);
        // Toggle visibility
        progressCircle.setVisibility(View.GONE);
        errorMessage.setVisibility(View.GONE);
        contentHolder.setVisibility(View.VISIBLE);
        fabAction.setVisibility(View.GONE);

        imageLoader.loadImage(imageWasher, "URL", "Fahri");
        billTotal.setText(Utils.Rupiah(50000));

        data.clear();
        AdditionalOrder a1 = new AdditionalOrder(0, "Perfurm");
        data.add(a1);
        adapter.notifyDataSetChanged();
        AdditionalOrder a2 = new AdditionalOrder(0, "Interior Vaccum");
        data.add(a2);
        adapter.notifyDataSetChanged();

        address.setText("Jl. Pamulang");
        vehicleModel.setText("Xenia");
        imgAddress.setImageDrawable(new IconDrawable(getActivity(), EntypoIcons.entypo_location_pin).colorRes(R.color.blue_1E87DA).actionBarSize());
        imgVehicleModel.setImageDrawable(new IconDrawable(getActivity(), MaterialCommunityIcons.mdi_car).colorRes(R.color.blue_1E87DA).actionBarSize());

    }

    private void onDownloadFailed(boolean notAvailable, String message) {
        errorMessage.setVisibility(View.VISIBLE);
        progressCircle.setVisibility(View.GONE);
        contentHolder.setVisibility(View.GONE);
        if (notAvailable) {
            toolbar.setTitle("Not Available");
            textError.setText(message);
            tryAgain.setVisibility(View.GONE);
            fabAction.setVisibility(View.GONE);
        }
    }

    @OnClick(R.id.try_again)
    public void onTryAgainClicked() {
        downloadLokasiDetails(id);
    }


    public void onRetrofitStart(String TAG) {
        toolbarTitle.setText(R.string.loading);
        errorMessage.setVisibility(View.GONE);
        progressCircle.setVisibility(View.VISIBLE);
    }

    public void onRetrofitEnd(String TAG) {

    }

    public void onRetrofitSuccessResponse(String TAG, Response<HistoryDetailResponse> response) {

        String isSuccess = response.body().getIsSuccess();
        String message = response.body().getMessage();

        if (Boolean.parseBoolean(isSuccess)) {

            History jsDetail = response.body().getHistory();
            String id_history = jsDetail.getId_history();
            String nama_history = jsDetail.getHistory_time();
            String alamat_history = jsDetail.getAddress();
            String no_identitas_history = jsDetail.getVehicle_model();

            history = new History(id_history, nama_history, alamat_history, no_identitas_history);

            onDownloadSuccessful();
        } else
            onDownloadFailed(true, message);

    }

    public void onRetrofitErrorResponse(String TAG, int response) {
        onDownloadFailed(false, "");
    }


    @Override
    public void onRootClick(View v, int position) {

    }
}