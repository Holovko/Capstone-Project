package com.holovko.kyivmommap.ui.map;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.util.Pair;
import android.support.v7.widget.AppCompatRatingBar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.holovko.kyivmommap.R;
import com.holovko.kyivmommap.data.Constant;
import com.holovko.kyivmommap.model.firebase.Place;
import com.holovko.kyivmommap.ui.details.DetailsActivity;
import com.holovko.kyivmommap.ui.select.SelectPresenter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MapsFragment extends Fragment implements MapView, OnMapReadyCallback {
    public static final String TAG = MapsFragment.class.getSimpleName();
    private GoogleMap mMap;
    private SelectPresenter mPresenter;
    private HashMap<Marker, Pair<String, Place>> mPlacesOnMap = new HashMap<>();

    public MapsFragment() {}

    public static MapsFragment newInstance() {
        return new MapsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_maps, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    public void clearMapData(){
        if(mMap!=null){
            mMap.clear();
        }
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
                Intent intent = new Intent(getActivity(), DetailsActivity.class);
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
        mPresenter = presenter;
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
            View v = getLayoutInflater(null).inflate(R.layout.item_info_window, null);
            ButterKnife.bind(this, v);
            Place place = mPlacesOnMap.get(marker).second;
            mTvTitle.setText(place.title());
            mTvDescription.setText(place.description());
            mRbStars.setRating(place.rank());
            return v;
        }
    }

}
