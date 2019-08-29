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
import com.example.newsanytime.adapter.BookmarkedNewsAdapter;
import com.example.newsanytime.contract.BookmarkedNewsContract;
import com.example.newsanytime.presenter.BookmarkedNewsPresenter;
import com.example.newsanytime.room.BookmarkedNews;
import java.util.List;

public class BookmarkedNewsActivity extends AppCompatActivity implements BookmarkedNewsContract, BookmarkedNewsAdapter.OnBookmarkedNewsItemListener {
    BookmarkedNewsPresenter bookmarkedNewsPresenter;
    List<BookmarkedNews> newsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmarked_news);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        bookmarkedNewsPresenter = new BookmarkedNewsPresenter(this);

        DisplayNews();
    }

    private void DisplayNews() {
        bookmarkedNewsPresenter.fetchNewsListFromDB(getApplication());
    }

    public void setNewsInRecyclerViewAdapter( RecyclerView recyclerView) {
        BookmarkedNewsAdapter adapter = new BookmarkedNewsAdapter(newsList, this,this);     //class object, which calls default constructor
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
    public void onNewsItemClickListener(String newsHeadline, String newsImage, String newsDescription, String newsContent, String newsPublishedDate) {
        Intent intent = new Intent(this, NewsDetailDetailActivity.class);

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
        menuInflater.inflate(R.menu.back_btn_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.back_button:
                Intent intent = new Intent(this, HomeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
