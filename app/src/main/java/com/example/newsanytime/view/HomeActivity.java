package com.example.newsanytime.view;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;
import com.example.newsanytime.R;
import com.example.newsanytime.adapter.RecyclerViewAdapter;
import com.example.newsanytime.contract.HomeActivityContract;
import com.example.newsanytime.pojo.News;
import com.example.newsanytime.presenter.HomePresenter;


public class HomeActivity extends AppCompatActivity implements HomeActivityContract {

    HomePresenter homePresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        homePresenter = new HomePresenter(this);
        homePresenter.fetchTopHeadlines();
    }

    @Override
    public void showTopHeadlinesList(News news) {
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(news,this);	 //class object, which calls default constructor
        recyclerView.setAdapter(adapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

}
