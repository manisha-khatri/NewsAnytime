package com.example.newsanytime.contract;

import com.example.newsanytime.room.BookmarkedNews;

import java.util.List;

public interface BookmarkedNewsContract {

    void renderNewsList(List<BookmarkedNews> newsList);
}