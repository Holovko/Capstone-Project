package com.holovko.kyivmommap.data;

import com.google.firebase.database.Query;
import com.holovko.kyivmommap.Constant;

/**
 * Created by Andrey Holovko on 7/26/16.
 */
public interface IDataProvider {
    Query getPlacesByType(@Constant.RubricType String node);
}
