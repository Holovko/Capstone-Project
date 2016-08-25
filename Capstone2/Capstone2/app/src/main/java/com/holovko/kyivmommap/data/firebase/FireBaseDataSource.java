package com.holovko.kyivmommap.data.firebase;

import com.google.firebase.database.Query;
import com.holovko.kyivmommap.data.Constant;
import com.holovko.kyivmommap.model.Comment;
import com.holovko.kyivmommap.model.Place;

import java.util.Map;

/**
 * Interface for data provider
 * Created by Andrey Holovko on 7/26/16.
 */
public interface FireBaseDataSource {
    Query getPlacesByType(@Constant.RubricType int type);
    Query getCommentsByKey(String key);
    void getPlaces(final GetPlacesCallback callback);
    void getPlacesByType(@Constant.RubricType int type, GetPlacesCallback pLacesListener);
    void getCommentListPlacesByKey(String key, final OnGetCommentsListener listener);

    void writeComment(String uid,
                      String key,
                      boolean approved,
                      String name,
                      String comment,
                      float rank);
    void writePlace(String uid,
                    @Constant.RubricType int type,
                    boolean approved,
                    String title,
                    String description,
                    String pic,
                    double latitude,
                    double longitude);
    interface GetPlacesCallback {
        void onPlacesLoaded(Map<String, Place> places);
    }
    interface OnGetCommentsListener{
        void onGetComments(Map<String, Comment> commentMap);
    }
}
