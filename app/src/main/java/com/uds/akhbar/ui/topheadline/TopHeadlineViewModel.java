package com.uds.akhbar.ui.topheadline;

import android.content.Context;
import android.util.Pair;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.uds.akhbar.model.NewsResponse;
import com.uds.akhbar.repository.Repository;

public class TopHeadlineViewModel extends ViewModel {

    private LiveData<NewsResponse> mArticles;
    private final Pair<String, String> countryCategoryPair;


    public TopHeadlineViewModel(String countryCode, String category, Context context) {
        mArticles = Repository.getInstance(context).getTopHeadlines(countryCode, category);
        countryCategoryPair = new Pair<>(countryCode, category);

    }

    private LiveData<NewsResponse> refresh(String countryCode, String category,Context context) {
        return Repository.getInstance(context).getTopHeadlines(countryCode, category);
    }


    public LiveData<NewsResponse> getArticlesList(Boolean refresh,Context context) {
        if (refresh) {
            mArticles = refresh(countryCategoryPair.first, countryCategoryPair.second,context);
        }
        return mArticles;
    }

    public LiveData<NewsResponse> getArticlesList(String countryCode, Boolean refresh,Context context) {
        if (refresh) {
            mArticles = refresh(countryCode, countryCategoryPair.second,context);
        }
        return mArticles;
    }
}
