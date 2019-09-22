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

public class NationalNewsRecyclerViewAdapter extends RecyclerView.Adapter<NationalNewsRecyclerViewAdapter.ViewHolder> {

    News news;
    Context context;
    List<Article> articles;
    private RecyclerViewItemListener recyclerViewItemListener;

    public NationalNewsRecyclerViewAdapter(News news, Context context, RecyclerViewItemListener recyclerViewItemListener) {
        this.news = news;
        this.context = context;
        this.articles = news.getArticles();
        this.recyclerViewItemListener = recyclerViewItemListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View listItem = layoutInflater.inflate(R.layout.recyclerview_item_activity_home, viewGroup, false);
        return new ViewHolder(listItem, recyclerViewItemListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        viewHolder.render(viewHolder, position);
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    public interface RecyclerViewItemListener {
        void onRecyclerViewItemClickListener(String newsHeadline, String newsImage, String newsDescription, String newsContent, String newsPublishedAt);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        int position;
        ViewHolder holder;
        ImageView newsImageIV;
        TextView newsHeadingTV;
        RecyclerViewItemListener recyclerViewItemListener;

        public ViewHolder(@NonNull View itemView, RecyclerViewItemListener recyclerViewItemListener) {
            super(itemView);
            newsImageIV = itemView.findViewById(R.id.news_image);
            newsHeadingTV = itemView.findViewById(R.id.news_headline);
            this.recyclerViewItemListener = recyclerViewItemListener;
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
                    passInfoToListener();
                }
            });
        }

        private void onNewsImageClickListener() {
            holder.newsImageIV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    passInfoToListener();
                }
            });
        }

        void passInfoToListener(){
            String newsHeadline = articles.get(position).getTitle();
            String newsImage = articles.get(position).getUrlToImage();
            String newsDescription = articles.get(position).getDescription();
            String newsContent = articles.get(position).getContent();
            String newsPublishedAt = articles.get(position).getPublishedAt();

            recyclerViewItemListener.onRecyclerViewItemClickListener(newsHeadline, newsImage, newsDescription, newsContent, newsPublishedAt);
        }

        private void setNewsHeadline() {
            newsHeadingTV.setText(articles.get(position).getTitle());
        }

        private void setNewsImage() {
            String imageUrl = articles.get(position).getUrlToImage();

            if (imageUrl != null && imageUrl !="" && imageUrl != " ") {
                Picasso.with(context)
                        .load(imageUrl)
                        .into(newsImageIV);
            }
            else
                holder.newsImageIV.setImageResource(R.drawable.image_not_present);

        }
    }
}
