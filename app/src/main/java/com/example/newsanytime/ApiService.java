package com.example.newsanytime;

import com.example.newsanytime.model.News;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {

    String BASE_URL = "https://newsapi.org/v2/";

    String BASE_URL2 = "https://api.myjson.com/bins/";

    @GET("1gfjbw")
    Call<String> getData();

    //https://newsapi.org/v2/top-headlines?country=in&apiKey=53ad2000d9d243f9b1a7e270275fe3a7
    @GET("top-headlines")
    Call<News> getTopHeadlinesBasedOnCountry(
            @Query("country") String country,
            @Query("apiKey") String apiKey
    );

    //https://newsapi.org/v2/top-headlines?country=in&category=business&apiKey=53ad2000d9d243f9b1a7e270275fe3a7
    @GET("top-headlines")
    Call<News> getTopHeadlinesBasedOnCategory(
            @Query("country") String country,
            @Query("category") String category,
            @Query("apiKey") String apiKey
    );

    //https://newsapi.org/v2/top-headlines?q=trump&apiKey=53ad2000d9d243f9b1a7e270275fe3a7
    @GET("top-headlines")
    Call<News> getTopHeadlinesBasedOnSearchedKeyword(
            @Query("q") String q,
            @Query("apiKey") String apiKey
    );


    String Demo_url = "https://api.myjson.com/bins/kj1ng";
}
