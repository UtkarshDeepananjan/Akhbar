package com.uds.akhbar.ui.detailarticle;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ShareCompat;
import androidx.databinding.DataBindingUtil;

import com.bumptech.glide.Glide;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.uds.akhbar.R;
import com.uds.akhbar.model.Articles;
import com.uds.akhbar.repository.Repository;

public class ArticleDetailActivity extends AppCompatActivity {
    public static final String ARTICLE_DETAIL = "article:detail";
    private Articles articles;
    private MenuItem saveMenu;
    private String articleId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        com.uds.akhbar.databinding.ActivityArticleDetailBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_article_detail);
        setSupportActionBar(binding.topAppBar);
        articles = getIntent().getParcelableExtra(ARTICLE_DETAIL);
        binding.setArticle(articles);
        articleId = articles.getId();
        AdRequest adRequest = new AdRequest.Builder().build();
        MobileAds.initialize(this, initializationStatus -> {
        });
        binding.adView.loadAd(adRequest);

        Glide.with(getApplicationContext())
                .load(articles.getUrlToImage())
                .into(binding.articleImage);
        binding.viewFullArticle.setOnClickListener(v -> {
            if (!TextUtils.isEmpty(articles.getUrl())) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(articles.getUrl()));
                startActivity(intent);
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.top_app_bar, menu);
        saveMenu = menu.findItem(R.id.save_menu);
        toggleBookmarkIcon();
        return super.onCreateOptionsMenu(menu);
    }

    private void toggleBookmarkIcon() {
        if (TextUtils.isEmpty(articleId)) {
            saveMenu.setIcon(R.drawable.ic_baseline_bookmark_border_24);
        } else {
            saveMenu.setIcon(R.drawable.ic_baseline_bookmark_24);
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int k = item.getItemId();
        if (k == android.R.id.home) {
            onBackPressed();
        }
        if (k == R.id.save_menu) {
            if (TextUtils.isEmpty(articleId)) {
                articleId = Repository.getInstance().saveArticles(articles);
                toggleBookmarkIcon();
            } else {
                deleteArticle();

            }
        }
        if (k == R.id.share_menu) {
            String mimeType = "text/plain";
            ShareCompat.IntentBuilder
                    .from(this)
                    .setType(mimeType)
                    .setChooserTitle(getString(R.string.share_app_title))
                    .setText(articles.getTitle() + "\n" + articles.getUrl())
                    .startChooser();
        }
        return super.onOptionsItemSelected(item);
    }

    private void deleteArticle() {
        Repository.getInstance().deleteArticle(articleId);
        articleId = null;
        toggleBookmarkIcon();
    }
}