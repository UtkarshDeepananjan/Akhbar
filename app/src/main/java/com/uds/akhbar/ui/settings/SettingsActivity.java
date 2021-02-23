package com.uds.akhbar.ui.settings;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.ShareCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SwitchPreference;

import com.firebase.ui.auth.AuthUI;
import com.squareup.picasso.Picasso;
import com.uds.akhbar.LoginActivity;
import com.uds.akhbar.R;
import com.uds.akhbar.model.SourcesItem;
import com.uds.akhbar.utils.FirebaseHelper;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class SettingsActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
        setContentView(R.layout.settings_activity);
        FirebaseHelper firebaseHelper = FirebaseHelper.newInstance();
        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.settings, new SettingsFragment())
                    .commit();
        }
        TextView titleText = findViewById(R.id.title_textView);
        titleText.setText(getString(R.string.title_settings));
        CircleImageView profilePicture = findViewById(R.id.iv_profile_picture);
        Picasso.get().load(firebaseHelper.getProfilePicture()).into(profilePicture);

    }

}