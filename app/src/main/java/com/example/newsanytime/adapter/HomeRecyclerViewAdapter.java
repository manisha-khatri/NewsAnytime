package com.example.newsanytime.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.newsanytime.R;
import com.example.newsanytime.model.Article;
import com.example.newsanytime.model.News;
import com.squareup.picasso.Picasso;

import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;

public class HomeRecyclerViewAdapter extends RecyclerView.Adapter<HomeRecyclerViewAdapter.ViewHolder> {

    News news;
    Context context;
    List<Article> articles;
    private OnNewsListener onNewsListener;

    public HomeRecyclerViewAdapter(News news, Context context, OnNewsListener onNewsListener) {
        this.news = news;
        this.context = context;
        this.articles = news.getArticles();
        this.onNewsListener = onNewsListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View listItem = layoutInflater.inflate(R.layout.home_activity_recyclerview_list_item, viewGroup, false);
        return new ViewHolder(listItem, onNewsListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        viewHolder.render(viewHolder, position);
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    public interface OnNewsListener {
        void onNewsItemClickListener(String newsHeadline, String newsImage, String newsDescription, String newsContent);

        void onBookmarkBtnClickListener(Dictionary<String,String> newsDict);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        int position;
        ViewHolder holder;
        ImageView newsImageIV;
        TextView newsHeadingTV;
        OnNewsListener onNewsListener;
        ImageView bookmarkBtn;

        public ViewHolder(@NonNull View itemView, OnNewsListener onNewsListener) {
            super(itemView);
            newsImageIV = itemView.findViewById(R.id.news_image);
            newsHeadingTV = itemView.findViewById(R.id.news_headline);
            bookmarkBtn = itemView.findViewById(R.id.bookmark_news_item);
            this.onNewsListener = onNewsListener;
        }

        public void render(ViewHolder viewHolder, int position) {
            this.position = position;
            this.holder = viewHolder;

            setNewsImage();
            setNewsHeadline();
            onNewsImageClickListener();
            onNewsHeadingClickListener();
            onBookmarkBtnClickListener();
        }
        private void onBookmarkBtnClickListener() {
            holder.bookmarkBtn.setOnClickListener(new  View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    bookmarkBtn.setImageResource(R.drawable.book_5);
                    String newsHeadline = articles.get(position).getTitle();
                    String newsImage = articles.get(position).getUrlToImage();
                    String newsDescription = articles.get(position).getDescription();
                    String newsContent = articles.get(position).getContent();

                    Dictionary<String,String> newsDict = new Hashtable<String, String>();
                    newsDict.put("news headline", newsHeadline);
                    newsDict.put("news image", newsImage);
                    newsDict.put("news description", newsDescription);
                    newsDict.put("news content", newsContent);

                    onNewsListener.onBookmarkBtnClickListener(newsDict);
                }
            });
        }

        private void onNewsHeadingClickListener() {
            holder.newsHeadingTV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String newsHeadline = articles.get(position).getTitle();
                    String newsImage = articles.get(position).getUrlToImage();
                    String newsDescription = articles.get(position).getDescription();
                    String newsContent = articles.get(position).getContent();

                    onNewsListener.onNewsItemClickListener(newsHeadline, newsImage, newsDescription, newsContent);

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

                    onNewsListener.onNewsItemClickListener(newsHeadline, newsImage, newsDescription, newsContent);
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
