package manisha.khatri.newsanytime.view.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import manisha.khatri.newsanytime.R;
import manisha.khatri.newsanytime.model.Article;
import manisha.khatri.newsanytime.model.News;
import manisha.khatri.newsanytime.util.DateCalculator;
import manisha.khatri.newsanytime.util.Helper;

import com.squareup.picasso.Picasso;
import java.util.List;

public class TopStoriesRecyclerViewAdapter extends RecyclerView.Adapter<TopStoriesRecyclerViewAdapter.ViewHolder> {
    Context context;
    List<Article> articles;
    private RecyclerViewListener recyclerViewListener;

    public TopStoriesRecyclerViewAdapter(News news, Context context, RecyclerViewListener recyclerViewListener) {
        this.context = context;
        this.articles = Helper.convertArrayToList(news.getArticles());
        this.recyclerViewListener = recyclerViewListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View listItem = layoutInflater.inflate(R.layout.recyclerview_top_stories_item2, viewGroup, false);
        return new ViewHolder(listItem, recyclerViewListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        viewHolder.render(viewHolder, position);
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    public interface RecyclerViewListener {
        void onItemClick(Article article);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView headlineTV;
        TextView publishedDateTV;
        RecyclerViewListener recyclerViewListener;  //handle any operation on the recyclerview items

        public ViewHolder(@NonNull View itemView, RecyclerViewListener recyclerViewListener) {
            super(itemView);
            initViews(itemView, recyclerViewListener);
        }

        private void initViews(@NonNull View itemView, RecyclerViewListener recyclerViewListener) {
            imageView = itemView.findViewById(R.id.top_stories2_img);
            headlineTV = itemView.findViewById(R.id.top_stories2_heading);
            publishedDateTV = itemView.findViewById(R.id.top_stories2_published_date);
            this.recyclerViewListener = recyclerViewListener;
        }

        public void render(ViewHolder viewHolder, int position) {
            loadImage(viewHolder,position);
            headlineTV.setText(articles.get(position).getTitle());
            publishedDateTV.setText(getPublishedDate(position));
            onItemClick(viewHolder,position);
        }

        private String getPublishedDate(int position) {
            DateCalculator dateCalculator = new DateCalculator();
            if(dateCalculator.validatePublishedDate(articles.get(position).getPublishedAt())) {
                return dateCalculator.calculateTotalTimeDifference(
                        dateCalculator.convertDateIntoISTTimeZone(articles.get(position).getPublishedAt()),
                        dateCalculator.getCurrentDate());
            }
            return null;
        }

        private void onItemClick(ViewHolder viewHolder, final int position) {
            viewHolder.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Article article = articles.get(position);
                    recyclerViewListener.onItemClick(article);
                }
            });
        }

        private void loadImage(ViewHolder holder, int position) {
            String imageUrl = articles.get(position).getUrlToImage();
            if (imageUrl != null && imageUrl !="" && imageUrl != " ") {
                Picasso.with(context)
                        .load(imageUrl)
                        .into(imageView);
            }
            else
                holder.imageView.setImageResource(R.drawable.image_not_present);
        }
    }
}
