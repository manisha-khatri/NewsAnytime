package manisha.khatri.newsanytime.presenter;

import android.app.Application;
import java.util.List;
import manisha.khatri.newsanytime.contract.NewsDetailContract;
import manisha.khatri.newsanytime.database.BookmarkedNews;
import manisha.khatri.newsanytime.service.DatabaseServiceRequest;
import manisha.khatri.newsanytime.service.DatabaseServiceCallBack;
import manisha.khatri.newsanytime.service.DBRemoteDataSource;
import manisha.khatri.newsanytime.util.NewsDetailCallBackEnum;

public class NewsDetailPresenter implements DatabaseServiceCallBack {
    private NewsDetailContract newsDetailContract;
    DBRemoteDataSource DBRemoteDataSource;

    public NewsDetailPresenter(NewsDetailContract newsDetailContract){
        this.newsDetailContract = newsDetailContract;
        DBRemoteDataSource = new DatabaseServiceRequest();
    }

    public void searchNews(String publishDate, Application application){
        DBRemoteDataSource.searchNewsByPublishDate( this, publishDate, application);
    }

    public void deleteNews(String publishDate, Application application){
        DBRemoteDataSource.deleteNewsByPublishDate(this, publishDate, application);
    }

    public void saveNews(BookmarkedNews bookmarkedNews, Application application){
        DBRemoteDataSource.saveNewsInDB(this, bookmarkedNews, application);
    }

    @Override
    public void onSuccessfulResponse(String res) {
        if(NewsDetailCallBackEnum.NEWS_DELETED_FROM_DB.toString() == res)
            newsDetailContract.uncheckBookmark();
        else if(NewsDetailCallBackEnum.NEWS_SAVED_IN_DB.toString() == res)
                newsDetailContract.checkBookmark();
    }

    @Override
    public void onFailureResponse() {

    }

    @Override
    public void onSuccessfulResponse(List<BookmarkedNews> bookmarkedNews) {

    }
}
