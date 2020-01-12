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
import manisha.khatri.newsanytime._enum.InternationalNewsType;
import manisha.khatri.newsanytime.adapter.InternationalNewsRecyclerViewAdapter;
import manisha.khatri.newsanytime.contract.InternationalNewsContract;
import manisha.khatri.newsanytime.model.Article;
import manisha.khatri.newsanytime.model.News;
import manisha.khatri.newsanytime.presenter.InternationalNewsPresenter;
import manisha.khatri.newsanytime.view.NewsDetailActivity;
import manisha.tuesda.walker.circlerefresh.CircleRefreshLayout;
import java.util.List;

public class InternationalFragment extends Fragment implements InternationalNewsContract, InternationalNewsRecyclerViewAdapter.RecyclerViewItemListener
{
    InternationalNewsPresenter internationalNewsPresenter;
    List<Article> articles;
    RecyclerView recyclerView1, recyclerView2, recyclerView3, recyclerView4;
    ProgressBar prgssBar1,prgssBar2,prgssBar3,prgssBar4;
    TextView callbackRespMsg1, callbackRespMsg2, callbackRespMsg3, callbackRespMsg4;
    LinearLayout callbackRespMsgHolder1, callbackRespMsgHolder2, callbackRespMsgHolder3, callbackRespMsgHolder4;
    View rootView;
    String tag="InternationalFragment";
    CircleRefreshLayout mRefreshLayout;
    private Handler mWaitHandler = new Handler();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle savedInstanceState){
        rootView = inflater.inflate(R.layout.fragment_international_news, viewGroup, false);
        Log.v(tag,"onCreateView");
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        Log.v(tag,"onViewCreated");
        initViews();
        refreshListener();
        internationalNewsPresenter.fetchNewsFromServer();  //fetch news by retrofit call
    }

    public void onStart(){
        super.onStart();
        Log.v(tag,"onStart");
    }

    private void refreshListener() {
        mRefreshLayout.setOnRefreshListener(
            new CircleRefreshLayout.OnCircleRefreshListener() {
                @Override
                public void refreshing() {
                    internationalNewsPresenter.fetchNewsFromServer();  //fetch news by retrofit call
                }
                @Override
                public void completeRefresh() {
                }
            });
    //return null;
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

    @Override
    public void displayInternationalNewsArticles(News news, InternationalNewsType internationalNewsType) {
        mWaitHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mRefreshLayout.finishRefreshing();
                //stopRefreshBtn.callOnClick();
            }
        }, 2000);
        //mRefreshLayout.finishRefreshing();
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
        mWaitHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mRefreshLayout.finishRefreshing();
                //stopRefreshBtn.callOnClick();
            }
        }, 2000);
       // mRefreshLayout3.finishRefreshing();
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