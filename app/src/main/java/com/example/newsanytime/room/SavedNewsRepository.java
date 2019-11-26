package com.example.newsanytime.room;

import android.app.Application;
import android.os.AsyncTask;
import java.util.List;

public class SavedNewsRepository {
    public SavedNewsDAO newsDAO;

    public SavedNewsRepository(Application application){
        SavedNewsDatabase db = SavedNewsDatabase.getInstance(application);
        newsDAO = db.newsDAO();
    }

    public void saveNews(SavedNews savedNews){
        new saveNewsAsyncTask(newsDAO).execute(savedNews); //constructor(dao)
    }

    public String searchNewsByPublishedDate(String publishedDate){
        String savedNews = newsDAO.seachNewsByPublishedDate(publishedDate);
         return savedNews;
    }

    public List<SavedNews> fetchNewsList(){
        List<SavedNews> newsList = newsDAO.fetchNewsList();
        return newsList;
    }

    public void deleteNewsByPublishDate(String publishDate) {
        newsDAO.deleteNewsByPublishDate(publishDate);
    }

    private static class saveNewsAsyncTask extends AsyncTask<SavedNews, Void, Void> {
        private SavedNewsDAO newsDao;

        public saveNewsAsyncTask(SavedNewsDAO newsDao){
            this.newsDao = newsDao;
        }

        @Override
        protected Void doInBackground(SavedNews... news) {
            newsDao.saveNews(news[0]);
            return null;
        }
    }

    private static class deleteNewsByPublishDateAsyncTask extends AsyncTask<String, Void, Void> {
        private SavedNewsDAO newsDao;

        public deleteNewsByPublishDateAsyncTask(SavedNewsDAO newsDao) {
            this.newsDao = newsDao;
        }

        @Override
        protected Void doInBackground(String... publishedDate) {
             newsDao.deleteNewsByPublishDate(publishedDate[0]);
             return null;
        }
    }

}
