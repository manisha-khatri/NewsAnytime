package com.example.newsanytime.presenter;

import com.example.newsanytime.Api;
import com.example.newsanytime.contract.HomeActivityContract;
import com.example.newsanytime.pojo.News;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.newsanytime.constants.Constants.API_KEY;

public class HomePresenter {

    private final HomeActivityContract contract;

    public HomePresenter(HomeActivityContract contract){
        this.contract = contract;
    }

    public void fetchTopHeadlines() {
        Retrofit retrofit = new retrofit2.Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api api = retrofit.create(Api.class);
        Call<News> call = api.getTopHeadlines(
                "in",API_KEY
        );

        call.enqueue(new Callback<News>() {
            @Override
            public void onResponse(Call<News> call, Response<News> response) {
                News news = response.body();
                contract.showTopHeadlinesList(news);
            }

            @Override
            public void onFailure(Call<News> call, Throwable t) {
                //Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
