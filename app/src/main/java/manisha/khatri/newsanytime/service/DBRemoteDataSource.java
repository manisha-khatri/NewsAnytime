package manisha.khatri.newsanytime.service;

import android.app.Application;
import manisha.khatri.newsanytime.database.BookmarkedNews;

public interface DBRemoteDataSource {
    public void searchNewsByPublishDate(DatabaseServiceCallBack databaseServiceCallBack, String publishDate, Application application);
    public void saveNewsInDB(DatabaseServiceCallBack databaseServiceCallBack, BookmarkedNews bookmarkedNews, Application application);
    public void deleteNewsByPublishDate(DatabaseServiceCallBack databaseServiceCallBack, String publishDate, Application application);
}
