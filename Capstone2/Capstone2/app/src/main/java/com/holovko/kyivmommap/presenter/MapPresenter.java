package com.holovko.kyivmommap.presenter;

import android.util.Log;

import com.holovko.kyivmommap.data.firebase.FireBaseDataSource;
import com.holovko.kyivmommap.model.Place;
import com.holovko.kyivmommap.view.MapView;

import java.util.Map;

/**
 * Presenter for MapView
 * Created by Andrey Holovko on 7/26/16.
 */
public class MapPresenter {
    private Map<String, Place> mPlaces;
    FireBaseDataSource mDataProvider;
    MapView mView;


    private final FireBaseDataSource.GetPlacesCallback mPLaceListener = new FireBaseDataSource.GetPlacesCallback() {
        @Override
        public void onPlacesLoaded(Map<String, Place> places) {
            updatePLaces(places);
        }
    };
    private boolean isMapReady;

    private void updatePLaces(Map<String, Place> places) {
        mPlaces = places;
        fillPlacesOnMap();
    }

    private void fillPlacesOnMap() {
        if(isMapReady && mPlaces!=null){
            for (Map.Entry<String,Place> placeWithKey : mPlaces.entrySet()) {
                Place place = placeWithKey.getValue();
                mView.fillMapMarkerPLace(placeWithKey.getKey(), place, place.latitude(), place.longitude());
            }
            mView.showAllOnMaps();
        }
    }

    public MapPresenter(MapView view, FireBaseDataSource dataProvider) {
        mDataProvider = dataProvider;
        mDataProvider.getPlaces(new FireBaseDataSource.GetPlacesCallback() {
            @Override
            public void onPlacesLoaded(Map<String, Place> places) {
                Log.e("TAG", "onPlacesLoaded: "+places.toString());
            }
        });
       // mDataProvider.getPlacesByType(Constant.RUBRIC_PARKS,mPLaceListener);
       // mDataProvider.writePlace("0000",Constant.RUBRIC_PARKS,false,"vvvv","ddddd","pic",50.389000, 30.499000);
        mView = view;
        mView.initView();
    }


    public void onMapReady() {
        isMapReady = true;
        fillPlacesOnMap();
    }
}
