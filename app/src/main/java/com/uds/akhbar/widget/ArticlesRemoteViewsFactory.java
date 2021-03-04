package com.uds.akhbar.widget;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
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
        Picasso.get().load(articles.getUrlToImage()).into(new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                rv.setImageViewBitmap(R.id.article_image, bitmap);
            }

            @Override
            public void onBitmapFailed(Exception e, Drawable errorDrawable) {

            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
        });
        Toast.makeText(context, articles.getTitle(), Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(context, ArticleDetailActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra(ArticleDetailActivity.ARTICLE_DETAIL, articles);
        rv.setOnClickFillInIntent(R.id.article_title, intent);
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
        SharedPreferences sharedPref = context.getSharedPreferences("top_headlines_widget", Context.MODE_PRIVATE);
        String json = sharedPref.getString("top_headlines_widget", "");
        Gson gson = new Gson();
        mArticles = gson.fromJson(json, new TypeToken<List<Articles>>() {
        }.getType());
        Timber.tag("uds_article").d(mArticles.toString());

    }

}
