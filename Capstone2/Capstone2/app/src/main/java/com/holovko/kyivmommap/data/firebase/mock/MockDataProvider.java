package com.holovko.kyivmommap.data.firebase.mock;

import com.google.firebase.database.Query;
import com.holovko.kyivmommap.data.Constant;
import com.holovko.kyivmommap.data.firebase.FireBaseDataSource;
import com.holovko.kyivmommap.model.Comment;
import com.holovko.kyivmommap.model.Place;

import java.util.HashMap;
import java.util.Map;

/**
 * Mock data for testing
 * Created by Andrey Holovko on 8/1/16.
 */
public class MockDataProvider implements FireBaseDataSource {
    @Override
    public Query getPlacesByType(@Constant.RubricType int type) {
        return null;
    }

    @Override
    public Query getCommentsByKey(String key) {
        return null;
    }

    @Override
    public void getPlaces(GetPlacesCallback callback) {
        //TODO
    }

    @Override
    public void getPlacesByType(@Constant.RubricType int type, GetPlacesCallback pLacesListener) {
        Place testPlace1 = Place.create(true,"My Title 1","Description 1","pic",50.389419, 30.499712,5);
        Place testPlace2 = Place.create(true,"My Title 2","Description 1","pic",50.365076, 30.466265,4);
        Place testPlace3 = Place.create(true,"My Title 3","Description 1","pic",50.343102, 30.549816,3);
        Map<String,Place> map = new HashMap<>();
        map.put("park1",testPlace1);
        map.put("park2",testPlace2);
        map.put("park3",testPlace3);
        pLacesListener.onPlacesLoaded(map);
    }

    @Override
    public void getCommentListPlacesByKey(String key, OnGetCommentsListener listener) {
        Comment comment1 = Comment.create(true, "FFFFF","HUYNA",4);
        Comment comment2 = Comment.create(false, "FFFFF","HUYNA",5);
        Comment comment3 = Comment.create(true, "FFFFF","HUYNA",4.5f);
        Map<String, Comment> map = new HashMap<>();
        map.put("rrrr",comment1);
        map.put("dddd",comment2);
        map.put("ffff",comment3);
        listener.onGetComments(map);
    }

    @Override
    public void writeComment(String uid, String key, boolean approved, String name, String comment, float rank) {

    }

    @Override
    public void writePlace(String uid, @Constant.RubricType int type, boolean approved, String title, String description, String pic, double latitude, double longitude) {

    }
}
