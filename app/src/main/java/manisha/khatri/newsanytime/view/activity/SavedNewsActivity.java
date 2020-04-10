package manisha.khatri.newsanytime.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import manisha.khatri.newsanytime.R;
import manisha.khatri.newsanytime.database.BookmarkedNews;
import manisha.khatri.newsanytime.view.adapter.SavedNewsAdapter;
import manisha.khatri.newsanytime.contract.SavedNewsContract;
import manisha.khatri.newsanytime.presenter.SavedNewsPresenter;
import java.util.List;

public class SavedNewsActivity extends AppCompatActivity implements SavedNewsContract, SavedNewsAdapter.RecyclerViewItemListener {

    SavedNewsPresenter savedNewsPresenter;
    ProgressBar prgssBar;
    TextView callbackRespMsg;
    RecyclerView recyclerView;
    LinearLayout callbackRespMsgHolder;
    ImageView backBtn;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_news);
        initViews();
        savedNewsPresenter.fetchNews(getApplication());
        onBackBtnClickListener();
    }

    private void initViews() {
        toolbar = findViewById(R.id.saved_news_toolbar);
        savedNewsPresenter = new SavedNewsPresenter(this);
        prgssBar = findViewById(R.id.saved_news_callback_prgss_bar);
        callbackRespMsg = findViewById(R.id.saved_news_callback_resp_msg);
        recyclerView = findViewById(R.id.recycler_view_saved_news);
        callbackRespMsgHolder = findViewById(R.id.saved_news_callback_Resp_Msg_holder);
        backBtn = findViewById(R.id.saved_news_back_btn);
        setToolbar();
    }

    private void setToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    public void setNewsInRecyclerViewAdapter( RecyclerView recyclerView, List<BookmarkedNews> bookmarkedNewsList) {
        SavedNewsAdapter adapter = new SavedNewsAdapter(bookmarkedNewsList, this,this);     //class object, which calls default constructor
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
    public void renderNewsList(List<BookmarkedNews> bookmarkedNewsList) {
        if(bookmarkedNewsList.size()>0) {
            hideNewsLoadingWidget();
            setNewsInRecyclerViewAdapter(recyclerView, bookmarkedNewsList);
        }
        else
            handleNoSavedNewsInDB();
    }

    private void hideNewsLoadingWidget() {
        prgssBar.setVisibility(View.GONE);
        callbackRespMsg.setVisibility(View.GONE);
        callbackRespMsgHolder.setVisibility(View.GONE);
    }

    @Override
    public void onRecyclerViewItemClickListener(BookmarkedNews bookmarkedNews) {
        Intent intent = new Intent(this, NewsDetailActivity.class);
        intent.putExtra("HEADLINE", bookmarkedNews.getHeadline());
        intent.putExtra("IMAGE", bookmarkedNews.getImageUrl());
        intent.putExtra("DESCRIPTION", bookmarkedNews.getDescription());
        intent.putExtra("CONTENT", bookmarkedNews.getContent());
        intent.putExtra("PUBLISHEDDATE", bookmarkedNews.getPublishedDate());

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
