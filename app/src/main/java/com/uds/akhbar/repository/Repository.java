package com.uds.akhbar.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.uds.akhbar.model.Articles;
import com.uds.akhbar.model.NewsResponse;
import com.uds.akhbar.model.SourceResponse;
import com.uds.akhbar.model.SourcesItem;
import com.uds.akhbar.network.ApiClient;
import com.uds.akhbar.network.ApiInterface;
import com.uds.akhbar.utils.FirebaseHelper;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Repository {
    private final ApiInterface apiInterface;
    private  DatabaseReference mReference;
    private boolean exists;

    public Repository() {
        apiInterface = ApiClient.getApiService();
       /* mReference = FirebaseDatabase.getInstance().getReference(FirebaseHelper.getInstance().getFirebaseUser().getUid());
        mReference.keepSynced(true);*/
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

    public String saveArticles(Articles articles) {
        String key = mReference.push().getKey();
        articles.setId(key);
        mReference.child("Saved Articles").child(key).setValue(articles);
        return key;
    }

    public void deleteArticle(String id) {
        mReference.child("Saved Articles").child(id).removeValue();
    }

    public boolean checkArticleExists(Articles article) {
        Query query = mReference.child("Saved Articles").orderByChild("title");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                exists = true;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                exists = false;
            }
        });
        return exists;

    }

    public MutableLiveData<List<Articles>> getSavedArticles() {
        MutableLiveData<List<Articles>> liveData = new MutableLiveData<>();
        mReference.child("Saved Articles").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Articles> articlesList = new ArrayList<>();
                for (DataSnapshot d : snapshot.getChildren()) {
                    Articles articles = d.getValue(Articles.class);
                    articlesList.add(articles);
                }
                liveData.setValue(articlesList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return liveData;
    }
}
