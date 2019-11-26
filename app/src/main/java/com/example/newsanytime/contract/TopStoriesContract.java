package com.example.newsanytime.contract;

import com.example.newsanytime.model.News;

public interface TopStoriesContract {
    public void displayTopStories(News news);
    public void handleInvalidResponseFromServer();
}
