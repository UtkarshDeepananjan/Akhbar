package com.uds.akhbar.ui.settings;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.ShareCompat;
import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceScreen;
import androidx.preference.SwitchPreference;

import com.firebase.ui.auth.AuthUI;
import com.uds.akhbar.R;
import com.uds.akhbar.ui.home.LoginActivity;
import com.yariksoffice.lingver.Lingver;

public class SettingsFragment extends PreferenceFragmentCompat implements SharedPreferences.OnSharedPreferenceChangeListener {

    private SettingsActivity settingsActivity;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        settingsActivity = (SettingsActivity) context;
    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey);
        SharedPreferences sharedPreferences = getPreferenceScreen().getSharedPreferences();
        PreferenceScreen preferenceScreen = getPreferenceScreen();
        int count = preferenceScreen.getPreferenceCount();
        for (int i = 0; i < count; i++) {
            Preference preference = preferenceScreen.getPreference(i);
            if (preference instanceof ListPreference) {
                String value = sharedPreferences.getString(preference.getKey(), "");
                setPreferenceSummary(preference, value);
            }

        }
        SwitchPreference darkModeSwitch = findPreference(getString(R.string.pref_dark_mode_key));
        darkModeSwitch.setChecked(sharedPreferences.getBoolean(getString(R.string.pref_dark_mode_key), true));

        Preference logout = findPreference(getString(R.string.pref_logout_key));
        if (logout != null) {
            logout.setOnPreferenceClickListener(preference -> {
                AuthUI.getInstance()
                        .signOut(settingsActivity)
                        .addOnCompleteListener(task -> {
                            startActivity(new Intent(getActivity(), LoginActivity.class));
                            settingsActivity.finishAffinity();
                        });
                return false;
            });
        }
        Preference share = findPreference(getString(R.string.pref_share_key));
        if (share != null) {
            share.setOnPreferenceClickListener(preference -> {
                String mimeType = "text/plain";
                ShareCompat.IntentBuilder
                        .from(settingsActivity)
                        .setType(mimeType)
                        .setChooserTitle(getString(R.string.share_app_title))
                        .setText("Use Akhbar App for latest news https://play.google.com/store/apps/details?id=" + settingsActivity.getPackageName())
                        .startChooser();
                return false;
            });
        }
    }

    public void setPreferenceSummary(Preference preference, String value) {
        if (preference instanceof ListPreference) {
            ListPreference listPreference = (ListPreference) preference;
            int prefIndex = listPreference.findIndexOfValue(value);
            if (prefIndex >= 0) {
                listPreference.setSummary(listPreference.getEntries()[prefIndex]);
            }
        }
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        Preference preference = findPreference(key);
        if (preference != null) {
            if (preference instanceof ListPreference) {
                if (key.equals(getString(R.string.pref_country_key))) {
                    String value = sharedPreferences.getString(preference.getKey(), "");
                    setPreferenceSummary(preference, value);
                } else if (key.equals(getString(R.string.pref_language_key))) {
                    Lingver.getInstance().setLocale(settingsActivity.getBaseContext(), ((ListPreference) preference).getValue());
                    Intent i = settingsActivity.getBaseContext().getPackageManager().getLaunchIntentForPackage(settingsActivity.getBaseContext().getPackageName());
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                    settingsActivity.finish();
                }
            } else if ((preference instanceof SwitchPreference)) {
                boolean value = sharedPreferences.getBoolean(preference.getKey(), true);
                if (value) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                }
            }
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
    }
}

