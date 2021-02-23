package com.uds.akhbar.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.uds.akhbar.model.Articles;
import com.uds.akhbar.model.NewsResponse;
import com.uds.akhbar.model.SourceResponse;
import com.uds.akhbar.model.SourcesItem;
import com.uds.akhbar.network.ApiClient;
import com.uds.akhbar.network.ApiInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Repository {
    private final ApiInterface apiInterface;

    public Repository() {
        apiInterface = ApiClient.getApiService();

    }

    public static Repository getInstance() {
        return new Repository();
    }

    public LiveData<List<Articles>> getTopHeadlines(String countryCode, String category) {
        MutableLiveData<List<Articles>> mutableLiveData = new MutableLiveData<>();
        Call<NewsResponse> responseCall = apiInterface.getTopHeadlines(countryCode, category);
        responseCall.enqueue(new Callback<NewsResponse>() {
            @Override
            public void onResponse(@NonNull Call<NewsResponse> call, @NonNull Response<NewsResponse> response) {
                if (response.body() != null) {
                    List<Articles> articles = response.body().getArticles();
                    mutableLiveData.setValue(articles);
                }
            }

            @Override
            public void onFailure(@NonNull Call<NewsResponse> call, @NonNull Throwable t) {

            }
        });
        return mutableLiveData;
    }

    public LiveData<List<Articles>> getSearchResults(String search) {
        MutableLiveData<List<Articles>> mutableLiveData = new MutableLiveData<>();
        Call<NewsResponse> responseCall = apiInterface.getSearchResults(search);
        responseCall.enqueue(new Callback<NewsResponse>() {
            @Override
            public void onResponse(@NonNull Call<NewsResponse> call, @NonNull Response<NewsResponse> response) {
                if (response.body() != null) {
                    List<Articles> articles = response.body().getArticles();
                    mutableLiveData.setValue(articles);
                }
            }

            @Override
            public void onFailure(@NonNull Call<NewsResponse> call, @NonNull Throwable t) {

            }
        });
        return mutableLiveData;
    }

    public LiveData<List<SourcesItem>> getSourcesResults() {
        MutableLiveData<List<SourcesItem>> mutableLiveData = new MutableLiveData<>();
        Call<SourceResponse> responseCall = apiInterface.getSourcesResults();
        responseCall.enqueue(new Callback<SourceResponse>() {
            @Override
            public void onResponse(@NonNull Call<SourceResponse> call, @NonNull Response<SourceResponse> response) {
                if (response.body() != null) {
                    List<SourcesItem> sources = response.body().getSources();
                    mutableLiveData.setValue(sources);
                }
            }

            @Override
            public void onFailure(@NonNull Call<SourceResponse> call, @NonNull Throwable t) {

            }
        });
        return mutableLiveData;
    }
}
