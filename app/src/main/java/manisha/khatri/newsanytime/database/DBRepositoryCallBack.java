package manisha.khatri.newsanytime.database;

import java.util.List;

public interface DBRepositoryCallBack {
    void onFailureResponse(String msg);
    void onSuccessfulResponse(List<BookmarkedNews> bookmarkedNews);
}
