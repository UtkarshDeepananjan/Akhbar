package com.uds.akhbar.network;

import android.content.Context;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.uds.akhbar.utils.Constants.BASE_URL;

public class ApiClient {

    private static Retrofit retrofit;


    public static ApiInterface getApiService(Context context) {
        Interceptor networkInterceptor = new Interceptor() {
            @Override
            public Response intercept(Interceptor.Chain chain) throws IOException {
                // set max-age and max-stale properties for cache header
                CacheControl cacheControl = new CacheControl.Builder()
                        .maxAge(1, TimeUnit.HOURS)
                        .maxStale(3, TimeUnit.DAYS)
                        .build();
                return chain.proceed(chain.request())
                        .newBuilder()
                        .removeHeader("Pragma")
                        .header("Cache-Control", cacheControl.toString())
                        .build();
            }
        };

        if (retrofit == null) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BASIC);
            Cache cache = new Cache(context.getApplicationContext().getCacheDir(), 5 * 1024 * 1024);
            // Building OkHttp client
            OkHttpClient httpClient = new OkHttpClient.Builder()
                    .cache(cache)
                    .addNetworkInterceptor(networkInterceptor)
                    .addInterceptor(logging).build();

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient)
                    .build();
        }
        return retrofit.create(ApiInterface.class);
    }

}
