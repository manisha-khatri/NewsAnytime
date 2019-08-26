package com.example.newsanytime.room;

import android.app.Application;
import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.List;

public class BookmarkedNewsRepository {

    public BookmarkedNewsDAO newsDAO;

    public BookmarkedNewsRepository(Application application){
        BookmarkedNewsDatabase db = BookmarkedNewsDatabase.getInstance(application);
        newsDAO = db.newsDAO();
    }

    public void saveNews(BookmarkedNews bookmarkedNews){
        new saveNewsAsyncTask(newsDAO).execute(bookmarkedNews); //constructor(dao)
    }

    public List<BookmarkedNews> fetchNewsList(){
        List<BookmarkedNews> newsList = newsDAO.fetchNewsList();
        return newsList;
    }

    private static class saveNewsAsyncTask extends AsyncTask<BookmarkedNews, Void, Void> {
        private BookmarkedNewsDAO newsDao;

        public saveNewsAsyncTask(BookmarkedNewsDAO newsDao){
            this.newsDao = newsDao;
        }

        @Override
        protected Void doInBackground(BookmarkedNews... news) {
            newsDao.saveNews(news[0]);
            return null;
        }
    }

    /*private static class fetchNewsAsyncTask extends AsyncTask<Void, Void, Void> {
        private BookmarkedNewsDAO newsDao;

        public fetchNewsAsyncTask(BookmarkedNewsDAO newsDao){
            this.newsDao = newsDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            newsDao.fetchNewsList();
            return null;
        }
    }*/

}
