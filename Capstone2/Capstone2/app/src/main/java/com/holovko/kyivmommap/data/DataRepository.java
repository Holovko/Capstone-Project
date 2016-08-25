package com.holovko.kyivmommap.data;

import android.support.annotation.NonNull;

import com.holovko.kyivmommap.data.firebase.FireBaseDataSource;
import com.holovko.kyivmommap.data.panoramio.PhotosDataSource;
import com.holovko.kyivmommap.model.Place;
import com.holovko.kyivmommap.model.panaramio.Photo;

import java.util.Map;

/**
 * Created by Andrey Holovko on 8/24/16.
 */

public class DataRepository implements DataSource {

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
    public void getPhotos(@NonNull PhotosDataSource.GetPhotosCallback callback) {
        mFireBaseDataSource.getPlaces(new FireBaseDataSource.GetPlacesCallback() {
            @Override
            public void onPlacesLoaded(Map<String, Place> places) {
                for (Map.Entry<String, Place> stringPlaceEntry : places.entrySet()) {

                    mPhotosRemoteDataSource.getPhotos();
                }
            }
        });
    }

    @Override
    public void savePhoto(@NonNull Photo photo) {

    }
}
