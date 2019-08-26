package com.example.newsanytime.singleton;

import android.app.Application;
import com.example.newsanytime.room.BookmarkedNewsRepository;

public class BookmarkedNewsSingleton {

    private static BookmarkedNewsRepository bookmarkedNewsRepository;

    public static synchronized BookmarkedNewsRepository getInstance(Application application){
        if(bookmarkedNewsRepository == null) {
            bookmarkedNewsRepository = new BookmarkedNewsRepository(application);
        }
        return bookmarkedNewsRepository;
    }
}
