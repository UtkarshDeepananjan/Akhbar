package com.uds.akhbar.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.uds.akhbar.R;
import com.uds.akhbar.ui.detailarticle.ArticleDetailActivity;


public class NewsAppWidget extends AppWidgetProvider {


    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.news_app_widget);
            Intent intent = new Intent(context, ArticlesWidgetService.class);
            views.setRemoteAdapter(R.id.list_view, intent);
            appWidgetManager.updateAppWidget(appWidgetIds, views);
            appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.list_view);

            Intent detailIntent = new Intent(context, ArticleDetailActivity.class);
            PendingIntent appPendingIntent = PendingIntent.getActivity(context, 0, detailIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            views.setPendingIntentTemplate(R.id.list_view, appPendingIntent);
            views.setEmptyView(R.id.list_view, R.id.empty_view);

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


}