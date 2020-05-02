package manisha.khatri.newsanytime.view.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import manisha.khatri.newsanytime.network.database.BookmarkedNews;
import manisha.khatri.newsanytime.util.DateCalculator;
import manisha.khatri.newsanytime.R;

import com.squareup.picasso.Picasso;
import java.util.List;

public class SavedNewsAdapter extends RecyclerView.Adapter<SavedNewsAdapter.ViewHolder> {

    Context context;
    List<BookmarkedNews> bookmarkedNewsList;
    private RecyclerViewListener recyclerViewListener;

    public SavedNewsAdapter(List<BookmarkedNews> bookmarkedNewsList, Context context, RecyclerViewListener recyclerViewListener) {
        this.context = context;
        this.bookmarkedNewsList = bookmarkedNewsList;
        this.recyclerViewListener = recyclerViewListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View listItem = layoutInflater.inflate(R.layout.recyclerview_saved_news_item, viewGroup, false);
        return new ViewHolder(listItem, recyclerViewListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        viewHolder.render(viewHolder,position);
    }

    @Override
    public int getItemCount() {
        return bookmarkedNewsList.size();
    }

    public interface RecyclerViewListener {
        void onItemClick(BookmarkedNews bookmarkedNews);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView headlineTV;
        TextView publishedDateTV;
        RecyclerViewListener recyclerViewListener;

        public ViewHolder(@NonNull View itemView, RecyclerViewListener recyclerViewListener) {
            super(itemView);
            initViews(itemView, recyclerViewListener);
        }

        private void initViews(@NonNull View itemView, RecyclerViewListener recyclerViewListener) {
            imageView = itemView.findViewById(R.id.saved_news_image);
            headlineTV = itemView.findViewById(R.id.saved_news_headline);
            publishedDateTV = itemView.findViewById(R.id.saved_news_published_date);
            this.recyclerViewListener = recyclerViewListener;
        }

        public void render(ViewHolder viewHolder, int position) {
            loadImage(viewHolder, position);
            headlineTV.setText(bookmarkedNewsList.get(position).getHeadline());
            publishedDateTV.setText(getPublishedDate(position));
            onItemClick(position);
        }

        private String getPublishedDate(int position) {
            DateCalculator dateCalculator = new DateCalculator();
            if(dateCalculator.validatePublishedDate(bookmarkedNewsList.get(position).getPublishedDate())) {
                return dateCalculator.calculateTotalTimeDifference(
                        dateCalculator.convertDateIntoISTTimeZone(bookmarkedNewsList.get(position).getPublishedDate()),
                        dateCalculator.getCurrentDate());
            }
            return null;
        }

        private void onItemClick(final int position) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    recyclerViewListener.onItemClick(bookmarkedNewsList.get(position));
                }
            });
        }

        private void loadImage(ViewHolder holder, int position) {
            String imageUrl = bookmarkedNewsList.get(position).getImageUrl();
            if (imageUrl != null && imageUrl != "" && imageUrl != " ") {
                Picasso.with(context)
                        .load(imageUrl)
                        .into(imageView);
            }
            else
                holder.imageView.setImageResource(R.drawable.image_not_present);
        }
    }
}
