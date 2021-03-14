package com.uds.akhbar.ui.topheadline;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.uds.akhbar.model.Articles;
import com.uds.akhbar.repository.Repository;

import java.util.List;

public class TopHeadlineViewModel extends ViewModel {

    private  LiveData<List<Articles>> mArticles;
    private  MutableLiveData<Object> mutableLiveData;


    public TopHeadlineViewModel(String countryCode, String category) {
        mArticles = Repository.getInstance().getTopHeadlines(countryCode, category);

    }

    public void refresh() {
    }


    public LiveData<List<Articles>> getArticlesList() {
        return mArticles;
    }
}