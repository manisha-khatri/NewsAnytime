package com.example.newsanytime.contract;

import com.example.newsanytime.model.News;

public interface HomeActivityContract {

    public void displayNationalNewsArticles(News news);

    public void displaySportsNewsArticles(News news);

    public void displayBusinessNewsArticles(News news);

    public void displayEntertainmentNewsArticles(News news);

    //public void displaySearchedNewsArticles(BookmarkedNews news);

}
