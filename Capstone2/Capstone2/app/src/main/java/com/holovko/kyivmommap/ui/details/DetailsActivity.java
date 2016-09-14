package com.holovko.kyivmommap.ui.details;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.holovko.kyivmommap.Injection;
import com.holovko.kyivmommap.R;
import com.holovko.kyivmommap.data.LoaderProvider;
import com.holovko.kyivmommap.model.firebase.Place;
import com.holovko.kyivmommap.ui.BaseActivity;
import com.holovko.kyivmommap.ui.select.SelectActivity;

public class DetailsActivity extends BaseActivity {
    public static final String BUNDLE_KEY = "bundle_key";
    public static final String BUNDLE_PLACE = "bundle_place";
    private String mKey;
    private DetailsPresenter mPresenter;
    private boolean mIsTwoPane;
    private Place mPlace;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_details);
        mIsTwoPane = (findViewById(R.id.fl_headlines) != null);
        mKey = getIntent().getExtras().getString(BUNDLE_KEY);
        mPlace = getIntent().getExtras().getParcelable(BUNDLE_PLACE);

        //Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            ActionBar bar = getSupportActionBar();
            if (bar != null) {
                bar.setDisplayShowHomeEnabled(true);
                bar.setDisplayHomeAsUpEnabled(true);
            }
        }
        //Fragments
        MapsFragment mapsFragment = (mIsTwoPane) ?
                (getSupportFragmentManager().findFragmentByTag(MapsFragment.TAG) == null)
                        ? MapsFragment.newInstance()
                        : (MapsFragment) getSupportFragmentManager().findFragmentByTag(MapsFragment.TAG) : null;

        DetailsFragment detailsFragment = (getSupportFragmentManager().findFragmentByTag(DetailsFragment.TAG) == null)
                ? DetailsFragment.newInstance()
                : (DetailsFragment) getSupportFragmentManager().findFragmentByTag(DetailsFragment.TAG);

        if (savedInstanceState == null) {
            if (mIsTwoPane) {
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fl_headlines, mapsFragment, MapsFragment.TAG)
                        .commit();
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fl_details, detailsFragment, DetailsFragment.TAG)
                        .commit();
            } else {
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fl_details, detailsFragment, DetailsFragment.TAG)
                        .commit();
            }
        }

        //Init presenter
        mPresenter = new DetailsPresenter(
                new LoaderProvider(this),
                getSupportLoaderManager(),
                detailsFragment,
                mKey,
                mPlace,
                mapsFragment, Injection.provideSharedPreferences(this)
        );
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if(!mIsTwoPane) {
                    Intent intent = NavUtils.getParentActivityIntent(this);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    NavUtils.navigateUpTo(this, intent);
                }else{
                    Intent upIntent = new Intent(this, SelectActivity.class);
                    upIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    NavUtils.navigateUpTo(this, upIntent);
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
