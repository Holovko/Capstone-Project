package com.holovko.kyivmommap.ui.favourite;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import com.holovko.kyivmommap.data.Constant;
import com.holovko.kyivmommap.data.DataSource;
import com.holovko.kyivmommap.model.firebase.Place;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Presenter for detail list
 * Created by Andrey Holovko on 9/3/16.
 */

class FavouritePresenter implements DataSource.GetPlacesCallback {

    private final FavouriteView mView;
    private final DataSource mDataProvider;
    private final SharedPreferences mSharedPreferences;
    private final Set<String> mFavouriteList;
    private HashMap<String, Place> mPlaces;

    FavouritePresenter(DataSource dataProvider, FavouriteView view, SharedPreferences sharedPreferences) {
        mDataProvider = dataProvider;
        mView = view;
        mSharedPreferences = sharedPreferences;

        mFavouriteList = getFavouriteSet();
        if (mFavouriteList.size() > 0) {
            mDataProvider.getPlacesByKey(new ArrayList<>(mFavouriteList), this);
        }else{
            mView.showEmptyList(true);
        }
    }

    @NonNull
    private Set<String> getFavouriteSet() {
        return mSharedPreferences.getStringSet(Constant.FAVOURITE_LIST, new HashSet<String>());
    }

    @Override
    public void onPlacesLoaded(Map<String, Place> places) {
        mPlaces = new HashMap<>(places);
        mView.showFavouriteList(places);
    }

    @Override
    public void onDataNotAvailable() {
        //TODO data not available
    }

    void removeFromFavourite(String placeKey) {
        mFavouriteList.remove(placeKey);
        mSharedPreferences.edit().putStringSet(Constant.FAVOURITE_LIST, mFavouriteList).apply();
        mPlaces.remove(placeKey);
        mView.showSuccessDeletedFromFavourite();
        mView.refreshList(mPlaces);
        if(mPlaces.size()==0){
            mView.showEmptyList(true);
        }
    }

    public void initDetailFavourite(Map.Entry<String, Place> entry) {
        mView.showDetails(entry.getKey(),entry.getValue());
    }
}
