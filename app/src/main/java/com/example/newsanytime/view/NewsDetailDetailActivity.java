package com.example.newsanytime.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.newsanytime.R;
import com.example.newsanytime.contract.NewsDetailContract;
import com.example.newsanytime.presenter.NewsDetailPresenter;
import com.squareup.picasso.Picasso;
import java.util.Dictionary;
import java.util.Hashtable;

public class NewsDetailDetailActivity extends AppCompatActivity implements NewsDetailContract {

    ImageView newsImageIV;
    TextView newsHeadlineTV;
    TextView newsDescriptionTV;
    TextView newsContentTV;
    ImageView bookmarkBtn;
    NewsDetailPresenter newsDetailPresenter;
    Dictionary<String, String> newsDict;
    Boolean newsSavedInDB = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        initViews();
        getNewsDetailsFromIntent();
        getSupportActionBar().hide();
        setToolBar();
        newsDetailPresenter = new NewsDetailPresenter(this);

        checkIfNewsAlreadySavedInDB();

        onBookmarkBtnClickListener();
    }


    public void setBookmark(String news) {

        if(news!=null) {
            bookmarkBtn.setImageResource(R.drawable.book_5);
            newsSavedInDB = true;
        }
        else{
            bookmarkBtn.setImageResource(R.drawable.book_4);
            newsSavedInDB = false;
        }
    }

    private void setToolBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
    }

    public void onBookmarkBtnClickListener() {
        bookmarkBtn.setOnClickListener(new  View.OnClickListener(){
            @Override
            public void onClick(View v) {

                if(newsSavedInDB) {
                    bookmarkBtn.setImageResource(R.drawable.book_4);
                    deleteBookmarkedNewsFromDB();
                    newsSavedInDB = false;
                }
                else{
                    bookmarkBtn.setImageResource(R.drawable.book_5);
                    newsDetailPresenter.saveBookmarkedNewsInDB(newsDict, getApplication());
                    newsSavedInDB = true;
                }

            }
        });
    }

    private void initViews() {
        newsHeadlineTV = findViewById(R.id.single_news_heading);
        newsImageIV = findViewById(R.id.single_news_image);
        newsDescriptionTV = findViewById(R.id.single_news_description);
        newsContentTV = findViewById(R.id.single_news_content);
        bookmarkBtn = findViewById(R.id.bookmark_news_item);
    }

    void getNewsDetailsFromIntent() {
        String newsHeadline;
        String newsImage;
        String newsDescription;
        String newsContent;
        String newsPublishedAt;

        if (getIntent().hasExtra("NEWS_HEADLINE")) {
            newsHeadline = getIntent().getStringExtra("NEWS_HEADLINE");
        } else newsHeadline = null;

        if (getIntent().hasExtra("NEWS_IMAGE")) {
            newsImage = getIntent().getStringExtra("NEWS_IMAGE");
        } else newsImage = null;

        if (getIntent().hasExtra("NEWS_DESCRIPTION")) {
            newsDescription = getIntent().getStringExtra("NEWS_DESCRIPTION");
        } else newsDescription = null;

        if (getIntent().hasExtra("NEWS_CONTENT")) {
            newsContent = getIntent().getStringExtra("NEWS_CONTENT");
        } else newsContent = null;

        if (getIntent().hasExtra("NEWS_PUBLISHED_DATE")) {
            newsPublishedAt = getIntent().getStringExtra("NEWS_PUBLISHED_DATE");
        } else newsPublishedAt = null;

        newsDict = new Hashtable<String, String>();
        newsDict.put("NEWS_HEADLINE", newsHeadline);
        newsDict.put("NEWS_IMAGE", newsImage);
        newsDict.put("NEWS_DESCRIPTION", newsDescription);
        newsDict.put("NEWS_CONTENT", newsContent);
        newsDict.put("NEWS_PUBLISHED_AT", newsPublishedAt);

        setActivityViews(newsHeadline, newsImage, newsDescription, newsContent);
    }

    private void setActivityViews(String newsHeadline, String newsImage, String newsDescription, String newsContent) {

        newsHeadlineTV.setText(newsHeadline);
        newsDescriptionTV.setText(newsDescription);
        newsContentTV.setText(newsContent);

        String imageUrl = newsImage;

        if (imageUrl != null) {
            Picasso.with(this)
                    .load(imageUrl)
                    .into(newsImageIV);
        }
    }

    public void checkIfNewsAlreadySavedInDB() {
        String publishDate = newsDict.get("NEWS_PUBLISHED_AT");
        newsDetailPresenter.searchNewsByPublishDate(publishDate, getApplication());
    }

    public void deleteBookmarkedNewsFromDB() {
        String publishDate = newsDict.get("NEWS_PUBLISHED_AT");
        newsDetailPresenter.deleteNewsByPublishDate(publishDate, getApplication());
    }

}