package com.holovko.kyivmommap.widget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.holovko.kyivmommap.R;
import com.holovko.kyivmommap.data.Constant;
import com.holovko.kyivmommap.data.DataRepository;
import com.holovko.kyivmommap.data.DataSource;
import com.holovko.kyivmommap.model.firebase.Place;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Andrey Holovko on 9/5/16.
 */
public class ListProvider implements RemoteViewsService.RemoteViewsFactory {
    private final Context mContext;
    private final int mAppWidgetId;
    private final DataRepository mDataRepository;
    private final SharedPreferences mSharedPreferences;
    private List<Map.Entry<String, Place>> mDataset = new ArrayList<>();

    public ListProvider(Context applicationContext, Intent intent, DataRepository dataRepository, SharedPreferences sharedPreferences) {
        mContext = applicationContext;
        mAppWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID);
        mDataRepository = dataRepository;
        mSharedPreferences = sharedPreferences;
    }

    @Override
    public void onCreate() {}

    @Override
    public void onDataSetChanged() {
        populateListItem();
    }

    private void populateListItem() {
        Set<String> favouriteKeys = mSharedPreferences.getStringSet(Constant.FAVOURITE_LIST, new HashSet<String>());

        if (favouriteKeys.size() > 0) {
            mDataRepository.getPlacesByKey(new ArrayList<>(favouriteKeys), new DataSource.GetPlacesCallback() {
                @Override
                public void onPlacesLoaded(Map<String, Place> places) {
                    if(mDataset.size()==0){
                        Intent widgetUpdateIntent = new Intent(MyAppWidget.ACTION_DATA_LOADED);
                        widgetUpdateIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId);
                        LocalBroadcastManager.getInstance(mContext).sendBroadcast(widgetUpdateIntent);

                    }
                    mDataset = new ArrayList<>(places.entrySet());
                }

                @Override
                public void onDataNotAvailable() {

                }
            });
        }
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return mDataset.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        final RemoteViews remoteView = new RemoteViews(
                mContext.getPackageName(), R.layout.item_widget);
        Map.Entry<String, Place> entry = mDataset.get(position);
        Place place = entry.getValue();
        remoteView.setTextViewText(R.id.tv_title, place.title());
        try {
            Bitmap image = Picasso.with(mContext).load(place.pic()).centerCrop().resize(600,400).get();
            remoteView.setImageViewBitmap(R.id.iv_place_pic, image);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // that is set on the collection view in StackWidgetProvider.
        Bundle extras = new Bundle();
        extras.putString(MyAppWidget.PLACE_KEY, entry.getKey());
        extras.putParcelable(MyAppWidget.PLACE_VALUE, place);
        Intent fillInIntent = new Intent();
        fillInIntent.putExtras(extras);
        // Make it possible to distinguish the individual on-click
        // action of a given item
        remoteView.setOnClickFillInIntent(R.id.ll_favourite_item, fillInIntent);
        return remoteView;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
