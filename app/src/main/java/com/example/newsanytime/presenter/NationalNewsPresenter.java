package com.example.newsanytime.presenter;

import android.widget.Toast;

import com.example.newsanytime.ApiService;
import com.example.newsanytime.singleton.RetrofitSingleton;
import com.example.newsanytime.contract.NationalNewsContract;
import com.example.newsanytime.model.News;
import java.util.Dictionary;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import static com.example.newsanytime.constants.Constants.API_KEY;


public class NationalNewsPresenter {

    private final NationalNewsContract contract;
    ApiService apiService;

    public NationalNewsPresenter(NationalNewsContract contract){
        this.contract = contract;
        apiService = RetrofitSingleton.getRetrofitInstance().create(ApiService.class);
    }

    public Call<News> getNationalNews(ApiService apiService) {
        return apiService.getTopHeadlinesBasedOnCountry(
                    "in",
                    API_KEY
            );
    }
    public Call<News> getSportsNews(ApiService apiService) {
        return apiService.getTopHeadlinesBasedOnCategory(
                "in",
                "sports",
                API_KEY
        );
    }
    public Call<News> getBusinessNews(ApiService apiService) {
        return apiService.getTopHeadlinesBasedOnCategory(
                "in",
                "business",
                API_KEY
        );
    }
    public Call<News> getEntertainmentNews(ApiService apiService) {
        return apiService.getTopHeadlinesBasedOnCategory(
                "in",
                "entertainment",
                API_KEY
        );
    }

    public void fetchNews() {
        Call<News> call;

        call = getNationalNews(apiService);
        retrieveNationalNewsRequestResponse(call);

        call = getSportsNews(apiService);
        retrieveSportsNewsRequestResponse(call);

        call = getBusinessNews(apiService);
        retrieveBusinessNewsRequestResponse(call);

        call = getEntertainmentNews(apiService);
        retrieveEntertainmentNewsRequestResponse(call);
    }

    public void retrieveNationalNewsRequestResponse(Call<News> call) {
        call.enqueue(new Callback<News>() {
            @Override
            public void onResponse(Call<News> call, Response<News> response) {
                News news = response.body();
                contract.displayNationalNewsArticles(news);
            }

            @Override
            public void onFailure(Call<News> call, Throwable t) {
                String err = t.getMessage();
            }
        });
    }

    public void retrieveSportsNewsRequestResponse(Call<News> call) {
        call.enqueue(new Callback<News>() {
            @Override
            public void onResponse(Call<News> call, Response<News> response) {
                News news = response.body();
                contract.displaySportsNewsArticles(news);
            }

            @Override
            public void onFailure(Call<News> call, Throwable t) {
                String err = t.getMessage();
            }
        });
    }

    public void retrieveBusinessNewsRequestResponse(Call<News> call) {
        call.enqueue(new Callback<News>() {
            @Override
            public void onResponse(Call<News> call, Response<News> response) {
                News news = response.body();
                contract.displayBusinessNewsArticles(news);
            }

            @Override
            public void onFailure(Call<News> call, Throwable t) {
                String err = t.getMessage();
            }
        });
    }

    public void retrieveEntertainmentNewsRequestResponse(Call<News> call) {
        call.enqueue(new Callback<News>() {
            @Override
            public void onResponse(Call<News> call, Response<News> response) {
                News news = response.body();
                contract.displayEntertainmentNewsArticles(news);
            }

            @Override
            public void onFailure(Call<News> call, Throwable t) {
                String err = t.getMessage();
            }
        });
    }

}







