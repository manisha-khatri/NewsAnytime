package com.example.newsanytime.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.SearchView;

import com.example.newsanytime.R;
import com.example.newsanytime.adapter.AdvanceSearchRecyclerViewAdapter;
import com.example.newsanytime.contract.AdvanceSearchContract;
import com.example.newsanytime.model.News;
import com.example.newsanytime.presenter.AdvanceSearchPresenter;

public class AdvanceSearchActivity extends AppCompatActivity implements AdvanceSearchContract {

    AdvanceSearchPresenter presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searched_news);

        getSupportActionBar().setDisplayShowTitleEnabled(false);

        presenter = new AdvanceSearchPresenter(this);

        String searchedKeyword = fetchSearchedKeyword();
        if(searchedKeyword!=null){
            presenter.fetchNewsForSearchedKeyword(searchedKeyword);
        }

    }

    public String fetchSearchedKeyword() {
        Bundle extras = getIntent().getExtras();
        if(extras == null) {
            return null;
        } else {
            return extras.getString("SEARCHED_KEYWORD");
        }
    }

    public void setNewsInRecyclerViewAdapter(News news, RecyclerView recyclerView) {
        AdvanceSearchRecyclerViewAdapter adapter = new AdvanceSearchRecyclerViewAdapter(news, this);     //class object, which calls default constructor
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
        menuInflater.inflate(R.menu.back_btn_menu,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.back_button:
                Intent intent = new Intent(AdvanceSearchActivity.this, HomeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
