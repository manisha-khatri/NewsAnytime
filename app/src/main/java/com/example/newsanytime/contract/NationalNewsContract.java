package com.example.newsanytime.contract;

import com.example.newsanytime.model.News;
import com.example.newsanytime._enum.NationalNewsType;

public interface NationalNewsContract {

    public void displayNationalNewsArticles(News news, NationalNewsType nationalNewsType);

    public void displaySportsNewsArticles(News news, NationalNewsType nationalNewsType);

    public void displayBusinessNewsArticles(News news, NationalNewsType nationalNewsType);

    public void displayEntertainmentNewsArticles(News news, NationalNewsType nationalNewsType);

    public void handleInvalidResponseFromServer(NationalNewsType nationalNewsType);

}
