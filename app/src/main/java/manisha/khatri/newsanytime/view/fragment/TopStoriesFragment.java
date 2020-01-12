package manisha.khatri.newsanytime.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import manisha.khatri.newsanytime.R;
import manisha.khatri.newsanytime.adapter.TopStoriesRecyclerViewAdapter;
import manisha.khatri.newsanytime.contract.TopStoriesContract;
import manisha.khatri.newsanytime.model.News;
import manisha.khatri.newsanytime.presenter.TopStoriesPresenter;
import manisha.khatri.newsanytime.view.NewsDetailActivity;
import manisha.tuesda.walker.circlerefresh.CircleRefreshLayout;

public class TopStoriesFragment extends Fragment implements TopStoriesContract, TopStoriesRecyclerViewAdapter.RecyclerViewItemListener {
    TopStoriesPresenter topStoriesPresenter;
    RecyclerView recyclerView;
    ProgressBar prgssBar;
    LinearLayout callbackRespMsgHolder;
    TextView callbackRespMsg;
    View rootView;
    String tag = "TopStoriesFragment";
    CircleRefreshLayout mRefreshLayout3;
    private Handler mWaitHandler = new Handler();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_top_stories, viewGroup, false);
        Log.v(tag,"onViewCreated");
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        Log.v(tag,"onViewCreated");
        initViews();
        refreshListener();
        topStoriesPresenter.fetchNewsFromServer();  //fetch news by retrofit call
    }

    public void onStart(){
        super.onStart();
    }

    private void refreshListener() {
        mRefreshLayout3.setOnRefreshListener(
                new CircleRefreshLayout.OnCircleRefreshListener() {
                    @Override
                    public void refreshing() {
                        topStoriesPresenter.fetchNewsFromServer();  //fetch news by retrofit call
                    }
                    @Override
                    public void completeRefresh() {
                    }
                });
    }

    private void initViews() {
        mRefreshLayout3 = getView().findViewById(R.id.refresh_layout3);
        topStoriesPresenter = new TopStoriesPresenter(this);
        recyclerView = rootView.findViewById(R.id.recycler_view_top_stories);
        prgssBar = rootView.findViewById(R.id.top_stories_callback_prgss_bar2);
        callbackRespMsgHolder = rootView.findViewById(R.id.top_stories_callback_Resp_Msg_holder2);
        callbackRespMsg = rootView.findViewById(R.id.top_stories_callback_resp_msg2);
    }

    public void displayTopStories(News news) {
        mWaitHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mRefreshLayout3.finishRefreshing();
                //stopRefreshBtn.callOnClick();
            }
        }, 2000);
        //mRefreshLayout3.finishRefreshing();
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
        mWaitHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mRefreshLayout3.finishRefreshing();
                //stopRefreshBtn.callOnClick();
            }
        }, 2000);
        //mRefreshLayout3.finishRefreshing();
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