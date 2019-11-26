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
import com.example.newsanytime.util.DateCalculator;
import com.squareup.picasso.Picasso;
import java.util.Dictionary;
import java.util.Hashtable;

public class NewsDetailActivity extends AppCompatActivity implements NewsDetailContract {

    ImageView imageIV;
    TextView headlineTV;
    TextView descriptionTV;
    TextView contentTV;
    TextView publishedDateTV;
    Button backBtn;
    ImageView bookmarkBtn;
    NewsDetailPresenter newsDetailPresenter;
    Dictionary<String, String> newsDict;
    Boolean newsSavedInDB = false;

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

    public void enableBookmark() {
        bookmarkBtn.setImageResource(R.drawable.icon2);
        newsSavedInDB = true;
    }

    public void disableBookmark(){
        bookmarkBtn.setImageResource(R.drawable.icon1);
        newsSavedInDB = false;
    }

    public void onBookmarkBtnClickListener() {
        bookmarkBtn.setOnClickListener(new  View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(newsSavedInDB) {
                    disableBookmark();
                    deleteSavedNewsFromDB();
                }
                else{
                    enableBookmark();
                    newsDetailPresenter.saveNewsInDB(newsDict, getApplication());
                }
            }
        });
    }

    private void initViews() {
        setToolbar();
        newsDetailPresenter = new NewsDetailPresenter(this);
        headlineTV = findViewById(R.id.single_news_heading);
        imageIV = findViewById(R.id.single_news_image);
        descriptionTV = findViewById(R.id.single_news_description);
        contentTV = findViewById(R.id.single_news_content);
        bookmarkBtn = findViewById(R.id.bookmark_news_item);
        backBtn = findViewById(R.id.news_detail_back_btn);
        publishedDateTV = findViewById(R.id.news_detail_published_date);
    }

    private void setToolbar() {
        Toolbar toolbar = findViewById(R.id.news_detail_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    void getNewsDetailsFromIntent() {
        newsDict = new Hashtable<String, String>();
        newsDict.put("HEADLINE", getValueFromIntent("HEADLINE"));
        newsDict.put("IMAGE", getValueFromIntent("IMAGE"));
        newsDict.put("DESCRIPTION", getValueFromIntent("DESCRIPTION"));
        newsDict.put("CONTENT", getValueFromIntent("CONTENT"));
        newsDict.put("PUBLISHEDAT", getValueFromIntent("PUBLISHEDDATE"));
        setActivityViews(newsDict);
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

    private void setActivityViews(Dictionary<String, String> newsDict) {
        headlineTV.setText(newsDict.get("HEADLINE"));
        descriptionTV.setText(newsDict.get("DESCRIPTION"));

        String content = newsDict.get("CONTENT");
        String newsContent = removeInvalidCharFromLast(content);
        contentTV.setText(newsContent);

        String imageUrl = newsDict.get("IMAGE");
        setImage(imageUrl);
        setPublishedDate(newsDict.get("PUBLISHEDAT"));
    }
    private void setPublishedDate(String publishedDateStr) {
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

    public void checkIfNewsAlreadySavedInDB() {
        String publishDate = newsDict.get("PUBLISHEDAT");
        newsDetailPresenter.searchNewsByPublishDate(publishDate, getApplication());
    }

    public void deleteSavedNewsFromDB() {
        String publishDate = newsDict.get("PUBLISHEDAT");
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