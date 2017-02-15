/*
 * Copyright (C) 2015 The Android Open Source Project
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

package com.holovko.kyivmommap;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import com.holovko.kyivmommap.data.DataRepository;
import com.holovko.kyivmommap.data.firebase.FireBaseDataSource;
import com.holovko.kyivmommap.data.firebase.FireBaseRepository;
import com.holovko.kyivmommap.data.panoramio.PhotosDataSource;
import com.holovko.kyivmommap.data.panoramio.local.PhotosLocalDataSource;
import com.holovko.kyivmommap.data.panoramio.remote.PhotosRemoteDataSource;

/**
 * Injection later for tests
 */
public class Injection {
    public static DataRepository provideDataRepository(@NonNull Context context) {
        return DataRepository.getInstance(provideRemoteDataSource(),
                provideLocalDataSource(context),
                provideFireBaseDataSource());
    }

    public static PhotosDataSource provideRemoteDataSource() {
        return PhotosRemoteDataSource.getInstance();
    }

    public static PhotosDataSource provideLocalDataSource(@NonNull Context context) {
        return PhotosLocalDataSource.getInstance(context.getContentResolver());
    }

    public static FireBaseDataSource provideFireBaseDataSource() {
        return FireBaseRepository.getInstance();
    }

    public static SharedPreferences provideSharedPreferences(@NonNull Context context){
        return context.getSharedPreferences("prefs_file",0);
    }
}
