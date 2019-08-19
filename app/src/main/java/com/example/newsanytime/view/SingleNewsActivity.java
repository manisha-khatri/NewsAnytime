package com.example.newsanytime.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.newsanytime.R;
import com.squareup.picasso.Picasso;

public class SingleNewsActivity extends AppCompatActivity {

    ImageView newsImageIV;
    TextView newsHeadlineTV;
    TextView newsDescriptionTV;
    TextView newsContentTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_news);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        initViews();
        getNewsDetailsFromIntent();

    }

    private void initViews() {
        newsHeadlineTV = findViewById(R.id.single_news_heading);
        newsImageIV = findViewById(R.id.single_news_image);
        newsDescriptionTV = findViewById(R.id.single_news_description);
        newsContentTV = findViewById(R.id.single_news_content);
    }

    void getNewsDetailsFromIntent() {
        String newsHeadline;
        String newsImage;
        String newsDescription;
        String newsContent;

        if (getIntent().hasExtra("NEWS_HEADLINE")) {
                 newsHeadline = getIntent().getStringExtra("NEWS_HEADLINE");
        }
        else newsHeadline = null;

        if (getIntent().hasExtra("NEWS_IMAGE")) {
            newsImage = getIntent().getStringExtra("NEWS_IMAGE");
        }
        else newsImage = null;

        if (getIntent().hasExtra("NEWS_DESCRIPTION")) {
            newsDescription = getIntent().getStringExtra("NEWS_DESCRIPTION");
        }
        else newsDescription = null;

        if (getIntent().hasExtra("NEWS_CONTENT")) {
            newsContent = getIntent().getStringExtra("NEWS_CONTENT");
        }
        else newsContent = null;

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

}