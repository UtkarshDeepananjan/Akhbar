package com.uds.akhbar.ui.settings;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.uds.akhbar.R;
import com.uds.akhbar.utils.FirebaseHelper;

import de.hdodenhof.circleimageview.CircleImageView;

public class SettingsActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
        FirebaseHelper firebaseHelper = FirebaseHelper.getInstance();
        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.settings, new SettingsFragment())
                    .commit();
        }
        ImageView backButton = findViewById(R.id.back_button);
        backButton.setVisibility(View.VISIBLE);
        backButton.setOnClickListener(v -> finish());
        TextView titleText = findViewById(R.id.title_textView);
        titleText.setText(getString(R.string.title_settings));
        CircleImageView profilePicture = findViewById(R.id.iv_profile_picture);
        Glide.with(getApplicationContext())
                .load(firebaseHelper.getProfilePicture())
                .into(profilePicture);

    }

}