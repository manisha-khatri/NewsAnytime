package manisha.khatri.newsanytime.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import manisha.khatri.newsanytime.R;
import manisha.khatri.newsanytime.model.Article;
import manisha.khatri.newsanytime.util._enum.GenericStrings;
import manisha.khatri.newsanytime.util._enum.NewsIntent;
import manisha.khatri.newsanytime.view.adapter.SearchNewsRecyclerViewAdapter;
import manisha.khatri.newsanytime.contract.SearchNewsContract;
import manisha.khatri.newsanytime.model.News;
import manisha.khatri.newsanytime.presenter.SearchNewsPresenter;

public class SearchNewsNewsActivity extends AppCompatActivity implements SearchNewsContract, SearchNewsRecyclerViewAdapter.RecyclerViewListener {

    SearchNewsPresenter searchNewsPresenter;
    RecyclerView searchNewsRecyclerView;

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

    public String getSearchedKeyword() {
        Bundle extras = getIntent().getExtras();
        if(extras == null) {
            return null;
        } else {
            return extras.getString(GenericStrings.SEARCHED_KEYWORD.toString());
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
    public void displaySearchNews(News news) {
        hideNewsLoadingWidgets();
        setNewsInRecyclerViewAdapter(news, searchNewsRecyclerView);
    }

    public void displaySearchNewsErrorMsg(String errMsg){
        findViewById(R.id.sn_prgss_bar).setVisibility(View.GONE);
        ((TextView)findViewById(R.id.sn_error_msg)).setText(errMsg);
    }

    @Override
    public void onItemClick(Article newsArticle) {
        Intent intent = new Intent(SearchNewsNewsActivity.this, NewsDetailActivity.class);
        intent.putExtra(NewsIntent.HEADLINE.toString(), newsArticle.getTitle());
        intent.putExtra(NewsIntent.IMAGE.toString(), newsArticle.getUrlToImage());
        intent.putExtra(NewsIntent.DESCRIPTION.toString(), newsArticle.getDescription());
        intent.putExtra(NewsIntent.CONTENT.toString(), newsArticle.getContent());
        intent.putExtra(NewsIntent.PUBLISHEDDATE.toString(), newsArticle.getPublishedAt());
        this.startActivity(intent);
    }

    private void initViews() {
        searchNewsPresenter = new SearchNewsPresenter(this);
        searchNewsRecyclerView = findViewById(R.id.sn_recycler_view);
        setToolbar();
    }

    private void setToolbar() {
        setSupportActionBar((Toolbar) findViewById(R.id.sn_toolbar));
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    private void backBtnClickListener(){
        findViewById(R.id.sn_back_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void hideNewsLoadingWidgets() {
        findViewById(R.id.sn_prgss_bar).setVisibility(View.GONE);   // hide progress bar
        findViewById(R.id.sn_msg_holder).setVisibility(View.GONE);
    }
}