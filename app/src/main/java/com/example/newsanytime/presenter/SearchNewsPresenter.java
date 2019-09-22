package com.example.newsanytime.presenter;

import com.example.newsanytime.ApiService;
import com.example.newsanytime.singleton.RetrofitSingleton;
import com.example.newsanytime.contract.SearchNewsContract;
import com.example.newsanytime.model.News;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import static com.example.newsanytime.constants.Constants.API_KEY;

public class SearchNewsPresenter {

    private final SearchNewsContract contract;
    ApiService apiService;

    public SearchNewsPresenter(SearchNewsContract contract){
        this.contract = contract;
        apiService = RetrofitSingleton.getRetrofitInstance().create(ApiService.class);
    }

    public void fetchNewsForSearchedKeyword(String searchedKeyword){
        Call<News> call;
        call = getNewsOnSearchedKeyword(apiService, searchedKeyword);
        retrieveSearchedNewsRequestResponse(call);
    }

    public Call<News> getNewsOnSearchedKeyword(ApiService apiService, String SearchedKeyword) {
        return apiService.getTopHeadlinesBasedOnSearchedKeyword(
                SearchedKeyword,
                API_KEY
        );
    }

    public void retrieveSearchedNewsRequestResponse(Call<News> call) {
        call.enqueue(new Callback<News>() {
            @Override
            public void onResponse(Call<News> call, Response<News> response) {
                News news = response.body();
                contract.displaySearchedNewsArticles(news);
            }

            @Override
            public void onFailure(Call<News> call, Throwable t) {
                //Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
