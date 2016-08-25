package com.holovko.kyivmommap.data.panoramio;

import android.support.annotation.NonNull;

import com.holovko.kyivmommap.model.panaramio.Photo;

import java.util.List;

/**
 * Created by Andrey Holovko on 8/24/16.
 */

public interface PhotosDataSource {

    interface GetPhotosCallback {
        void onPhotosLoaded(List<Photo> photos);
        void onDataNotAvailable();
    }

    void getPhotos(@NonNull GetPhotosCallback callback);

    void savePhoto(@NonNull Photo photo);

    void deleteAllPhotos();

    void deletePhoto(@NonNull String photoFileUrl);
}
