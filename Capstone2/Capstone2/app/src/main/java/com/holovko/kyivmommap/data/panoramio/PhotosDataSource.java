package com.holovko.kyivmommap.data.panoramio;

import android.accounts.NetworkErrorException;
import android.support.annotation.NonNull;

import com.holovko.kyivmommap.model.local.Photo;
import com.holovko.kyivmommap.model.panaramio.PhotoPanaramio;

import java.util.List;

/**
 * interface for main operations for app API
 * Created by Andrey Holovko on 8/24/16.
 */

public interface PhotosDataSource {
    public List<PhotoPanaramio> getPhotos(float minx,
                                          float miny,
                                          float maxx,
                                          float maxy) throws NetworkErrorException;

    void savePhoto(@NonNull Photo photo);

    void deleteAllPhotos();

    void deletePhoto(@NonNull String photoFileUrl);
}
