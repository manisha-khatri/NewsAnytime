package manisha.khatri.newsanytime.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import manisha.khatri.newsanytime.R;
import manisha.khatri.newsanytime.model.Article;
import manisha.khatri.newsanytime.util._enum.NewsIntent;
import manisha.khatri.newsanytime.view.adapter.TopStoriesRecyclerViewAdapter;
import manisha.khatri.newsanytime.contract.TopStoriesContract;
import manisha.khatri.newsanytime.model.News;
import manisha.khatri.newsanytime.presenter.TopStoriesPresenter;
import manisha.khatri.newsanytime.view.activity.NewsDetailActivity;
import manisha.tuesda.walker.circlerefresh.CircleRefreshLayout;

public class TopStoriesNewsFragment extends Fragment implements TopStoriesContract, TopStoriesRecyclerViewAdapter.RecyclerViewItemListener {
    TopStoriesPresenter topStoriesPresenter;
    RecyclerView recyclerView;
    ProgressBar prgssBar;
    LinearLayout callbackRespMsgHolder;
    TextView callbackRespMsg;
    View rootView;
    CircleRefreshLayout mRefreshLayout3;
    private Handler mWaitHandler = new Handler();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_top_stories, viewGroup, false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        initViews();
        pullToRefreshAnimListener();
        topStoriesPresenter.fetchNews();  //fetch news by retrofit call
    }

    public void onStart(){
        super.onStart();
    }

    private void pullToRefreshAnimListener() {
        mRefreshLayout3.setOnRefreshListener(
                new CircleRefreshLayout.OnCircleRefreshListener() {
                    @Override
                    public void refreshing() {
                        topStoriesPresenter.fetchNews();  //fetch news by retrofit call
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

    void stopPullToRefreshAnim(){
        mWaitHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mRefreshLayout3.finishRefreshing();
                //stopRefreshBtn.callOnClick();
            }
        }, 2000);
    }

    @Override
    public void onSuccessfulResponse(News news) {
        stopLoader(prgssBar, callbackRespMsgHolder);
        displayTopStories(news);
    }

    private void stopLoader(ProgressBar progressBar, LinearLayout layout) {
        progressBar.setVisibility(View.GONE);
        layout.setVisibility(View.GONE);
    }

    public void displayTopStories(News news) {
        stopPullToRefreshAnim();
        setNewsInRecyclerViewAdapter(news, recyclerView);
    }

    public void setNewsInRecyclerViewAdapter(News news, RecyclerView recyclerView) {
        TopStoriesRecyclerViewAdapter adapter = new TopStoriesRecyclerViewAdapter(news, getActivity(), this);     //class object, which calls default constructor
        recyclerView.setAdapter(adapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    @Override
    public void onFailureResponse(String errorMsg) {
        stopPullToRefreshAnim();
        showErrorOnFailure(prgssBar, callbackRespMsg, errorMsg);
    }

    void showErrorOnFailure(ProgressBar prgssBar, TextView tv, String errorMsg){
        prgssBar.setVisibility(View.GONE);
        tv.setText(errorMsg);
    }

    @Override
    public void onRecyclerViewItemClickListener(Article article) {
        Intent intent = new Intent(getActivity(), NewsDetailActivity.class);
        intent.putExtra(NewsIntent.HEADLINE.toString(), article.getTitle());
        intent.putExtra(NewsIntent.IMAGE.toString(), article.getUrlToImage());
        intent.putExtra(NewsIntent.DESCRIPTION.toString(), article.getDescription());
        intent.putExtra(NewsIntent.CONTENT.toString(), article.getContent());
        intent.putExtra(NewsIntent.PUBLISHEDDATE.toString(), article.getPublishedAt());

        this.startActivity(intent);
    }


}