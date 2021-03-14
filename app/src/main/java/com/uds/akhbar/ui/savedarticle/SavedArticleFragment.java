package com.uds.akhbar.ui.savedarticle;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.util.Pair;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.uds.akhbar.R;
import com.uds.akhbar.adapters.NewsAdapter;
import com.uds.akhbar.model.Articles;
import com.uds.akhbar.ui.detailarticle.ArticleDetailActivity;
import com.uds.akhbar.utils.ItemClickListener;

import java.util.ArrayList;
import java.util.List;

public class SavedArticleFragment extends Fragment implements ItemClickListener {

    private List<Articles> articlesList;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        SavedArticleViewModel savedArticleViewModel = new ViewModelProvider(this).get(SavedArticleViewModel.class);
        View root = inflater.inflate(R.layout.fragment_saved_articles, container, false);
        RecyclerView recyclerView = root.findViewById(R.id.recyclerView);
        LottieAnimationView animationView = root.findViewById(R.id.animationView);
        articlesList = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        NewsAdapter adapter = new NewsAdapter(getActivity(), this, new ArrayList<>(), 1);
        recyclerView.setAdapter(adapter);
/*
        savedArticleViewModel.getArticles().observe(getViewLifecycleOwner(), articles -> {
            articlesList = articles;
            if (articles.isEmpty()) {
                animationView.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
            } else {
                animationView.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
            }
            adapter.setArticlesList(articles);
        });
*/
        return root;
    }

    @Override
    public void onClick(int position, ImageView imageView, TextView titleTextView) {
        Intent intent = new Intent(getContext(), ArticleDetailActivity.class);
        intent.putExtra(ArticleDetailActivity.ARTICLE_DETAIL, articlesList.get(position));
        ActivityOptionsCompat options = ActivityOptionsCompat.
                makeSceneTransitionAnimation(getActivity(),
                        Pair.create(imageView, "article_image"),
                        Pair.create(titleTextView, "article_title"));
        startActivity(intent, options.toBundle());
    }
}