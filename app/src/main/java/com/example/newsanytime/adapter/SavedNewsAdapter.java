package com.example.newsanytime.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.newsanytime.util.DateCalculator;
import com.example.newsanytime.R;
import com.example.newsanytime.room.SavedNews;
import com.squareup.picasso.Picasso;
import java.util.List;

public class SavedNewsAdapter extends RecyclerView.Adapter<SavedNewsAdapter.ViewHolder> {

    Context context;
    List<SavedNews> newsList;
    private RecyclerViewItemListener recyclerViewItemListener;

    public SavedNewsAdapter(List<SavedNews> newsList, Context context, RecyclerViewItemListener recyclerViewItemListener) {
        this.context = context;
        this.newsList = newsList;
        this.recyclerViewItemListener = recyclerViewItemListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View listItem = layoutInflater.inflate(R.layout.recyclerview_saved_news_item, viewGroup, false);
        return new ViewHolder(listItem, recyclerViewItemListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        viewHolder.render(viewHolder,position);
    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }

    public interface RecyclerViewItemListener {
        void onRecyclerViewItemClickListener(String headline, String image, String description, String content, String publishedDate);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        int position;
        ViewHolder holder;
        ImageView image;
        TextView heading;
        TextView publishedDateTV;
        DateCalculator dateCalculator;
        RecyclerViewItemListener recyclerViewItemListener;

        public ViewHolder(@NonNull View itemView, RecyclerViewItemListener recyclerViewItemListener) {
            super(itemView);
            initViews(itemView, recyclerViewItemListener);
        }

        private void initViews(@NonNull View itemView, RecyclerViewItemListener recyclerViewItemListener) {
            image = itemView.findViewById(R.id.saved_news_image);
            heading = itemView.findViewById(R.id.saved_news_headline);
            publishedDateTV = itemView.findViewById(R.id.saved_news_published_date);
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
            String publishedDateStr = newsList.get(position).getPublishedDate();
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

        public void passInfoToListener() {
            String headline = newsList.get(position).getHeadline();
            String image = newsList.get(position).getImageUrl();
            String description = newsList.get(position).getDescription();
            String content = newsList.get(position).getContent();
            String publishedDate= newsList.get(position).getPublishedDate();

            recyclerViewItemListener.onRecyclerViewItemClickListener(headline, image, description, content,publishedDate);
        }

        private void onImageClickListener() {
            holder.image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    passInfoToListener();
                }
            });
        }

        private void setHeadline() {
            heading.setText(newsList.get(position).getHeadline());
        }

        private void setImage() {
            String imageUrl = newsList.get(position).getImageUrl();
            if (imageUrl != null && imageUrl != "" && imageUrl != " ") {
                Picasso.with(context)
                        .load(imageUrl)
                        .into(image);
            }
            else
                holder.image.setImageResource(R.drawable.image_not_present);
        }
    }
}
