package com.qwash.washer.ui.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.qwash.washer.R;
import com.qwash.washer.adapter.InProgressAdapter;
import com.qwash.washer.api.client.InProgressInterface;
import com.qwash.washer.api.model.Detail;
import com.qwash.washer.api.model.InProgressResponse;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * A simple {@link Fragment} subclass.
 */
public class InProgressFragment extends Fragment {

    InProgressAdapter inProgressAdapter;
    Detail inProgressResponses;
    LinearLayoutManager mLayoutManager;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    String id;
    public static final String BASE_URL = "https://api.myjson.com/bins/";

    public InProgressFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_in_progress, container, false);
        ButterKnife.bind(this, view);

        getDataInProgress(id);

        return view;
    }

    private void getDataInProgress(String id) {
        OkHttpClient okClient = new OkHttpClient.Builder()
                .addInterceptor(
                        new Interceptor() {
                            @Override
                            public okhttp3.Response intercept(Chain chain) throws IOException {
                                Request request = chain.request().newBuilder()
                                        .addHeader("Accept", "Application/JSON").build();
                                return chain.proceed(request);
                            }
                        }).build();


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        InProgressInterface service = retrofit.create(InProgressInterface.class);

        Call<InProgressResponse> call = service.getInput(id);

        progressBar.setVisibility(View.VISIBLE);

        call.enqueue(new Callback<InProgressResponse>() {
            @Override
            public void onResponse(Call<InProgressResponse> call, retrofit2.Response<InProgressResponse> response) {
                Log.d("MainActivity", "Status Code = " + response.code());

                if (response.isSuccessful()) {
                    // request successful (status code 200, 201)
                    progressBar.setVisibility(View.GONE);

                    inProgressResponses = new Detail();

                    InProgressResponse result = response.body();

                    inProgressResponses = result.getDetail();

                    // This is where data loads
                    inProgressAdapter = new InProgressAdapter((List<InProgressResponse>) inProgressResponses);

                    //attach to recyclerview
                    mLayoutManager = new LinearLayoutManager(getActivity());
                    recyclerView.setLayoutManager(mLayoutManager);
                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                    inProgressAdapter.notifyDataSetChanged();
                    recyclerView.setAdapter(inProgressAdapter);
                }
            }

            @Override
            public void onFailure(Call<InProgressResponse> call, Throwable t) {
                Toast.makeText(getActivity(), "Aktifkan koneksi anda !", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
            }
        });

    }


}
