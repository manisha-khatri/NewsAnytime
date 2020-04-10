package manisha.khatri.newsanytime.presenter;

import manisha.khatri.newsanytime.service.WebServiceCallBack;
import manisha.khatri.newsanytime.service.WebRemoteDataSource;
import manisha.khatri.newsanytime.service.WebServiceRequest;
import manisha.khatri.newsanytime.contract.InternationalNewsContract;
import manisha.khatri.newsanytime.model.News;
import manisha.khatri.newsanytime.util.NewsCategory;

public class InternationalNewsPresenter implements WebServiceCallBack {
    private final InternationalNewsContract contract;
    WebRemoteDataSource webRemoteDataSource;

    public InternationalNewsPresenter(InternationalNewsContract contract) {
        this.contract = contract;
    }

    public void fetchNews(){
        webRemoteDataSource = new WebServiceRequest();
        fetchInternationalNews();
        fetchSportsNews();
        fetchBusinessNews();
        fetchEntertainmentNews();
    }

    private void fetchSportsNews() {
        String category = "sports";
        String language = "en";
        webRemoteDataSource.fetchNewsByLanguageAndCategory(this, category, language, NewsCategory.INTERNATIONAL_SPORTS);
    }

    private void fetchInternationalNews() {
        String category = "general";
        String language = "en";
        webRemoteDataSource.fetchNewsByLanguageAndCategory(this, category, language, NewsCategory.INTERNATIONAL);
    }

    private void fetchBusinessNews() {
        String category = "business";
        String language = "en";
        webRemoteDataSource.fetchNewsByLanguageAndCategory(this, category, language, NewsCategory.INTERNATIONAL_BUSINESS);
    }

    private void fetchEntertainmentNews() {
        String category = "entertainment";
        String language = "en";
        webRemoteDataSource.fetchNewsByLanguageAndCategory(this, category, language, NewsCategory.INTERNATIONAL_ENTERTAINMENT);
    }

    public void onSuccessfulResponse(News news, NewsCategory newsCategory){
        contract.onSuccessfulResponse(news, newsCategory);
    }

    public void onFailureResponse(String errorMsg, NewsCategory newsCategory){
        contract.onFailureResponse(errorMsg, newsCategory);
    }

}