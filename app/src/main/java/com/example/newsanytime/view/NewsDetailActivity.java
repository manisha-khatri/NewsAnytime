package com.example.newsanytime.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.newsanytime.R;
import com.example.newsanytime.contract.NewsDetailContract;
import com.example.newsanytime.presenter.NewsDetailPresenter;
import com.squareup.picasso.Picasso;
import java.util.Dictionary;
import java.util.Hashtable;

public class NewsDetailActivity extends AppCompatActivity implements NewsDetailContract {

    ImageView newsImageIV;
    TextView newsHeadlineTV;
    Button backBtn;
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
       // getSupportActionBar().setDisplayShowTitleEnabled(false);
        initViews();
        getNewsDetailsFromIntent();
        //getSupportActionBar().hide();
        setToolBar();
        checkIfNewsAlreadySavedInDB();

        onBookmarkBtnClickListener();
        onBackBtnClickListener();
    }


    public void setBookmark(String news) {

        if(news!=null) {
            bookmarkBtn.setImageResource(R.drawable.icon2);
            newsSavedInDB = true;
        }
        else{
            bookmarkBtn.setImageResource(R.drawable.icon1);
            newsSavedInDB = false;
        }
    }

    private void setToolBar() {
        Toolbar toolbar = findViewById(R.id.toolbar1);
    }

    public void onBookmarkBtnClickListener() {
        bookmarkBtn.setOnClickListener(new  View.OnClickListener(){
            @Override
            public void onClick(View v) {

                if(newsSavedInDB) {
                    bookmarkBtn.setImageResource(R.drawable.icon1);
                    deleteBookmarkedNewsFromDB();
                    newsSavedInDB = false;
                }
                else{
                    bookmarkBtn.setImageResource(R.drawable.icon2);
                    newsDetailPresenter.saveBookmarkedNewsInDB(newsDict, getApplication());
                    newsSavedInDB = true;
                }

            }
        });
    }

    private void initViews() {
        newsDetailPresenter = new NewsDetailPresenter(this);
        newsHeadlineTV = findViewById(R.id.single_news_heading);
        newsImageIV = findViewById(R.id.single_news_image);
        newsDescriptionTV = findViewById(R.id.single_news_description);
        newsContentTV = findViewById(R.id.single_news_content);
        bookmarkBtn = findViewById(R.id.bookmark_news_item);
        backBtn = findViewById(R.id.news_detail_back_btn);
    }

    void getNewsDetailsFromIntent() {

        newsDict = new Hashtable<String, String>();
        newsDict.put("NEWS_HEADLINE", getValueFromIntent("NEWS_HEADLINE"));
        newsDict.put("NEWS_IMAGE", getValueFromIntent("NEWS_IMAGE"));
        newsDict.put("NEWS_DESCRIPTION", getValueFromIntent("NEWS_DESCRIPTION"));
        newsDict.put("NEWS_CONTENT", getValueFromIntent("NEWS_CONTENT"));
        newsDict.put("NEWS_PUBLISHED_AT", getValueFromIntent("NEWS_PUBLISHED_DATE"));

        setActivityViews(newsDict.get("NEWS_HEADLINE"),
                newsDict.get("NEWS_IMAGE"),
                newsDict.get("NEWS_DESCRIPTION"),
                newsDict.get("NEWS_CONTENT"));
    }

    private String getValueFromIntent(String key) {
        String value = " ";

        if (getIntent().hasExtra(key)) {
            value = getIntent().getStringExtra(key);

            if(value == null)
                value = " ";
        }
        return value;
    }

    private void setActivityViews(String newsHeadline, String newsImage, String newsDescription, String newsContent) {

        newsHeadlineTV.setText(newsHeadline);
        newsDescriptionTV.setText(newsDescription);


        String[] s1 = newsContent.split("\\[");
        String[] s2 = s1[0].split("\\.");
        int len = s2.length;
        String content = "";
        for (int i=0; i<len-1;i++) {
            content = content + s2[i];
        }

        newsContentTV.setText(content);

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

    void onBackBtnClickListener(){
        backBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });
    }

}