package com.uds.akhbar.network;

import com.uds.akhbar.BuildConfig;
import com.uds.akhbar.model.NewsResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface ApiInterface {

    @Headers({"X-Api-Key:" + BuildConfig.ApiKey})
    @GET("top-headlines")
    Call<NewsResponse> getTopHeadlines(@Query("country") String country, @Query("category") String category);

    @Headers({"X-Api-Key:" + BuildConfig.ApiKey})
    @GET("everything")
    Call<NewsResponse> getSearchResults(@Query("q") String query);


}
