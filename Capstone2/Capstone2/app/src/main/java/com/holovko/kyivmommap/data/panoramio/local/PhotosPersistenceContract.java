
package com.holovko.kyivmommap.data.panoramio.local;

import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * The contract used for the db to save the photos locally.
 */
public final class PhotosPersistenceContract {

    public static final String CONTENT_AUTHORITY = "com.holovko.kyivmommap.data";
    public static final String CONTENT_TASK_TYPE = "vnd.android.cursor.dir/" + CONTENT_AUTHORITY + "/" + PhotoEntry.TABLE_NAME;
    public static final String CONTENT_TASK_ITEM_TYPE = "vnd.android.cursor.item/" + CONTENT_AUTHORITY + "/" + PhotoEntry.TABLE_NAME;
    public static final String VND_ANDROID_CURSOR_ITEM_VND = "vnd.android.cursor.item/vnd." + CONTENT_AUTHORITY + ".";
    private static final String CONTENT_SCHEME = "content://";
    public static final Uri BASE_CONTENT_URI = Uri.parse(CONTENT_SCHEME + CONTENT_AUTHORITY);
    private static final String VND_ANDROID_CURSOR_DIR_VND = "vnd.android.cursor.dir/vnd." + CONTENT_AUTHORITY + ".";
    private static final String SEPARATOR = "/";

    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    public PhotosPersistenceContract() {
    }

    public static Uri getBaseTaskUri(String taskId) {
        return Uri.parse(CONTENT_SCHEME + CONTENT_TASK_ITEM_TYPE + SEPARATOR + taskId);
    }

    /* Inner class that defines the table contents */
    public static abstract class PhotoEntry implements BaseColumns {

        public static final String TABLE_NAME = "photo";


        public static final String COLUMN_NAME_FIREBASE_ID = "firebaseId";
        public static final String COLUMN_NAME_PHOTO_URL = "photoUrl";
        public static final String COLUMN_NAME_OWNER_NAME = "ownerName";
        public static final String COLUMN_NAME_OWNER_URL = "ownerUrl";
        public static final String COLUMN_NAME_PHOTO_FILE_URL = "photoFileUrl";
        public static final Uri CONTENT_TASK_URI = BASE_CONTENT_URI.buildUpon().appendPath(TABLE_NAME).build();
        public static String[] TASKS_COLUMNS = new String[]{
                PhotoEntry._ID,
                PhotoEntry.COLUMN_NAME_FIREBASE_ID,
                PhotoEntry.COLUMN_NAME_PHOTO_URL,
                PhotoEntry.COLUMN_NAME_OWNER_NAME,
                PhotoEntry.COLUMN_NAME_OWNER_URL,
                PhotoEntry.COLUMN_NAME_PHOTO_FILE_URL};

        public static Uri buildTasksUriWith(long id) {
            return ContentUris.withAppendedId(CONTENT_TASK_URI, id);
        }

        public static Uri buildTasksUriWith(String id) {
            Uri uri = CONTENT_TASK_URI.buildUpon().appendPath(id).build();
            return uri;
        }

        public static Uri buildTasksUri() {
            return CONTENT_TASK_URI.buildUpon().build();
        }

    }

}
