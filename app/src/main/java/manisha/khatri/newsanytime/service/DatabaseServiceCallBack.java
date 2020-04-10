package manisha.khatri.newsanytime.service;

import java.util.List;
import manisha.khatri.newsanytime.database.BookmarkedNews;

public interface DatabaseServiceCallBack {
    public void onSuccessfulResponse(String res);
    public void onFailureResponse();
    public void onSuccessfulResponse(List<BookmarkedNews> bookmarkedNews);
}
