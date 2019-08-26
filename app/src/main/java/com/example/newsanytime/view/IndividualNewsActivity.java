package com.example.newsanytime.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.newsanytime.R;
import com.example.newsanytime.contract.IndividualNewsContract;
import com.example.newsanytime.presenter.IndividualNewsPresenter;
import com.squareup.picasso.Picasso;

import java.util.Dictionary;
import java.util.Hashtable;

public class IndividualNewsActivity extends AppCompatActivity implements IndividualNewsContract {

    ImageView newsImageIV;
    TextView newsHeadlineTV;
    TextView newsDescriptionTV;
    TextView newsContentTV;
    Button bookmarkBtn;
    IndividualNewsPresenter individualNewsPresenter;
    Dictionary<String,String> newsDict;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_individual_news);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().hide();
        individualNewsPresenter = new IndividualNewsPresenter(this);

        initViews();
        getNewsDetailsFromIntent();
       // onBookmarkBtnClickListener();
    }

    /*private void onBookmarkBtnClickListener() {
        bookmarkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                individualNewsPresenter.saveBookmarkedNewsInDB(newsDict, getApplication());
                bookmarkBtn.setBackgroundResource(R.drawable.bookmark_see_green);
            }
        });
    }*/

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
        newsDict = new Hashtable<String, String>();
        newsDict.put("news headline", newsHeadline);
        newsDict.put("news image", newsImage);
        newsDict.put("news description", newsDescription);
        newsDict.put("news content", newsContent);

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