package manisha.khatri.newsanytime.presenter;

import manisha.khatri.newsanytime.contract.NewsContract;
import manisha.khatri.newsanytime.model.News;
import manisha.khatri.newsanytime.service.APIResponseCallBack;
import manisha.khatri.newsanytime.service.APINewsRepository;
import manisha.khatri.newsanytime.util._enum.NewsCategory;
import manisha.khatri.newsanytime.util._enum.NewsType;

public class NewsPresenter {
    private final NewsContract newsContract;
    APINewsRepository apiNewsRepository;
    NewsType newsType;

    public NewsPresenter(NewsContract newsContract, NewsType newsType) {
        this.newsContract = newsContract;
        this.newsType = newsType;
    }

    public void fetchNews() {
        apiNewsRepository = new APINewsRepository();
        if(newsType == NewsType.NATIONAL){
            String language = "en";
            String country = "in";
            apiNewsRepository.fetchNewsByCountryAndLanguage(new APIResponseCallBack() {
                @Override
                public void onSuccessfulResponse(News news) {
                    newsContract.displayNationalNews(news);
                }
                @Override
                public void onFailureResponse(String errorMsg) {
                    newsContract.onNatInternatNewsFailureResponse(errorMsg);
                }
            }, country, language);
            apiNewsRepository.fetchNewsByLanguageCategoryAndCountry(new APIResponseCallBack() {
                @Override
                public void onSuccessfulResponse(News news) {
                    newsContract.displaySportsNews(news);
                }
                @Override
                public void onFailureResponse(String errorMsg) {
                    newsContract.onSportsNewsFailureResponse(errorMsg);
                }
            }, NewsCategory.SPORTS.toString(), language, country);
            apiNewsRepository.fetchNewsByLanguageCategoryAndCountry(new APIResponseCallBack() {
                @Override
                public void onSuccessfulResponse(News news) {
                    newsContract.displayBusinessNews(news);
                }
                @Override
                public void onFailureResponse(String errorMsg) {
                    newsContract.onBusinessNewsFailureResponse(errorMsg);
                }
            }, NewsCategory.BUSINESS.toString(), language, country);
            apiNewsRepository.fetchNewsByLanguageCategoryAndCountry(new APIResponseCallBack() {
                @Override
                public void onSuccessfulResponse(News news) {
                    newsContract.displayEntertainmentNews(news);
                }
                @Override
                public void onFailureResponse(String errorMsg) {
                    newsContract.onEntertainmentNewsFailureResponse(errorMsg);
                }
            }, NewsCategory.ENTERTAINMENT.toString(), language, country);
        }
        else if(newsType == NewsType.INTERNATIONAL){
            String category = "general";
            String language = "en";
            apiNewsRepository.fetchNewsByLanguageAndCategory(new APIResponseCallBack() {
                @Override
                public void onSuccessfulResponse(News news) {
                    newsContract.displayInternationalNews(news);
                }
                @Override
                public void onFailureResponse(String errorMsg) {
                    newsContract.onNatInternatNewsFailureResponse(errorMsg);
                }
            }, category, language);
            apiNewsRepository.fetchNewsByLanguageAndCategory(new APIResponseCallBack() {
                @Override
                public void onSuccessfulResponse(News news) {
                    newsContract.displaySportsNews(news);
                }
                @Override
                public void onFailureResponse(String errorMsg) {
                    newsContract.onSportsNewsFailureResponse(errorMsg);
                }
            }, NewsCategory.SPORTS.toString(), language);
            apiNewsRepository.fetchNewsByLanguageAndCategory(new APIResponseCallBack() {
                @Override
                public void onSuccessfulResponse(News news) {
                    newsContract.displayBusinessNews(news);
                }
                @Override
                public void onFailureResponse(String errorMsg) {
                    newsContract.onBusinessNewsFailureResponse(errorMsg);
                }
            }, NewsCategory.BUSINESS.toString(), language);
            apiNewsRepository.fetchNewsByLanguageAndCategory(new APIResponseCallBack() {
                @Override
                public void onSuccessfulResponse(News news) {
                    newsContract.displayEntertainmentNews(news);
                }
                @Override
                public void onFailureResponse(String errorMsg) {
                    newsContract.onEntertainmentNewsFailureResponse(errorMsg);
                }
            }, NewsCategory.ENTERTAINMENT.toString(), language);
        }
    }

}