package com.holovko.kyivmommap.data;

import com.google.firebase.database.Query;
import com.holovko.kyivmommap.Constant;
import com.holovko.kyivmommap.model.Place;

import java.util.ArrayList;
import java.util.List;

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
        Place testPlace1 = new Place(true,"My Title 1","Description 1",50.389419, 30.499712,5);
        Place testPlace2 = new Place(true,"My Title 2","Description 1",50.365076, 30.466265,4);
        Place testPlace3 = new Place(true,"My Title 3","Description 1",50.343102, 30.549816,3);
        List<Place> list = new ArrayList<>();
        list.add(testPlace1);
        list.add(testPlace2);
        list.add(testPlace3);
        pLacesListener.onGetPlaces(list);
    }
}
