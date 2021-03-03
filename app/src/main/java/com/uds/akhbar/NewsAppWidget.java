package com.uds.akhbar;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.RemoteViews;

import com.squareup.picasso.Picasso;
import com.uds.akhbar.model.Articles;
import com.uds.akhbar.ui.detailarticle.ArticleDetailActivity;

import java.util.List;


public class NewsAppWidget extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, List<Articles> articles, int selected,
                                int appWidgetId) {

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.news_app_widget);
        if (articles != null && selected > -1) {
            views.setViewVisibility(R.id.iv_news_image, View.VISIBLE);
            views.setViewVisibility(R.id.tv_news_title, View.VISIBLE);
            views.setViewVisibility(R.id.tv_page, View.VISIBLE);
            views.setViewVisibility(R.id.ib_next, View.VISIBLE);
            views.setViewVisibility(R.id.ib_previous, View.VISIBLE);
            views.setViewVisibility(R.id.tv_error, View.GONE);
            if (articles.size() > 0 && selected < articles.size()) {
                views.setTextViewText(R.id.tv_news_title, articles.get(selected).getTitle());

                views.setTextViewText(R.id.tv_page, (selected + 1) + "/" + articles.size());
            }


        } else {
            views.setViewVisibility(R.id.iv_news_image, View.GONE);
            views.setViewVisibility(R.id.tv_news_title, View.GONE);
            views.setViewVisibility(R.id.tv_page, View.GONE);
            views.setViewVisibility(R.id.ib_next, View.GONE);
            views.setViewVisibility(R.id.ib_previous, View.GONE);
            views.setViewVisibility(R.id.tv_error, View.VISIBLE);

            Intent home = new Intent(context, HomeScreenActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, home, PendingIntent.FLAG_UPDATE_CURRENT);
            views.setOnClickPendingIntent(R.id.widget_parent, pendingIntent);
        }
        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

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