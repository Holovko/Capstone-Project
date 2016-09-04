package com.holovko.kyivmommap.ui.select;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.holovko.kyivmommap.Injection;
import com.holovko.kyivmommap.R;
import com.holovko.kyivmommap.data.Constant;
import com.holovko.kyivmommap.model.firebase.Place;
import com.holovko.kyivmommap.service.PhotoService;
import com.holovko.kyivmommap.ui.BaseActivity;
import com.holovko.kyivmommap.ui.add_new.AddPlaceActivity;
import com.holovko.kyivmommap.ui.favourite.FavouriteActivity;
import com.holovko.kyivmommap.utils.CollectionUtils;

import java.util.Map;

public class SelectActivity extends BaseActivity implements SelectFragment.OnSelectFragmentListener, SelectView {

    private SelectPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if(savedInstanceState==null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fl_details, SelectFragment.newInstance())
                    .commit();
        }
        mPresenter = new SelectPresenter(
                Injection.provideDataRepository(this),
                Injection.provideSharedPreferences(this),
                this);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SelectActivity.this,AddPlaceActivity.class));
                overridePendingTransition(R.anim.push_left_in,0);
            }
        });
    }

    @Override
    public void onRubricSelect(@Constant.RubricType int rubricType) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_select, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_favourites:
                startActivity(new Intent(this, FavouriteActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void startServiceToCollectPhoto(Map<String, Place> places) {
        Intent intent = new Intent(SelectActivity.this,PhotoService.class);
        intent.putExtra(PhotoService.BUNDLE_MAP_PLACES, CollectionUtils.mapToBundle(places));
        startService(intent);
    }
}
