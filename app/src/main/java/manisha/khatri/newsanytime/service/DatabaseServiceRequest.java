package manisha.khatri.newsanytime.service;

import android.app.Application;
import android.os.AsyncTask;

import java.util.List;

import manisha.khatri.newsanytime.database.BookmarkedNews;
import manisha.khatri.newsanytime.database.NewsRepository;
import manisha.khatri.newsanytime.util.NewsDetailCallBackEnum;

public class DatabaseServiceRequest implements DBRemoteDataSource {
    NewsRepository repository;
    DatabaseServiceCallBack databaseServiceCallBack;

    void initRepository(Application application, DatabaseServiceCallBack databaseServiceCallBack){
        repository = new NewsRepository(application);
        this.databaseServiceCallBack = databaseServiceCallBack;
    }

    public void searchNewsByPublishDate(DatabaseServiceCallBack databaseServiceCallBack, String publishDate, Application application) {
        initRepository(application, databaseServiceCallBack);
        new searchNewsByPublishedDateAsyncTask(repository, databaseServiceCallBack).execute(publishDate);
    }

    public void deleteNewsByPublishDate(DatabaseServiceCallBack databaseServiceCallBack, String publishDate, Application application) {
        initRepository(application, databaseServiceCallBack);
        new deleteNewsByPublishDateAsyncTask(repository, databaseServiceCallBack).execute(publishDate);
    }

    public void saveNewsInDB(DatabaseServiceCallBack databaseServiceCallBack, BookmarkedNews bookmarkedNews, Application application){
        initRepository(application, databaseServiceCallBack);
        repository.saveNews(bookmarkedNews);
    }

    private class searchNewsByPublishedDateAsyncTask extends AsyncTask<String, Void, String > {
        private NewsRepository repository;
        DatabaseServiceCallBack databaseServiceCallBack;

        public searchNewsByPublishedDateAsyncTask(NewsRepository repository, DatabaseServiceCallBack databaseServiceCallBack){
            this.repository = repository;
            this.databaseServiceCallBack = databaseServiceCallBack;
        }
        @Override
        protected String  doInBackground(String... publishedDate) {
            return repository.searchNewsByPublishedDate(publishedDate[0]);
        }
        protected void onPostExecute(String savedNews){
            if(savedNews != null)
                databaseServiceCallBack.onSuccessfulResponse(NewsDetailCallBackEnum.NEWS_SAVED_IN_DB.toString());
            else
                databaseServiceCallBack.onSuccessfulResponse(NewsDetailCallBackEnum.NEWS_DELETED_FROM_DB.toString());
        }
    }

    private  class deleteNewsByPublishDateAsyncTask extends AsyncTask<String, Void, Void> {
        private NewsRepository repository;
        DatabaseServiceCallBack databaseServiceCallBack;

        public deleteNewsByPublishDateAsyncTask(NewsRepository repository, DatabaseServiceCallBack databaseServiceCallBack) {
            this.repository = repository;
        }

        @Override
        protected Void doInBackground(String... publishedDate) {
            repository.deleteNewsByPublishDate(publishedDate[0]);
            return null;
        }
    }

    public void fetchNewsFromDB(Application application){
        NewsRepository newsRepository = new NewsRepository(application);
        new fetchNewsListAsyncTask(newsRepository).execute();
    }

    private  class fetchNewsListAsyncTask extends AsyncTask<Void, Void, List<BookmarkedNews>> {
        NewsRepository newsRepository;
        fetchNewsListAsyncTask(NewsRepository newsRepository){
            this.newsRepository = newsRepository;
        }
        @Override
        protected List<BookmarkedNews> doInBackground(Void... voids) {
            return newsRepository.fetchNewsList();
        }
        protected void onPostExecute(List<BookmarkedNews> bookmarkedNewsList){
                databaseServiceCallBack.onSuccessfulResponse(bookmarkedNewsList);
        }
    }

}
