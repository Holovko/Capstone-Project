package com.holovko.kyivmommap.ui.details;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;

import com.holovko.kyivmommap.data.Constant;
import com.holovko.kyivmommap.data.LoaderProvider;
import com.holovko.kyivmommap.model.firebase.Place;
import com.holovko.kyivmommap.model.local.Photo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Presenter for detail list
 * Created by Andrey Holovko on 9/3/16.
 */

public class DetailsPresenter implements LoaderManager.LoaderCallbacks<Cursor> {

    private final String mKeyPlace;
    private final Place mPlace;
    private final DetailsView mView;
    private final LoaderManager mLoaderManager;
    private final LoaderProvider mLoaderProvider;
    private final SharedPreferences mSharedPreferences;

    public DetailsPresenter(LoaderProvider loaderProvider,
                            LoaderManager loaderManager,
                            DetailsView view,
                            String key,
                            Place place,
                            SharedPreferences sharedPreferences) {
        mLoaderProvider = loaderProvider;
        mLoaderManager = loaderManager;
        mView = view;
        mKeyPlace = key;
        mPlace = place;
        mSharedPreferences = sharedPreferences;

        boolean isInFavouriteList = mSharedPreferences
                .getStringSet(Constant.FAVOURITE_LIST, new HashSet<String>())
                .contains(mKeyPlace);
        mView.initView(mPlace.pic(), mPlace.title(),mPlace.description(),mPlace.rank(), isInFavouriteList);
        mLoaderManager.initLoader(0, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return mLoaderProvider.createTaskLoader(mKeyPlace);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (data.getCount() > 0) {
            List<String> urls = new ArrayList<>();
            while (data.moveToNext()) {
                Photo photo = Photo.from(data);
                urls.add(photo.getPhotoFileUrl());
            }
            mView.showPhotos(urls);
        }

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    public void initToFavourite() {
        Set<String> favouriteList = mSharedPreferences.getStringSet(Constant.FAVOURITE_LIST,new HashSet<String>());
        if(favouriteList.contains(mKeyPlace)){
            favouriteList.remove(mKeyPlace);
            SharedPreferences.Editor editor = mSharedPreferences.edit();
            editor.putStringSet(Constant.FAVOURITE_LIST,favouriteList).apply();
            mView.setFabWasAddedToFavourite(false);
            mView.showSuccessDeletedFromFavourite();
        }else{
            favouriteList.add(mKeyPlace);
            SharedPreferences.Editor editor = mSharedPreferences.edit();
            editor.putStringSet(Constant.FAVOURITE_LIST,favouriteList).apply();
            mView.setFabWasAddedToFavourite(true);
            mView.showSuccessAddedToFavourite();
        }
    }
}
