package manisha.khatri.newsanytime.contract;

import manisha.khatri.newsanytime.database.BookmarkedNews;
import java.util.List;

public interface SavedNewsContract {
    public void onSuccessfulResponse(List<BookmarkedNews> bookmarkedNews);
    public void onFailureResponse(String msg);
}
