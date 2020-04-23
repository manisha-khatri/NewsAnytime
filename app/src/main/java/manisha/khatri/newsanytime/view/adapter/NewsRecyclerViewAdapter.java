package manisha.khatri.newsanytime.view.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import manisha.khatri.newsanytime.util.DateCalculator;
import manisha.khatri.newsanytime.R;
import manisha.khatri.newsanytime.model.Article;
import manisha.khatri.newsanytime.model.News;
import manisha.khatri.newsanytime.util.HelperFunctions;
import com.squareup.picasso.Picasso;
import java.util.List;

public class NewsRecyclerViewAdapter extends RecyclerView.Adapter<NewsRecyclerViewAdapter.ViewHolder> {

    News news;
    Context context;
    List<Article> articles;
    private RecyclerViewItemListener recyclerViewItemListener;

    public NewsRecyclerViewAdapter(News news, Context context, RecyclerViewItemListener recyclerViewItemListener) {
        this.news = news;
        this.context = context;
        this.articles = HelperFunctions.convertArrayToList(news.getArticles());
        this.recyclerViewItemListener = recyclerViewItemListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View listItem = layoutInflater.inflate(R.layout.recyclerview_item_national_news, viewGroup, false);
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
        void onRecyclerViewItemClickListener(Article article);
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
            image = itemView.findViewById(R.id.news_image);
            heading = itemView.findViewById(R.id.news_headline);
            publishedDateTV = itemView.findViewById(R.id.bookmarked_news_published_date);
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
            Article article = articles.get(position);
            recyclerViewItemListener.onRecyclerViewItemClickListener(article);
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
