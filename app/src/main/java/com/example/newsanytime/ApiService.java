package com.example.newsanytime;

import com.example.newsanytime.model.News;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {

    String BASE_URL = "https://newsapi.org/v2/";
    //https://newsapi.org/v2/sources?language=en&apiKey=53ad2000d9d243f9b1a7e270275fe3a7

    @GET("top-headlines")
    Call<News> getTopHeadlinesBasedOnCountry(
            @Query("country") String country,
            @Query("apiKey") String apiKey
    );

    @GET("top-headlines")
    Call<News> getTopHeadlinesBasedOnCategory(
            @Query("country") String country,
            @Query("category") String category,
            @Query("apiKey") String apiKey
    );

    @GET("top-headlines")
    Call<News> getTopHeadlinesBasedOnSearchedKeyword(
            @Query("q") String q,
            @Query("apiKey") String apiKey
    );

}
