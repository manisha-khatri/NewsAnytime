package com.example.newsanytime.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.example.newsanytime.R;
import com.example.newsanytime._enum.InternationalNewsType;
import com.example.newsanytime.adapter.InternationalNewsRecyclerViewAdapter;
import com.example.newsanytime.contract.InternationalNewsContract;
import com.example.newsanytime.model.Article;
import com.example.newsanytime.model.News;
import com.example.newsanytime.presenter.InternationalNewsPresenter;
import java.util.List;

public class InternationalFragment extends Fragment implements InternationalNewsContract, InternationalNewsRecyclerViewAdapter.RecyclerViewItemListener
{
    InternationalNewsPresenter internationalNewsPresenter;
    List<Article> articles;
    RecyclerView recyclerView1, recyclerView2, recyclerView3, recyclerView4;
    ProgressBar prgssBar1,prgssBar2,prgssBar3,prgssBar4;
    TextView callbackRespMsg1, callbackRespMsg2, callbackRespMsg3, callbackRespMsg4;
    LinearLayout callbackRespMsgHolder1, callbackRespMsgHolder2, callbackRespMsgHolder3, callbackRespMsgHolder4;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle savedInstanceState){
        return inflater.inflate(R.layout.fragment_international_news, viewGroup, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        initViews();
        internationalNewsPresenter.fetchNewsFromServer();  //fetch news by retrofit call
    }

    private void initViews(){
        internationalNewsPresenter = new InternationalNewsPresenter(this);
        recyclerView1 = getView().findViewById(R.id.int_recycler_view_1);
        recyclerView2 = getView().findViewById(R.id.int_recycler_view2);
        recyclerView3 = getView().findViewById(R.id.int_recycler_view3);
        recyclerView4 = getView().findViewById(R.id.int_recycler_view4);

        prgssBar1 = getView().findViewById(R.id.int_1_prgss_bar);
        prgssBar2 = getView().findViewById(R.id.int_2_prgss_bar);
        prgssBar3 = getView().findViewById(R.id.int_3_prgss_bar);
        prgssBar4 = getView().findViewById(R.id.int_4_prgss_bar);

        callbackRespMsg1 = getView().findViewById(R.id.int_1_resp_msg);
        callbackRespMsg2 = getView().findViewById(R.id.int_2_resp_msg);
        callbackRespMsg3 = getView().findViewById(R.id.int_3_resp_msg);
        callbackRespMsg4 = getView().findViewById(R.id.int_4_resp_msg);

        callbackRespMsgHolder1 = getView().findViewById(R.id.int_1_Resp_Msg_holder);
        callbackRespMsgHolder2 = getView().findViewById(R.id.int_2_Resp_Msg_holder);
        callbackRespMsgHolder3 = getView().findViewById(R.id.int_3_Resp_Msg_holder);
        callbackRespMsgHolder4 = getView().findViewById(R.id.int_4_Resp_Msg_holder);
    }

    @Override
    public void displayInternationalNewsArticles(News news, InternationalNewsType internationalNewsType) {
        hideNewsLoadingWidgets(internationalNewsType);  //recycler view position
        setNewsInRecyclerViewAdapter(news, recyclerView1);
    }

    @Override
    public void displaySportsNewsArticles(News news, InternationalNewsType internationalNewsType) {
        hideNewsLoadingWidgets(internationalNewsType);  //recycler view position
        setNewsInRecyclerViewAdapter(news, recyclerView2);
    }

    public void displayBusinessNewsArticles(News news, InternationalNewsType internationalNewsType) {
        hideNewsLoadingWidgets(internationalNewsType);
        setNewsInRecyclerViewAdapter(news, recyclerView3);
    }

    public void displayEntertainmentNewsArticles(News news, InternationalNewsType internationalNewsType) {
        hideNewsLoadingWidgets(internationalNewsType);
        setNewsInRecyclerViewAdapter(news, recyclerView4);

    }

    public void setNewsInRecyclerViewAdapter(News news, RecyclerView recyclerView) {
        this.articles = news.getArticles();
        InternationalNewsRecyclerViewAdapter adapter = new InternationalNewsRecyclerViewAdapter(news, getActivity(),this);     //class object, which calls default constructor
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

    public void handleInvalidResponseFromServer(InternationalNewsType internationalNewsType){
        String msg = "Data is not available";
        TextView callbackRespMsg;
        switch(internationalNewsType){
            case INTERNATIONAL:
                prgssBar1.setVisibility(View.GONE);
                callbackRespMsg = callbackRespMsg1;
                callbackRespMsg.setText(msg);
                break;
            case SPORTS:
                prgssBar2.setVisibility(View.GONE);
                callbackRespMsg = callbackRespMsg2;
                callbackRespMsg.setText(msg);
                break;
            case BUSINESS:
                prgssBar3.setVisibility(View.GONE);
                callbackRespMsg = callbackRespMsg3;
                callbackRespMsg.setText(msg);
                break;
            case ENTERTAINMENT:
                prgssBar4.setVisibility(View.GONE);
                callbackRespMsg = callbackRespMsg4;
                callbackRespMsg.setText(msg);
                break;
        }
    }

    private void hideNewsLoadingWidgets(InternationalNewsType newType) {
        switch(newType){
            case INTERNATIONAL:
                prgssBar1.setVisibility(View.GONE);
                callbackRespMsgHolder1.setVisibility(View.GONE);
                break;
            case SPORTS:
                prgssBar2.setVisibility(View.GONE);
                callbackRespMsgHolder2.setVisibility(View.GONE);
                break;
            case BUSINESS:
                prgssBar3.setVisibility(View.GONE);
                callbackRespMsgHolder3.setVisibility(View.GONE);
                break;
            case ENTERTAINMENT:
                prgssBar4.setVisibility(View.GONE);
                callbackRespMsgHolder4.setVisibility(View.GONE);
                break;
        }
    }

}