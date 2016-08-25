package com.holovko.kyivmommap.data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.holovko.kyivmommap.data.panoramio.local.PhotosDbHelper;
import com.holovko.kyivmommap.data.panoramio.local.PhotosPersistenceContract;

/**
 * Created by Andrey Holovko on 8/15/16.
 */

public class DataBaseProvider extends ContentProvider{

    private static final int PHOTO = 100;
    private static final int PHOTO_ITEM = 101;
    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private PhotosDbHelper mPhotosDbHelper;


    private static UriMatcher buildUriMatcher() {
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = PhotosPersistenceContract.CONTENT_AUTHORITY;

        matcher.addURI(authority, PhotosPersistenceContract.PhotoEntry.TABLE_NAME, PHOTO);
        matcher.addURI(authority, PhotosPersistenceContract.PhotoEntry.TABLE_NAME + "/*", PHOTO_ITEM);

        return matcher;
    }


    @Nullable
    @Override
    public String getType(Uri uri) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case PHOTO:
                return PhotosPersistenceContract.CONTENT_TASK_TYPE;
            case PHOTO_ITEM:
                return PhotosPersistenceContract.CONTENT_TASK_ITEM_TYPE;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }


    @Override
    public boolean onCreate() {
        mPhotosDbHelper = new PhotosDbHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Cursor retCursor;
        switch (sUriMatcher.match(uri)) {
            case PHOTO:
                retCursor = mPhotosDbHelper.getReadableDatabase().query(
                        PhotosPersistenceContract.PhotoEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            case PHOTO_ITEM:
                String[] where = {uri.getLastPathSegment()};
                retCursor = mPhotosDbHelper.getReadableDatabase().query(
                        PhotosPersistenceContract.PhotoEntry.TABLE_NAME,
                        projection,
                        PhotosPersistenceContract.PhotoEntry.COLUMN_NAME_PHOTO_URL + " = ?",
                        where,
                        null,
                        null,
                        sortOrder
                );
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        retCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return retCursor;

    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        final SQLiteDatabase db = mPhotosDbHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        Uri returnUri;

        switch (match) {
            case PHOTO:
                Cursor exists = db.query(
                        PhotosPersistenceContract.PhotoEntry.TABLE_NAME,
                        new String[]{PhotosPersistenceContract.PhotoEntry.COLUMN_NAME_PHOTO_URL},
                        PhotosPersistenceContract.PhotoEntry.COLUMN_NAME_PHOTO_URL + " = ?",
                        new String[]{values.getAsString(PhotosPersistenceContract.PhotoEntry.COLUMN_NAME_PHOTO_URL)},
                        null,
                        null,
                        null
                );
                if (exists.moveToLast()) {
                    long _id = db.update(
                            PhotosPersistenceContract.PhotoEntry.TABLE_NAME, values,
                            PhotosPersistenceContract.PhotoEntry.COLUMN_NAME_PHOTO_URL + " = ?",
                            new String[]{values.getAsString(PhotosPersistenceContract.PhotoEntry.COLUMN_NAME_PHOTO_URL)}
                    );
                    if (_id > 0) {
                        returnUri = PhotosPersistenceContract.PhotoEntry.buildTasksUriWith(_id);
                    } else {
                        throw new android.database.SQLException("Failed to insert row into " + uri);
                    }
                } else {
                    long _id = db.insert(PhotosPersistenceContract.PhotoEntry.TABLE_NAME, null, values);
                    if (_id > 0) {
                        returnUri = PhotosPersistenceContract.PhotoEntry.buildTasksUriWith(_id);
                    } else {
                        throw new android.database.SQLException("Failed to insert row into " + uri);
                    }
                }
                exists.close();
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;

    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mPhotosDbHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int rowsDeleted;

        switch (match) {
            case PHOTO:
                rowsDeleted = db.delete(
                        PhotosPersistenceContract.PhotoEntry.TABLE_NAME, selection, selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        if (selection == null || rowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsDeleted;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mPhotosDbHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int rowsUpdated;

        switch (match) {
            case PHOTO:
                rowsUpdated = db.update(PhotosPersistenceContract.PhotoEntry.TABLE_NAME, values, selection,
                        selectionArgs
                );
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        if (rowsUpdated != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsUpdated;
    }

}
