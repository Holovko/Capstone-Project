package com.holovko.kyivmommap.ui.details;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.AppCompatRatingBar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.holovko.kyivmommap.R;
import com.holovko.kyivmommap.utils.PanaramioScrollListener;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailsFragment extends Fragment implements DetailsView, View.OnClickListener {

    public static final String TAG = DetailsFragment.class.getSimpleName();
    @BindView(R.id.iv_main_pic)
    ImageView mIvMainPic;
    @BindView(R.id.tv_description)
    TextView mTvDescription;
    @BindView(R.id.grid_view)
    GridView mGridView;
    @BindView(R.id.fab)
    FloatingActionButton mFab;
    @BindView(R.id.rb_stars)
    AppCompatRatingBar mRbStars;
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    private DetailsPresenter mPresenter;

    public DetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_details, container, false);
        ButterKnife.bind(this, view);
        mPresenter.start();
        return view;
    }

    public static DetailsFragment newInstance() {
        return new DetailsFragment();
    }

    @Override
    public void initView(String pic, String title, String description, float rank, boolean isInFavouriteList) {
        //Place
        Picasso.with(getActivity())
                .load(pic)
                .error(android.R.drawable.ic_dialog_alert)
                .fit()
                .into(mIvMainPic);

        if (getActivity().getActionBar() != null) getActivity().getActionBar().setTitle(title);
        if (mTvTitle!=null) mTvTitle.setText(title);

        mTvDescription.setText(description);
        mRbStars.setRating(rank);
        mGridView.setFocusable(false);
        //Add to favourite
        mFab.setOnClickListener(this);
        setFabWasAddedToFavourite(isInFavouriteList);
        //For photos
        ViewCompat.setNestedScrollingEnabled(mGridView, true);
    }


    @Override
    public void showSuccessAddedToFavourite() {
        Toast.makeText(getActivity(), R.string.msg_place_added_to_favourite, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showSuccessDeletedFromFavourite() {
        Toast.makeText(getActivity(), R.string.msg_place_was_removed, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View view) {
        mPresenter.initToFavourite();
    }

    public void showPhotos(List<String> urls) {
        mGridView.setAdapter(new PhotoGridViewAdapter(getActivity(), urls));
        mGridView.setOnScrollListener(new PanaramioScrollListener(getActivity()));
    }

    public void setFabWasAddedToFavourite(boolean isInFavouriteList) {
        int color = isInFavouriteList ? R.color.colorAccent : R.color.colorSecondaryDark;
        mFab.setBackgroundTintList(ContextCompat.getColorStateList(getActivity(), color));
    }

    @Override
    public void setPresenter(DetailsPresenter presenter) {
        mPresenter = presenter;
    }

}
