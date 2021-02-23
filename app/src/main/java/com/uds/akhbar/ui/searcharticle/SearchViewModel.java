package com.uds.akhbar.ui.searcharticle;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.uds.akhbar.model.Articles;
import com.uds.akhbar.repository.Repository;

import java.util.List;

public class SearchViewModel extends ViewModel {


    public SearchViewModel() {

    }

    public LiveData<List<Articles>> getArticles(String search) {
        return Repository.getInstance().getSearchResults(search);
    }
}