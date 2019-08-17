package com.example.newsanytime.model;

import java.util.List;

public class NewsList {

    private List<News> news = null;

    public NewsList(List<News> news){
        this.news = news;
    }

    public List<News> getNews() {
        return news;
    }

    public void setNews(List<News> news) {
        this.news = news;
    }

}
