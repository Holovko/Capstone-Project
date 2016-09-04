package com.holovko.kyivmommap.ui.details;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.NavUtils;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatRatingBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.holovko.kyivmommap.Injection;
import com.holovko.kyivmommap.R;
import com.holovko.kyivmommap.data.LoaderProvider;
import com.holovko.kyivmommap.model.firebase.Place;
import com.holovko.kyivmommap.utils.PanaramioScrollListener;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailsActivity extends AppCompatActivity implements DetailsView, View.OnClickListener {

    public static final String BUNDLE_KEY = "bundle_key";
    public static final String BUNDLE_PLACE = "bundle_place";
    @BindView(R.id.iv_main_pic)
    ImageView mIvMainPic;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.toolbar_layout)
    CollapsingToolbarLayout mToolbarLayout;
    @BindView(R.id.app_bar)
    AppBarLayout mAppBar;
    @BindView(R.id.tv_description)
    TextView mTvDescription;
    @BindView(R.id.grid_view)
    GridView mGridView;
    @BindView(R.id.fab)
    FloatingActionButton mFab;
    @BindView(R.id.rb_stars)
    AppCompatRatingBar mRbStars;
    private String mKey;
    private DetailsPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        ButterKnife.bind(this);
        mKey = getIntent().getExtras().getString(BUNDLE_KEY);
        Place place = getIntent().getExtras().getParcelable(BUNDLE_PLACE);
        mPresenter = new DetailsPresenter(
                new LoaderProvider(this),
                getSupportLoaderManager(),
                this,
                mKey,
                place,
                Injection.provideSharedPreferences(this)
        );
    }

    public void initView(String pic, String title, String description, float rank, boolean isInFavouriteList) {
        //Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar bar = getSupportActionBar();
        if (bar != null) {
            bar.setDisplayShowHomeEnabled(true);
            bar.setDisplayHomeAsUpEnabled(true);
        }

        //Place
        Picasso.with(this)
                .load(pic)
                .error(android.R.drawable.ic_dialog_alert)
                .fit()
                .into(mIvMainPic);
        mToolbarLayout.setTitle(title);
        mTvDescription.setText(description);
        mRbStars.setRating(rank);

        //Add to favourite
        mFab.setOnClickListener(this);
        setFabWasAddedToFavourite(isInFavouriteList);
        //For photos
        ViewCompat.setNestedScrollingEnabled(mGridView, true);

    }

    @Override
    public void showSuccessAddedToFavourite() {
        Toast.makeText(this, R.string.msg_place_added_to_favourite, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showSuccessDeletedFromFavourite() {
        Toast.makeText(this, R.string.msg_place_was_removed, Toast.LENGTH_SHORT).show();
    }

    public void setFabWasAddedToFavourite(boolean isInFavouriteList) {
        int color = isInFavouriteList?R.color.colorAccent:R.color.colorSecondaryDark;
        mFab.setBackgroundTintList(ContextCompat.getColorStateList(this, color));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = NavUtils.getParentActivityIntent(this);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                NavUtils.navigateUpTo(this, intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void showPhotos(List<String> urls) {
        mGridView.setAdapter(new PhotoGridViewAdapter(this, urls));
        mGridView.setOnScrollListener(new PanaramioScrollListener(this));
    }

    @Override
    public void onClick(View view) {
        mPresenter.initToFavourite();
    }
}
