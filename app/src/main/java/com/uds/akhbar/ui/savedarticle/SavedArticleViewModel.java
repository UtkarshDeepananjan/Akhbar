package com.uds.akhbar.ui.savedarticle;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SavedArticleViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public SavedArticleViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is notifications fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}