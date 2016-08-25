package com.holovko.kyivmommap;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.holovko.kyivmommap.data.Constant;
import com.holovko.kyivmommap.fragment.SelectFragment;
import com.holovko.kyivmommap.service.PhotoService;

public class SelectActivity extends AppCompatActivity implements SelectFragment.OnSelectFragmentListener {

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
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SelectActivity.this,PhotoService.class);
                startService(intent);
                //startActivity(new Intent(SelectActivity.this,MapActivity.class));
            }
        });
    }

    @Override
    public void onRubricSelect(@Constant.RubricType int rubricType) {

    }
}
