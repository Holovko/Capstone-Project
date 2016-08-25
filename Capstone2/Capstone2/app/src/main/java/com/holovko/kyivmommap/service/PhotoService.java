package com.holovko.kyivmommap.service;

import android.app.IntentService;
import android.content.Intent;

/**
 * Created by Andrey Holovko on 8/22/16.
 */

public class PhotoService extends IntentService {

    private static final String TAG = PhotoService.class.getSimpleName();

    public PhotoService() {
        super("PhotoService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
/*        PanoramioClient client = ServiceGenerator.createService(PanoramioClient.class);
        Call<List<Photo>> call = client.photos(30.497899f,50.387506f,30.505614f,50.390714f);
        try {
            List<Photo> photos = call.execute().body();
            Log.i(TAG, "onHandleIntent:"+photos);
        }catch (IOException e){
            Log.e(TAG, "onHandleIntent: ", e);
        }*/
    }
}
