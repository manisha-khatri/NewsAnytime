package com.example.newsanytime.presenter;

import android.util.Log;
import com.example.newsanytime.ApiService;
import com.example.newsanytime.singleton.RetrofitSingleton;
import com.example.newsanytime.contract.SearchNewsContract;
import com.example.newsanytime.model.News;
import java.io.IOException;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import static com.example.newsanytime.constants.Constants.API_KEY;

public class SearchNewsPresenter {
    private final SearchNewsContract contract;
    ApiService apiService;
    private static final String tag = "FETCHING NEWS";

    public SearchNewsPresenter(SearchNewsContract contract){
        this.contract = contract;
        apiService = RetrofitSingleton.getRetrofitInstance().create(ApiService.class);
    }

    public void fetchNewsFromServer(String searchedKeyword){
        Call<News> call;
        call = getNewsOnSearchedKeyword(apiService, searchedKeyword);
        retrieveSearchedNewsRequestResponse(call);
    }

    public Call<News> getNewsOnSearchedKeyword(ApiService apiService, String SearchedKeyword) {
        return apiService.getTopHeadlinesBasedOnSearchedKeyword(
                SearchedKeyword,
                "en",
                API_KEY
        );
    }

    public void retrieveSearchedNewsRequestResponse(Call<News> call) {
        call.enqueue(new Callback<News>() {
            @Override
            public void onResponse(Call<News> call, Response<News> response) {
                if (response.isSuccessful()) {
                    News news = response.body();
                    if (news.getTotalResults() > 0) {
                        contract.displaySearchedNewsArticles(news);
                    } else {
                        Log.e(tag,"No news article found");
                        contract.handleInvalidResponseFromServer();
                    }
                }
                else{
                    Log.e(tag,"Internal server error");
                    contract.handleInvalidResponseFromServer();
                }
            }

            @Override
            public void onFailure(Call<News> call, Throwable t) {
                if(t instanceof IOException){
                    Log.e(tag,"Network error " + t);
                    contract.handleInvalidResponseFromServer();
                }
                else{
                    Log.e(tag,"Converter error " + t);
                    contract.handleInvalidResponseFromServer();
                }
            }
        });
    }

}
