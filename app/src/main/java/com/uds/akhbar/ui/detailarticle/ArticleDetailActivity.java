package com.uds.akhbar.ui.detailarticle;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;
import com.uds.akhbar.R;
import com.uds.akhbar.model.Articles;

public class ArticleDetailActivity extends AppCompatActivity {
    public static final String ARTICLE_DETAIL = "article:detail";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_detail);
        ImageView imageView = findViewById(R.id.article_image);
        Articles articles = getIntent().getParcelableExtra(ARTICLE_DETAIL);
        Picasso.get().load(articles.getUrlToImage()).into(imageView);
        TextView textView = findViewById(R.id.article_title);
        String title = articles.getTitle();
        textView.setText(title);

    }
}