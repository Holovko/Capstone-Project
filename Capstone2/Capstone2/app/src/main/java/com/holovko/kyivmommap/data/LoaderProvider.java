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

package com.holovko.kyivmommap.data;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;

import com.holovko.kyivmommap.data.panoramio.local.PhotosPersistenceContract;

public class LoaderProvider {

    @NonNull
    private final Context mContext;

    public LoaderProvider(@NonNull Context context) {
        mContext = context;
    }

    public Loader<Cursor> createTaskLoader(String placeKey) {
        return new CursorLoader(mContext,
                PhotosPersistenceContract.PhotoEntry.buildTasksUri(),
                PhotosPersistenceContract.PhotoEntry.PHOTOS_COLUMNS,
                PhotosPersistenceContract.PhotoEntry.COLUMN_NAME_FIREBASE_ID + "=?",
                new String[]{placeKey},
                null);

    }
}
