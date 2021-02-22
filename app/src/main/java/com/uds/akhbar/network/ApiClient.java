package com.uds.akhbar.network;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.uds.akhbar.BuildConfig;
import com.uds.akhbar.model.Articles;
import com.uds.akhbar.model.NewsResponse;

import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.uds.akhbar.utils.Constants.BASE_URL;

public class ApiClient {

    private static ApiInterface apiInterface;
    private static final ApiClient apiClient = new ApiClient();

    public ApiClient() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.HEADERS);

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        if (BuildConfig.DEBUG)
            httpClient.addInterceptor(logging);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();

        apiInterface = retrofit.create(ApiInterface.class);
    }

    public static ApiClient getInstance() {
        return apiClient;
    }

    public LiveData<List<Articles>> getTopHeadlines(String countryCode, String category) {
        MutableLiveData<List<Articles>> mutableLiveData = new MutableLiveData<>();
        Call<NewsResponse> responseCall = apiInterface.getTopHeadlines(countryCode, category);
        responseCall.enqueue(new Callback<NewsResponse>() {
            @Override
            public void onResponse(@NonNull Call<NewsResponse> call, @NonNull Response<NewsResponse> response) {
                if (response.body() != null) {
                    List<Articles> movies = response.body().getArticles();
                    mutableLiveData.setValue(movies);
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
                    List<Articles> movies = response.body().getArticles();
                    mutableLiveData.setValue(movies);
                }
            }

            @Override
            public void onFailure(@NonNull Call<NewsResponse> call, @NonNull Throwable t) {

            }
        });
        return mutableLiveData;
    }

}
