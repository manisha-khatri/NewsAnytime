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
import com.example.newsanytime.room.BookmarkedNews;
import com.squareup.picasso.Picasso;
import java.util.List;

public class BookmarkedNewsAdapter extends RecyclerView.Adapter<BookmarkedNewsAdapter.ViewHolder> {

    Context context;
    List<BookmarkedNews> newsList;
    private OnBookmarkedNewsItemListener onBookmarkedNewsItemListener;

    public BookmarkedNewsAdapter(List<BookmarkedNews> newsList, Context context, OnBookmarkedNewsItemListener onBookmarkedNewsItemListener) {
        this.context = context;
        this.newsList = newsList;
        this.onBookmarkedNewsItemListener = onBookmarkedNewsItemListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View listItem = layoutInflater.inflate(R.layout.bookmarked_news_list_item, viewGroup, false);
        return new ViewHolder(listItem, onBookmarkedNewsItemListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        viewHolder.render(viewHolder,position);
    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }

    public interface OnBookmarkedNewsItemListener {
        void onNewsItemClickListener(String newsHeadline, String newsImage, String newsDescription, String newsContent, String newsPublishedDate);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        int position;
        ViewHolder holder;
        ImageView newsImageIV;
        TextView newsHeadingTV;
        OnBookmarkedNewsItemListener onBookmarkedNewsItemListener;

        public ViewHolder(@NonNull View itemView, OnBookmarkedNewsItemListener onBookmarkedNewsItemListener) {
            super(itemView);
            newsImageIV = itemView.findViewById(R.id.bookmarked_news_image);
            newsHeadingTV = itemView.findViewById(R.id.bookmarked_news_headline);
            this.onBookmarkedNewsItemListener = onBookmarkedNewsItemListener;
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
                    setAction();

                }
            });
        }

        public void setAction() {
            String newsHeadline = newsList.get(position).getHeadline();
            String newsImage = newsList.get(position).getImageUrl();
            String newsDescription = newsList.get(position).getDescription();
            String newsContent = newsList.get(position).getContent();
            String newsPublishedDate= newsList.get(position).getPublishedDate();

            onBookmarkedNewsItemListener.onNewsItemClickListener(newsHeadline, newsImage, newsDescription, newsContent,newsPublishedDate);
        }

        private void onNewsImageClickListener() {
            holder.newsImageIV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setAction();
                }
            });
        }

        private void setNewsHeadline() {
            newsHeadingTV.setText(newsList.get(position).getHeadline());
        }

        private void setNewsImage() {
            String imageUrl = newsList.get(position).getImageUrl();

            if (imageUrl != null) {
                Picasso.with(context)
                        .load(imageUrl)
                        .into(newsImageIV);
            }
        }
    }
}
