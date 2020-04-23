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
import manisha.khatri.newsanytime.contract.NewsContract;
import manisha.khatri.newsanytime.util._enum.NewsIntent;
import manisha.khatri.newsanytime.util._enum.NewsType;
import manisha.khatri.newsanytime.view.adapter.NewsRecyclerViewAdapter;
import manisha.khatri.newsanytime.model.Article;
import manisha.khatri.newsanytime.model.News;
import manisha.khatri.newsanytime.presenter.NewsPresenter;
import manisha.khatri.newsanytime.view.activity.NewsDetailActivity;
import manisha.tuesda.walker.circlerefresh.CircleRefreshLayout;

public class NewsFragment extends Fragment implements NewsContract, NewsRecyclerViewAdapter.RecyclerViewItemListener {
    NewsPresenter newsPresenter;
    ProgressBar prgssBar1, prgssBar2,prgssBar3, prgssBar4;
    TextView callbackRespMsg1, callbackRespMsg2, callbackRespMsg3, callbackRespMsg4;
    LinearLayout callbackRespMsgHolder1, callbackRespMsgHolder2, callbackRespMsgHolder3, callbackRespMsgHolder4;
    RecyclerView recyclerView1,recyclerView2,recyclerView3,recyclerView4;
    View rootView;
    CircleRefreshLayout mRefreshLayout2;
    private Handler mWaitHandler = new Handler();
    NewsType newsType;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle savedInstanceState){
        setNewsType(this.getArguments());
        rootView = inflater.inflate(R.layout.fragment_national_news, viewGroup, false);
        return rootView;
    }

    private void setNewsType(Bundle bundle) {
        if (bundle != null) {
             newsType = NewsType.valueOf(bundle.getString("NEWS_TYPE"));
        }
        else newsType = NewsType.NATIONAL;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        initViews();
        pullToRefreshAnim();
        newsPresenter.fetchNews();  //fetch news by retrofit cal
    }

    public void onStart(){
        super.onStart();
    }

    private void pullToRefreshAnim() {
        mRefreshLayout2.setOnRefreshListener(
                new CircleRefreshLayout.OnCircleRefreshListener() {
                    @Override
                    public void refreshing() {
                        newsPresenter.fetchNews();  //fetch news by retrofit call
                    }
                    @Override
                    public void completeRefresh() {
                    }
                });
    }

    private void initViews() {
        mRefreshLayout2 = getView().findViewById(R.id.refresh_layout2);
        newsPresenter = new NewsPresenter(this, newsType);
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

    public void displayInternationalNews(News news) {
        stopLoader(prgssBar1, callbackRespMsgHolder1);
        stopPullToRefreshAnim();
        setNewsInRecyclerViewAdapter(news, recyclerView1);
    }

    public void displayNationalNews(News news) {
        stopLoader(prgssBar1, callbackRespMsgHolder1);
        stopPullToRefreshAnim();
        setNewsInRecyclerViewAdapter(news, recyclerView1);
    }

    public void displaySportsNews(News news) {
        stopLoader(prgssBar2, callbackRespMsgHolder2);
        setNewsInRecyclerViewAdapter(news, recyclerView2);
    }

    public void displayBusinessNews(News news) {
        stopLoader(prgssBar3, callbackRespMsgHolder3);
        setNewsInRecyclerViewAdapter(news, recyclerView3);
    }

    public void displayEntertainmentNews(News news) {
        stopLoader(prgssBar4, callbackRespMsgHolder4);
        setNewsInRecyclerViewAdapter(news, recyclerView4);
    }

    private void stopLoader(ProgressBar progressBar, LinearLayout layout) {
        progressBar.setVisibility(View.GONE);
        layout.setVisibility(View.GONE);
    }

    public void stopPullToRefreshAnim(){
        mWaitHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mRefreshLayout2.finishRefreshing();
                //stopRefreshBtn.callOnClick();
            }
        }, 2000);
    }

    public void setNewsInRecyclerViewAdapter(News news, RecyclerView recyclerView) {
        NewsRecyclerViewAdapter adapter = new NewsRecyclerViewAdapter(news, getActivity(),this);     //class object, which calls default constructor
        recyclerView.setAdapter(adapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    public void onNatInternatNewsFailureResponse(String errorMsg) {
        stopPullToRefreshAnim();
        showErrorOnFailure(prgssBar1, callbackRespMsg1, errorMsg);
    }

    public void onSportsNewsFailureResponse(String errorMsg) {
        showErrorOnFailure(prgssBar2, callbackRespMsg2, errorMsg);
    }

    public void onBusinessNewsFailureResponse(String errorMsg) {
        showErrorOnFailure(prgssBar3, callbackRespMsg3, errorMsg);
    }

    public void onEntertainmentNewsFailureResponse(String errorMsg) {
        showErrorOnFailure(prgssBar4, callbackRespMsg4, errorMsg);
    }

    void showErrorOnFailure(ProgressBar prgssBar, TextView tv, String errorMsg){
        TextView callbackRespMsg;
        prgssBar.setVisibility(View.GONE);
        callbackRespMsg = tv;
        callbackRespMsg.setText(errorMsg);
    }

    @Override
    public void onRecyclerViewItemClickListener(Article article){
        Intent intent = new Intent(getActivity(), NewsDetailActivity.class);
        intent.putExtra(NewsIntent.HEADLINE.toString(), article.getTitle());
        intent.putExtra(NewsIntent.IMAGE.toString(), article.getUrlToImage());
        intent.putExtra(NewsIntent.DESCRIPTION.toString(), article.getDescription());
        intent.putExtra(NewsIntent.CONTENT.toString(), article.getContent());
        intent.putExtra(NewsIntent.PUBLISHEDDATE.toString(), article.getPublishedAt());

        this.startActivity(intent);
    }

}