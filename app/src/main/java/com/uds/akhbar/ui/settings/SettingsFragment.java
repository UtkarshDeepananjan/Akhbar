package com.uds.akhbar.ui.settings;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.ShareCompat;
import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;
import androidx.preference.PreferenceScreen;
import androidx.preference.SwitchPreference;

import com.firebase.ui.auth.AuthUI;
import com.uds.akhbar.R;
import com.uds.akhbar.ui.home.LoginActivity;

public class SettingsFragment extends PreferenceFragmentCompat implements SharedPreferences.OnSharedPreferenceChangeListener {


    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey);
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getActivity());
        SharedPreferences.Editor editor = sharedPref.edit();
        SharedPreferences sharedPreferences = getPreferenceScreen().getSharedPreferences();
        PreferenceScreen preferenceScreen = getPreferenceScreen();
        int count = preferenceScreen.getPreferenceCount();
        for (int i = 0; i < count; i++) {
            Preference preference = preferenceScreen.getPreference(i);
            if (preference instanceof ListPreference) {
                String value = sharedPreferences.getString(preference.getKey(), "");
                setPreferenceSummary(preference, value);
            } else if (preference instanceof SwitchPreference) {

            }

        }

        SwitchPreference switchPreference = getPreferenceManager().findPreference(getString(R.string.dark_mode_key));
        switchPreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                switchPreference.setChecked((Boolean) newValue);
                editor.putBoolean(SettingsFragment.this.getString(R.string.dark_mode_key), (Boolean) newValue);
                editor.apply();
                return true;
            }
        });


        Preference share = getPreferenceManager().findPreference(getString(R.string.share_key));
        Preference logout = getPreferenceManager().findPreference(getString(R.string.logout_key));
        share.setOnPreferenceClickListener(preference -> {
            String mimeType = "text/plain";
            ShareCompat.IntentBuilder
                    .from(getActivity())
                    .setType(mimeType)
                    .setChooserTitle(getString(R.string.share_app_title))
                    .setText("Use Akhbar App for latest news https://play.google.com/store/apps/details?id=" + getActivity().getPackageName())
                    .startChooser();
            return true;
        });
        logout.setOnPreferenceClickListener(preference -> {
            AuthUI.getInstance()
                    .signOut(getActivity())
                    .addOnCompleteListener(task -> {
                        startActivity(new Intent(getActivity(), LoginActivity.class));
                        getActivity().finishAffinity();
                    });
            return true;
        });
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
                String value = sharedPreferences.getString(preference.getKey(), "");
                Toast.makeText(getContext(), ((ListPreference) preference).getValue(), Toast.LENGTH_SHORT).show();
                setPreferenceSummary(preference, value);
            } else if ((preference instanceof SwitchPreference)) {
                boolean value = sharedPreferences.getBoolean(preference.getKey(), false);
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

