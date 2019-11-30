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
    ProgressBar prgssBar1, prgssBar2,prgssBar3, prgssBar4;
    TextView callbackRespMsg1, callbackRespMsg2, callbackRespMsg3, callbackRespMsg4;
    LinearLayout callbackRespMsgHolder1, callbackRespMsgHolder2, callbackRespMsgHolder3, callbackRespMsgHolder4;
    RecyclerView recyclerView1,recyclerView2,recyclerView3,recyclerView4;

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
        prgssBar1 = getView().findViewById(R.id.callback_1_prgss_bar);
        prgssBar2 = getView().findViewById(R.id.callback_2_prgss_bar);
        prgssBar3 = getView().findViewById(R.id.callback_3_prgss_bar);
        prgssBar4 = getView().findViewById(R.id.callback_4_prgss_bar);

        callbackRespMsg1 = getView().findViewById(R.id.callback_1_resp_msg);
        callbackRespMsg2 = getView().findViewById(R.id.callback_2_resp_msg);
        callbackRespMsg3 = getView().findViewById(R.id.callback_3_resp_msg);
        callbackRespMsg4 = getView().findViewById(R.id.callback_4_resp_msg);

        callbackRespMsgHolder1 = getView().findViewById(R.id.callback_1_Resp_Msg_holder);
        callbackRespMsgHolder2 = getView().findViewById(R.id.callback_2_Resp_Msg_holder);
        callbackRespMsgHolder3 = getView().findViewById(R.id.callback_3_Resp_Msg_holder);
        callbackRespMsgHolder4 = getView().findViewById(R.id.callback_4_Resp_Msg_holder);

        recyclerView1 = getView().findViewById(R.id.recycler_view_1);
        recyclerView2 = getView().findViewById(R.id.recycler_view2);
        recyclerView3 = getView().findViewById(R.id.recycler_view3);
        recyclerView4 = getView().findViewById(R.id.recycler_view4);
    }

    public void handleInvalidResponseFromServer(NationalNewsType nationalNewsType){
        String msg = "Data is not available";
        TextView callbackRespMsg;
        switch(nationalNewsType){
            case NATIONAL:
                    prgssBar1.setVisibility(View.GONE);
                    callbackRespMsg1.setText(msg);
                    break;
            case SPORTS:
                    prgssBar2.setVisibility(View.GONE);
                callbackRespMsg2.setText(msg);
                    break;
            case BUSINESS:
                    prgssBar3.setVisibility(View.GONE);
                    callbackRespMsg3.setText(msg);
                    break;
            case ENTERTAINMENT:
                    prgssBar4.setVisibility(View.GONE);
                    callbackRespMsg4.setText(msg);
                    break;
        }
    }

    private void hideNewsLoadingWidgets(NationalNewsType newType) {
        switch(newType){
            case NATIONAL:
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

    @Override
    public void displayNationalNewsArticles(News news, NationalNewsType nationalNewsType) {
        hideNewsLoadingWidgets(nationalNewsType);  //recycler view position
        setNewsInRecyclerViewAdapter(news, recyclerView1);
    }

    @Override
    public void displaySportsNewsArticles(News news, NationalNewsType nationalNewsType) {
        hideNewsLoadingWidgets(nationalNewsType);  //recycler view position
        setNewsInRecyclerViewAdapter(news, recyclerView2);
    }

    public void displayBusinessNewsArticles(News news, NationalNewsType nationalNewsType) {
        hideNewsLoadingWidgets(nationalNewsType);
        setNewsInRecyclerViewAdapter(news, recyclerView3);

    }

    public void displayEntertainmentNewsArticles(News news, NationalNewsType nationalNewsType) {
        hideNewsLoadingWidgets(nationalNewsType);
        setNewsInRecyclerViewAdapter(news, recyclerView4);

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