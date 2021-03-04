package com.uds.akhbar.ui.topheadline;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.app.ActivityOptionsCompat;
import androidx.core.util.Pair;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.uds.akhbar.R;
import com.uds.akhbar.adapters.NewsAdapter;
import com.uds.akhbar.model.Articles;
import com.uds.akhbar.ui.detailarticle.ArticleDetailActivity;
import com.uds.akhbar.utils.ItemClickListener;

import java.util.ArrayList;
import java.util.List;

public class NewsFragment extends Fragment implements ItemClickListener {

    private static final String CATEGORY_PARAMS = "all";
    private String category;
    private NewsAdapter adapter;
    List<Articles> articlesList;

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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            category = getArguments().getString(CATEGORY_PARAMS);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_news, container, false);
        adapter = new NewsAdapter(getContext(), this, new ArrayList<>(), 1);
        RecyclerView recyclerView = root.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        TopHeadlineViewModelFactory factory = new TopHeadlineViewModelFactory(getCountryCode(), category);
        TopHeadlineViewModel topHeadlineViewModel = new ViewModelProvider(this, factory).get(TopHeadlineViewModel.class);
        topHeadlineViewModel.getArticlesList().observe(getViewLifecycleOwner(),
                articles -> {
                    this.articlesList = articles;
                    adapter.setArticlesList(articles);
                    if (category.equals("general")) {
                        saveArticlesForWidget(getCountryCode());
                    }

                });
        return root;
    }

    private void saveArticlesForWidget(String countryCode) {
        SharedPreferences sharedPref = getContext().getSharedPreferences("top_headlines_widget", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        Gson gson = new Gson();
        String json = gson.toJson(articlesList);
        editor.putString("top_headlines_widget", json);
        editor.apply();

    }

    private String getCountryCode() {
        SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        return sharedPref.getString(getString(R.string.preference_country_key), "");

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
}