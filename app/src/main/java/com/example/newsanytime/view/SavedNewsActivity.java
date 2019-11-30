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
import com.example.newsanytime.adapter.SavedNewsAdapter;
import com.example.newsanytime.contract.SavedNewsContract;
import com.example.newsanytime.presenter.SavedNewsPresenter;
import com.example.newsanytime.room.SavedNews;
import java.util.List;

public class SavedNewsActivity extends AppCompatActivity implements SavedNewsContract, SavedNewsAdapter.RecyclerViewItemListener {

    SavedNewsPresenter savedNewsPresenter;
    List<SavedNews> newsList = null;
    ProgressBar prgssBar;
    TextView callbackRespMsg;
    RecyclerView recyclerView;
    LinearLayout callbackRespMsgHolder;
    Button backBtn;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_news);
        initViews();
        savedNewsPresenter.fetchNewsFromDB(getApplication());
    }

    private void initViews() {
        toolbar = findViewById(R.id.saved_news_toolbar);
        savedNewsPresenter = new SavedNewsPresenter(this);
        prgssBar = findViewById(R.id.saved_news_callback_prgss_bar);
        callbackRespMsg = findViewById(R.id.saved_news_callback_resp_msg);
        recyclerView = findViewById(R.id.recycler_view_saved_news);
        callbackRespMsgHolder = findViewById(R.id.saved_news_callback_Resp_Msg_holder);
        backBtn = findViewById(R.id.saved_news_back_btn);
        onBackBtnClickListener();
        setToolbar();
    }
    private void setToolbar() {
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
        prgssBar.setVisibility(View.GONE);
        callbackRespMsg.setText("No saved news");
    }

    @Override
    public void renderNewsList(List<SavedNews> newsList) {
        this.newsList = newsList;
        hideNewsLoadingWidget();
        setNewsInRecyclerViewAdapter(recyclerView);
    }

    private void hideNewsLoadingWidget() {
        prgssBar.setVisibility(View.GONE);
        callbackRespMsg.setVisibility(View.GONE);
        callbackRespMsgHolder.setVisibility(View.GONE);
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
        backBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });
    }
}
