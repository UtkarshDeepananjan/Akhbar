package com.uds.akhbar.ui.searcharticle;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.uds.akhbar.model.Articles;
import com.uds.akhbar.model.NewsResponse;
import com.uds.akhbar.repository.Repository;

import java.util.List;

public class SearchViewModel extends ViewModel {


    public SearchViewModel() {

    }

    public LiveData<NewsResponse> getArticles(String search, Context context) {
        return Repository.getInstance(context).getSearchResults(search);
    }
}