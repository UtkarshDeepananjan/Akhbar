package com.uds.akhbar.ui.detailarticle;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.squareup.picasso.Picasso;
import com.uds.akhbar.R;
import com.uds.akhbar.databinding.ActivityArticleDetailBinding;
import com.uds.akhbar.model.Articles;

public class ArticleDetailActivity extends AppCompatActivity {
    public static final String ARTICLE_DETAIL = "article:detail";
    ActivityArticleDetailBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_article_detail);
        Articles articles = getIntent().getParcelableExtra(ARTICLE_DETAIL);
        binding.setArticle(articles);
        Picasso.get().load(articles.getUrlToImage()).into(binding.articleImage);

    }
}