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
import manisha.khatri.newsanytime.model.Article;
import manisha.khatri.newsanytime.view.adapter.SearchNewsRecyclerViewAdapter;
import manisha.khatri.newsanytime.contract.SearchNewsContract;
import manisha.khatri.newsanytime.model.News;
import manisha.khatri.newsanytime.presenter.SearchNewsPresenter;

public class SearchNewsNewsActivity extends AppCompatActivity implements SearchNewsContract, SearchNewsRecyclerViewAdapter.RecyclerViewItemListener {

    SearchNewsPresenter searchNewsPresenter;
    ImageView backBtn;
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
        backBtnClickListener();
        String searchedKeyword = getSearchedKeyword();
        if(searchedKeyword != null) {
            searchNewsPresenter.fetchNews(searchedKeyword);
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
    }

    private void setToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    void backBtnClickListener(){
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
    public void onRecyclerViewItemClickListener(Article newsArticle) {
        Intent intent = new Intent(SearchNewsNewsActivity.this, NewsDetailActivity.class);
        intent.putExtra("HEADLINE", newsArticle.getTitle());
        intent.putExtra("IMAGE", newsArticle.getUrlToImage());
        intent.putExtra("DESCRIPTION", newsArticle.getDescription());
        intent.putExtra("CONTENT", newsArticle.getContent());
        intent.putExtra("CONTENT", newsArticle.getContent());
        intent.putExtra("PUBLISHEDDATE", newsArticle.getPublishedAt());
        this.startActivity(intent);
    }
}