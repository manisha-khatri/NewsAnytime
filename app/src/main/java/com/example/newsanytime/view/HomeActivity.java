package com.example.newsanytime.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.SearchView;
import com.example.newsanytime.R;
import com.example.newsanytime.adapter.HomeRecyclerViewAdapter;
import com.example.newsanytime.contract.HomeActivityContract;
import com.example.newsanytime.model.Article;
import com.example.newsanytime.model.News;
import com.example.newsanytime.presenter.HomePresenter;

import java.util.Dictionary;
import java.util.List;


public class HomeActivity extends AppCompatActivity implements HomeActivityContract, HomeRecyclerViewAdapter.OnNewsListener {

    HomePresenter homePresenter;
    List<Article> articles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        homePresenter = new HomePresenter(this);
        homePresenter.fetchNews();
    }


    @Override
    public void displayNationalNewsArticles(News news) {
        RecyclerView recyclerView = findViewById(R.id.recycler_view1);
        setNewsInRecyclerViewAdapter(news, recyclerView);
    }

    @Override
    public void displaySportsNewsArticles(News news) {
        RecyclerView recyclerView = findViewById(R.id.recycler_view2);
        setNewsInRecyclerViewAdapter(news, recyclerView);
    }

    public void displayBusinessNewsArticles(News news) {
        RecyclerView recyclerView = findViewById(R.id.recycler_view3);
        setNewsInRecyclerViewAdapter(news, recyclerView);
    }

    public void displayEntertainmentNewsArticles(News news) {
        RecyclerView recyclerView = findViewById(R.id.recycler_view4);
        setNewsInRecyclerViewAdapter(news, recyclerView);
    }

    public void setNewsInRecyclerViewAdapter(News news, RecyclerView recyclerView) {
        this.articles = news.getArticles();
        HomeRecyclerViewAdapter adapter = new HomeRecyclerViewAdapter(news, this,this);     //class object, which calls default constructor
        recyclerView.setAdapter(adapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.search_view_menu,menu);

        MenuItem items = menu.findItem(R.id.search_news);
        SearchView searchView = (SearchView)items.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                callSearchNewsActivity(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // adapter.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.bookmark_items:
                Intent intent = new Intent(this, BookmarkedNewsActivity.class);
                startActivity(intent);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


    public void callSearchNewsActivity(String searchedKeyword){
        Intent intent = new Intent(HomeActivity.this, AdvanceSearchActivity.class);
        intent.putExtra("SEARCHED_KEYWORD",searchedKeyword);
        startActivity(intent);
    }

    @Override
    public void onNewsItemClickListener(String newsHeadline, String newsImage, String newsDescription, String newsContent){
        Intent intent = new Intent(HomeActivity.this, IndividualNewsActivity.class);

        intent.putExtra("NEWS_HEADLINE", newsHeadline);
        intent.putExtra("NEWS_IMAGE", newsImage);
        intent.putExtra("NEWS_DESCRIPTION", newsDescription);
        intent.putExtra("NEWS_CONTENT", newsContent);
        this.startActivity(intent);

    }

    @Override
    public void onBookmarkBtnClickListener(Dictionary<String, String> newsDict) {
        homePresenter.saveBookmarkedNewsInDB(newsDict, getApplication());
    }
}
