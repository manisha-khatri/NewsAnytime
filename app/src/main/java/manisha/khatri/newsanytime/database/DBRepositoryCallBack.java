package manisha.khatri.newsanytime.database;

import java.util.List;
import manisha.khatri.newsanytime.database.BookmarkedNews;

public interface DBRepositoryCallBack {
    public void onFailureResponse(String msg);
    public void onSuccessfulResponse(List<BookmarkedNews> bookmarkedNews);
}
