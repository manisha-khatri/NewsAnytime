package com.example.newsanytime.presenter;

import android.util.Log;

import com.example.newsanytime.ApiService;
import com.example.newsanytime._enum.InternationalNewsType;
import com.example.newsanytime.contract.InternationalNewsContract;
import com.example.newsanytime.model.News;
import com.example.newsanytime.singleton.RetrofitSingleton;
import java.io.IOException;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import static com.example.newsanytime.constants.Constants.API_KEY;

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
                API_KEY
        );
    }
    public Call<News> getSportsNews(ApiService apiService) {
        return apiService.getInternationalTopHeadlinesBasedOnCategory(
                "sports",
                "en",
                API_KEY
        );
    }
    public Call<News> getBusinessNews(ApiService apiService) {
        return apiService.getInternationalTopHeadlinesBasedOnCategory(
                "business",
                "en",
                API_KEY
        );
    }
    public Call<News> getEntertainmentNews(ApiService apiService) {
        return apiService.getInternationalTopHeadlinesBasedOnCategory(
                "entertainment",
                "en",
                API_KEY
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