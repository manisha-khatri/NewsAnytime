package com.example.newsanytime.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.newsanytime.R;
import com.example.newsanytime.model.Article;
import com.example.newsanytime.model.News;
import com.squareup.picasso.Picasso;
import java.util.List;

public class AdvanceSearchRecyclerViewAdapter extends RecyclerView.Adapter<AdvanceSearchRecyclerViewAdapter.ViewHolder> {

    News news;
    Context context;
    List<Article> articles;
    private OnNewsListener onNewsListener;

    public AdvanceSearchRecyclerViewAdapter(News news, Context context, OnNewsListener onNewsListener) {
        this.news = news;
        this.context = context;
        this.articles = news.getArticles();
        this.onNewsListener = onNewsListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View listItem = layoutInflater.inflate(R.layout.searched_activity_recyclerview_list_item, viewGroup, false);
        return new ViewHolder(listItem, onNewsListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        viewHolder.render(viewHolder,position);
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    public interface OnNewsListener {
        void onNewsItemClickListener(String newsHeadline, String newsImage, String newsDescription, String newsContent, String newsPublishedDate);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        int position;
        ViewHolder holder;
        ImageView newsImageIV;
        TextView newsHeadingTV;
        OnNewsListener onNewsListener;

        public ViewHolder(@NonNull View itemView, OnNewsListener onNewsListener) {
            super(itemView);
            newsImageIV = itemView.findViewById(R.id.searched_news_image);
            newsHeadingTV = itemView.findViewById(R.id.searched_news_headline);
            this.onNewsListener = onNewsListener;
        }

        public void render(ViewHolder viewHolder, int position) {
            this.position = position;
            this.holder = viewHolder;

            setNewsImage();
            setNewsHeadline();
            onNewsImageClickListener();
            onNewsHeadingClickListener();
        }

        private void onNewsHeadingClickListener() {
            holder.newsHeadingTV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String newsHeadline = articles.get(position).getTitle();
                    String newsImage = articles.get(position).getUrlToImage();
                    String newsDescription = articles.get(position).getDescription();
                    String newsContent = articles.get(position).getContent();
                    String newsPublishedDate = articles.get(position).getPublishedAt();

                    onNewsListener.onNewsItemClickListener(newsHeadline, newsImage, newsDescription, newsContent,newsPublishedDate);
                }
            });
        }

        private void onNewsImageClickListener() {
            holder.newsImageIV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String newsHeadline = articles.get(position).getTitle();
                    String newsImage = articles.get(position).getUrlToImage();
                    String newsDescription = articles.get(position).getDescription();
                    String newsContent = articles.get(position).getContent();
                    String newsPublishedDate = articles.get(position).getPublishedAt();

                    onNewsListener.onNewsItemClickListener(newsHeadline, newsImage, newsDescription, newsContent,newsPublishedDate);
                }
            });
        }

        private void setNewsHeadline() {
            newsHeadingTV.setText(articles.get(position).getTitle());
        }

        private void setNewsImage() {
            String imageUrl = articles.get(position).getUrlToImage();

            if (imageUrl != null) {
                Picasso.with(context)
                        .load(imageUrl)
                        .into(newsImageIV);
            }
        }
    }
}

