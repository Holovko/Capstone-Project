package com.holovko.kyivmommap.model.local;


import android.database.Cursor;

import com.holovko.kyivmommap.data.panoramio.local.PhotosPersistenceContract;

/**
 * Object for database
 * Created by Andrey Holovko on 8/27/16.
 */

public class Photo implements IPhoto {
    private IPhoto mPhoto;
    private String mFireBaseId;
    public Photo(IPhoto photo, String fireBaseId) {
        mPhoto = photo;
        mFireBaseId = fireBaseId;
    }

    private Photo(String fireBaseId, final String photoUrl, final String ownerName, final String ownerUrl, final String photoFileUrl) {
        mFireBaseId = fireBaseId;
        mPhoto = new IPhoto() {
            @Override
            public Integer getHeight() {
                return null;
            }

            @Override
            public Double getLatitude() {
                return null;
            }

            @Override
            public Double getLongitude() {
                return null;
            }

            @Override
            public Integer getOwnerId() {
                return null;
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
                return null;
            }

            @Override
            public String getPhotoTitle() {
                return null;
            }

            @Override
            public String getPhotoUrl() {
                return photoUrl;
            }

            @Override
            public String getUploadDate() {
                return null;
            }

            @Override
            public Integer getWidth() {
                return null;
            }

            @Override
            public String getPlaceId() {
                return null;
            }
        };
    }


    public String getFireBaseId() {
        return mFireBaseId;
    }

    @Override
    public Integer getHeight() {
        return mPhoto.getHeight();
    }

    @Override
    public Double getLatitude() {
        return mPhoto.getLatitude();
    }

    @Override
    public Double getLongitude() {
        return mPhoto.getLongitude();
    }

    @Override
    public Integer getOwnerId() {
        return mPhoto.getOwnerId();
    }

    @Override
    public String getOwnerName() {
        return mPhoto.getOwnerName();
    }

    @Override
    public String getOwnerUrl() {
        return mPhoto.getOwnerUrl();
    }

    @Override
    public String getPhotoFileUrl() {
        return mPhoto.getPhotoFileUrl();
    }

    @Override
    public Integer getPhotoId() {
        return mPhoto.getPhotoId();
    }

    @Override
    public String getPhotoTitle() {
        return mPhoto.getPhotoTitle();
    }

    @Override
    public String getPhotoUrl() {
        return mPhoto.getPhotoUrl();
    }

    @Override
    public String getUploadDate() {
        return mPhoto.getUploadDate();
    }

    @Override
    public Integer getWidth() {
        return mPhoto.getWidth();
    }

    @Override
    public String getPlaceId() {
        return mPhoto.getPlaceId();
    }

    public static Photo from(Cursor cursor) {
        String fireBaseId = cursor.getString(cursor.getColumnIndexOrThrow(
                PhotosPersistenceContract.PhotoEntry.COLUMN_NAME_FIREBASE_ID));
        String photoUrl = cursor.getString(cursor.getColumnIndexOrThrow(
                PhotosPersistenceContract.PhotoEntry.COLUMN_NAME_PHOTO_URL));
        String ownerName = cursor.getString(cursor.getColumnIndexOrThrow(
                PhotosPersistenceContract.PhotoEntry.COLUMN_NAME_OWNER_NAME));
        String ownerUrl = cursor.getString(cursor.getColumnIndexOrThrow(
                PhotosPersistenceContract.PhotoEntry.COLUMN_NAME_OWNER_URL));
        String photoFileUrl = cursor.getString(cursor.getColumnIndexOrThrow(
                PhotosPersistenceContract.PhotoEntry.COLUMN_NAME_PHOTO_FILE_URL));
        return new Photo(fireBaseId,photoUrl,ownerName,ownerUrl,photoFileUrl);
    }


}
