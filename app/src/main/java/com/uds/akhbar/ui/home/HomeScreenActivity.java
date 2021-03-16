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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        if (sharedPreferences.getBoolean(getString(R.string.pref_dark_mode_key), true)) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        setContentView(R.layout.activity_home_screen);
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
                if (!aBoolean) {
                    Snackbar mySnackBar = Snackbar.make(root, "No Internet Connection", Snackbar.LENGTH_SHORT);
                    mySnackBar.setBackgroundTint(ContextCompat.getColor(this, android.R.color.holo_red_dark));
                    mySnackBar.setAnchorView(navView);
                    mySnackBar.show();
                }
            });
        }
    }


}