package com.uds.akhbar.utils;

import android.net.Uri;

import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;

public class FirebaseHelper {
    public static FirebaseHelper getInstance() {

        return new FirebaseHelper();
    }

    public FirebaseUser getFirebaseUser() {
        return getFirebaseAuth().getCurrentUser();
    }

    public FirebaseAuth getFirebaseAuth() {
        return FirebaseAuth.getInstance();
    }

    public Uri getProfilePicture() {
        String facebookUserId = "";
        for (UserInfo profile : getFirebaseUser().getProviderData()) {
            if (FacebookAuthProvider.PROVIDER_ID.equals(profile.getProviderId())) {
                facebookUserId = profile.getUid();
            }
        }
        if (facebookUserId.equals("")) {
            return getFirebaseUser().getPhotoUrl();
        } else {
            return Uri.parse("https://graph.facebook.com/" + facebookUserId + "/picture?height=500");
        }
    }
}
