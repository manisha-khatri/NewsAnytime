package com.example.newsanytime.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import com.example.newsanytime.R;
import com.example.newsanytime.adapter.RecyclerViewAdapter;
import com.example.newsanytime.contract.HomeActivityContract;
import com.example.newsanytime.model.News;
import com.example.newsanytime.presenter.HomePresenter;


public class HomeActivity extends AppCompatActivity implements HomeActivityContract {

    HomePresenter homePresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

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
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(news, this);     //class object, which calls default constructor
        recyclerView.setAdapter(adapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);
    }



}