package com.example.newsanytime.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import com.example.newsanytime.R;
import com.example.newsanytime.adapter.SavedNewsAdapter;
import com.example.newsanytime.contract.SavedNewsContract;
import com.example.newsanytime.presenter.SavedNewsPresenter;
import com.example.newsanytime.room.SavedNews;
import java.util.List;

public class SavedNewsActivity extends AppCompatActivity implements SavedNewsContract, SavedNewsAdapter.RecyclerViewItemListener {

    SavedNewsPresenter savedNewsPresenter;
    List<SavedNews> newsList = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_news);
        initViews();
        savedNewsPresenter.fetchNewsFromDB(getApplication());
    }

    private void initViews() {
        savedNewsPresenter = new SavedNewsPresenter(this);
        onBackBtnClickListener();
        setToolbar();
    }
    private void setToolbar() {
        Toolbar toolbar = findViewById(R.id.saved_news_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    public void setNewsInRecyclerViewAdapter( RecyclerView recyclerView) {
        SavedNewsAdapter adapter = new SavedNewsAdapter(newsList, this,this);     //class object, which calls default constructor
        recyclerView.setAdapter(adapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
    }
    public void handleNoSavedNewsInDB(){
        findViewById(R.id.saved_news_callback_prgss_bar).setVisibility(View.GONE);
        TextView callbackRespMsg = findViewById(R.id.saved_news_callback_resp_msg);
        callbackRespMsg.setText("No saved news");
    }

    @Override
    public void renderNewsList(List<SavedNews> newsList) {
        this.newsList = newsList;
        hideNewsLoadingWidget();
        RecyclerView recyclerView = findViewById(R.id.recycler_view_saved_news);
        setNewsInRecyclerViewAdapter(recyclerView);
    }

    private void hideNewsLoadingWidget() {
        findViewById(R.id.saved_news_callback_prgss_bar).setVisibility(View.GONE);
        findViewById(R.id.saved_news_callback_resp_msg).setVisibility(View.GONE);
        findViewById(R.id.saved_news_callback_Resp_Msg_holder).setVisibility(View.GONE);
    }

    @Override
    public void onRecyclerViewItemClickListener(String headline, String image, String description, String content, String publishedDate) {
        Intent intent = new Intent(this, NewsDetailActivity.class);

        intent.putExtra("HEADLINE", headline);
        intent.putExtra("IMAGE", image);
        intent.putExtra("DESCRIPTION", description);
        intent.putExtra("CONTENT", content);
        intent.putExtra("PUBLISHEDDATE", publishedDate);

        this.startActivity(intent);
    }

    void onBackBtnClickListener(){
        findViewById(R.id.saved_news_back_btn).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });
    }
}
