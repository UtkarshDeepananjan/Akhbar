package com.uds.akhbar.ui.home;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.content.ContextCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;
import androidx.preference.PreferenceManager;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.uds.akhbar.R;
import com.uds.akhbar.ui.settings.SettingsActivity;
import com.uds.akhbar.utils.FirebaseHelper;
import com.uds.akhbar.utils.NetworkUtils;

import de.hdodenhof.circleimageview.CircleImageView;

public class HomeScreenActivity extends AppCompatActivity {
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        FirebaseHelper firebaseHelper = FirebaseHelper.getInstance();
        BottomNavigationView navView = findViewById(R.id.nav_view);
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupWithNavController(navView, navController);
        TextView titleText = findViewById(R.id.title_textView);
        navController.addOnDestinationChangedListener((controller, destination, arguments) -> titleText.setText(destination.getLabel()));
        CircleImageView profilePicture = findViewById(R.id.iv_profile_picture);
        Glide.with(getApplicationContext())
                .load(firebaseHelper.getProfilePicture())
                .centerCrop()
                .into(profilePicture);
        profilePicture.setOnClickListener(v -> startActivity(new Intent(HomeScreenActivity.this, SettingsActivity.class)));
        CoordinatorLayout root = findViewById(R.id.container);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            NetworkUtils networkUtils = new NetworkUtils(this);
            networkUtils.observe(this, aBoolean -> {
                Snackbar mySnackBar;
                if (aBoolean) {
                    if (sharedPreferences.getBoolean(getString(R.string.pref_first_launch), false)) {
                        mySnackBar = Snackbar.make(root, getString(R.string.internet_available), Snackbar.LENGTH_SHORT);
                        mySnackBar.setBackgroundTint(ContextCompat.getColor(this, android.R.color.holo_green_dark));
                        mySnackBar.setAnchorView(navView);
                        mySnackBar.setTextColor(ContextCompat.getColor(this, R.color.white));
                        mySnackBar.show();
                    }
                    sharedPreferences.edit().putBoolean(getString(R.string.pref_first_launch), true).apply();

                } else {
                    mySnackBar = Snackbar.make(root, getString(R.string.internet_unavailable), Snackbar.LENGTH_INDEFINITE);
                    mySnackBar.setBackgroundTint(ContextCompat.getColor(this, android.R.color.holo_red_dark));
                    mySnackBar.setAnchorView(navView);
                    mySnackBar.setTextColor(ContextCompat.getColor(this, R.color.white));
                    mySnackBar.show();
                }

            });
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        sharedPreferences.edit().putBoolean(getString(R.string.pref_first_launch), false).apply();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        sharedPreferences.edit().putBoolean(getString(R.string.pref_first_launch), false).apply();
    }
}