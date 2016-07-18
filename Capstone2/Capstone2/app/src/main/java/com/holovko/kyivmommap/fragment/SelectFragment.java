package com.holovko.kyivmommap.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.holovko.kyivmommap.Constant;
import com.holovko.kyivmommap.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SelectFragment extends Fragment {

    @BindView(R.id.rv_select_rubric)
    RecyclerView mRvSelectRubric;
    private OnSelectFragmentListener mListener;

    public SelectFragment() {
        // Required empty public constructor
    }

    public static SelectFragment newInstance() {
        return new SelectFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_select, container, false);
        ButterKnife.bind(this, view);
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnSelectFragmentListener) {
            mListener = (OnSelectFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnSelectFragmentListener {
        void onRubricSelect(@Constant.RubricType int rubricType);
    }
}
