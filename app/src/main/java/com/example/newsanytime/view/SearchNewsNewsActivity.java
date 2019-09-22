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
import com.example.newsanytime.adapter.SearchNewsRecyclerViewAdapter;
import com.example.newsanytime.contract.SearchNewsContract;
import com.example.newsanytime.model.News;
import com.example.newsanytime.presenter.SearchNewsPresenter;

public class SearchNewsNewsActivity extends AppCompatActivity implements SearchNewsContract, SearchNewsRecyclerViewAdapter.RecyclerViewItemListener {

    SearchNewsPresenter searchNewsPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_news);

        initViews();

        String searchedKeyword = getSearchedKeyword();

        if(searchedKeyword != null){
            searchNewsPresenter.fetchNewsForSearchedKeyword(searchedKeyword);
        }
    }

    private void initViews() {
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        searchNewsPresenter = new SearchNewsPresenter(this);
    }

    public String getSearchedKeyword() {
        Bundle extras = getIntent().getExtras();
        if(extras == null) {
            return null;
        } else {
            return extras.getString("SEARCHED_KEYWORD");
        }
    }

    public void setNewsInRecyclerViewAdapter(News news, RecyclerView recyclerView) {
        SearchNewsRecyclerViewAdapter adapter = new SearchNewsRecyclerViewAdapter(news, this,this);     //class object, which calls default constructor
        recyclerView.setAdapter(adapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    @Override
    public void displaySearchedNewsArticles(News news) {
        RecyclerView recyclerView = findViewById(R.id.recycler_view_searched_news);
        setNewsInRecyclerViewAdapter(news, recyclerView);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.search_news_activity_menu,menu);
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

    @Override
    public void onRecyclerViewItemClickListener(String newsHeadline, String newsImage, String newsDescription, String newsContent, String newsPublishedDate) {
        Intent intent = new Intent(SearchNewsNewsActivity.this, NewsDetailActivity.class);

        intent.putExtra("NEWS_HEADLINE", newsHeadline);
        intent.putExtra("NEWS_IMAGE", newsImage);
        intent.putExtra("NEWS_DESCRIPTION", newsDescription);
        intent.putExtra("NEWS_CONTENT", newsContent);
        intent.putExtra("NEWS_CONTENT", newsContent);
        intent.putExtra("NEWS_PUBLISHED_DATE", newsPublishedDate);
        this.startActivity(intent);

    }
}
