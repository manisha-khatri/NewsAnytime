package com.example.newsanytime.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.example.newsanytime.R;
import com.example.newsanytime.adapter.SearchNewsRecyclerViewAdapter;
import com.example.newsanytime.contract.SearchNewsContract;
import com.example.newsanytime.model.News;
import com.example.newsanytime.presenter.SearchNewsPresenter;

public class SearchNewsNewsActivity extends AppCompatActivity implements SearchNewsContract, SearchNewsRecyclerViewAdapter.RecyclerViewItemListener {

    SearchNewsPresenter searchNewsPresenter;
    Button backBtn;
    RecyclerView recyclerView;
    ProgressBar progsBar;
    LinearLayout callbackRespMsgHolder;
    TextView callbackRespMsg;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_news);

        initViews();
        String searchedKeyword = getSearchedKeyword();
        if(searchedKeyword != null) {
            searchNewsPresenter.fetchNewsFromServer(searchedKeyword);
        }
    }

    private void initViews() {
        toolbar = findViewById(R.id.searched_news_toolbar);
        searchNewsPresenter = new SearchNewsPresenter(this);
        backBtn = findViewById(R.id.news_searched_news_back_btn);
        recyclerView = findViewById(R.id.recycler_view_searched_news);
        progsBar = findViewById(R.id.srch_news_callback_prgss_bar);
        callbackRespMsgHolder = findViewById(R.id.srch_news_callback_Resp_Msg_holder);
        callbackRespMsg = findViewById(R.id.srch_news_callback_resp_msg);
        setToolbar();
        onBackBtnClick();
    }

    private void setToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    void onBackBtnClick(){
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
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
        hideNewsLoadingWidgets();
        setNewsInRecyclerViewAdapter(news, recyclerView);
    }

    private void hideNewsLoadingWidgets() {
        progsBar.setVisibility(View.GONE);   // hide progress bar
        callbackRespMsgHolder.setVisibility(View.GONE);
    }

    public void handleInvalidResponseFromServer(){
        String msg = "Data is not available";
        progsBar.setVisibility(View.GONE);
        callbackRespMsg.setText(msg);
    }

    @Override
    public void onRecyclerViewItemClickListener(String newsHeadline, String newsImage, String newsDescription, String newsContent, String newsPublishedDate) {
        Intent intent = new Intent(SearchNewsNewsActivity.this, NewsDetailActivity.class);
        intent.putExtra("HEADLINE", newsHeadline);
        intent.putExtra("IMAGE", newsImage);
        intent.putExtra("DESCRIPTION", newsDescription);
        intent.putExtra("CONTENT", newsContent);
        intent.putExtra("CONTENT", newsContent);
        intent.putExtra("PUBLISHEDDATE", newsPublishedDate);
        this.startActivity(intent);
    }
}
