package com.example.newsanytime.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import com.example.newsanytime.R;
import com.example.newsanytime.adapter.BookmarkNewsAdapter;
import com.example.newsanytime.contract.BookmarkNewsContract;
import com.example.newsanytime.presenter.BookmarkNewsPresenter;
import com.example.newsanytime.room.BookmarkedNews;
import java.util.List;

public class BookmarkNewsActivity extends AppCompatActivity implements BookmarkNewsContract, BookmarkNewsAdapter.RecyclerViewItemListener {

    BookmarkNewsPresenter bookmarkNewsPresenter;
    List<BookmarkedNews> newsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmark_news);

        initViews();

        DisplayNews();
    }

    private void initViews() {
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        bookmarkNewsPresenter = new BookmarkNewsPresenter(this);
    }

    private void DisplayNews() {
        bookmarkNewsPresenter.fetchNewsListFromDB(getApplication());
    }

    public void setNewsInRecyclerViewAdapter( RecyclerView recyclerView) {
        BookmarkNewsAdapter adapter = new BookmarkNewsAdapter(newsList, this,this);     //class object, which calls default constructor
        recyclerView.setAdapter(adapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    @Override
    public void renderNewsList(List<BookmarkedNews> newsList) {
        this.newsList = newsList;
        RecyclerView recyclerView =  findViewById(R.id.recycler_view_bookmarked_news);
        setNewsInRecyclerViewAdapter(recyclerView);
    }


    @Override
    public void onRecyclerViewItemClickListener(String newsHeadline, String newsImage, String newsDescription, String newsContent, String newsPublishedDate) {
        Intent intent = new Intent(this, NewsDetailActivity.class);

        intent.putExtra("NEWS_HEADLINE", newsHeadline);
        intent.putExtra("NEWS_IMAGE", newsImage);
        intent.putExtra("NEWS_DESCRIPTION", newsDescription);
        intent.putExtra("NEWS_CONTENT", newsContent);
        intent.putExtra("NEWS_PUBLISHED_DATE", newsPublishedDate);

        this.startActivity(intent);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.bookmark_activity_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.back_button:
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
