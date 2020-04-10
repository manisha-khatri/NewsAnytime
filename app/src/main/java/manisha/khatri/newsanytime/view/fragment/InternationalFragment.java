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
import manisha.khatri.newsanytime.util.NewsCategory;
import manisha.khatri.newsanytime.view.adapter.InternationalNewsRecyclerViewAdapter;
import manisha.khatri.newsanytime.contract.InternationalNewsContract;
import manisha.khatri.newsanytime.model.News;
import manisha.khatri.newsanytime.presenter.InternationalNewsPresenter;
import manisha.khatri.newsanytime.view.activity.NewsDetailActivity;
import manisha.tuesda.walker.circlerefresh.CircleRefreshLayout;

public class InternationalFragment extends Fragment implements InternationalNewsContract, InternationalNewsRecyclerViewAdapter.RecyclerViewItemListener
{
    InternationalNewsPresenter internationalNewsPresenter;
    RecyclerView recyclerView1, recyclerView2, recyclerView3, recyclerView4;
    ProgressBar prgssBar1,prgssBar2,prgssBar3,prgssBar4;
    TextView callbackRespMsg1, callbackRespMsg2, callbackRespMsg3, callbackRespMsg4;
    LinearLayout callbackRespMsgHolder1, callbackRespMsgHolder2, callbackRespMsgHolder3, callbackRespMsgHolder4;
    View rootView;
    CircleRefreshLayout mRefreshLayout;
    private Handler mWaitHandler = new Handler();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle savedInstanceState){
        rootView = inflater.inflate(R.layout.fragment_international_news, viewGroup, false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        initViews();
        pullToRefreshAnimListener();
        internationalNewsPresenter.fetchNews();  //fetch news by retrofit call
    }

    public void onStart(){
        super.onStart();
    }

    private void pullToRefreshAnimListener() {
        mRefreshLayout.setOnRefreshListener(
            new CircleRefreshLayout.OnCircleRefreshListener() {
                @Override
                public void refreshing() {
                    internationalNewsPresenter.fetchNews();  //fetch news by retrofit call
                }
                @Override
                public void completeRefresh() {
                }
            });
    }

    private void initViews(){
        mRefreshLayout = getView().findViewById(R.id.refresh_layout);
        internationalNewsPresenter = new InternationalNewsPresenter(this);
        recyclerView1 = rootView.findViewById(R.id.int_recycler_view_1);
        recyclerView2 = rootView.findViewById(R.id.int_recycler_view2);
        recyclerView3 = rootView.findViewById(R.id.int_recycler_view3);
        recyclerView4 = rootView.findViewById(R.id.int_recycler_view4);

        prgssBar1 = rootView.findViewById(R.id.int_1_prgss_bar);
        prgssBar2 = rootView.findViewById(R.id.int_2_prgss_bar);
        prgssBar3 = rootView.findViewById(R.id.int_3_prgss_bar);
        prgssBar4 = rootView.findViewById(R.id.int_4_prgss_bar);

        callbackRespMsg1 = rootView.findViewById(R.id.int_1_resp_msg);
        callbackRespMsg2 = rootView.findViewById(R.id.int_2_resp_msg);
        callbackRespMsg3 = rootView.findViewById(R.id.int_3_resp_msg);
        callbackRespMsg4 = rootView.findViewById(R.id.int_4_resp_msg);

        callbackRespMsgHolder1 = rootView.findViewById(R.id.int_1_Resp_Msg_holder);
        callbackRespMsgHolder2 = rootView.findViewById(R.id.int_2_Resp_Msg_holder);
        callbackRespMsgHolder3 = rootView.findViewById(R.id.int_3_Resp_Msg_holder);
        callbackRespMsgHolder4 = rootView.findViewById(R.id.int_4_Resp_Msg_holder);
    }

    public void onSuccessfulResponse(News news, NewsCategory newsCategory) {
        switch(newsCategory){
            case INTERNATIONAL:
                stopLoader(prgssBar1, callbackRespMsgHolder1);
                displayInternationalNews(news);
                break;
            case INTERNATIONAL_SPORTS:
                stopLoader(prgssBar2, callbackRespMsgHolder2);
                displaySportsNews(news);
                break;
            case INTERNATIONAL_BUSINESS:
                stopLoader(prgssBar3, callbackRespMsgHolder3);
                displayBusinessNews(news);
                break;
            case INTERNATIONAL_ENTERTAINMENT:
                stopLoader(prgssBar4, callbackRespMsgHolder4);
                displayEntertainmentNews(news);
                break;
        }
    }

    private void stopLoader(ProgressBar progressBar, LinearLayout layout) {
        progressBar.setVisibility(View.GONE);
        layout.setVisibility(View.GONE);
    }

    public void onFailureResponse(String errorMsg, NewsCategory newsCategory) {
        stopPullToRefreshAnim();
        switch(newsCategory){
            case INTERNATIONAL:
                showErrorOnFailure(prgssBar1, callbackRespMsg1, errorMsg);
                break;
            case INTERNATIONAL_SPORTS:
                showErrorOnFailure(prgssBar2, callbackRespMsg2, errorMsg);
                break;
            case INTERNATIONAL_BUSINESS:
                showErrorOnFailure(prgssBar3, callbackRespMsg3, errorMsg);
                break;
            case INTERNATIONAL_ENTERTAINMENT:
                showErrorOnFailure(prgssBar4, callbackRespMsg4, errorMsg);
                break;
        }
    }

    void showErrorOnFailure(ProgressBar prgssBar, TextView tv, String errorMsg){
        TextView callbackRespMsg;
        prgssBar.setVisibility(View.GONE);
        callbackRespMsg = tv;
        callbackRespMsg.setText(errorMsg);
    }

    void stopPullToRefreshAnim(){
        mWaitHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mRefreshLayout.finishRefreshing();
            }
        }, 2000);
    }

    public void displayInternationalNews(News news) {
        stopPullToRefreshAnim();
        setNewsInRecyclerViewAdapter(news, recyclerView1);
    }

    public void displaySportsNews(News news) {
        setNewsInRecyclerViewAdapter(news, recyclerView2);
    }

    public void displayBusinessNews(News news) {
        setNewsInRecyclerViewAdapter(news, recyclerView3);
    }

    public void displayEntertainmentNews(News news) {
        setNewsInRecyclerViewAdapter(news, recyclerView4);
    }

    public void setNewsInRecyclerViewAdapter(News news, RecyclerView recyclerView) {
        InternationalNewsRecyclerViewAdapter adapter = new InternationalNewsRecyclerViewAdapter(news, getActivity(),this);     //class object, which calls default constructor
        recyclerView.setAdapter(adapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    @Override
    public void onRecyclerViewItemClickListener(Article article){
        Intent intent = new Intent(getActivity(), NewsDetailActivity.class);
        intent.putExtra("HEADLINE", article.getTitle());
        intent.putExtra("IMAGE", article.getUrlToImage());
        intent.putExtra("DESCRIPTION", article.getDescription());
        intent.putExtra("CONTENT", article.getContent());
        intent.putExtra("PUBLISHEDDATE", article.getPublishedAt());
        this.startActivity(intent);
    }

}