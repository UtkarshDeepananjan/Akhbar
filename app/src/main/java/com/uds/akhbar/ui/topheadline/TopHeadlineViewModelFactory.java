package com.uds.akhbar.ui.topheadline;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class TopHeadlineViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private final String countryCode;
    private final String category;

    public TopHeadlineViewModelFactory(String countryCode,String category) {
        this.countryCode = countryCode;
        this.category=category;
    }

    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new TopHeadlineViewModel(countryCode,category);
    }
}
