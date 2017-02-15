package com.holovko.kyivmommap.ui.map;

import com.holovko.kyivmommap.model.firebase.Place;
import com.holovko.kyivmommap.ui.select.SelectPresenter;

/**
 * Created by Andrey Holovko on 7/26/16.
 */
public interface MapView {
    void showAllOnMaps();
    void clearMapData();
    void fillMapMarkerPLace(String key, Place place, double latitude, double longitude);
    void setPresenter(SelectPresenter presenter);
}
