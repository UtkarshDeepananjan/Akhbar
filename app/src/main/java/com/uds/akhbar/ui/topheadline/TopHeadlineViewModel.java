package com.uds.akhbar.ui.topheadline;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.uds.akhbar.model.Articles;
import com.uds.akhbar.repository.Repository;

import java.util.List;

public class TopHeadlineViewModel extends ViewModel {

    private final LiveData<List<Articles>> mArticles;


    public TopHeadlineViewModel(String countryCode, String category) {
        mArticles = Repository.getInstance().getTopHeadlines(countryCode, category);
    }

    public LiveData<List<Articles>> getArticlesList() {
        return mArticles;
    }
}