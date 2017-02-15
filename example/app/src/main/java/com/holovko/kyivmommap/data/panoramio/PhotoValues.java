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

package com.holovko.kyivmommap.data.panoramio;

import android.content.ContentValues;

import com.holovko.kyivmommap.data.panoramio.local.PhotosPersistenceContract;
import com.holovko.kyivmommap.model.local.Photo;

public class PhotoValues {
    public static ContentValues from(Photo photo) {
        ContentValues values = new ContentValues();
        values.put(PhotosPersistenceContract.PhotoEntry.COLUMN_NAME_FIREBASE_ID, photo.getFireBaseId());
        values.put(PhotosPersistenceContract.PhotoEntry.COLUMN_NAME_PHOTO_URL, photo.getPhotoUrl());
        values.put(PhotosPersistenceContract.PhotoEntry.COLUMN_NAME_OWNER_NAME, photo.getOwnerName());
        values.put(PhotosPersistenceContract.PhotoEntry.COLUMN_NAME_OWNER_URL, photo.getOwnerUrl());
        values.put(PhotosPersistenceContract.PhotoEntry.COLUMN_NAME_PHOTO_FILE_URL, photo.getPhotoFileUrl());
        return values;
    }
}
