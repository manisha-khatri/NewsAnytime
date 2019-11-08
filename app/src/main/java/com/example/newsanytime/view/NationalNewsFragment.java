package com.example.newsanytime.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.newsanytime.R;
import com.example.newsanytime.adapter.NationalNewsRecyclerViewAdapter;
import com.example.newsanytime.contract.NationalNewsContract;
import com.example.newsanytime.model.Article;
import com.example.newsanytime.model.News;
import com.example.newsanytime.presenter.NationalNewsPresenter;
import java.util.List;

public class NationalNewsFragment extends Fragment implements NationalNewsContract, NationalNewsRecyclerViewAdapter.RecyclerViewItemListener {

    NationalNewsPresenter nationalNewsPresenter;
    List<Article> articles;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle savedInstanceState){
        return inflater.inflate(R.layout.fragment_national_news, viewGroup, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        initViews();
        nationalNewsPresenter.fetchNews();
    }


    private void initViews() {
        nationalNewsPresenter = new NationalNewsPresenter(this);
    }

    @Override
    public void displayNationalNewsArticles(News news) {
        RecyclerView recyclerView = getView().findViewById(R.id.recycler_view1);
        setNewsInRecyclerViewAdapter(news, recyclerView);
    }

    @Override
    public void displaySportsNewsArticles(News news) {
        RecyclerView recyclerView = getView().findViewById(R.id.recycler_view2);
        setNewsInRecyclerViewAdapter(news, recyclerView);
    }

    public void displayBusinessNewsArticles(News news) {
        RecyclerView recyclerView = getView().findViewById(R.id.recycler_view3);
        setNewsInRecyclerViewAdapter(news, recyclerView);
    }

    public void displayEntertainmentNewsArticles(News news) {
        RecyclerView recyclerView = getView().findViewById(R.id.recycler_view4);
        setNewsInRecyclerViewAdapter(news, recyclerView);
    }

    public void setNewsInRecyclerViewAdapter(News news, RecyclerView recyclerView) {
        this.articles = news.getArticles();
        NationalNewsRecyclerViewAdapter adapter = new NationalNewsRecyclerViewAdapter(news, getActivity(),this);     //class object, which calls default constructor
        recyclerView.setAdapter(adapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);
    }
    @Override
    public void onRecyclerViewItemClickListener(String newsHeadline, String newsImage, String newsDescription, String newsContent, String newsPublishedDate){
        Intent intent = new Intent(getActivity(), NewsDetailActivity.class);
        intent.putExtra("NEWS_HEADLINE", newsHeadline);
        intent.putExtra("NEWS_IMAGE", newsImage);
        intent.putExtra("NEWS_DESCRIPTION", newsDescription);
        intent.putExtra("NEWS_CONTENT", newsContent);
        intent.putExtra("NEWS_PUBLISHED_DATE", newsPublishedDate);

        this.startActivity(intent);
    }

}