package com.example.newsanytime.presenter;

import android.util.Log;
import com.example.newsanytime.ApiService;
import com.example.newsanytime._enum.TopStoriesType;
import com.example.newsanytime.contract.TopStoriesContract;
import com.example.newsanytime.model.News;
import com.example.newsanytime.singleton.RetrofitSingleton;
import java.io.IOException;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import static com.example.newsanytime.constants.Constants.API_KEY;

public class TopStoriesPresenter {
    private final TopStoriesContract contract;    
    ApiService apiService;
    private static final String tag = "FETCHING NEWS";

    public TopStoriesPresenter(TopStoriesContract contract){
        this.contract = contract;
        apiService = RetrofitSingleton.getRetrofitInstance().create(ApiService.class);
    }

    public void fetchNewsFromServer() {
        Call<News> call;
        call = getTopStories(apiService);
        fetchTopStoriesFromServer(call);
    }


    private void fetchTopStoriesFromServer(Call<News> call) {
        call.enqueue(new Callback<News>() {
            @Override
            public void onResponse(Call<News> call, Response<News> response) {
                if (response.isSuccessful()) {
                    News news = response.body();
                    if (news.getTotalResults() > 0) {
                        contract.displayTopStories(news);
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

    private Call<News> getTopStories(ApiService apiService) {
        return apiService.getTopHeadlines(
                "en",
                API_KEY
        );
    }


}
