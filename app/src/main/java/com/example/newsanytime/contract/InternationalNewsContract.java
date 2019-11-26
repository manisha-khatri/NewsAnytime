package com.example.newsanytime.contract;

import com.example.newsanytime._enum.InternationalNewsType;
import com.example.newsanytime.model.News;

public interface InternationalNewsContract {
    public void displayInternationalNewsArticles(News news, InternationalNewsType internationalNewsType);

    public void displaySportsNewsArticles(News news, InternationalNewsType internationalNewsType);

    public void displayBusinessNewsArticles(News news, InternationalNewsType internationalNewsType);

    public void displayEntertainmentNewsArticles(News news, InternationalNewsType internationalNewsType);

    public void handleInvalidResponseFromServer(InternationalNewsType internationalNewsType);
}
