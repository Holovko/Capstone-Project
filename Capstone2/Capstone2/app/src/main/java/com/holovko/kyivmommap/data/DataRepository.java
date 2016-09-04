package com.holovko.kyivmommap.data;

import android.accounts.NetworkErrorException;
import android.support.annotation.NonNull;

import com.holovko.kyivmommap.data.firebase.FireBaseDataSource;
import com.holovko.kyivmommap.data.panoramio.PhotosDataSource;
import com.holovko.kyivmommap.model.firebase.Comment;
import com.holovko.kyivmommap.model.firebase.Place;
import com.holovko.kyivmommap.model.local.Photo;
import com.holovko.kyivmommap.model.panaramio.PhotoPanaramio;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Data repository for all api
 * Created by Andrey Holovko on 8/24/16.
 */

public class DataRepository implements DataSource {

    private static final String TAG = DataRepository.class.getSimpleName();
    private static final double DISTANCE_ERROR = 0.02f;
    private static DataRepository INSTANCE = null;

    private final PhotosDataSource mPhotosRemoteDataSource;

    private final PhotosDataSource mPhotosLocalDataSource;

    private final FireBaseDataSource mFireBaseDataSource;

    // Prevent direct instantiation.
    private DataRepository(@NonNull PhotosDataSource photosRemoteDataSource,
                           @NonNull PhotosDataSource photosLocalDataSource,
                           @NonNull FireBaseDataSource fireBaseDataSource) {
        mPhotosRemoteDataSource = photosRemoteDataSource;
        mPhotosLocalDataSource = photosLocalDataSource;
        mFireBaseDataSource = fireBaseDataSource;
    }

    /**
     * Returns the single instance of this class, creating it if necessary.
     *
     * @param photosRemoteDataSource the backend data source
     * @param photosLocalDataSource  the device storage data source
     * @return the {@link DataRepository} instance
     */
    public static DataRepository getInstance(PhotosDataSource photosRemoteDataSource,
                                             PhotosDataSource photosLocalDataSource,
                                             FireBaseDataSource fireBaseDataSource) {
        if (INSTANCE == null) {
            INSTANCE = new DataRepository(photosRemoteDataSource, photosLocalDataSource, fireBaseDataSource);
        }
        return INSTANCE;
    }

    /**
     * Used to force  to create a new instance
     * next time it's called.
     */
    public static void destroyInstance() {
        INSTANCE = null;
    }

    @Override
    public List<Photo> getPhotos(@NonNull Map<String, Place> places) throws NetworkErrorException {
        List<Photo> list = new ArrayList<>();
        try {
            for (Map.Entry<String, Place> stringPlaceEntry : places.entrySet()) {
                String id = stringPlaceEntry.getKey();
                Place place = stringPlaceEntry.getValue();
                float minx = (float) (place.longitude() - DISTANCE_ERROR);
                float miny = (float) (place.latitude() - DISTANCE_ERROR);
                float maxx = (float) (place.longitude() + DISTANCE_ERROR);
                float maxy = (float) (place.latitude() + DISTANCE_ERROR);
                List<PhotoPanaramio> photoList = mPhotosRemoteDataSource.getPhotos(minx, miny, maxx, maxy);
                for (PhotoPanaramio photo : photoList) {
                    list.add(new Photo(photo, id));
                }
            }
            return list;
        } catch (NetworkErrorException e) {
            e.printStackTrace();
            throw new NetworkErrorException(e);
        }
    }

    /** Get places **/
    @Override
    public void getPlaces(final GetPlacesCallback callback) {
        mFireBaseDataSource.getPlaces(new FireBaseDataSource.GetPlacesCallback() {
            @Override
            public void onPlacesLoaded(Map<String, Place> places) {
                callback.onPlacesLoaded(places);
            }

            @Override
            public void onDataNotAvailable() {

            }
        });
    }

    /** Save photo to DB*/
    @Override
    public void savePhoto(@NonNull Photo photo) {
        mPhotosLocalDataSource.savePhoto(photo);
    }

    /** Get places by type **/
    @Override
    public void getPlacesByType(@Constant.RubricType int type, final GetPlacesCallback callback) {
        mFireBaseDataSource.getPlacesByType(type, new FireBaseDataSource.GetPlacesCallback() {
            @Override
            public void onPlacesLoaded(Map<String, Place> places) {
                callback.onPlacesLoaded(places);
            }

            @Override
            public void onDataNotAvailable() {
                callback.onDataNotAvailable();
            }
        });
    }

    /** Get places by type **/
    @Override
    public void getPlacesByKey(List<String> keys, final GetPlacesCallback callback) {
        mFireBaseDataSource.getPlacesByKey(keys, new FireBaseDataSource.GetPlacesCallback() {
            @Override
            public void onPlacesLoaded(Map<String, Place> places) {
                callback.onPlacesLoaded(places);
            }

            @Override
            public void onDataNotAvailable() {
                callback.onDataNotAvailable();
            }
        });
    }

    /** Get comments list by place key **/

    @Override
    public void getCommentListPlacesByKey(String key, final GetCommentsCallback callback) {
        mFireBaseDataSource.getCommentListPlacesByKey(key, new FireBaseDataSource.OnGetCommentsCallback() {
            @Override
            public void onGetComments(Map<String, Comment> commentMap) {
                callback.onGetComments(commentMap);
            }

            @Override
            public void onDataNotAvailable() {
                callback.onDataNotAvailable();
            }
        });
    }

    /** Add comment for place **/
    @Override
    public void addComment(String uid, String key, boolean approved, String name, String comment, float rank) {
        mFireBaseDataSource.writeComment(uid,key,approved,name,comment,rank);
    }

    /** Add place **/
    @Override
    public void addPlace(String uid, @Constant.RubricType int type, boolean approved, String title, String description, String pic, double latitude, double longitude) {
        mFireBaseDataSource.writePlace(uid,type,approved,title,description,pic,latitude,longitude);
    }
}
