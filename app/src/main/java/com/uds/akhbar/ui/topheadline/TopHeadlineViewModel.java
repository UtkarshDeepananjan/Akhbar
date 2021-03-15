package com.uds.akhbar.ui.topheadline;

import android.util.Pair;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.uds.akhbar.model.Articles;
import com.uds.akhbar.repository.Repository;

import java.util.List;

public class TopHeadlineViewModel extends ViewModel {

    private LiveData<List<Articles>> mArticles;
    private final Pair<String, String> countryCategoryPair;


    public TopHeadlineViewModel(String countryCode, String category) {
        mArticles = Repository.getInstance().getTopHeadlines(countryCode, category);
        countryCategoryPair = new Pair<>(countryCode, category);

    }

    private LiveData<List<Articles>> refresh(String countryCode, String category) {
        return Repository.getInstance().getTopHeadlines(countryCode, category);
    }


    public LiveData<List<Articles>> getArticlesList(Boolean refresh) {
        if (refresh) {
            mArticles = refresh(countryCategoryPair.first, countryCategoryPair.second);
        }
        return mArticles;
    }

    public LiveData<List<Articles>> getArticlesList(String countryCode, Boolean refresh) {
        if (refresh) {
            mArticles = refresh(countryCode, countryCategoryPair.second);
        }
        return mArticles;
    }
}
