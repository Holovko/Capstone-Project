package com.holovko.kyivmommap.ui.map;

import com.holovko.kyivmommap.data.Constant;
import com.holovko.kyivmommap.data.DataSource;
import com.holovko.kyivmommap.model.firebase.Place;

import java.util.Map;

/**
 * Presenter for MapView
 * Created by Andrey Holovko on 7/26/16.
 */
public class MapPresenter {
    DataSource mDataProvider;
    MapView mView;
    private Map<String, Place> mPlaces;
    private boolean isMapReady;

    public MapPresenter(MapView view, DataSource dataProvider, int rubricType) {
        mDataProvider = dataProvider;
        mDataProvider.getPlacesByType(Constant.getRubricType(rubricType), new DataSource.GetPlacesCallback() {
            @Override
            public void onPlacesLoaded(Map<String, Place> places) {
                updatePLaces(places);
            }

            @Override
            public void onDataNotAvailable() {
                //TODO
            }
        });
        mView = view;
    }

    private void updatePLaces(Map<String, Place> places) {
        mPlaces = places;
        fillPlacesOnMap();
    }

    private void fillPlacesOnMap() {
        if (isMapReady && mPlaces != null) {
            for (Map.Entry<String, Place> placeWithKey : mPlaces.entrySet()) {
                Place place = placeWithKey.getValue();
                mView.fillMapMarkerPLace(placeWithKey.getKey(), place, place.latitude(), place.longitude());
            }
            mView.showAllOnMaps();
        }
    }

    public void onMapReady() {
        isMapReady = true;
        fillPlacesOnMap();
    }
}
