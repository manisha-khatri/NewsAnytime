package manisha.khatri.newsanytime.presenter;

import manisha.khatri.newsanytime.contract.SearchNewsContract;
import manisha.khatri.newsanytime.model.News;
import manisha.khatri.newsanytime.service.WebRemoteDataSource;
import manisha.khatri.newsanytime.service.WebServiceCallBack;
import manisha.khatri.newsanytime.service.WebServiceRequest;
import manisha.khatri.newsanytime.util.NewsCategory;

public class SearchNewsPresenter implements WebServiceCallBack {
    private final SearchNewsContract contract;
    WebRemoteDataSource webRemoteDataSource;


    public SearchNewsPresenter(SearchNewsContract contract){
        this.contract = contract;
        webRemoteDataSource = new WebServiceRequest();
    }

    public void fetchNews(String keyword){
        webRemoteDataSource.fetchNewsBySearchedKeyword( this, keyword, NewsCategory.SEARCHED_NEWS);
    }

    @Override
    public void onSuccessfulResponse(News news, NewsCategory newsCategory) {
        contract.displaySearchedNewsArticles(news);
    }

    @Override
    public void onFailureResponse(String errorMsg, NewsCategory newsCategory) {
        contract.handleInvalidResponseFromServer();
    }
}
