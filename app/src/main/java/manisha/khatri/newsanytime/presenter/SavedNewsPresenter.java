package manisha.khatri.newsanytime.presenter;

import android.app.Application;
import java.util.List;
import manisha.khatri.newsanytime.contract.SavedNewsContract;
import manisha.khatri.newsanytime.database.BookmarkedNews;
import manisha.khatri.newsanytime.database.DBNewsRepository;
import manisha.khatri.newsanytime.database.DBNewsRepositoryImpl;
import manisha.khatri.newsanytime.database.DBRepositoryCallBack;

public class SavedNewsPresenter {
    SavedNewsContract savedNewsContract;
    DBNewsRepository DBNewsRepository;

    public SavedNewsPresenter(SavedNewsContract savedNewsContract){
        this.savedNewsContract = savedNewsContract;
    }

    public void fetchNews(Application application){
        DBNewsRepository = new DBNewsRepositoryImpl(application);
        DBNewsRepository.fetchNewsFromDB(new DBRepositoryCallBack() {
            @Override
            public void onFailureResponse(String msg) {
                savedNewsContract.onFailureResponse(msg);
            }

            @Override
            public void onSuccessfulResponse(List<BookmarkedNews> bookmarkedNews) {
                savedNewsContract.onSuccessfulResponse(bookmarkedNews);
            }
        });
    }

}
