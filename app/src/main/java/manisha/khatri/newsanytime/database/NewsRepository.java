package manisha.khatri.newsanytime.database;

import android.app.Application;
import android.os.AsyncTask;
import java.util.List;

public class NewsRepository {
    public NewsDAO newsDAO;

    public NewsRepository(Application application){
        NewsDatabase db = NewsDatabase.getInstance(application);
        newsDAO = db.newsDAO();
    }

    //save news in the database
    public void saveNews(BookmarkedNews bookmarkedNews){
        new saveNewsAsyncTask(newsDAO).execute(bookmarkedNews);
    }

    //search news by published date
    public String searchNewsByPublishedDate(String publishedDate){
        String savedNews = newsDAO.seachNewsByPublishedDate(publishedDate);
         return savedNews;
    }

    //fetch news list
    public List<BookmarkedNews> fetchNewsList(){
        List<BookmarkedNews> bookmarkedNewsList = newsDAO.fetchNewsList();
        return bookmarkedNewsList;
    }

    //delete news by published date
    public void deleteNewsByPublishDate(String publishDate) {
        newsDAO.deleteNewsByPublishDate(publishDate);
    }

    private static class saveNewsAsyncTask extends AsyncTask<BookmarkedNews, Void, Void> {
        private NewsDAO newsDao;

        public saveNewsAsyncTask(NewsDAO newsDao){
            this.newsDao = newsDao;
        }

        @Override
        protected Void doInBackground(BookmarkedNews... bookmarkedNews) {
            newsDao.saveNews(bookmarkedNews[0]);
            return null;
        }
    }

    private static class deleteNewsByPublishDateAsyncTask extends AsyncTask<String, Void, Void> {
        private NewsDAO newsDao;

        public deleteNewsByPublishDateAsyncTask(NewsDAO newsDao) {
            this.newsDao = newsDao;
        }

        @Override
        protected Void doInBackground(String... publishedDate) {
             newsDao.deleteNewsByPublishDate(publishedDate[0]);
             return null;
        }
    }

}
