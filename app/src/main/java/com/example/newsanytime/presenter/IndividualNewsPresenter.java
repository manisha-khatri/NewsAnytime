package com.example.newsanytime.presenter;

import android.app.Application;

import com.example.newsanytime.contract.IndividualNewsContract;
import com.example.newsanytime.room.BookmarkedNews;
import com.example.newsanytime.room.BookmarkedNewsRepository;
import com.example.newsanytime.singleton.BookmarkedNewsSingleton;
import java.util.Dictionary;

public class IndividualNewsPresenter {
    private IndividualNewsContract individualNewsContract;
    Dictionary<String,String> bookmarkedNews;

    public IndividualNewsPresenter(IndividualNewsContract individualNewsContract){
        this.individualNewsContract = individualNewsContract;
    }

    /*public void saveBookmarkedNewsInDB(Dictionary newsDict, Application application){
        this.bookmarkedNews = newsDict;
        String headline = (String) newsDict.get("news headline");
        String image = (String) newsDict.get("news image");
        String description = (String) newsDict.get("news description");
        String content = (String) newsDict.get("news content");

        BookmarkedNews bookmarkedNews = new BookmarkedNews(headline, image, description, content);
        BookmarkedNewsRepository repository = BookmarkedNewsSingleton.getInstance(application);
        repository.saveNews(bookmarkedNews);
    }*/
}
