package com.holovko.kyivmommap.data;

import com.google.firebase.database.Query;
import com.holovko.kyivmommap.Constant;
import com.holovko.kyivmommap.model.Place;

import java.util.List;

/**
 * Created by Andrey Holovko on 7/26/16.
 */
public interface IDataProvider {
    Query getPlacesByType(@Constant.RubricType int type);
    void getListPlacesByType(@Constant.RubricType int type, OnGetPLacesListener pLacesListener);

    interface OnGetPLacesListener{
        void onGetPlaces(List<Place> places);
    }
}
