package manisha.khatri.newsanytime.view.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import manisha.khatri.newsanytime.R;
import manisha.khatri.newsanytime.contract.NewsDetailContract;
import manisha.khatri.newsanytime.database.BookmarkedNews;
import manisha.khatri.newsanytime.presenter.NewsDetailPresenter;
import manisha.khatri.newsanytime.util.DateCalculator;
import manisha.khatri.newsanytime.util._enum.NewsIntent;

import com.squareup.picasso.Picasso;

public class NewsDetailActivity extends AppCompatActivity implements NewsDetailContract {
    ImageView bookmarkBtn;
    NewsDetailPresenter newsDetailPresenter;
    Boolean newsSavedInDB = false;
    BookmarkedNews bookmarkedNews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);
        initViews();
        getNewsDetailsFromIntent();
        checkIfNewsAlreadySavedInDB();
        onBookmarkBtnClickListener();
        onBackBtnClickListener();
    }

    private void initViews() {
        setToolbar();
        newsDetailPresenter = new NewsDetailPresenter(this);
        bookmarkBtn = findViewById(R.id.nd_bookmark_btn);
    }

    void getNewsDetailsFromIntent() {
        bookmarkedNews = new BookmarkedNews();
        bookmarkedNews.setHeadline(getValueFromIntent(NewsIntent.HEADLINE.toString()));
        bookmarkedNews.setImageUrl(getValueFromIntent(NewsIntent.IMAGE.toString()));
        bookmarkedNews.setContent(getValueFromIntent(NewsIntent.CONTENT.toString()));
        bookmarkedNews.setDescription(getValueFromIntent(NewsIntent.DESCRIPTION.toString()));
        bookmarkedNews.setPublishedDate(getValueFromIntent(NewsIntent.PUBLISHEDDATE.toString()));
        setActivityViews();
    }

    private void setActivityViews() {
        TextView headlineTV = findViewById(R.id.nd_heading);
        TextView descriptionTV = findViewById(R.id.nd_description);
        TextView contentTV = findViewById(R.id.nd_content);

        headlineTV.setText(bookmarkedNews.getHeadline());
        descriptionTV.setText(bookmarkedNews.getDescription());

        String content = (bookmarkedNews.getContent()!=null)?removeInvalidCharFromLast(bookmarkedNews.getContent()):null;
        contentTV.setText(content);

        setImage(bookmarkedNews.getImageUrl());
        setPublishedDate(bookmarkedNews.getPublishedDate());
    }

    public void checkIfNewsAlreadySavedInDB() {
        newsDetailPresenter.searchNews(bookmarkedNews.getPublishedDate(), getApplication());
    }

    public void onBookmarkBtnClickListener() {
        bookmarkBtn.setOnClickListener(new  View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(newsSavedInDB) {
                    uncheckBookmark();
                    deleteSavedNewsFromDB();
                }
                else{
                    checkBookmark();
                    newsDetailPresenter.saveNews(bookmarkedNews, getApplication());
                }
            }
        });
    }

    public void uncheckBookmark(){
        bookmarkBtn.setImageResource(R.drawable.uncheck_bookmark_img);
        newsSavedInDB = false;
    }

    public void checkBookmark() {
        bookmarkBtn.setImageResource(R.drawable.checked_bookmark_img);
        newsSavedInDB = true;
    }

    public void deleteSavedNewsFromDB() {
        newsDetailPresenter.deleteNews(bookmarkedNews.getPublishedDate(), getApplication());
    }

    private void setToolbar() {
        Toolbar toolbar = findViewById(R.id.nd_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    private String getValueFromIntent(String key) {
        return (getIntent().hasExtra(key)) ? getIntent().getStringExtra(key) : " ";
    }

    private void setPublishedDate(String publishedDateStr) {
        TextView publishedDateTV = findViewById(R.id.nd_published_date);
        DateCalculator dateCalculator = new DateCalculator();
        if(dateCalculator.validatePublishedDate(publishedDateStr)) {
            String totalTime = dateCalculator.calculateTotalTimeDifference(
                    dateCalculator.convertDateIntoISTTimeZone(publishedDateStr),
                    dateCalculator.getCurrentDate());
            publishedDateTV.setText(totalTime);
        }
        else
            publishedDateTV.setVisibility(View.GONE);
    }

    private void setImage(String imageUrl) {
        ImageView imageIV = findViewById(R.id.nd_image);
        if (imageUrl != null && imageUrl !="" && imageUrl != " ") {
            Picasso.with(this)
                    .load(imageUrl)
                    .into(imageIV);
        }
        else
            imageIV.setImageResource(R.drawable.image_not_present);
    }

    private String removeInvalidCharFromLast(String content) {
        String[] s1 = content.split("\\[");
        String[] s2 = s1[0].split("\\.");
        int len = s2.length;
        String newsContent = "";
        for (int i=0; i<len-1;i++) {
            newsContent = newsContent + s2[i];
        }
        return newsContent;
    }

    void onBackBtnClickListener(){
        findViewById(R.id.nd_back_btn).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });
    }
}