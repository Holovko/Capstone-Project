package com.holovko.kyivmommap.ui.select;

import android.content.SharedPreferences;

import com.holovko.kyivmommap.data.Constant;
import com.holovko.kyivmommap.data.DataRepository;
import com.holovko.kyivmommap.data.DataSource;
import com.holovko.kyivmommap.model.firebase.Place;

import java.util.Calendar;
import java.util.Map;

/**
 * Created by Andrey Holovko on 9/4/16.
 */

class SelectPresenter {
    private final static int ONE_DAY_MILISECONDS = 86400000;
    private final DataRepository mDataRepositore;
    private final SharedPreferences mSharedPreferences;
    SelectView mView;

    SelectPresenter(DataRepository dataRepository, SharedPreferences sharedPreferences, SelectView view) {
        mView = view;
        mDataRepositore = dataRepository;
        mSharedPreferences = sharedPreferences;
        collectPhotos();
    }

    private void collectPhotos() {
        long dateLastUpdate = mSharedPreferences.getLong(Constant.DATE_PHOTO_UPDATE, 0L);
        final long currentTimeInMilliseconds = Calendar.getInstance().getTimeInMillis();
        if ((currentTimeInMilliseconds -dateLastUpdate)> ONE_DAY_MILISECONDS) {
            mDataRepositore.getPlaces(new DataSource.GetPlacesCallback() {
                @Override
                public void onPlacesLoaded(Map<String, Place> places) {
                    mSharedPreferences.edit().putLong(Constant.DATE_PHOTO_UPDATE,currentTimeInMilliseconds).apply();
                    mView.startServiceToCollectPhoto(places);
                }

                @Override
                public void onDataNotAvailable() {
                    //TODO show
                }
            });
        }
    }
}
