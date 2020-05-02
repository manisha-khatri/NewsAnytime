package manisha.khatri.newsanytime.database;

import android.app.Application;
import android.os.AsyncTask;
import java.util.List;

public class DBNewsRepositoryImpl implements DBNewsRepository{
    public NewsDAO newsDAO;

    public DBNewsRepositoryImpl(Application application){
        NewsDatabase db = NewsDatabase.getInstance(application);
        newsDAO = db.newsDAO();
    }

    public void fetchNewsFromDB(DBRepositoryCallBack dbRepositoryCallBack){
        new fetchNewsListAsyncTask(dbRepositoryCallBack).execute();
    }

    public void searchNewsByPublishDate(DBRepositorySearchNewsCallBck dbRepositorySearchNewsCallBck, String publishDate) {
        new searchNewsByPublishedDateAsyncTask(dbRepositorySearchNewsCallBck).execute(publishDate);
    }

    public void deleteNewsByPublishDate(String publishDate) {
        new deleteNewsByPublishDateAsyncTask().execute(publishDate);
    }

    public void saveNewsInDB(BookmarkedNews bookmarkedNews){
        new saveNewsAsyncTask().execute(bookmarkedNews);
    }

    private  class deleteNewsByPublishDateAsyncTask extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... publishedDate) {
            newsDAO.deleteNewsByPublishDate(publishedDate[0]);
            return null;
        }
    }

    private class searchNewsByPublishedDateAsyncTask extends AsyncTask<String, Void, String > {
        DBRepositorySearchNewsCallBck dbRepositorySearchNewsCallBck;
        public searchNewsByPublishedDateAsyncTask(DBRepositorySearchNewsCallBck dbRepositorySearchNewsCallBck){
            this.dbRepositorySearchNewsCallBck = dbRepositorySearchNewsCallBck;
        }
        @Override
        protected String  doInBackground(String... publishedDate) {
            return newsDAO.seachNewsByPublishedDate(publishedDate[0]);
        }
        protected void onPostExecute(String savedNews){
            if(savedNews != null)
                dbRepositorySearchNewsCallBck.isNewsFound(true);
            else
                dbRepositorySearchNewsCallBck.isNewsFound(false);
        }
    }

    private  class fetchNewsListAsyncTask extends AsyncTask<Void, Void, List<BookmarkedNews>> {
        DBRepositoryCallBack dbRepositoryCallBack;
        public fetchNewsListAsyncTask(DBRepositoryCallBack dbRepositoryCallBack){
            this.dbRepositoryCallBack = dbRepositoryCallBack;
        }
        @Override
        protected List<BookmarkedNews> doInBackground(Void... voids) {
            return newsDAO.fetchNewsList();
        }
        protected void onPostExecute(List<BookmarkedNews> bookmarkedNewsList){
            if(bookmarkedNewsList.size() > 0)
                dbRepositoryCallBack.onSuccessfulResponse(bookmarkedNewsList);
            else
                dbRepositoryCallBack.onFailureResponse("No news found!");
        }
    }

    private  class saveNewsAsyncTask extends AsyncTask<BookmarkedNews, Void, Void> {
        @Override
        protected Void doInBackground(BookmarkedNews... bookmarkedNews) {
            newsDAO.saveNews(bookmarkedNews[0]);
            return null;
        }
    }

}
