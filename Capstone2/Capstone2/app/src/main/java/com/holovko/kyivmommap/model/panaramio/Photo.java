package com.holovko.kyivmommap.model.panaramio;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Andrey Holovko on 8/22/16.
 */
//TODO experiment with autovalue Gson
public class Photo {
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
    public String fireBaseId;

    public String getFireBaseId() {
        return fireBaseId;
    }

    public Integer getHeight() {
        return height;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public Integer getOwnerId() {
        return ownerId;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public String getOwnerUrl() {
        return ownerUrl;
    }

    public String getPhotoFileUrl() {
        return photoFileUrl;
    }

    public Integer getPhotoId() {
        return photoId;
    }

    public String getPhotoTitle() {
        return photoTitle;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public String getUploadDate() {
        return uploadDate;
    }

    public Integer getWidth() {
        return width;
    }

    public String getPlaceId() {
        return placeId;
    }
}