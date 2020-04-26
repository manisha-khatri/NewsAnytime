package manisha.khatri.newsanytime.presenter;

import manisha.khatri.newsanytime.contract.NewsContract;
import manisha.khatri.newsanytime.model.News;
import manisha.khatri.newsanytime.service.APINewsRepositoryImpl;
import manisha.khatri.newsanytime.service.APIResponseCallBack;
import manisha.khatri.newsanytime.service.APINewsRepository;
import manisha.khatri.newsanytime.util._enum.NewsCategory;
import manisha.khatri.newsanytime.util._enum.Country;

public class NewsPresenter {
    private final NewsContract newsContract;
    APINewsRepository apiNewsRepository;

    public NewsPresenter(NewsContract newsContract) {
        this.newsContract = newsContract;
    }

    public void fetchNews(String country) {
        String language = "en";
        apiNewsRepository = new APINewsRepositoryImpl();

        if(country != Country.ALL.toString()){

            apiNewsRepository.fetchNewsFor(new APIResponseCallBack() {
                @Override
                public void onSuccessfulResponse(News news) {
                    newsContract.displayNationalNews(news);
                }
                @Override
                public void onFailureResponse(String errorMsg) {
                    newsContract.onNatInternatNewsFailureResponse(errorMsg);
                }
            }, country, language,"");
            apiNewsRepository.fetchNewsFor(new APIResponseCallBack() {
                @Override
                public void onSuccessfulResponse(News news) {
                    newsContract.displaySportsNews(news);
                }
                @Override
                public void onFailureResponse(String errorMsg) {
                    newsContract.onSportsNewsFailureResponse(errorMsg);
                }
            }, country, language, NewsCategory.SPORTS.toString());
            apiNewsRepository.fetchNewsFor(new APIResponseCallBack() {
                @Override
                public void onSuccessfulResponse(News news) {
                    newsContract.displayBusinessNews(news);
                }
                @Override
                public void onFailureResponse(String errorMsg) {
                    newsContract.onBusinessNewsFailureResponse(errorMsg);
                }
            }, country, language, NewsCategory.BUSINESS.toString());
            apiNewsRepository.fetchNewsFor(new APIResponseCallBack() {
                @Override
                public void onSuccessfulResponse(News news) {
                    newsContract.displayEntertainmentNews(news);
                }
                @Override
                public void onFailureResponse(String errorMsg) {
                    newsContract.onEntertainmentNewsFailureResponse(errorMsg);
                }
            }, country, language, NewsCategory.ENTERTAINMENT.toString());
        }
        else {
            apiNewsRepository.fetchNewsFor(new APIResponseCallBack() {
                @Override
                public void onSuccessfulResponse(News news) {
                    newsContract.displayInternationalNews(news);
                }
                @Override
                public void onFailureResponse(String errorMsg) {
                    newsContract.onNatInternatNewsFailureResponse(errorMsg);
                }
            }, "", language, NewsCategory.GENERAL.toString());
            apiNewsRepository.fetchNewsFor(new APIResponseCallBack() {
                @Override
                public void onSuccessfulResponse(News news) {
                    newsContract.displaySportsNews(news);
                }
                @Override
                public void onFailureResponse(String errorMsg) {
                    newsContract.onSportsNewsFailureResponse(errorMsg);
                }
            }, "", language, NewsCategory.SPORTS.toString());
            apiNewsRepository.fetchNewsFor(new APIResponseCallBack() {
                @Override
                public void onSuccessfulResponse(News news) {
                    newsContract.displayBusinessNews(news);
                }
                @Override
                public void onFailureResponse(String errorMsg) {
                    newsContract.onBusinessNewsFailureResponse(errorMsg);
                }
            }, "", language, NewsCategory.BUSINESS.toString());
            apiNewsRepository.fetchNewsFor(new APIResponseCallBack() {
                @Override
                public void onSuccessfulResponse(News news) {
                    newsContract.displayEntertainmentNews(news);
                }
                @Override
                public void onFailureResponse(String errorMsg) {
                    newsContract.onEntertainmentNewsFailureResponse(errorMsg);
                }
            }, "", language, NewsCategory.ENTERTAINMENT.toString());
        }
    }

}