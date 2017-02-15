package com.holovko.kyivmommap.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.RemoteViews;

import com.holovko.kyivmommap.R;
import com.holovko.kyivmommap.ui.details.DetailsActivity;

import static com.google.android.gms.internal.zzs.TAG;

/**
 * Implementation of App Widget functionality.
 */
public class MyAppWidget extends AppWidgetProvider {

    public static final String PLACE_KEY = "bundle_key";
    public static final String PLACE_VALUE = "bundle_place";
    public static final String ACTION_DATA_LOADED = "com.holovko.kyivmommap.widget.ACTION_DATA_LOADED";
    //For the updateView after load
    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();
            Log.e(TAG, "onReceive: action = "+action);
            if (ACTION_DATA_LOADED.equals(action)) {
                AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
                int appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                        AppWidgetManager.INVALID_APPWIDGET_ID);

                appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetId,R.id.lv_widget);
            }
        }
    };

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
    }

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        // Set up the intent that starts the StackViewService, which will
        // provide the views for this collection.
        Intent intent = new Intent(context, UpdateWidgetService.class);
        // Add the app widget ID to the intent extras.
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));

        RemoteViews rv = new RemoteViews(context.getPackageName(), R.layout.my_app_widget);
        rv.setRemoteAdapter(appWidgetId, R.id.lv_widget,intent);
        rv.setEmptyView(R.id.lv_widget, R.id.tv_empty_view);


        Intent startActivityIntent = new Intent(context, DetailsActivity.class);
        PendingIntent startActivityPendingIntent = PendingIntent.getActivity(context, 0, startActivityIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        rv.setPendingIntentTemplate(R.id.lv_widget, startActivityPendingIntent);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, rv);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        LocalBroadcastManager.getInstance(context.getApplicationContext())
                .registerReceiver(mBroadcastReceiver, new IntentFilter(ACTION_DATA_LOADED));
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }



}

