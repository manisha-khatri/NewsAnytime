package com.example.newsanytime.presenter;

import android.app.Application;
import android.os.AsyncTask;
import com.example.newsanytime.contract.NewsDetailContract;
import com.example.newsanytime.room.BookmarkedNews;
import com.example.newsanytime.room.BookmarkedNewsRepository;
import com.example.newsanytime.singleton.BookmarkedNewsSingleton;
import java.util.Dictionary;

public class NewsDetailPresenter {
    private NewsDetailContract newsDetailContract;
    Dictionary<String,String> newsDict;

    public NewsDetailPresenter(NewsDetailContract newsDetailContract){
        this.newsDetailContract = newsDetailContract;
    }

    public void searchNewsByPublishDate(String publishDate, Application application) {
        BookmarkedNewsRepository repository = BookmarkedNewsSingleton.getInstance(application);
        new searchNewsByPublishedDateAsyncTask(repository).execute(publishDate);
    }

    private class searchNewsByPublishedDateAsyncTask extends AsyncTask<String, Void, String > {
        private BookmarkedNewsRepository repository;

        public searchNewsByPublishedDateAsyncTask(BookmarkedNewsRepository repository){
            this.repository = repository;
        }
        @Override
        protected String  doInBackground(String... publishedDate) {
            return repository.searchNewsByPublishedDate(publishedDate[0]);
        }
        protected void onPostExecute(String  bookmarkedNews){
            setBookmark(bookmarkedNews);
        }

    }
    public void setBookmark(String bookmarkedNews) {
        newsDetailContract.setBookmark(bookmarkedNews);
    }

    public void deleteNewsByPublishDate(String publishDate, Application application) {
        BookmarkedNewsRepository repository = BookmarkedNewsSingleton.getInstance(application);
        new deleteNewsByPublishDateAsyncTask(repository).execute(publishDate);
    }

    private static class deleteNewsByPublishDateAsyncTask extends AsyncTask<String, Void, Void> {
        private BookmarkedNewsRepository repository;

        public deleteNewsByPublishDateAsyncTask(BookmarkedNewsRepository repository) {
            this.repository = repository;
        }

        @Override
        protected Void doInBackground(String... publishedDate) {
            repository.deleteNewsByPublishDate(publishedDate[0]);
            return null;
        }
    }



    public void saveBookmarkedNewsInDB(Dictionary newsDict, Application application){

        String headline = (String) newsDict.get("NEWS_HEADLINE");
        String image = (String) newsDict.get("NEWS_IMAGE");
        String description = (String) newsDict.get("NEWS_DESCRIPTION");
        String content = (String) newsDict.get("NEWS_CONTENT");
        String publishedDate = (String) newsDict.get("NEWS_PUBLISHED_AT");

        BookmarkedNews bookmarkedNews = new BookmarkedNews(headline, image, description, content,publishedDate);
        BookmarkedNewsRepository repository = BookmarkedNewsSingleton.getInstance(application);
        repository.saveNews(bookmarkedNews);
    }

}
