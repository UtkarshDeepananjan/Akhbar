package com.uds.akhbar.adapters;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.uds.akhbar.R;
import com.uds.akhbar.model.Articles;
import com.uds.akhbar.utils.ItemClickListener;

import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {
    private final ItemClickListener mClickHandler;
    private List<Articles> articlesList;
    private final int layoutType;
    private final Context context;

    public NewsAdapter(Context context, ItemClickListener mClickHandler, List<Articles> articlesList, int layoutType) {
        this.mClickHandler = mClickHandler;
        this.articlesList = articlesList;
        this.layoutType = layoutType;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (layoutType == 1) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.article_list_item, parent, false);
        } else
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.article_search_list_item, parent, false);
        return new ViewHolder(view);
    }

    public void setArticlesList(List<Articles> articles) {
        articlesList.clear();
        articlesList = articles;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Articles articles = articlesList.get(position);
        holder.title.setText(articles.getTitle());
        String imageUrl = articles.getUrlToImage();
        if (!TextUtils.isEmpty(imageUrl)) {
            Glide.with(context)
                    .load(imageUrl)
                    .placeholder(R.drawable.image_loading)
                    .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                    .into(holder.imageView);
        }


    }

    @Override
    public int getItemCount() {
        return articlesList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView title;
        ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.article_title);
            imageView = itemView.findViewById(R.id.article_image);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mClickHandler.onClick(getAdapterPosition(), imageView, title);
        }
    }
}
