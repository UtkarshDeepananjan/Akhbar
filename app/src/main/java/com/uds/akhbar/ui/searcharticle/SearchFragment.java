package com.uds.akhbar.ui.searcharticle;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.util.Pair;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.uds.akhbar.R;
import com.uds.akhbar.adapters.NewsAdapter;
import com.uds.akhbar.databinding.FragmentSearchBinding;
import com.uds.akhbar.model.Articles;
import com.uds.akhbar.ui.detailarticle.ArticleDetailActivity;
import com.uds.akhbar.utils.ItemClickListener;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment implements ItemClickListener {

    private SearchViewModel searchViewModel;
    private NewsAdapter adapter;
    private List<Articles> articlesList;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        searchViewModel = new ViewModelProvider(this).get(SearchViewModel.class);
        FragmentSearchBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_search, container, false);
        adapter = new NewsAdapter(getContext(), this, new ArrayList<>(), 2);

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerView.setAdapter(adapter);

        binding.searchView.onActionViewExpanded();
        binding.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (!TextUtils.isEmpty(newText))
                    searchViewModel.getArticles(newText, getContext()).observe(getViewLifecycleOwner(), newsResponse -> {
                        if (newsResponse.getStatus().equalsIgnoreCase("ok")) {
                            articlesList = newsResponse.getArticles();
                            adapter.setArticlesList(articlesList);
                        } else {
                            Toast.makeText(getContext(), newsResponse.getCode(), Toast.LENGTH_SHORT).show();
                        }
                    });
                adapter.notifyDataSetChanged();
                return false;
            }
        });
        binding.searchView.setOnCloseListener(() -> {
            return true;
        });
        return binding.getRoot();
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