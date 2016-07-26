package com.holovko.kyivmommap;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.holovko.kyivmommap.model.Place;

import java.util.ArrayList;

public class MapsActivity extends BaseActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Place testPlace1 = new Place(true,"My Title 1","Description 1",50.389419, 30.499712,5);
    private Place testPlace2 = new Place(true,"My Title 2","Description 1",50.365076, 30.466265,4);
    private Place testPlace3 = new Place(true,"My Title 3","Description 1",50.343102, 30.549816,3);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null) {
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
        // Add a marker in Sydney and move the camera

        LatLng sydney = new LatLng(50.389419, 30.499712);
        Marker marker = mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

        ArrayList<Marker> markers = new ArrayList<>();
        markers.add(marker);
        LatLngBounds bounds = getBoundsOnMap(markers);
        setAnimatesOnMap(bounds);
    }


    /**
     * Show all markers
     * @param latLngBounds
     */
    private void setAnimatesOnMap(LatLngBounds latLngBounds) {
        int padding = 50; // offset from edges of the map in pixels
        CameraUpdate cu = CameraUpdateFactory
                .newLatLngBounds(latLngBounds,padding,0,0);
                //.newLatLngBounds(latLngBounds, padding);
        mMap.moveCamera(cu);
        mMap.animateCamera(cu);
    }

    /**
     * Return bounds to fit all markers
     * @param markers
     * @return
     */

    private  LatLngBounds getBoundsOnMap(ArrayList<Marker> markers) {
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        for (Marker marker : markers) {
            builder.include(marker.getPosition());
        }
        return builder.build();
    }

    private  LatLngBounds getBoundsOnMapByCoords(ArrayList<LatLng> latLngs) {
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        for (LatLng latLng : latLngs) {
            //Skip null object
            if(latLng==null)continue;
            builder.include(latLng);
        }
        return builder.build();
    }


    public class CustomInfoWindow implements GoogleMap.InfoWindowAdapter {

        @Override
        public View getInfoWindow(Marker marker) {
            return null;
        }

        @Override
        public View getInfoContents(Marker marker) {
            View v = getLayoutInflater().inflate(R.layout.item_info_window,null);
            return v;
        }
    }
}
