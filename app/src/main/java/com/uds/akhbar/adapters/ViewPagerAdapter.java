package com.uds.akhbar.adapters;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.uds.akhbar.R;
import com.uds.akhbar.ui.topheadline.NewsFragment;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    private final NewsFragment[] newsFragments;
    String[] categories;
    String[] categoriesLabel;

    public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior, Context context) {
        super(fm, behavior);
        categories = context.getResources().getStringArray(R.array.category_value);
        categoriesLabel = context.getResources().getStringArray(R.array.category_label);
        newsFragments = new NewsFragment[categories.length];
        for (int i = 0; i < categories.length; i++) {
            newsFragments[i] = NewsFragment.newInstance(categories[i]);
        }
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return categoriesLabel[position];
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return newsFragments[position];
    }

    @Override
    public int getCount() {
        return newsFragments == null ? 0 : newsFragments.length;
    }
}
