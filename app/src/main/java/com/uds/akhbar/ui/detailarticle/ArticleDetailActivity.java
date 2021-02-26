package com.uds.akhbar.ui.detailarticle;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ShareCompat;
import androidx.databinding.DataBindingUtil;

import com.squareup.picasso.Picasso;
import com.uds.akhbar.R;
import com.uds.akhbar.databinding.ActivityArticleDetailBinding;
import com.uds.akhbar.model.Articles;
import com.uds.akhbar.repository.Repository;

public class ArticleDetailActivity extends AppCompatActivity {
    public static final String ARTICLE_DETAIL = "article:detail";
    ActivityArticleDetailBinding binding;
    private MenuItem saveMenu;
    Articles articles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_article_detail);
        setSupportActionBar(binding.topAppBar);
        articles = getIntent().getParcelableExtra(ARTICLE_DETAIL);
        binding.setArticle(articles);
        Picasso.get().load(articles.getUrlToImage()).into(binding.articleImage);

    }

    private void updateSaveStatus(Boolean saved) {
        if (saved)
            saveMenu.setIcon(R.drawable.ic_baseline_bookmark_24);
        else
            saveMenu.setIcon(R.drawable.ic_baseline_bookmark_border_24);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.top_app_bar, menu);
//        saveMenu = menu.getItem(R.id.save_menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int k = item.getItemId();
        if (k == android.R.id.home) {
            onBackPressed();
        }
        if (k == R.id.save_menu) {
            String message = Repository.getInstance().saveArticles(articles);
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
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
}