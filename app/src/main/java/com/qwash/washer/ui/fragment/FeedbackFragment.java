package com.qwash.washer.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.qwash.washer.R;
import com.qwash.washer.adapter.FeedbackAdapter;
import com.qwash.washer.adapter.InProgressAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by binderbyte on 10/01/17.
 */
public class FeedbackFragment extends Fragment {

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    public FeedbackFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_feedback, container, false);
        ButterKnife.bind(this, view);

        FeedbackAdapter adapter = new FeedbackAdapter(getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);

        //Layout manager for Recycler view
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        return view;
    }

}
