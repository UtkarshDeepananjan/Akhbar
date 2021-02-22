package com.uds.akhbar.ui.topheadline;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.uds.akhbar.R;
import com.uds.akhbar.adapters.ViewPagerAdapter;

public class TopHeadlineFragment extends Fragment {
    ViewPager viewPager;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_top_headlines, container, false);
        TabLayout tabLayout = root.findViewById(R.id.categoryTab);
        viewPager = root.findViewById(R.id.categoryViewpager);
        viewPager.setAdapter(new ViewPagerAdapter(getChildFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT, getContext()));
        tabLayout.setupWithViewPager(viewPager);

        return root;
    }

}