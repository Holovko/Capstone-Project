package com.holovko.kyivmommap.ui.add_new;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;

import com.holovko.kyivmommap.Injection;
import com.holovko.kyivmommap.R;
import com.holovko.kyivmommap.ui.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddPlaceActivity extends BaseActivity {

    @BindView(R.id.frame_layout)
    FrameLayout mFrameLayout;
    private AddPresenter mPresenter;
    private FloatingActionButton mFab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_place);
        ButterKnife.bind(this);
        AddPlaceActivityFragment fragment = AddPlaceActivityFragment.newInstance();
        if(savedInstanceState ==null){
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, fragment).commit();
        }
        mPresenter = new AddPresenter(Injection.provideDataRepository(this),fragment);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mFab = (FloatingActionButton) findViewById(R.id.fab);
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.initCreatePlace();
            }
        });
    }

    public void setFabVisibility(boolean isVisible){
        mFab.setVisibility(isVisible?View.VISIBLE:View.GONE);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(0,R.anim.push_left_out);
    }
}
