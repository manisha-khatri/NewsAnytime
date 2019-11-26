package com.example.newsanytime.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.newsanytime.R;
import com.example.newsanytime._enum.NationalNewsType;
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
        nationalNewsPresenter.fetchNewsFromServer();  //fetch news by retrofit call
    }

    private void initViews() {
        nationalNewsPresenter = new NationalNewsPresenter(this);
    }

    public void handleInvalidResponseFromServer(NationalNewsType nationalNewsType){
        String msg = "Data is not available";
        TextView callbackRespMsg;
        switch(nationalNewsType){
            case NATIONAL:
                    getView().findViewById(R.id.callback_1_prgss_bar).setVisibility(View.GONE);
                    callbackRespMsg = getView().findViewById(R.id.callback_1_resp_msg);
                    callbackRespMsg.setText(msg);
                    break;
            case SPORTS:
                    getView().findViewById(R.id.callback_2_prgss_bar).setVisibility(View.GONE);
                    callbackRespMsg = getView().findViewById(R.id.callback_2_resp_msg);
                    callbackRespMsg.setText(msg);
                    break;
            case BUSINESS:
                    getView().findViewById(R.id.callback_3_prgss_bar).setVisibility(View.GONE);
                    callbackRespMsg = getView().findViewById(R.id.callback_3_resp_msg);
                    callbackRespMsg.setText(msg);
                    break;
            case ENTERTAINMENT:
                    getView().findViewById(R.id.callback_4_prgss_bar).setVisibility(View.GONE);
                    callbackRespMsg = getView().findViewById(R.id.callback_4_resp_msg);
                    callbackRespMsg.setText(msg);
                    break;
        }
    }

    private void hideNewsLoadingWidgets(NationalNewsType newType) {
        switch(newType){
            case NATIONAL:
                    getView().findViewById(R.id.callback_1_prgss_bar).setVisibility(View.GONE);
                    getView().findViewById(R.id.callback_1_Resp_Msg_holder).setVisibility(View.GONE);
                    break;
            case SPORTS:
                    getView().findViewById(R.id.callback_2_prgss_bar).setVisibility(View.GONE);
                    getView().findViewById(R.id.callback_2_Resp_Msg_holder).setVisibility(View.GONE);
                    break;
            case BUSINESS:
                    getView().findViewById(R.id.callback_3_prgss_bar).setVisibility(View.GONE);
                    getView().findViewById(R.id.callback_3_Resp_Msg_holder).setVisibility(View.GONE);
                    break;
            case ENTERTAINMENT:
                    getView().findViewById(R.id.callback_4_prgss_bar).setVisibility(View.GONE);
                    getView().findViewById(R.id.callback_4_Resp_Msg_holder).setVisibility(View.GONE);
                    break;
        }
    }

    @Override
    public void displayNationalNewsArticles(News news, NationalNewsType nationalNewsType) {
        hideNewsLoadingWidgets(nationalNewsType);  //recycler view position
        RecyclerView recyclerView = getView().findViewById(R.id.recycler_view_1);
        setNewsInRecyclerViewAdapter(news, recyclerView);
    }

    @Override
    public void displaySportsNewsArticles(News news, NationalNewsType nationalNewsType) {
        hideNewsLoadingWidgets(nationalNewsType);  //recycler view position
        RecyclerView recyclerView = getView().findViewById(R.id.recycler_view2);
        setNewsInRecyclerViewAdapter(news, recyclerView);
    }

    public void displayBusinessNewsArticles(News news, NationalNewsType nationalNewsType) {
        hideNewsLoadingWidgets(nationalNewsType);
        RecyclerView recyclerView = getView().findViewById(R.id.recycler_view3);
        setNewsInRecyclerViewAdapter(news, recyclerView);

    }

    public void displayEntertainmentNewsArticles(News news, NationalNewsType nationalNewsType) {
        hideNewsLoadingWidgets(nationalNewsType);
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
        intent.putExtra("HEADLINE", newsHeadline);
        intent.putExtra("IMAGE", newsImage);
        intent.putExtra("DESCRIPTION", newsDescription);
        intent.putExtra("CONTENT", newsContent);
        intent.putExtra("PUBLISHEDDATE", newsPublishedDate);

        this.startActivity(intent);
    }

}