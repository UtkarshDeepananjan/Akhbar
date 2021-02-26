package com.uds.akhbar.ui.settings;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;
import com.uds.akhbar.R;
import com.uds.akhbar.utils.FirebaseHelper;

import de.hdodenhof.circleimageview.CircleImageView;

public class SettingsActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
        setContentView(R.layout.settings_activity);
        FirebaseHelper firebaseHelper = FirebaseHelper.getInstance();
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