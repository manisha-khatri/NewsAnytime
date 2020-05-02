package manisha.khatri.newsanytime.presenter;

import java.io.IOException;
import manisha.khatri.newsanytime.contract.NewsContract;
import manisha.khatri.newsanytime.model.News;
import manisha.khatri.newsanytime.network.APINewsRepositoryImpl;
import manisha.khatri.newsanytime.network.APIResponseCallBack;
import manisha.khatri.newsanytime.network.APINewsRepository;
import manisha.khatri.newsanytime.util._enum.GenericStrings;
import manisha.khatri.newsanytime.util._enum.NewsCategory;
import manisha.khatri.newsanytime.util._enum.Country;
import retrofit2.Call;
import retrofit2.Response;
import static manisha.khatri.newsanytime.util._enum.NewsCategory.*;

public class NewsPresenter {
    private final NewsContract newsContract;
    private APINewsRepository apiNewsRepository;

    public NewsPresenter(NewsContract newsContract) {
        this.newsContract = newsContract;
    }

    public void fetchNews(String country) {
        String language = "en";
        apiNewsRepository = new APINewsRepositoryImpl();

        if(country.equals(Country.ALL.toString())){
            country = "";
        }
        apiNewsRepository.fetchNewsFor(new APIResponseCallBack() {
            @Override
            public void onSuccessfulResponse(Call<News> call, Response<News> response) {
                validateSuccessfulResponse(call, response, GENERAL);
            }
            @Override
            public void onFailureResponse(Call<News> call, Throwable t) {
                validateFailureResponse(call, t, GENERAL);
            }
        }, country, language, GENERAL.toString());
        apiNewsRepository.fetchNewsFor(new APIResponseCallBack() {
            @Override
            public void onSuccessfulResponse(Call<News> call, Response<News> response) {
                validateSuccessfulResponse(call, response, SPORTS);
            }
            @Override
            public void onFailureResponse(Call<News> call, Throwable t) {
                validateFailureResponse(call, t, SPORTS);
            }
        }, country, language, SPORTS.toString());
        apiNewsRepository.fetchNewsFor(new APIResponseCallBack() {
            @Override
            public void onSuccessfulResponse(Call<News> call, Response<News> response) {
                validateSuccessfulResponse(call, response, BUSINESS);
            }
            @Override
            public void onFailureResponse(Call<News> call, Throwable t) {
                validateFailureResponse(call, t, BUSINESS);
            }
        }, country, language, BUSINESS.toString());
        apiNewsRepository.fetchNewsFor(new APIResponseCallBack() {
            @Override
            public void onSuccessfulResponse(Call<News> call, Response<News> response) {
                validateSuccessfulResponse(call, response, ENTERTAINMENT);
            }
            @Override
            public void onFailureResponse(Call<News> call, Throwable t) {
                validateFailureResponse(call, t, ENTERTAINMENT);
            }
        }, country, language, ENTERTAINMENT.toString());
    }

    private void validateSuccessfulResponse(Call<News> call, Response<News> response, NewsCategory category){
        if (response.isSuccessful()) {
            News news = response.body();
            if (news.getTotalResults() > 0) {
                displayNewsOnSuccess(category, news);
            } else {
                displayErrorOnFailure(category, GenericStrings.No_news_found.toString());
            }
        }
        else{
            displayErrorOnFailure(category, GenericStrings.Internal_server_error.toString());
        }
    }

    private void validateFailureResponse(Call<News> call, Throwable t, NewsCategory category){
        if(t instanceof IOException){
            displayErrorOnFailure(category, GenericStrings.Network_error.toString());
        }
        else{
            displayErrorOnFailure(category, GenericStrings.Converter_error.toString());
        }
    }

    private void displayNewsOnSuccess(NewsCategory category, News news){
        switch (category){
            case GENERAL    :  newsContract.displayGeneralNews(news);   break;
            case SPORTS     :  newsContract.displaySportsNews(news);   break;
            case BUSINESS   :  newsContract.displayBusinessNews(news);   break;
            case ENTERTAINMENT :  newsContract.displayEntertainmentNews(news);break;
        }
    }

    private void displayErrorOnFailure(NewsCategory category, String errMsg){
        switch (category){
            //displayGeneralNewsErrorMsg
            //showGeneralNewsErrorMsg
            case GENERAL    :  newsContract.displayGeneralNewsErrorMsg(errMsg);   break;
            case SPORTS     :  newsContract.displaySportsNewsErrorMsg(errMsg);   break;
            case BUSINESS   :  newsContract.displayBusinessNewsErrorMsg(errMsg);   break;
            case ENTERTAINMENT :  newsContract.displayEntertainmentNewsErrorMsg(errMsg);   break;
        }
    }

}