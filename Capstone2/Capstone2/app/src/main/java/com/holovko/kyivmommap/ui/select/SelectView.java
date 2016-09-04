package com.holovko.kyivmommap.ui.select;

import com.holovko.kyivmommap.model.firebase.Place;

import java.util.Map;

/**
 * Created by Andrey Holovko on 9/4/16.
 */

public interface SelectView {
    void startServiceToCollectPhoto(Map<String, Place> places);
}
