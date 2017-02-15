package com.holovko.kyivmommap.ui.map;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.holovko.kyivmommap.Injection;
import com.holovko.kyivmommap.R;
import com.holovko.kyivmommap.ui.BaseActivity;
import com.holovko.kyivmommap.ui.select.SelectPresenter;

import static com.holovko.kyivmommap.R.id.fab;

public class MapActivity extends BaseActivity{

    public static final String BUNDLE_RUBRIC_TYPE = "bundle_rubric_type";
    private SelectPresenter mPresenter;
    private int mRubricType;
    private MapsFragment mFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        mRubricType = (savedInstanceState == null)
                ?getIntent().getExtras().getInt(BUNDLE_RUBRIC_TYPE)
                :savedInstanceState.getInt(BUNDLE_RUBRIC_TYPE);
        mFragment = (getSupportFragmentManager().findFragmentByTag(MapsFragment.TAG)==null)
                ?MapsFragment.newInstance()
                :(MapsFragment) getSupportFragmentManager().findFragmentByTag(MapsFragment.TAG);

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fl_details, mFragment,MapsFragment.TAG)
                    .commit();
        }

        mPresenter = new SelectPresenter(
                Injection.provideDataRepository(this),
                Injection.provideSharedPreferences(this),
                null,mFragment,mRubricType);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }

        FloatingActionButton favButton = (FloatingActionButton) findViewById(fab);
        favButton.setVisibility(View.GONE);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(BUNDLE_RUBRIC_TYPE, mRubricType);
    }
}
