package manisha.khatri.newsanytime.presenter;

import android.app.Application;
import java.util.List;
import manisha.khatri.newsanytime.contract.SavedNewsContract;
import manisha.khatri.newsanytime.database.BookmarkedNews;
import manisha.khatri.newsanytime.service.DatabaseServiceRequest;
import manisha.khatri.newsanytime.service.DatabaseServiceCallBack;

public class SavedNewsPresenter implements DatabaseServiceCallBack {
    SavedNewsContract savedNewsContract;
    DatabaseServiceRequest databaseServiceRequest;

    public SavedNewsPresenter(SavedNewsContract savedNewsContract){
        this.savedNewsContract = savedNewsContract;
        databaseServiceRequest = new DatabaseServiceRequest();
    }

    public void fetchNews(Application application){
        databaseServiceRequest.fetchNewsFromDB(application);
    }

    @Override
    public void onSuccessfulResponse(List<BookmarkedNews> bookmarkedNews) {
        savedNewsContract.renderNewsList(bookmarkedNews);
    }

    @Override
    public void onSuccessfulResponse(String res) {
    }

    @Override
    public void onFailureResponse() {
    }
}
