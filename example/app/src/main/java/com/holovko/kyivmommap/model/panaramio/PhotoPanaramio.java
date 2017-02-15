package com.holovko.kyivmommap.model.panaramio;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.holovko.kyivmommap.model.local.IPhoto;

/**
 * Created by Andrey Holovko on 8/22/16.
 */
//TODO experiment with autovalue Gson
public class PhotoPanaramio implements IPhoto {
    @SerializedName("height")
    @Expose
    public Integer height;
    @SerializedName("latitude")
    @Expose
    public Double latitude;
    @SerializedName("longitude")
    @Expose
    public Double longitude;
    @SerializedName("owner_id")
    @Expose
    public Integer ownerId;
    @SerializedName("owner_name")
    @Expose
    public String ownerName;
    @SerializedName("owner_url")
    @Expose
    public String ownerUrl;
    @SerializedName("photo_file_url")
    @Expose
    public String photoFileUrl;
    @SerializedName("photo_id")
    @Expose
    public Integer photoId;
    @SerializedName("photo_title")
    @Expose
    public String photoTitle;
    @SerializedName("photo_url")
    @Expose
    public String photoUrl;
    @SerializedName("upload_date")
    @Expose
    public String uploadDate;
    @SerializedName("width")
    @Expose
    public Integer width;
    @SerializedName("place_id")
    @Expose
    public String placeId;

    @Override
    public Integer getHeight() {
        return height;
    }

    @Override
    public Double getLatitude() {
        return latitude;
    }

    @Override
    public Double getLongitude() {
        return longitude;
    }

    @Override
    public Integer getOwnerId() {
        return ownerId;
    }

    @Override
    public String getOwnerName() {
        return ownerName;
    }

    @Override
    public String getOwnerUrl() {
        return ownerUrl;
    }

    @Override
    public String getPhotoFileUrl() {
        return photoFileUrl;
    }

    @Override
    public Integer getPhotoId() {
        return photoId;
    }

    @Override
    public String getPhotoTitle() {
        return photoTitle;
    }

    @Override
    public String getPhotoUrl() {
        return photoUrl;
    }

    @Override
    public String getUploadDate() {
        return uploadDate;
    }

    @Override
    public Integer getWidth() {
        return width;
    }

    @Override
    public String getPlaceId() {
        return placeId;
    }
}