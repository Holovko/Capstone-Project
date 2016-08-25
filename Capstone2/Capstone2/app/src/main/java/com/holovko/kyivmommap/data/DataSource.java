package com.holovko.kyivmommap.data;

import android.support.annotation.NonNull;

import com.holovko.kyivmommap.data.panoramio.PhotosDataSource;
import com.holovko.kyivmommap.model.panaramio.Photo;

/**
 * Created by Andrey Holovko on 8/24/16.
 */

public interface DataSource {
    void getPhotos(@NonNull PhotosDataSource.GetPhotosCallback callback);

    void savePhoto(@NonNull Photo photo);
}
