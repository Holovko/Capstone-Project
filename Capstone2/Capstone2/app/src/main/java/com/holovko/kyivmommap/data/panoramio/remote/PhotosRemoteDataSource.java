package com.holovko.kyivmommap.data.panoramio.remote;

import android.accounts.NetworkErrorException;
import android.support.annotation.NonNull;

import com.holovko.kyivmommap.data.panoramio.PhotosDataSource;
import com.holovko.kyivmommap.model.local.Photo;
import com.holovko.kyivmommap.model.panaramio.PhotoPanaramio;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;

/**
 * PhotoPanaramio remote data source
 * Created by Andrey Holovko on 8/27/16.
 */

public class PhotosRemoteDataSource implements PhotosDataSource {

    private static final String TAG = PhotosRemoteDataSource.class.getSimpleName();
    private static PhotosRemoteDataSource INSTANCE;
    private PanoramioClient mClient = ServiceGenerator.createService(PanoramioClient.class);

    // Prevent direct instantiation.
    private PhotosRemoteDataSource() {
    }

    public static PhotosRemoteDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new PhotosRemoteDataSource();
        }
        return INSTANCE;
    }

    /**
     * Find photos by location from rect
     * test example 30.497899f,50.387506f,30.505614f,50.390714f
     */

    @Override
    public List<PhotoPanaramio> getPhotos(float minx,
                                          float miny,
                                          float maxx,
                                          float maxy) throws NetworkErrorException {
        Call<List<PhotoPanaramio>> call = mClient.photos(minx, miny, maxx, maxy);
        try {
            return call.execute().body();
        } catch (IOException e) {
            throw new NetworkErrorException("error get photo");
        }
    }

    @Override
    public void savePhoto(@NonNull Photo photo) {
        //No need
    }

    @Override
    public void deleteAllPhotos() {
        //No need
    }

    @Override
    public void deletePhoto(@NonNull String photoFileUrl) {
        //No need
    }
}
