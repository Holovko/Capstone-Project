package com.holovko.kyivmommap.ui.favourite;

import com.holovko.kyivmommap.model.firebase.Place;

import java.util.Map;

/**
 * Created by Andrey Holovko on 9/3/16.
 */

public interface FavouriteView {
    void showSuccessDeletedFromFavourite();
    void showFavouriteList(Map<String, Place> places);
    void refreshList(Map<String, Place> stringPlaceMap);
    void showDetails(String key, Place value);
    void showEmptyList(boolean isVisible);
}
