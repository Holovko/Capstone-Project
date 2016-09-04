package com.holovko.kyivmommap.data;

import android.accounts.NetworkErrorException;
import android.support.annotation.NonNull;

import com.holovko.kyivmommap.model.firebase.Comment;
import com.holovko.kyivmommap.model.firebase.Place;
import com.holovko.kyivmommap.model.local.Photo;

import java.util.List;
import java.util.Map;

/**
 * Api for all app interface
 * Created by Andrey Holovko on 8/24/16.
 */

public interface DataSource {

    List<Photo> getPhotos(@NonNull Map<String, Place> places) throws NetworkErrorException;
    void getPlaces(@NonNull GetPlacesCallback callback);

    void savePhoto(@NonNull Photo photo);

    void getPlacesByType(@Constant.RubricType int type, GetPlacesCallback callback);
    void getPlacesByKey(final List<String> keys, final GetPlacesCallback callback);
    void getCommentListPlacesByKey(String key, final GetCommentsCallback callback);
    void addComment(String uid,
                    String key,
                    boolean approved,
                    String name,
                    String comment,
                    float rank);

    void addPlace(String uid,
                  @Constant.RubricType int type,
                  boolean approved,
                  String title,
                  String description,
                  String pic,
                  double latitude,
                  double longitude);

    interface GetPhotosCallback {
        void onPhotosLoaded(List<Photo> photos);
        void onDataNotAvailable();
    }

    interface GetPlacesCallback {
        void onPlacesLoaded(Map<String, Place> places);
        void onDataNotAvailable();
    }
    interface GetCommentsCallback {
        void onGetComments(Map<String, Comment> commentMap);
        void onDataNotAvailable();
    }
}
