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
import manisha.khatri.newsanytime.util._enum.GenericStrings;
import manisha.khatri.newsanytime.util._enum.NewsIntent;
import manisha.khatri.newsanytime.util._enum.Country;
import manisha.khatri.newsanytime.view.adapter.NewsRecyclerViewAdapter;
import manisha.khatri.newsanytime.model.Article;
import manisha.khatri.newsanytime.model.News;
import manisha.khatri.newsanytime.presenter.NewsPresenter;
import manisha.khatri.newsanytime.view.activity.NewsDetailActivity;
import manisha.tuesda.walker.circlerefresh.CircleRefreshLayout;

public class NewsFragment extends Fragment implements NewsContract, NewsRecyclerViewAdapter.RecyclerViewItemListener {
    NewsPresenter newsPresenter;
    View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle savedInstanceState){
        rootView = inflater.inflate(R.layout.fragment_news, viewGroup, false);
        return rootView;
    }

    public static Fragment getNewsInstance(String country){
        Fragment newsFragment = new NewsFragment();
        Bundle bundle = new Bundle();
        bundle.putString("COUNTRY", country);
        newsFragment.setArguments(bundle);
        return newsFragment;
    }

    private String getCountry() {
        return this.getArguments().getString("COUNTRY", Country.INDIA.toString());
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        initViews();
        pullToRefreshAnim();
        newsPresenter.fetchNews(getCountry());  //fetch news by retrofit cal
    }

    public void onStart(){
        super.onStart();
    }

    private void pullToRefreshAnim() {
        ((CircleRefreshLayout)getView().findViewById(R.id.refresh_layout)).setOnRefreshListener(
                new CircleRefreshLayout.OnCircleRefreshListener() {
                    @Override
                    public void refreshing() {
                        newsPresenter.fetchNews(getCountry());  //fetch news by retrofit call
                    }
                    @Override
                    public void completeRefresh() {
                    }
                });
    }

    private void initViews() {
        newsPresenter = new NewsPresenter(this);
    }

    public void displayInternationalNews(News news) {
        stopLoader((ProgressBar) rootView.findViewById(R.id.prgssbar_1), (LinearLayout) rootView.findViewById(R.id.holder_1));
        stopPullToRefreshAnim();
        setNewsInRecyclerViewAdapter(news, (RecyclerView) rootView.findViewById(R.id.recycler_view_1));
    }

    public void displayNationalNews(News news) {
        stopLoader((ProgressBar) rootView.findViewById(R.id.prgssbar_1), (LinearLayout) rootView.findViewById(R.id.holder_1));
        stopPullToRefreshAnim();
        setNewsInRecyclerViewAdapter(news, (RecyclerView) rootView.findViewById(R.id.recycler_view_1));
    }

    public void displaySportsNews(News news) {
        stopLoader((ProgressBar) rootView.findViewById(R.id.prgssbar_2), (LinearLayout) rootView.findViewById(R.id.holder_2));
        setNewsInRecyclerViewAdapter(news, (RecyclerView) rootView.findViewById(R.id.recycler_view2));
    }

    public void displayBusinessNews(News news) {
        stopLoader((ProgressBar) rootView.findViewById(R.id.prgss_bar_3), (LinearLayout) rootView.findViewById(R.id.holder_3));
        setNewsInRecyclerViewAdapter(news, (RecyclerView) rootView.findViewById(R.id.recycler_view3));
    }

    public void displayEntertainmentNews(News news) {
        stopLoader((ProgressBar) rootView.findViewById(R.id.prgssbar_4), (LinearLayout) rootView.findViewById(R.id.holder_4));
        setNewsInRecyclerViewAdapter(news, (RecyclerView) rootView.findViewById(R.id.recycler_view4));
    }

    private void stopLoader(ProgressBar progressBar, LinearLayout layout) {
        progressBar.setVisibility(View.GONE);
        layout.setVisibility(View.GONE);
    }

    public void stopPullToRefreshAnim(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ((CircleRefreshLayout)getView().findViewById(R.id.refresh_layout)).finishRefreshing();
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
        showErrorOnFailure((ProgressBar) rootView.findViewById(R.id.prgssbar_1), (TextView) rootView.findViewById(R.id.err_msg_1), errorMsg);
    }

    public void onSportsNewsFailureResponse(String errorMsg) {
        showErrorOnFailure((ProgressBar) rootView.findViewById(R.id.prgssbar_2), (TextView) rootView.findViewById(R.id.err_msg_2), errorMsg);
    }

    public void onBusinessNewsFailureResponse(String errorMsg) {
        showErrorOnFailure((ProgressBar) rootView.findViewById(R.id.prgss_bar_3), (TextView) rootView.findViewById(R.id.err_msg_3), errorMsg);
    }

    public void onEntertainmentNewsFailureResponse(String errorMsg) {
        showErrorOnFailure((ProgressBar) rootView.findViewById(R.id.prgssbar_4), (TextView) rootView.findViewById(R.id.err_msg_4), errorMsg);
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