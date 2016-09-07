package com.holovko.kyivmommap.ui.select;

import android.content.SharedPreferences;

import com.holovko.kyivmommap.data.Constant;
import com.holovko.kyivmommap.data.DataRepository;
import com.holovko.kyivmommap.data.DataSource;
import com.holovko.kyivmommap.model.firebase.Place;
import com.holovko.kyivmommap.ui.map.MapView;

import java.util.Calendar;
import java.util.Map;

/**
 * Created by Andrey Holovko on 9/4/16.
 */

public class SelectPresenter {
    //Select
    private final static int ONE_DAY_MILLISECONDS = 86400000;
    private final DataSource mDataRepository;
    private final SharedPreferences mSharedPreferences;
    SelectView mSelectView;
    MapView mView;
    private Map<String, Place> mPlaces;
    private boolean isMapReady;

    public SelectPresenter(DataRepository dataRepository, SharedPreferences sharedPreferences, SelectView selectView, MapView mapView, int rubricType) {
        mSelectView = selectView;
        mView = mapView;
        mDataRepository = dataRepository;
        mSharedPreferences = sharedPreferences;
        if(selectView!=null) collectPhotos();
        if(mapView!=null) getPlaceByRubricType(rubricType);
    }

    public void getPlaceByRubricType(int rubricType) {
        mDataRepository.getPlacesByType(Constant.getRubricType(rubricType), new DataSource.GetPlacesCallback() {
            @Override
            public void onPlacesLoaded(Map<String, Place> places) {
                updatePLaces(places);
            }

            @Override
            public void onDataNotAvailable() {
                //TODO
            }
        });
    }

    private void collectPhotos() {
        long dateLastUpdate = mSharedPreferences.getLong(Constant.DATE_PHOTO_UPDATE, 0L);
        final long currentTimeInMilliseconds = Calendar.getInstance().getTimeInMillis();
        if ((currentTimeInMilliseconds -dateLastUpdate)> ONE_DAY_MILLISECONDS) {
            mDataRepository.getPlaces(new DataSource.GetPlacesCallback() {
                @Override
                public void onPlacesLoaded(Map<String, Place> places) {
                    mSharedPreferences.edit().putLong(Constant.DATE_PHOTO_UPDATE,currentTimeInMilliseconds).apply();
                    mSelectView.startServiceToCollectPhoto(places);
                }

                @Override
                public void onDataNotAvailable() {
                    //TODO show
                }
            });
        }
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
