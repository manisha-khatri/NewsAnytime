package com.example.newsanytime.room;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface BookmarkedNewsDAO {

    @Insert
    void saveNews(BookmarkedNews bookmarkedNews);

    @Query("select * from bookmarked_news")
    List<BookmarkedNews> fetchNewsList();

}
