package manisha.khatri.newsanytime.presenter;

import manisha.khatri.newsanytime.contract.NationalNewsContract;
import manisha.khatri.newsanytime.model.News;
import manisha.khatri.newsanytime.service.WebServiceCallBack;
import manisha.khatri.newsanytime.service.WebRemoteDataSource;
import manisha.khatri.newsanytime.service.WebServiceRequest;
import manisha.khatri.newsanytime.util.NewsCategory;

public class NationalNewsPresenter implements WebServiceCallBack {
    private final NationalNewsContract nationalNewsContract;
    WebRemoteDataSource webRemoteDataSource;

    public NationalNewsPresenter(NationalNewsContract nationalNewsContract) {
        this.nationalNewsContract = nationalNewsContract;
    }

    public void fetchNews() {
        webRemoteDataSource = new WebServiceRequest();
        fetchNationalNews();
        fetchSportsNews();
        fetchBusinessNews();
        fetchEntertainmentNews();
    }

    private void fetchSportsNews() {
        String category = "sports";
        String language = "en";
        String country = "in";
        webRemoteDataSource.fetchNewsByLanguageCategoryAndCountry(this, category, language, country,NewsCategory.NATIONAL_SPORTS);
    }

    private void fetchNationalNews() {
        String language = "en";
        String country = "in";
        webRemoteDataSource.fetchNewsByCountryAndLanguage(this, country, language, NewsCategory.NATIONAL);
    }

    private void fetchBusinessNews() {
        String category = "business";
        String language = "en";
        String country = "in";
        webRemoteDataSource.fetchNewsByLanguageCategoryAndCountry(this, category, language, country,NewsCategory.NATIONAL_BUSINESS);
    }

    private void fetchEntertainmentNews() {
        String category = "entertainment";
        String language = "en";
        String country = "in";
        webRemoteDataSource.fetchNewsByLanguageCategoryAndCountry(this, category, language, country,NewsCategory.NATIONAL_ENTERTAINMENT);
    }

    public void onSuccessfulResponse(News news, NewsCategory newsCategory) {
        nationalNewsContract.onSuccessfulResponse(news, newsCategory);
    }

    public void onFailureResponse(String errorMsg, NewsCategory newsCategory) {
        nationalNewsContract.onFailureResponse(errorMsg, newsCategory);
    }

}