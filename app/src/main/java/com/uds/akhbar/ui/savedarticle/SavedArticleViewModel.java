package com.uds.akhbar.ui.savedarticle;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.uds.akhbar.model.Articles;
import com.uds.akhbar.repository.Repository;

import java.util.List;

public class SavedArticleViewModel extends ViewModel {

    public LiveData<List<Articles>> getArticles(Context context) {
        return Repository.getInstance(context).getSavedArticles();
    }
}