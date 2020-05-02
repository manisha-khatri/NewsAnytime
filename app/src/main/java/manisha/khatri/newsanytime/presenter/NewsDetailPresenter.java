package manisha.khatri.newsanytime.presenter;

import android.app.Application;
import manisha.khatri.newsanytime.contract.NewsDetailContract;
import manisha.khatri.newsanytime.network.database.BookmarkedNews;
import manisha.khatri.newsanytime.network.database.DBNewsRepository;
import manisha.khatri.newsanytime.network.database.DBNewsRepositoryImpl;
import manisha.khatri.newsanytime.network.database.DBRepositorySearchNewsCallBck;

public class NewsDetailPresenter {
    private NewsDetailContract newsDetailContract;
    DBNewsRepository DBNewsRepository;

    public NewsDetailPresenter(NewsDetailContract newsDetailContract){
        this.newsDetailContract = newsDetailContract;
    }

    public void searchNews(String publishDate, Application application){
        DBNewsRepository = new DBNewsRepositoryImpl(application);
        DBNewsRepository.searchNewsByPublishDate(new DBRepositorySearchNewsCallBck() {
            @Override
            public void isNewsFound(boolean result) {
                if(result == true)
                    newsDetailContract.checkBookmark();
                else
                    newsDetailContract.uncheckBookmark();
            }
        }, publishDate);
    }

    public void deleteNews(String publishDate, Application application){
        DBNewsRepository = new DBNewsRepositoryImpl(application);
        DBNewsRepository.deleteNewsByPublishDate(publishDate);
    }

    public void saveNews(BookmarkedNews bookmarkedNews, Application application){
        DBNewsRepository = new DBNewsRepositoryImpl(application);
        DBNewsRepository.saveNewsInDB( bookmarkedNews);
    }
}
