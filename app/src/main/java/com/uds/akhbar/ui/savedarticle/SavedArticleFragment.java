package com.uds.akhbar.ui.savedarticle;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.uds.akhbar.R;

public class SavedArticleFragment extends Fragment {

    private SavedArticleViewModel savedArticleViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        savedArticleViewModel =
                new ViewModelProvider(this).get(SavedArticleViewModel.class);
        View root = inflater.inflate(R.layout.fragment_saved_articles, container, false);
        final TextView textView = root.findViewById(R.id.text_notifications);
        savedArticleViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}