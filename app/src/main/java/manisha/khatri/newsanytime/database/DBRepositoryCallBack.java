package manisha.khatri.newsanytime.database;

import java.util.List;

public interface DBRepositoryCallBack {
    public void onFailureResponse(String msg);
    public void onSuccessfulResponse(List<BookmarkedNews> bookmarkedNews);
}
