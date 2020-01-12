package manisha.khatri.newsanytime.presenter;

import android.app.Application;
import android.os.AsyncTask;
import manisha.khatri.newsanytime.contract.NewsDetailContract;
import manisha.khatri.newsanytime.room.SavedNews;
import manisha.khatri.newsanytime.room.SavedNewsRepository;
import manisha.khatri.newsanytime.singleton.SavedNewsSingleton;
import java.util.Dictionary;

public class NewsDetailPresenter {
    private NewsDetailContract newsDetailContract;
    Dictionary<String,String> newsDict;

    public NewsDetailPresenter(NewsDetailContract newsDetailContract){
        this.newsDetailContract = newsDetailContract;
    }

    public void searchNewsByPublishDate(String publishDate, Application application) {
        SavedNewsRepository repository = SavedNewsSingleton.getInstance(application);
        new searchNewsByPublishedDateAsyncTask(repository).execute(publishDate);
    }

    private class searchNewsByPublishedDateAsyncTask extends AsyncTask<String, Void, String > {
        private SavedNewsRepository repository;

        public searchNewsByPublishedDateAsyncTask(SavedNewsRepository repository){
            this.repository = repository;
        }
        @Override
        protected String  doInBackground(String... publishedDate) {
            return repository.searchNewsByPublishedDate(publishedDate[0]);
        }
        protected void onPostExecute(String savedNews){
            if(savedNews!=null)
                newsDetailContract.enableBookmark();
            else
                newsDetailContract.disableBookmark();
        }

    }

    public void deleteNewsByPublishDate(String publishDate, Application application) {
        SavedNewsRepository repository = SavedNewsSingleton.getInstance(application);
        new deleteNewsByPublishDateAsyncTask(repository).execute(publishDate);
    }

    private static class deleteNewsByPublishDateAsyncTask extends AsyncTask<String, Void, Void> {
        private SavedNewsRepository repository;

        public deleteNewsByPublishDateAsyncTask(SavedNewsRepository repository) {
            this.repository = repository;
        }

        @Override
        protected Void doInBackground(String... publishedDate) {
            repository.deleteNewsByPublishDate(publishedDate[0]);
            return null;
        }
    }

    public void saveNewsInDB(Dictionary newsDict, Application application){

        String headline = (String) newsDict.get("HEADLINE");
        String image = (String) newsDict.get("IMAGE");
        String description = (String) newsDict.get("DESCRIPTION");
        String content = (String) newsDict.get("CONTENT");
        String publishedDate = (String) newsDict.get("PUBLISHEDAT");

        SavedNews savedNews = new SavedNews(headline, image, description, content,publishedDate);
        SavedNewsRepository repository = SavedNewsSingleton.getInstance(application);
        repository.saveNews(savedNews);
    }

}
