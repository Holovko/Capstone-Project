package com.holovko.kyivmommap.data;

import com.google.firebase.database.Query;
import com.holovko.kyivmommap.Constant;
import com.holovko.kyivmommap.model.Place;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Andrey Holovko on 8/1/16.
 */
public class MockDataProvider implements IDataProvider {
    @Override
    public Query getPlacesByType(@Constant.RubricType int type) {
        return null;
    }

    @Override
    public void getListPlacesByType(@Constant.RubricType int type, OnGetPLacesListener pLacesListener) {
        Place testPlace1 = Place.create(true,"My Title 1","Description 1","pic",50.389419, 30.499712,5);
        Place testPlace2 = Place.create(true,"My Title 2","Description 1","pic",50.365076, 30.466265,4);
        Place testPlace3 = Place.create(true,"My Title 3","Description 1","pic",50.343102, 30.549816,3);
        Map<String,Place> map = new HashMap<>();
        map.put("park1",testPlace1);
        map.put("park2",testPlace2);
        map.put("park3",testPlace3);
        pLacesListener.onGetPlaces(map);
    }
}
