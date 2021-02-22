package com.uds.akhbar;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SwitchPreference;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class SettingsActivity extends AppCompatActivity {
    FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        setContentView(R.layout.settings_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.settings, new SettingsFragment())
                    .commit();
        }
        TextView titleText = findViewById(R.id.title_textView);
        titleText.setText(getString(R.string.title_settings));
        CircleImageView profilePicture = findViewById(R.id.iv_profile_picture);
        Picasso.get().load(firebaseUser.getPhotoUrl()).into(profilePicture);

    }

    public static class SettingsFragment extends PreferenceFragmentCompat {
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

        }
    }
}