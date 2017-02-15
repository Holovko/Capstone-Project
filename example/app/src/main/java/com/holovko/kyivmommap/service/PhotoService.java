package com.holovko.kyivmommap.service;

import android.accounts.NetworkErrorException;
import android.app.IntentService;
import android.content.Intent;

import com.holovko.kyivmommap.Injection;
import com.holovko.kyivmommap.data.DataRepository;
import com.holovko.kyivmommap.model.firebase.Place;
import com.holovko.kyivmommap.model.local.Photo;
import com.holovko.kyivmommap.utils.CollectionUtils;

import java.util.List;
import java.util.Map;

/**
 * Service to get data from Panaramio service photo and put it to DB
 * Created by Andrey Holovko on 8/22/16.
 */

public class PhotoService extends IntentService {

    private static final String TAG = PhotoService.class.getSimpleName();
    public static final String BUNDLE_MAP_PLACES = "bundle_map_places";


    public PhotoService() {
        super("PhotoService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        DataRepository repository = Injection.provideDataRepository(getApplicationContext());
        Map<String, Place> mapPlaces = CollectionUtils
                .mapFromBundle(intent.getExtras().getBundle(BUNDLE_MAP_PLACES),
                        Place.class);
        try {
            List<Photo> listPhotos = repository.getPhotos(mapPlaces);
            for (Photo photo : listPhotos) {
                repository.savePhoto(photo);
            }
        } catch (NetworkErrorException e) {
            e.printStackTrace();
        }
    }
}
