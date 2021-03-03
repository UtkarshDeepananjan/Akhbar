package com.uds.akhbar.utils;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.uds.akhbar.model.Articles;
import com.uds.akhbar.repository.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WidgetWorker extends Worker {
    private List<Articles> mSavedArticles;
    private Context context;


    public WidgetWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        mSavedArticles = new ArrayList<>();
        this.context=context;
    }

    @NonNull
    @Override
    public Result doWork() {
        getSavedArticles();
        Map<String, List<Articles>> listMap = new HashMap<>();
        listMap.put("articles", mSavedArticles);
      return Result.success();
    }

    public void getSavedArticles() {
        LiveData<List<Articles>> savedArticles = Repository.getInstance().getSavedArticles();
        savedArticles.observeForever(articles ->
        {
            mSavedArticles=articles;
        });

    }
}
