package com.uds.akhbar.ui.settings;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.uds.akhbar.model.SourcesItem;
import com.uds.akhbar.repository.Repository;

import java.util.List;

public class SettingsViewModel extends ViewModel {
    private final LiveData<List<SourcesItem>> mSources;

    public SettingsViewModel() {
        mSources = Repository.getInstance().getSourcesResults();
    }

    public LiveData<List<SourcesItem>> getSources() {
        return mSources;
    }
}
