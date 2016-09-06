package com.holovko.kyivmommap.widget;

import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.widget.RemoteViewsService;

import com.holovko.kyivmommap.Injection;

/**
 * Created by Andrey Holovko on 9/5/16.
 */

public class UpdateWidgetService extends RemoteViewsService{

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        int appWidgetId = intent.getIntExtra(
                AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID);

        return (new ListProvider(this.getApplicationContext(), intent,
                Injection.provideDataRepository(this.getApplicationContext()),
        Injection.provideSharedPreferences(this.getApplicationContext())));
    }
}