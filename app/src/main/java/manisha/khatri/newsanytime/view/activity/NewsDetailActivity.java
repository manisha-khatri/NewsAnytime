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
import com.squareup.picasso.Picasso;

public class NewsDetailActivity extends AppCompatActivity implements NewsDetailContract {
    ImageView imageIV;
    TextView headlineTV;
    TextView descriptionTV;
    TextView contentTV;
    TextView publishedDateTV;
    ImageView backBtn;
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
        headlineTV = findViewById(R.id.single_news_heading);
        imageIV = findViewById(R.id.single_news_image);
        descriptionTV = findViewById(R.id.single_news_description);
        contentTV = findViewById(R.id.single_news_content);
        bookmarkBtn = findViewById(R.id.bookmark_news_item);
        backBtn = findViewById(R.id.news_detail_back_btn);
        publishedDateTV = findViewById(R.id.news_detail_published_date);
    }

    void getNewsDetailsFromIntent() {
        bookmarkedNews = new BookmarkedNews();
        bookmarkedNews.setHeadline(getValueFromIntent("HEADLINE"));
        bookmarkedNews.setImageUrl(getValueFromIntent("IMAGE"));
        bookmarkedNews.setContent(getValueFromIntent("CONTENT"));
        bookmarkedNews.setDescription(getValueFromIntent("DESCRIPTION"));
        bookmarkedNews.setPublishedDate(getValueFromIntent("PUBLISHEDDATE"));
        setActivityViews();
    }

    private void setActivityViews() {
        headlineTV.setText(bookmarkedNews.getHeadline());
        descriptionTV.setText(bookmarkedNews.getDescription());

        String content = bookmarkedNews.getContent();
        String newsContent = removeInvalidCharFromLast(content);
        contentTV.setText(newsContent);

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
        bookmarkBtn.setImageResource(R.drawable.final_bookmark);
        newsSavedInDB = false;
    }

    public void checkBookmark() {
        bookmarkBtn.setImageResource(R.drawable.final_bookmark_filled_icon);
        newsSavedInDB = true;
    }

    public void deleteSavedNewsFromDB() {
        newsDetailPresenter.deleteNews(bookmarkedNews.getPublishedDate(), getApplication());
    }

    private void setToolbar() {
        Toolbar toolbar = findViewById(R.id.news_detail_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    private String getValueFromIntent(String key) {
        return (getIntent().hasExtra(key)) ? getIntent().getStringExtra(key) : " ";
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

    void onBackBtnClickListener(){
        backBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });
    }
}