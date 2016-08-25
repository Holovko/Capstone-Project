/*
 * Copyright 2016, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.holovko.kyivmommap.data.panoramio.local;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.support.annotation.NonNull;

import com.holovko.kyivmommap.data.panoramio.PhotoValues;
import com.holovko.kyivmommap.data.panoramio.PhotosDataSource;
import com.holovko.kyivmommap.model.panaramio.Photo;

public class PhotosLocalDataSource implements PhotosDataSource {

    private static PhotosLocalDataSource INSTANCE;
    private ContentResolver mContentResolver;

    private PhotosLocalDataSource(@NonNull ContentResolver contentResolver) {
        mContentResolver = contentResolver;
    }

    public static PhotosLocalDataSource getInstance(@NonNull ContentResolver contentResolver) {
        if (INSTANCE == null) {
            INSTANCE = new PhotosLocalDataSource(contentResolver);
        }
        return INSTANCE;
    }

    @Override
    public void getPhotos(@NonNull GetPhotosCallback callback) {
        //later
    }

    @Override
    public void savePhoto(@NonNull Photo photo) {
        ContentValues values = PhotoValues.from(photo);
        mContentResolver.insert(PhotosPersistenceContract.PhotoEntry.buildTasksUri(), values);
    }

    @Override
    public void deleteAllPhotos() {
        mContentResolver.delete(PhotosPersistenceContract.PhotoEntry.buildTasksUri(), null, null);
    }

    @Override
    public void deletePhoto(@NonNull String taskId) {
        String selection = PhotosPersistenceContract.PhotoEntry.COLUMN_NAME_PHOTO_FILE_URL + " LIKE ?";
        String[] selectionArgs = {taskId};
        mContentResolver.delete(PhotosPersistenceContract.PhotoEntry.buildTasksUri(), selection, selectionArgs);
    }

}
