package com.example.newsanytime.contract;

import com.example.newsanytime.room.SavedNews;
import java.util.List;

public interface SavedNewsContract {
    void renderNewsList(List<SavedNews> newsList);
    void handleNoSavedNewsInDB();
}
