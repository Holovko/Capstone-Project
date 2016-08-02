package com.holovko.kyivmommap;

import android.content.Intent;
import android.os.Bundle;
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
import com.holovko.kyivmommap.data.DataProvider;
import com.holovko.kyivmommap.model.Place;
import com.holovko.kyivmommap.presenter.MapPresenter;
import com.holovko.kyivmommap.view.MapView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MapsActivity extends BaseActivity implements MapView, OnMapReadyCallback {

    private GoogleMap mMap;
    private MapPresenter mPresenter;
    private HashMap<Marker, Place> mPlacesOnMap = new HashMap<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        mPresenter = new MapPresenter(this, DataProvider.getInstance());
        //mPresenter = new MapPresenter(this, new MockDataProvider());
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
        mMap.setInfoWindowAdapter(new CustomInfoWindow());
        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                startActivity(new Intent(MapsActivity.this,DetailsActivity.class));
                mPlacesOnMap.get(marker);
            }
        });
        mPresenter.onMapReady();
    }

    public void fillMapMarkerPLace(double latitude, double longitude, Place place) {
        LatLng coordinate = new LatLng(latitude, longitude);
        Marker marker = mMap.addMarker(new MarkerOptions().position(coordinate));
        mPlacesOnMap.put(marker, place);

    }

    public void showAllOnMaps() {
        List<Marker> markers = new ArrayList<>();
        for (Map.Entry<Marker, Place> entry : mPlacesOnMap.entrySet()) {
            markers.add(entry.getKey());
        }
        LatLngBounds bounds = getBoundsOnMap(markers);
        setAnimatesOnMap(bounds);
    }


    /**
     * Show all markers
     *
     * @param latLngBounds
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
     *
     * @param markers
     * @return
     */

    private LatLngBounds getBoundsOnMap(List<Marker> markers) {
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        for (Marker marker : markers) {
            builder.include(marker.getPosition());
        }
        return builder.build();
    }

    private LatLngBounds getBoundsOnMapByCoords(ArrayList<LatLng> latLngs) {
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        for (LatLng latLng : latLngs) {
            //Skip null object
            if (latLng == null) continue;
            builder.include(latLng);
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
            Place place = mPlacesOnMap.get(marker);
            mTvTitle.setText(place.title());
            mTvDescription.setText(place.description());
            mRbStars.setRating(place.rank());
            return v;
        }
    }
}
