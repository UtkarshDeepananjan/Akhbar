package com.uds.akhbar.ui.settings;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.ShareCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SwitchPreference;

import com.firebase.ui.auth.AuthUI;
import com.uds.akhbar.LoginActivity;
import com.uds.akhbar.R;
import com.uds.akhbar.model.SourcesItem;

import java.util.ArrayList;
import java.util.List;

public class SettingsFragment extends PreferenceFragmentCompat {
    private ArrayList<String> entries;
    private ArrayList<String> entriesValues;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey);
        SharedPreferences sharedPref = getActivity().getSharedPreferences(
                getString(R.string.dark_mode_key), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        SwitchPreference switchPreference = getPreferenceManager().findPreference(getString(R.string.dark_mode_key));
        switchPreference.setOnPreferenceChangeListener((preference, newValue) -> {
            switchPreference.setChecked((Boolean) newValue);
            if ((Boolean) newValue) {

                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                Toast.makeText(getActivity(), "Dark Mode Enable", Toast.LENGTH_SHORT).show();
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                Toast.makeText(getActivity(), "Dark Mode Disabled", Toast.LENGTH_SHORT).show();
            }
            editor.putBoolean(getString(R.string.dark_mode_key), (Boolean) newValue);
            editor.apply();
            return true;
        });
        Preference share = getPreferenceManager().findPreference(getString(R.string.share_key));
        Preference logout = getPreferenceManager().findPreference(getString(R.string.logout_key));
        share.setOnPreferenceClickListener(preference -> {
            String mimeType = "text/plain";
            ShareCompat.IntentBuilder
                    .from(getActivity())
                    .setType(mimeType)
                    .setChooserTitle(getString(R.string.share_app_title))
                    .setText("Use Akhbar App for latest news " + getActivity().getPackageName())
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
//        SettingsViewModel viewModel = new ViewModelProvider(getActivity()).get(SettingsViewModel.class);
        entries = new ArrayList<>();
        entriesValues = new ArrayList<>();
        ListPreference sourcesPreference = getPreferenceManager().findPreference(getString(R.string.news_sources_key));
//            viewModel.getSources().observe(getViewLifecycleOwner(), new Observer<List<SourcesItem>>() {
//                @Override
//                public void onChanged(List<SourcesItem> sourcesItems) {
//                    setUpSourcesPreference(sourcesItems);
//                }
//            });
//            sourcesPreference.setEntries(entries.toArray(new CharSequence[entries.size()]));
//            sourcesPreference.setEntryValues(entriesValues.toArray(new CharSequence[entriesValues.size()]));
    }

    private void setUpSourcesPreference(List<SourcesItem> sourcesItems) {
        for (SourcesItem source : sourcesItems) {
            entries.add(source.getId());
            entriesValues.add(source.getName());
        }
    }
}

