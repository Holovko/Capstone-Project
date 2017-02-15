package com.holovko.kyivmommap.model.local;

/**
 * Created by Andrey Holovko on 8/27/16.
 */
public interface IPhoto {

    Integer getHeight();

    Double getLatitude();

    Double getLongitude();

    Integer getOwnerId();

    String getOwnerName();

    String getOwnerUrl();

    String getPhotoFileUrl();

    Integer getPhotoId();

    String getPhotoTitle();

    String getPhotoUrl();

    String getUploadDate();

    Integer getWidth();

    String getPlaceId();
}
