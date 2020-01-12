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
import manisha.khatri.newsanytime._enum.NationalNewsType;
import manisha.khatri.newsanytime.adapter.NationalNewsRecyclerViewAdapter;
import manisha.khatri.newsanytime.contract.NationalNewsContract;
import manisha.khatri.newsanytime.model.Article;
import manisha.khatri.newsanytime.model.News;
import manisha.khatri.newsanytime.presenter.NationalNewsPresenter;
import manisha.khatri.newsanytime.view.NewsDetailActivity;
import manisha.tuesda.walker.circlerefresh.CircleRefreshLayout;
import java.util.List;

public class NationalNewsFragment extends Fragment implements NationalNewsContract, NationalNewsRecyclerViewAdapter.RecyclerViewItemListener {
    NationalNewsPresenter nationalNewsPresenter;
    List<Article> articles;
    ProgressBar prgssBar1, prgssBar2,prgssBar3, prgssBar4;
    TextView callbackRespMsg1, callbackRespMsg2, callbackRespMsg3, callbackRespMsg4;
    LinearLayout callbackRespMsgHolder1, callbackRespMsgHolder2, callbackRespMsgHolder3, callbackRespMsgHolder4;
    RecyclerView recyclerView1,recyclerView2,recyclerView3,recyclerView4;
    View rootView;
    String tag = "NationalNewsFragment";
    CircleRefreshLayout mRefreshLayout2;
    private Handler mWaitHandler = new Handler();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle savedInstanceState){
        rootView = inflater.inflate(R.layout.fragment_national_news, viewGroup, false);
        Log.v(tag,"onCreateView");
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        Log.v(tag,"onViewCreated");
        initViews();
        refreshListener();
        nationalNewsPresenter.fetchNewsFromServer();  //fetch news by retrofit cal
    }

    public void onStart(){
        super.onStart();
        Log.v(tag,"onStart");
    }

    private void refreshListener() {
        mRefreshLayout2.setOnRefreshListener(
                new CircleRefreshLayout.OnCircleRefreshListener() {
                    @Override
                    public void refreshing() {
                        nationalNewsPresenter.fetchNewsFromServer();  //fetch news by retrofit call
                    }
                    @Override
                    public void completeRefresh() {
                    }
                });
    }
    private void initViews() {
        mRefreshLayout2 = getView().findViewById(R.id.refresh_layout2);
        nationalNewsPresenter = new NationalNewsPresenter(this);
        prgssBar1 = rootView.findViewById(R.id.callback_1_prgss_bar);
        prgssBar2 = rootView.findViewById(R.id.callback_2_prgss_bar);
        prgssBar3 = rootView.findViewById(R.id.callback_3_prgss_bar);
        prgssBar4 = rootView.findViewById(R.id.callback_4_prgss_bar);

        callbackRespMsg1 = rootView.findViewById(R.id.callback_1_resp_msg);
        callbackRespMsg2 = rootView.findViewById(R.id.callback_2_resp_msg);
        callbackRespMsg3 = rootView.findViewById(R.id.callback_3_resp_msg);
        callbackRespMsg4 = rootView.findViewById(R.id.callback_4_resp_msg);

        callbackRespMsgHolder1 = rootView.findViewById(R.id.callback_1_Resp_Msg_holder);
        callbackRespMsgHolder2 = rootView.findViewById(R.id.callback_2_Resp_Msg_holder);
        callbackRespMsgHolder3 = rootView.findViewById(R.id.callback_3_Resp_Msg_holder);
        callbackRespMsgHolder4 = rootView.findViewById(R.id.callback_4_Resp_Msg_holder);

        recyclerView1 = rootView.findViewById(R.id.recycler_view_1);
        recyclerView2 = rootView.findViewById(R.id.recycler_view2);
        recyclerView3 = rootView.findViewById(R.id.recycler_view3);
        recyclerView4 = rootView.findViewById(R.id.recycler_view4);
    }

    public void handleInvalidResponseFromServer(NationalNewsType nationalNewsType){
        mWaitHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mRefreshLayout2.finishRefreshing();
                //stopRefreshBtn.callOnClick();
            }
        }, 2000);
        //mRefreshLayout3.finishRefreshing();
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
        mWaitHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mRefreshLayout2.finishRefreshing();
                //stopRefreshBtn.callOnClick();
            }
        }, 2000);
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