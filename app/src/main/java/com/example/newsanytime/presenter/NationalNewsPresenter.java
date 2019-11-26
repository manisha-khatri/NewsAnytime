package com.example.newsanytime.presenter;

import android.util.Log;
import com.example.newsanytime.ApiService;
import com.example.newsanytime.singleton.RetrofitSingleton;
import com.example.newsanytime.contract.NationalNewsContract;
import com.example.newsanytime.model.News;
import com.example.newsanytime._enum.NationalNewsType;
import java.io.IOException;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import static com.example.newsanytime.constants.Constants.API_KEY;

public class NationalNewsPresenter {

    private final NationalNewsContract contract;
    ApiService apiService;
    private static final String tag = "FETCHING NEWS";

    public NationalNewsPresenter(NationalNewsContract contract){
        this.contract = contract;
        apiService = RetrofitSingleton.getRetrofitInstance().create(ApiService.class);
    }

    public Call<News> getNationalNews(ApiService apiService) {
        return apiService.getTopHeadlinesBasedOnCountry(
                    "in",
                    "en",
                    API_KEY
            );
    }
    public Call<News> getSportsNews(ApiService apiService) {
        return apiService.getTopHeadlinesBasedOnCategory(
                "in",
                "sports",
                "en",
                API_KEY
        );
    }
    public Call<News> getBusinessNews(ApiService apiService) {
        return apiService.getTopHeadlinesBasedOnCategory(
                "in",
                "business",
                "en",
                API_KEY
        );
    }
    public Call<News> getEntertainmentNews(ApiService apiService) {
        return apiService.getTopHeadlinesBasedOnCategory(
                "in",
                "entertainment",
                "en",
                API_KEY
        );
    }

    public void fetchNewsFromServer() {
        Call<News> call;

        call = getNationalNews(apiService);
        fetchNationalNewsFromServer(call);

        call = getSportsNews(apiService);
        retrieveSportsNewsRequestResponse(call);

        call = getBusinessNews(apiService);
        retrieveBusinessNewsRequestResponse(call);

        call = getEntertainmentNews(apiService);
        retrieveEntertainmentNewsRequestResponse(call);
    }

    public void fetchNationalNewsFromServer(Call<News> call) {
        call.enqueue(new Callback<News>() {
            NationalNewsType nationalNewsType = NationalNewsType.NATIONAL;
            @Override
            public void onResponse(Call<News> call, Response<News> response) {
                if (response.isSuccessful()) {
                    News news = response.body();
                    if (news.getTotalResults() > 0) {
                        contract.displayNationalNewsArticles(news, nationalNewsType);
                    } else {
                        Log.e(tag,"No news article found");
                        contract.handleInvalidResponseFromServer(nationalNewsType);
                    }
                }
                else{
                    Log.e(tag,"Internal server error");
                    contract.handleInvalidResponseFromServer(nationalNewsType);
                }
            }
            @Override
            public void onFailure(Call<News> call, Throwable t) {
                if(t instanceof IOException){
                    Log.e(tag,"Network error " + t);
                    contract.handleInvalidResponseFromServer(nationalNewsType);
                }
                else{
                    Log.e(tag,"Converter error " + t);
                    contract.handleInvalidResponseFromServer(nationalNewsType);
                }
            }
        });
    }

    public void retrieveSportsNewsRequestResponse(Call<News> call) {
        call.enqueue(new Callback<News>() {
            NationalNewsType nationalNewsType = NationalNewsType.SPORTS;
            @Override
            public void onResponse(Call<News> call, Response<News> response) {
                if (response.isSuccessful()) {
                    News news = response.body();
                    if (news.getTotalResults() > 0) {
                        contract.displaySportsNewsArticles(news, nationalNewsType);
                    } else {
                        Log.e(tag,"No news article found");
                        contract.handleInvalidResponseFromServer(nationalNewsType);
                    }
                }
                else{
                    Log.e(tag,"Internal server error");
                    contract.handleInvalidResponseFromServer(nationalNewsType);
                }
            }
            @Override
            public void onFailure(Call<News> call, Throwable t) {
                if(t instanceof IOException){
                    Log.e(tag,"Network error " + t);
                    contract.handleInvalidResponseFromServer(nationalNewsType);
                }
                else{
                    Log.e(tag,"Converter error " + t);
                    contract.handleInvalidResponseFromServer(nationalNewsType);
                }
            }
        });
    }

    public void retrieveBusinessNewsRequestResponse(Call<News> call) {
        call.enqueue(new Callback<News>() {
            NationalNewsType nationalNewsType = NationalNewsType.BUSINESS;
            @Override
            public void onResponse(Call<News> call, Response<News> response) {
                if (response.isSuccessful()) {
                    News news = response.body();
                    if (news.getTotalResults() > 0) {
                        contract.displayBusinessNewsArticles(news, nationalNewsType);
                    } else {
                        Log.e(tag,"No news article found");
                        contract.handleInvalidResponseFromServer(nationalNewsType);
                    }
                }
                else{
                    Log.e(tag,"Internal server error");
                    contract.handleInvalidResponseFromServer(nationalNewsType);
                }
            }
            @Override
            public void onFailure(Call<News> call, Throwable t) {
                if(t instanceof IOException){
                    Log.e(tag,"Network error " + t);
                    contract.handleInvalidResponseFromServer(nationalNewsType);
                }
                else{
                    Log.e(tag,"Converter error " + t);
                    contract.handleInvalidResponseFromServer(nationalNewsType);
                }
            }
        });
    }

    public void retrieveEntertainmentNewsRequestResponse(Call<News> call) {
        call.enqueue(new Callback<News>() {
            NationalNewsType nationalNewsType = NationalNewsType.ENTERTAINMENT;
            @Override
            public void onResponse(Call<News> call, Response<News> response) {
                if (response.isSuccessful()) {
                    News news = response.body();
                    if (news.getTotalResults() > 0) {
                        contract.displayEntertainmentNewsArticles(news, nationalNewsType);
                    } else {
                        Log.e(tag,"No news article found");
                        contract.handleInvalidResponseFromServer(nationalNewsType);
                    }
                }
                else{
                    Log.e(tag,"Internal server error");
                    contract.handleInvalidResponseFromServer(nationalNewsType);
                }
            }
            @Override
            public void onFailure(Call<News> call, Throwable t) {
                if(t instanceof IOException){
                    Log.e(tag,"Network error " + t);
                    contract.handleInvalidResponseFromServer(nationalNewsType);
                }
                else{
                    Log.e(tag,"Converter error " + t);
                    contract.handleInvalidResponseFromServer(nationalNewsType);
                }
            }
        });
    }

}
