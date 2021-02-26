package com.uds.akhbar.ui.savedarticle;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.uds.akhbar.model.Articles;
import com.uds.akhbar.repository.Repository;

import java.util.List;

public class SavedArticleViewModel extends ViewModel {

    private final LiveData<List<Articles>> mArticles;

    public SavedArticleViewModel() {
        mArticles = Repository.getInstance().getSavedArticles();
    }

    public LiveData<List<Articles>> getArticles() {
        return mArticles;
    }
}