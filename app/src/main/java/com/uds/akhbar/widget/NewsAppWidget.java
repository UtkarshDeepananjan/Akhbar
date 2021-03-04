package com.uds.akhbar.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.uds.akhbar.R;


public class NewsAppWidget extends AppWidgetProvider {


    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.news_app_widget);
        Intent intent = new Intent(context, ArticlesWidgetService.class);
        views.setRemoteAdapter(R.id.list_view, intent);
        /*views.setEmptyView(R.id.list_view, R.id.empty_view);*/
        appWidgetManager.updateAppWidget(appWidgetIds, views);
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.list_view);
        super.onUpdate(context, appWidgetManager, appWidgetIds);

    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
    }
}