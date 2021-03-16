package com.uds.akhbar.utils;

import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;
import com.uds.akhbar.BuildConfig;
import com.yariksoffice.lingver.Lingver;

import java.util.Locale;

import timber.log.Timber;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
       Lingver.init(this, Locale.getDefault().getLanguage());
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }

    }


}
