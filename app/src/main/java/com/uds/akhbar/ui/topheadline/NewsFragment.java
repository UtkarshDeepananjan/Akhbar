package com.uds.akhbar.ui.topheadline;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.util.Pair;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.google.gson.Gson;
import com.uds.akhbar.R;
import com.uds.akhbar.adapters.NewsAdapter;
import com.uds.akhbar.databinding.FragmentNewsBinding;
import com.uds.akhbar.model.Articles;
import com.uds.akhbar.ui.detailarticle.ArticleDetailActivity;
import com.uds.akhbar.ui.home.HomeScreenActivity;
import com.uds.akhbar.utils.ItemClickListener;
import com.uds.akhbar.utils.NetworkUtils;

import java.util.ArrayList;
import java.util.List;

public class NewsFragment extends Fragment implements ItemClickListener, SharedPreferences.OnSharedPreferenceChangeListener {

    private static final String CATEGORY_PARAMS = "all";
    private String category;
    private NewsAdapter adapter;
    private List<Articles> articlesList;
    private FragmentNewsBinding binding;
    private SharedPreferences sharedPreferences;
    private TopHeadlineViewModel topHeadlineViewModel;
    private HomeScreenActivity activity;

    public NewsFragment() {
    }

    public static NewsFragment newInstance(String category) {
        NewsFragment fragment = new NewsFragment();
        Bundle args = new Bundle();
        args.putString(CATEGORY_PARAMS, category);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        activity = (HomeScreenActivity) context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            category = getArguments().getString(CATEGORY_PARAMS);
        }
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentNewsBinding.inflate(inflater, container, false);
        adapter = new NewsAdapter(getContext(), this, new ArrayList<>(), 1);
        if (getResources().getBoolean(R.bool.isTablet))
            binding.recyclerView.setLayoutManager(new StaggeredGridLayoutManager(getSpanCount(), LinearLayout.VERTICAL));
        else
            binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerView.setAdapter(adapter);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(activity);
        sharedPreferences.registerOnSharedPreferenceChangeListener(this);
        TopHeadlineViewModelFactory factory = new TopHeadlineViewModelFactory(getCountryCode(), category,getContext());
        topHeadlineViewModel = new ViewModelProvider(this, factory).get(TopHeadlineViewModel.class);
        topHeadlineViewModel.getArticlesList(false,getContext()).observe(getViewLifecycleOwner(), newsResponse -> {
            if (newsResponse.getStatus().equalsIgnoreCase("ok")) {
                articlesList = newsResponse.getArticles();
                binding.emptyBox.setVisibility(articlesList.size() == 0 ? View.VISIBLE : View.GONE);
                binding.shimmer.stopShimmer();
                binding.shimmer.setVisibility(View.GONE);
                adapter.setArticlesList(articlesList);
                if (category.equals("general")) {
                    saveArticlesForWidget();
                }
            } else {
                binding.shimmer.stopShimmer();
                binding.shimmer.setVisibility(View.GONE);
                adapter.setArticlesList(articlesList);
                Toast.makeText(getContext(), newsResponse.getCode(), Toast.LENGTH_SHORT).show();
            }
        });
        binding.refresh.setOnRefreshListener(() -> getLatestHeadlines(getCountryCode()));

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            NetworkUtils networkUtils = new NetworkUtils(getContext());
            networkUtils.observe(getViewLifecycleOwner(), aBoolean -> {
                if (!aBoolean) {
                    binding.shimmer.setVisibility(View.GONE);
                }
            });
        }

        return binding.getRoot();
    }


    private String getCountryCode() {
        return sharedPreferences.getString(getString(R.string.pref_country_key), "in");
    }

    private void saveArticlesForWidget() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(articlesList);
        editor.putString(getString(R.string.pref_widget_key), json);
        editor.apply();

    }


    @Override
    public void onClick(int position, ImageView imageView, TextView titleTextView) {
        Intent intent = new Intent(getContext(), ArticleDetailActivity.class);
        intent.putExtra(ArticleDetailActivity.ARTICLE_DETAIL, articlesList.get(position));
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(),
                Pair.create(imageView, "article_image"),
                Pair.create(titleTextView, "article_title"));
        startActivity(intent, options.toBundle());
    }

    private void getLatestHeadlines(String countryCode) {
        topHeadlineViewModel.getArticlesList(countryCode, true,getContext()).observe(getViewLifecycleOwner(), newsResponse -> {
            if (newsResponse.getStatus().equalsIgnoreCase("ok")) {
                articlesList = newsResponse.getArticles();
                binding.shimmer.stopShimmer();
                binding.shimmer.setVisibility(View.GONE);
                adapter.setArticlesList(articlesList);
                if (binding.refresh.isRefreshing()) {
                    binding.refresh.setRefreshing(false);
                }
            } else {
                Toast.makeText(activity, newsResponse.getCode(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (isAdded()) {
            if (key.equals(getString(R.string.pref_country_key)))
                getLatestHeadlines(sharedPreferences.getString(key, "in"));
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        sharedPreferences.unregisterOnSharedPreferenceChangeListener(this);
    }

    private int getSpanCount() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int widthDivider = 400;
        int width = displayMetrics.widthPixels;
        int nColumns = width / widthDivider;
        return Math.max(nColumns, 2);
    }


}
