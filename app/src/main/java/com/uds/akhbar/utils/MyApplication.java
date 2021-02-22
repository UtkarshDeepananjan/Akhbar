package com.uds.akhbar.utils;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.appcompat.app.AppCompatDelegate;

import com.uds.akhbar.R;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        SharedPreferences sharedPref = getSharedPreferences(
                getString(R.string.dark_mode_key), Context.MODE_PRIVATE);
        boolean value = sharedPref.getBoolean(getString(R.string.dark_mode_key), false);
        if (value) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
    }
}
