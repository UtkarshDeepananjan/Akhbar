package com.uds.akhbar.ui.topheadline;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class TopHeadlineViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private final String countryCode;
    private final String category;
    private final Context context;

    public TopHeadlineViewModelFactory(String countryCode, String category,Context context) {
        this.countryCode = countryCode;
        this.category = category;
        this.context=context;
    }

    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new TopHeadlineViewModel(countryCode, category,context);
    }
}
