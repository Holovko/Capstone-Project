package com.holovko.kyivmommap.ui.details;

import com.holovko.kyivmommap.model.firebase.Place;

/**
 * Created by Andrey Holovko on 7/26/16.
 */
public interface MapView {
    void showAllOnMaps();
    void clearMapData();
    void fillMapMarkerPLace(String key, Place place, double latitude, double longitude);
    void setPresenter(DetailsPresenter presenter);
}
