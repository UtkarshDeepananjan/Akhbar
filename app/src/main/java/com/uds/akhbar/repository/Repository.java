package com.uds.akhbar.repository;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.uds.akhbar.model.Articles;
import com.uds.akhbar.model.NewsResponse;
import com.uds.akhbar.network.ApiClient;
import com.uds.akhbar.network.ApiInterface;
import com.uds.akhbar.utils.FirebaseHelper;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Repository {
    private final ApiInterface apiInterface;
    private final DatabaseReference mReference;

    public Repository(Context context) {
        apiInterface = ApiClient.getApiService(context);
        mReference = FirebaseDatabase.getInstance().getReference(FirebaseHelper.getInstance().getFirebaseUser().getUid());
        mReference.keepSynced(true);
    }

    public static Repository getInstance(Context context) {
        return new Repository(context);
    }

    public LiveData<NewsResponse> getTopHeadlines(String countryCode, String category) {
        MutableLiveData<NewsResponse> mutableLiveData = new MutableLiveData<>();
        Call<NewsResponse> responseCall = apiInterface.getTopHeadlines(countryCode, category);
        responseCall.enqueue(new Callback<NewsResponse>() {
            @Override
            public void onResponse(@NonNull Call<NewsResponse> call, @NonNull Response<NewsResponse> response) {
                if (response.body() != null) {
                    mutableLiveData.setValue(response.body());
                } else {
                    Gson gson = new Gson();
                    Type token = new TypeToken<NewsResponse>() {
                    }.getType();
                    if (response.errorBody() != null) {
                        NewsResponse errorResponse = gson.fromJson(response.errorBody().charStream(), token);
                        mutableLiveData.postValue(errorResponse);
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<NewsResponse> call, @NonNull Throwable t) {

            }
        });
        return mutableLiveData;
    }

    public LiveData<NewsResponse> getSearchResults(String search) {
        MutableLiveData<NewsResponse> mutableLiveData = new MutableLiveData<>();
        Call<NewsResponse> responseCall = apiInterface.getSearchResults(search,"popularity");
        responseCall.enqueue(new Callback<NewsResponse>() {
            @Override
            public void onResponse(@NonNull Call<NewsResponse> call, @NonNull Response<NewsResponse> response) {
                if (response.body() != null) {
                    mutableLiveData.setValue(response.body());
                } else {
                    Gson gson = new Gson();
                    Type token = new TypeToken<NewsResponse>() {
                    }.getType();
                    if (response.errorBody() != null) {
                        NewsResponse errorResponse = gson.fromJson(response.errorBody().charStream(), token);
                        mutableLiveData.postValue(errorResponse);
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<NewsResponse> call, @NonNull Throwable t) {

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
