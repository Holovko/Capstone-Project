package com.holovko.kyivmommap.ui.map;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.util.Pair;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.AppCompatRatingBar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.holovko.kyivmommap.Injection;
import com.holovko.kyivmommap.R;
import com.holovko.kyivmommap.data.Constant;
import com.holovko.kyivmommap.model.firebase.Place;
import com.holovko.kyivmommap.ui.BaseActivity;
import com.holovko.kyivmommap.ui.details.DetailsActivity;
import com.holovko.kyivmommap.ui.select.SelectPresenter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MapActivityTemp extends BaseActivity implements MapView, OnMapReadyCallback {

    public static final String BUNDLE_RUBRIC_TYPE = "bundle_rubric_type";
    private GoogleMap mMap;
    private MapPresenter mPresenter;
    private HashMap<Marker, Pair<String, Place>> mPlacesOnMap = new HashMap<>();
    private int mRubricType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        if (savedInstanceState == null) {
            mRubricType = getIntent().getExtras().getInt(BUNDLE_RUBRIC_TYPE);
        } else {
            mRubricType = savedInstanceState.getInt(BUNDLE_RUBRIC_TYPE);
        }
        mPresenter = new MapPresenter(this,
                Injection.provideDataRepository(this),
                mRubricType);
        initView();
    }

    @Override
    public void initView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(Constant.KYIV_LATITUDE, Constant.KYIV_LONGTITUDE), 14.0f));
        mMap.setInfoWindowAdapter(new CustomInfoWindow());
        mMap.setOnInfoWindowClickListener(getOnInfoWindowClickListener());
        mPresenter.onMapReady();
    }

    @NonNull
    private GoogleMap.OnInfoWindowClickListener getOnInfoWindowClickListener() {
        return new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                Pair<String, Place> pair = mPlacesOnMap.get(marker);
                String key = pair.first;
                Place place = pair.second;
                Intent intent = new Intent(MapActivityTemp.this, DetailsActivity.class);
                intent.putExtra(DetailsActivity.BUNDLE_KEY, key);
                intent.putExtra(DetailsActivity.BUNDLE_PLACE, place);
                startActivity(intent);
            }
        };
    }

    public void fillMapMarkerPLace(String key, Place place, double latitude, double longitude) {
        LatLng coordinate = new LatLng(latitude, longitude);
        Marker marker = mMap.addMarker(new MarkerOptions().position(coordinate));
        Pair<String, Place> placeWithKey = Pair.create(key, place);
        mPlacesOnMap.put(marker, placeWithKey);

    }

    @Override
    public void setPresenter(SelectPresenter presenter) {

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(BUNDLE_RUBRIC_TYPE, mRubricType);
    }

    public void showAllOnMaps() {
        List<Marker> markers = new ArrayList<>();
        for (Map.Entry<Marker, Pair<String, Place>> entry : mPlacesOnMap.entrySet()) {
            markers.add(entry.getKey());
        }
        if(markers.size()>0) {
            LatLngBounds bounds = getBoundsOnMap(markers);
            setAnimatesOnMap(bounds);
        }
    }

    /**
     * Show all markers
     *
     * @param latLngBounds bounds on map
     */
    private void setAnimatesOnMap(LatLngBounds latLngBounds) {
        int width = getResources().getDisplayMetrics().widthPixels;
        int height = getResources().getDisplayMetrics().heightPixels;
        int padding = (int) (width * 0.12); // offset from edges of the map 12% of screen

        CameraUpdate cu = CameraUpdateFactory
                .newLatLngBounds(latLngBounds, width, height, padding);
        mMap.moveCamera(cu);
        mMap.animateCamera(cu);
    }

    /**
     * Return bounds to fit all markers
     */

    private LatLngBounds getBoundsOnMap(List<Marker> markers) {
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        for (Marker marker : markers) {
            builder.include(marker.getPosition());
        }
        return builder.build();
    }

    public class CustomInfoWindow implements GoogleMap.InfoWindowAdapter {

        @BindView(R.id.tv_title)
        TextView mTvTitle;
        @BindView(R.id.tv_description)
        TextView mTvDescription;
        @BindView(R.id.rb_stars)
        AppCompatRatingBar mRbStars;

        @Override
        public View getInfoWindow(Marker marker) {
            return null;
        }

        @Override
        public View getInfoContents(Marker marker) {
            View v = getLayoutInflater().inflate(R.layout.item_info_window, null);
            ButterKnife.bind(this, v);
            Place place = mPlacesOnMap.get(marker).second;
            mTvTitle.setText(place.title());
            mTvDescription.setText(place.description());
            mRbStars.setRating(place.rank());
            return v;
        }
    }
}
