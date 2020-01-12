package manisha.khatri.newsanytime.presenter;

import android.util.Log;

import manisha.khatri.newsanytime.ApiService;
import manisha.khatri.newsanytime._enum.InternationalNewsType;
import manisha.khatri.newsanytime.contract.InternationalNewsContract;
import manisha.khatri.newsanytime.model.News;
import manisha.khatri.newsanytime.singleton.RetrofitSingleton;
import java.io.IOException;

import manisha.khatri.newsanytime.constants.Constants;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InternationalNewsPresenter {
    private final InternationalNewsContract contract;
    ApiService apiService;
    private static final String tag = "FETCHING NEWS";

    public InternationalNewsPresenter(InternationalNewsContract contract) {
        this.contract = contract;
        apiService = RetrofitSingleton.getRetrofitInstance().create(ApiService.class);
    }

    public void fetchNewsFromServer() {
        Call<News> call;

        call = getInternationalNews(apiService);
        fetchInternationalNewsFromServer(call);

        call = getSportsNews(apiService);
        retrieveSportsNewsRequestResponse(call);

        call = getBusinessNews(apiService);
        retrieveBusinessNewsRequestResponse(call);

        call = getEntertainmentNews(apiService);
        retrieveEntertainmentNewsRequestResponse(call);
    }

    public Call<News> getInternationalNews(ApiService apiService) {
        return apiService.getInternationalTopHeadlinesBasedOnCategory(
                "general",
                "en",
                Constants.API_KEY
        );
    }
    public Call<News> getSportsNews(ApiService apiService) {
        return apiService.getInternationalTopHeadlinesBasedOnCategory(
                "sports",
                "en",
                Constants.API_KEY
        );
    }
    public Call<News> getBusinessNews(ApiService apiService) {
        return apiService.getInternationalTopHeadlinesBasedOnCategory(
                "business",
                "en",
                Constants.API_KEY
        );
    }
    public Call<News> getEntertainmentNews(ApiService apiService) {
        return apiService.getInternationalTopHeadlinesBasedOnCategory(
                "entertainment",
                "en",
                Constants.API_KEY
        );
    }

    public void fetchInternationalNewsFromServer(Call<News> call) {
        call.enqueue(new Callback<News>() {
            InternationalNewsType internationalNewsType = InternationalNewsType.INTERNATIONAL;
            @Override
            public void onResponse(Call<News> call, Response<News> response) {
                if (response.isSuccessful()) {
                    News news = response.body();
                    if (news.getTotalResults() > 0) {
                        contract.displayInternationalNewsArticles(news, internationalNewsType);
                    } else {
                        Log.e(tag,"No news article found");
                        contract.handleInvalidResponseFromServer(internationalNewsType);
                    }
                }
                else{
                    Log.e(tag,"Internal server error");
                    contract.handleInvalidResponseFromServer(internationalNewsType);
                }
            }
            @Override
            public void onFailure(Call<News> call, Throwable t) {
                if(t instanceof IOException){
                    Log.e(tag,"Network error " + t);
                    contract.handleInvalidResponseFromServer(internationalNewsType);
                }
                else{
                    Log.e(tag,"Converter error " + t);
                    contract.handleInvalidResponseFromServer(internationalNewsType);
                }
            }
        });
    }

    public void retrieveSportsNewsRequestResponse(Call<News> call) {
        call.enqueue(new Callback<News>() {
            InternationalNewsType internationalNewsType = InternationalNewsType.SPORTS;
            @Override
            public void onResponse(Call<News> call, Response<News> response) {
                if (response.isSuccessful()) {
                    News news = response.body();
                    if (news.getTotalResults() > 0) {
                        contract.displaySportsNewsArticles(news, internationalNewsType);
                    } else {
                        Log.e(tag,"No news article found");
                        contract.handleInvalidResponseFromServer(internationalNewsType);
                    }
                }
                else{
                    Log.e(tag,"Internal server error");
                    contract.handleInvalidResponseFromServer(internationalNewsType);
                }
            }
            @Override
            public void onFailure(Call<News> call, Throwable t) {
                if(t instanceof IOException){
                    Log.e(tag,"Network error " + t);
                    contract.handleInvalidResponseFromServer(internationalNewsType);
                }
                else{
                    Log.e(tag,"Converter error " + t);
                    contract.handleInvalidResponseFromServer(internationalNewsType);
                }
            }
        });
    }

    public void retrieveBusinessNewsRequestResponse(Call<News> call) {
        call.enqueue(new Callback<News>() {
            InternationalNewsType internationalNewsType = InternationalNewsType.BUSINESS;
            @Override
            public void onResponse(Call<News> call, Response<News> response) {
                if (response.isSuccessful()) {
                    News news = response.body();
                    if (news.getTotalResults() > 0) {
                        contract.displayBusinessNewsArticles(news, internationalNewsType);
                    } else {
                        Log.e(tag,"No news article found");
                        contract.handleInvalidResponseFromServer(internationalNewsType);
                    }
                }
                else{
                    Log.e(tag,"Internal server error");
                    contract.handleInvalidResponseFromServer(internationalNewsType);
                }
            }
            @Override
            public void onFailure(Call<News> call, Throwable t) {
                if(t instanceof IOException){
                    Log.e(tag,"Network error " + t);
                    contract.handleInvalidResponseFromServer(internationalNewsType);
                }
                else{
                    Log.e(tag,"Converter error " + t);
                    contract.handleInvalidResponseFromServer(internationalNewsType);
                }
            }
        });
    }

    public void retrieveEntertainmentNewsRequestResponse(Call<News> call) {
        call.enqueue(new Callback<News>() {
            InternationalNewsType internationalNewsType = InternationalNewsType.ENTERTAINMENT;
            @Override
            public void onResponse(Call<News> call, Response<News> response) {
                if (response.isSuccessful()) {
                    News news = response.body();
                    if (news.getTotalResults() > 0) {
                        contract.displayEntertainmentNewsArticles(news, internationalNewsType);
                    } else {
                        Log.e(tag,"No news article found");
                        contract.handleInvalidResponseFromServer(internationalNewsType);
                    }
                }
                else{
                    Log.e(tag,"Internal server error");
                    contract.handleInvalidResponseFromServer(internationalNewsType);
                }
            }
            @Override
            public void onFailure(Call<News> call, Throwable t) {
                if(t instanceof IOException){
                    Log.e(tag,"Network error " + t);
                    contract.handleInvalidResponseFromServer(internationalNewsType);
                }
                else{
                    Log.e(tag,"Converter error " + t);
                    contract.handleInvalidResponseFromServer(internationalNewsType);
                }
            }
        });
    }

}