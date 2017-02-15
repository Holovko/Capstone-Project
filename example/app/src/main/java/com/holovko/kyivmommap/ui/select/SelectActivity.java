package com.holovko.kyivmommap.ui.select;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.holovko.kyivmommap.Injection;
import com.holovko.kyivmommap.R;
import com.holovko.kyivmommap.data.Constant;
import com.holovko.kyivmommap.ui.BaseActivity;
import com.holovko.kyivmommap.ui.add_new.AddPlaceActivity;
import com.holovko.kyivmommap.ui.favourite.FavouriteActivity;
import com.holovko.kyivmommap.ui.map.MapActivity;
import com.holovko.kyivmommap.ui.map.MapsFragment;
import com.holovko.kyivmommap.utils.Utils;

public class SelectActivity extends BaseActivity implements SelectFragment.OnSelectFragmentListener {

    public static final String BUNDLE_RUBRIC_TYPE = "bundle_rubric_type";
    private SelectPresenter mPresenter;
    private int mRubricType;
    private boolean mIsTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mIsTwoPane = (findViewById(R.id.fl_headlines) != null);
        mRubricType = (savedInstanceState != null) ? savedInstanceState.getInt(BUNDLE_RUBRIC_TYPE) : Constant.RUBRIC_PARKS;

        MapsFragment mapsFragment = (mIsTwoPane) ?
                (getSupportFragmentManager().findFragmentByTag(MapsFragment.TAG) == null)
                        ? MapsFragment.newInstance()
                        : (MapsFragment) getSupportFragmentManager().findFragmentByTag(MapsFragment.TAG) : null;

        SelectFragment selectFragment = (getSupportFragmentManager().findFragmentByTag(SelectFragment.TAG) == null)
                ? SelectFragment.newInstance((mIsTwoPane) ? 1 : 2)
                : (SelectFragment) getSupportFragmentManager().findFragmentByTag(SelectFragment.TAG);

        mPresenter = new SelectPresenter(
                Injection.provideDataRepository(this),
                Injection.provideSharedPreferences(this),
                selectFragment,
                mapsFragment,
                mRubricType
        );

        if (savedInstanceState == null) {
            if (mIsTwoPane) {
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fl_headlines, selectFragment, SelectFragment.TAG)
                        .commit();
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fl_details, mapsFragment, MapsFragment.TAG)
                        .commit();
            } else {
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fl_details, selectFragment, SelectFragment.TAG)
                        .commit();
            }
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SelectActivity.this, AddPlaceActivity.class));
                overridePendingTransition(R.anim.push_left_in, 0);
            }
        });
    }

    @Override
    public void onRubricSelect(int rubricType) {
        //TODO in second version - remove this checking
        if (!Utils.isOnline(this)) {
            Toast.makeText(this, getString(R.string.msg_connect_to_internet), Toast.LENGTH_SHORT).show();
            return;
        }

        if (mIsTwoPane) {
            mPresenter.getPlaceByRubricType(rubricType);
        } else {
            Intent intent = new Intent(this, MapActivity.class);
            intent.putExtra(MapActivity.BUNDLE_RUBRIC_TYPE, rubricType);
            startActivity(intent);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_select, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_favourites:
                startActivity(new Intent(this, FavouriteActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(BUNDLE_RUBRIC_TYPE, mRubricType);
    }

}
