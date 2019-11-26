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
import com.example.newsanytime.util.DateCalculator;
import com.squareup.picasso.Picasso;
import java.util.List;

public class InternationalNewsRecyclerViewAdapter extends RecyclerView.Adapter<InternationalNewsRecyclerViewAdapter.ViewHolder> {

    News news;
    Context context;
    List<Article> articles;
    private RecyclerViewItemListener recyclerViewItemListener;

    public InternationalNewsRecyclerViewAdapter(News news, Context context, RecyclerViewItemListener recyclerViewItemListener) {
        this.news = news;
        this.context = context;
        this.articles = news.getArticles();
        this.recyclerViewItemListener = recyclerViewItemListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View listItem = layoutInflater.inflate(R.layout.recyclerview_item_international_news, viewGroup, false);
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
        ImageView image;
        DateCalculator dateCalculator;
        TextView heading;
        TextView publishedDateTV;
        RecyclerViewItemListener recyclerViewItemListener;  //handle any operation on the recyclerview items

        public ViewHolder(@NonNull View itemView, RecyclerViewItemListener recyclerViewItemListener) {
            super(itemView);
            initViews(itemView, recyclerViewItemListener);
        }

        private void initViews(@NonNull View itemView, RecyclerViewItemListener recyclerViewItemListener) {
            image = itemView.findViewById(R.id.int_news_image);
            heading = itemView.findViewById(R.id.int_news_headline);
            publishedDateTV = itemView.findViewById(R.id.int_bookmarked_news_published_date);
            this.recyclerViewItemListener = recyclerViewItemListener;
        }

        public void render(ViewHolder viewHolder, int position) {
            this.position = position;
            this.holder = viewHolder;
            setImage();
            setHeadline();
            setPublishedDate();
            onImageClickListener();
            onHeadingClickListener();
        }

        private void setPublishedDate() {
            String publishedDateStr = articles.get(position).getPublishedAt();
            dateCalculator = new DateCalculator();
            if(dateCalculator.validatePublishedDate(publishedDateStr)) {
                String totalTime = dateCalculator.calculateTotalTimeDifference(
                        dateCalculator.convertDateIntoISTTimeZone(publishedDateStr),
                        dateCalculator.getCurrentDate());
                publishedDateTV.setText(totalTime);
            }
        }

        private void onHeadingClickListener() {
            holder.heading.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    passInfoToListener();
                }
            });
        }

        private void onImageClickListener() {
            holder.image.setOnClickListener(new View.OnClickListener() {
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

        private void setHeadline() {
            String headLine = articles.get(position).getTitle();
            String str2 = removeInvalidNewsLastChar(headLine);
            heading.setText(str2);
        }

        private String removeInvalidNewsLastChar(String headLine) {
            String str[] = headLine.split("-");
            int len = str.length;
            String str2="";
            for (int i=0; i<len-1;i++) {
                str2 = str2 + str[i];
            }
            return str2;
        }

        private void setImage() {
            String imageUrl = articles.get(position).getUrlToImage();
            if (imageUrl != null && imageUrl !="" && imageUrl != " ") {
                Picasso.with(context)
                        .load(imageUrl)
                        .into(image);
            }
            else
                holder.image.setImageResource(R.drawable.image_not_present);
        }
    }
}
