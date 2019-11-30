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
import com.example.newsanytime.adapter.TopStoriesRecyclerViewAdapter;
import com.example.newsanytime.contract.TopStoriesContract;
import com.example.newsanytime.model.News;
import com.example.newsanytime.presenter.TopStoriesPresenter;

public class TopStoriesFragment extends Fragment implements TopStoriesContract, TopStoriesRecyclerViewAdapter.RecyclerViewItemListener {
    TopStoriesPresenter topStoriesPresenter;
    RecyclerView recyclerView;
    ProgressBar prgssBar;
    LinearLayout callbackRespMsgHolder;
    TextView callbackRespMsg;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_top_stories, viewGroup, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        initViews();
        topStoriesPresenter.fetchNewsFromServer();  //fetch news by retrofit call
    }

    private void initViews() {
        topStoriesPresenter = new TopStoriesPresenter(this);
        recyclerView = getView().findViewById(R.id.recycler_view_top_stories);
        prgssBar = getView().findViewById(R.id.top_stories_callback_prgss_bar2);
        callbackRespMsgHolder = getView().findViewById(R.id.top_stories_callback_Resp_Msg_holder2);
        callbackRespMsg = getView().findViewById(R.id.top_stories_callback_resp_msg2);
    }

    public void displayTopStories(News news) {
        hideNewsLoadingWidgets();
        setNewsInRecyclerViewAdapter(news, recyclerView);
    }

    public void setNewsInRecyclerViewAdapter(News news, RecyclerView recyclerView) {
        TopStoriesRecyclerViewAdapter adapter = new TopStoriesRecyclerViewAdapter(news, getActivity(), this);     //class object, which calls default constructor
        recyclerView.setAdapter(adapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    private void hideNewsLoadingWidgets() {
        prgssBar.setVisibility(View.GONE);
        callbackRespMsgHolder.setVisibility(View.GONE);
    }

    public void handleInvalidResponseFromServer() {
        String msg = "Data is not available";
        prgssBar.setVisibility(View.GONE);
        callbackRespMsg.setText(msg);
    }

    @Override
    public void onRecyclerViewItemClickListener(String newsHeadline, String newsImage, String newsDescription, String newsContent, String newsPublishedDate) {
        Intent intent = new Intent(getActivity(), NewsDetailActivity.class);
        intent.putExtra("HEADLINE", newsHeadline);
        intent.putExtra("IMAGE", newsImage);
        intent.putExtra("DESCRIPTION", newsDescription);
        intent.putExtra("CONTENT", newsContent);
        intent.putExtra("PUBLISHEDDATE", newsPublishedDate);

        this.startActivity(intent);
    }
}