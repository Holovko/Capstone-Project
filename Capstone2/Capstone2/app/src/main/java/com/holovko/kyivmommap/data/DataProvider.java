package com.holovko.kyivmommap.data;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.holovko.kyivmommap.Constant;

/**
 * Created by Andrey Holovko on 7/26/16.
 */
public class DataProvider implements IDataProvider {
    private static final String LANGUAGE_ENG = "eng";
    private final String CATEGORY_NODE = "category";
    private DatabaseReference mDatabase;

    public DataProvider(DatabaseReference database) {
        mDatabase = database;
        mDatabase = FirebaseDatabase.getInstance().getReference(LANGUAGE_ENG);
    }

    @Override
    public Query getPlacesByType(@Constant.RubricType String node) {
        return mDatabase.child(CATEGORY_NODE).child(node);
    }
}
