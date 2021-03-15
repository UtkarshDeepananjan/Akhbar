package com.uds.akhbar.widget;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.preference.PreferenceManager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.Request;
import com.bumptech.glide.request.target.AppWidgetTarget;
import com.bumptech.glide.request.target.SizeReadyCallback;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.uds.akhbar.R;
import com.uds.akhbar.model.Articles;
import com.uds.akhbar.ui.detailarticle.ArticleDetailActivity;

import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;

public class ArticlesRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private final Context context;
    private List<Articles> mArticles;

    public ArticlesRemoteViewsFactory(Context context) {
        this.context = context;
        mArticles = new ArrayList<>();
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
        getArticlesForWidget();
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return 5;
    }

    @Override
    public RemoteViews getViewAt(int position) {
        Articles articles = mArticles.get(position);
        RemoteViews rv = new RemoteViews(context.getPackageName(), R.layout.articles_widget_list_item);
        rv.setTextViewText(R.id.article_title, articles.getTitle());
        /*AppWidgetTarget appWidgetTarget=new AppWidgetTarget(context,rv,R.id.article_image,a)
        Glide.with(context)
                .load(articles.getUrlToImage())
                .into(new );*/
        Bundle extra=new Bundle();
        extra.putParcelable(ArticleDetailActivity.ARTICLE_DETAIL,articles);
        Intent intent = new Intent();
        intent.putExtra(ArticleDetailActivity.ARTICLE_DETAIL, articles);
        intent.putExtras(extra);
        rv.setOnClickFillInIntent(R.id.root, intent);
        return rv;
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
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    private void getArticlesForWidget() {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        String json = sharedPref.getString(context.getString(R.string.pref_widget_key), "");
        Gson gson = new Gson();
        mArticles = gson.fromJson(json, new TypeToken<List<Articles>>() {
        }.getType());
        Timber.tag("uds_article").d(mArticles.toString());

    }

}
