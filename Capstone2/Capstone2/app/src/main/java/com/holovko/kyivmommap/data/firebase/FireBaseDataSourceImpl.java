package com.holovko.kyivmommap.data.firebase;

import android.support.annotation.NonNull;
import android.util.Log;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.holovko.kyivmommap.data.Constant;
import com.holovko.kyivmommap.model.Comment;
import com.holovko.kyivmommap.model.Place;

import java.util.HashMap;
import java.util.Map;

/**
 * Data provider
 * Created by Andrey Holovko on 7/26/16.
 */
public class FireBaseDataSourceImpl implements FireBaseDataSource {
    private static final String TAG = "FireBaseDataSourceImpl";
    private static final String LANGUAGE_ENG = "eng";
    private static final String NODE_CATEGORY = "category";
    private static final String NODE_COMMENT = "comment";
    private static FireBaseDataSourceImpl INSTANCE;
    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference(LANGUAGE_ENG);

    private FireBaseDataSourceImpl() {
    }

    public static synchronized FireBaseDataSourceImpl getInstance() {
        if (INSTANCE == null) INSTANCE = new FireBaseDataSourceImpl();
        return INSTANCE;
    }


    @Override
    public void getPlaces(final GetPlacesCallback callback) {
        getCategory().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Map<String,Place> places = new HashMap<>();
                ObjectMapper mapper = new ObjectMapper();
                for (Map.Entry<String, HashMap> cursor : getStringObjectMap(dataSnapshot,HashMap.class).entrySet()){

                    for (Object place : cursor.getValue().entrySet()) {
                        Map.Entry<String, Object> objectEntry = (Map.Entry<String, Object>) place;
                        places.put(objectEntry.getKey(),
                                mapper.convertValue(objectEntry.getValue(), Place.class));
                    }
                }
                callback.onPlacesLoaded(places);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                throw new DatabaseException("Error with database");
            }
        });
    }

    private DatabaseReference getCategory() {
        return mDatabase.child(NODE_CATEGORY);
    }

    @Override
    public Query getPlacesByType(@Constant.RubricType int type) {
        return getCategory().child(Constant.getNodeByType(type));
    }


    public void getPlacesByType(@Constant.RubricType int type, final GetPlacesCallback callback) {
        getPlacesByType(type).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                callback.onPlacesLoaded(getStringObjectMap(dataSnapshot, Place.class));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                throw new DatabaseException("Error with database");
            }
        });
    }

    @Override
    public Query getCommentsByKey(String key) {
        return mDatabase.child(NODE_COMMENT).child(key);
    }


    public void getCommentListPlacesByKey(String key, final OnGetCommentsListener listener) {
        getCommentsByKey(key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listener.onGetComments(getStringObjectMap(dataSnapshot, Comment.class));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                throw new DatabaseException("Error with database");
            }
        });
    }

    public void writeComment(String uid, String key, boolean approved,
                             String name, String comment, float rank) {
        Comment commentToWrite = Comment.create(approved, name, comment, rank);
        mDatabase.child(NODE_COMMENT).child(key).child(uid).setValue(getSerialize(commentToWrite));
    }

    public void writePlace(String uid,
                           @Constant.RubricType int type,
                           boolean approved,
                           String title,
                           String description,
                           String pic,
                           double latitude,
                           double longitude) {
        Place place = Place.create(approved, title, description, pic, latitude, longitude, 0);
        getCategory()
                .child(Constant.getNodeByType(type))
                .push()
                .setValue(getSerialize(place));
    }


    /**
     * For serialize autovalue
     */
    private Object getSerialize(Object object) {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.convertValue(object, Map.class);
    }

    /**
     * Converter for deserialize autovalue
     */
    @NonNull
    private <T> Map<String, T> getStringObjectMap(DataSnapshot dataSnapshot, Class<T> cls) {
        Map<String, T> keyResultMaps = new HashMap<>();
        // Use Firebase to convert to a Map<String,Object>
        GenericTypeIndicator<Map<String, Object>> t = new GenericTypeIndicator<Map<String, Object>>() {
        };
        Map<String, Object> map = dataSnapshot.getValue(t);
        // Use Jackson to convert from a Map to an Office object
        ObjectMapper mapper = new ObjectMapper();

        for (Map.Entry<String, Object> cursor : map.entrySet()) {
            String key = cursor.getKey();
            T item = mapper.convertValue(cursor.getValue(), cls);
            keyResultMaps.put(key, item);
        }
        return keyResultMaps;
    }
}
