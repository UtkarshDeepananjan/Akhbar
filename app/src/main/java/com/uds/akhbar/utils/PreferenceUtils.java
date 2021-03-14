package com.uds.akhbar.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.uds.akhbar.R;

public class PreferenceUtils {
    private static SharedPreferences sharedPreferences;

    public PreferenceUtils(Context context) {
        sharedPreferences = context.getSharedPreferences(context.getString(R.string.preference_file_key), Context.MODE_PRIVATE);
    }

    public String getString(String key) {
        return sharedPreferences.getString(key, "");
    }

    public void setString(String key, String value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }
}
