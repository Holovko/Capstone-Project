package com.holovko.kyivmommap.presenter;

import com.holovko.kyivmommap.Constant;
import com.holovko.kyivmommap.data.IDataProvider;
import com.holovko.kyivmommap.model.Place;
import com.holovko.kyivmommap.view.MapView;

import java.util.Map;

/**
 * Presenter for MapView
 * Created by Andrey Holovko on 7/26/16.
 */
public class MapPresenter {
    private Map<String, Place> mPlaces;
    IDataProvider mDataProvider;
    MapView mView;


    private final IDataProvider.OnGetPLacesListener mPLaceListener = new IDataProvider.OnGetPLacesListener() {
        @Override
        public void onGetPlaces(Map<String, Place> places) {
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

    public MapPresenter(MapView view, IDataProvider dataProvider) {
        mDataProvider = dataProvider;
        mDataProvider.getListPlacesByType(Constant.RUBRIC_PARKS,mPLaceListener);
        mView = view;
        mView.initView();
    }


    public void onMapReady() {
        isMapReady = true;
        fillPlacesOnMap();
    }
}
