package com.holovko.kyivmommap.data.panoramio.remote;

import com.holovko.kyivmommap.model.panaramio.PhotoPanaramio;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * API for Panoramio service
 * Created by Andrey Holovko on 8/22/16.
 */

public interface PanoramioClient {
    @GET ("get_panoramas.php?set=public&from=0&to=20&size=medium&mapfilter=true")
    Call<List<PhotoPanaramio>> photos(@Query("minx") float minx,
                                      @Query("miny") float miny,
                                      @Query("maxx") float maxx,
                                      @Query("maxy") float maxy);
}
