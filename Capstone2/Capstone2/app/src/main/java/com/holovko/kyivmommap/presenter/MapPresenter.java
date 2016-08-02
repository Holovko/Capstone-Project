package com.holovko.kyivmommap.presenter;

import com.holovko.kyivmommap.Constant;
import com.holovko.kyivmommap.data.IDataProvider;
import com.holovko.kyivmommap.model.Place;
import com.holovko.kyivmommap.model.PlaceOld;
import com.holovko.kyivmommap.view.MapView;

import java.util.List;

/**
 * Presenter for MapView
 * Created by Andrey Holovko on 7/26/16.
 */
public class MapPresenter {
    private List<Place> mPlaces;
    IDataProvider mDataProvider;
    MapView mView;


    private final IDataProvider.OnGetPLacesListener mPLaceListener = new IDataProvider.OnGetPLacesListener() {
        @Override
        public void onGetPlaces(List<Place> places) {
            updatePLaces(places);
        }
    };
    private boolean isMapReady;

    private void updatePLaces(List<Place> places) {
        mPlaces = places;
        fillPlacesOnMap();
    }

    private void fillPlacesOnMap() {
        if(isMapReady && mPlaces!=null){
            for (Place place : mPlaces) {
                mView.fillMapMarkerPLace(place.latitude(),place.longitude(), place);
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
