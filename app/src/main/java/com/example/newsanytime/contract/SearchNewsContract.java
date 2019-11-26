package com.example.newsanytime.contract;

import com.example.newsanytime.model.News;

public interface SearchNewsContract {
     void displaySearchedNewsArticles(News news);
     void handleInvalidResponseFromServer();
}
