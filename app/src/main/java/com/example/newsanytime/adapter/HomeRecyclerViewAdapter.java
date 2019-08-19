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

public class HomeRecyclerViewAdapter extends RecyclerView.Adapter<HomeRecyclerViewAdapter.ViewHolder> {

    News news;
    Context context;
    List<Article> articles;

    public HomeRecyclerViewAdapter(News news, Context context){
        this.news = news;
        this.context = context;
        this.articles = news.getArticles();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View listItem = layoutInflater.inflate(R.layout.home_activity_recyclerview_list_item, viewGroup, false);
        return new ViewHolder(listItem);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        viewHolder.render(viewHolder,position);
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        int position;
        ViewHolder holder;
        ImageView imageView;
        TextView textView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.news_image);
            textView = itemView.findViewById(R.id.news_headline);
        }

        public void render(ViewHolder viewHolder, int position) {
            this.position = position;
            this.holder = viewHolder;

            textView.setText(articles.get(position).getTitle());

            String imageUrl = articles.get(position).getUrlToImage();

            if (imageUrl != null) {
                Picasso.with(context)
                        .load(imageUrl)
                        .into(imageView);
            }
        }
    }
}
