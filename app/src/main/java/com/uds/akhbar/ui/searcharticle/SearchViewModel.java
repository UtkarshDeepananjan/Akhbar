package com.uds.akhbar.ui.searcharticle;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.uds.akhbar.model.Articles;
import com.uds.akhbar.network.ApiClient;

import java.util.List;

public class SearchViewModel extends ViewModel {


    public SearchViewModel() {

    }

    public LiveData<List<Articles>> getArticles(String search) {
        return ApiClient.getInstance().getSearchResults(search);
    }
}