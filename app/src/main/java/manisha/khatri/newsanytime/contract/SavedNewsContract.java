package manisha.khatri.newsanytime.contract;

import manisha.khatri.newsanytime.network.database.BookmarkedNews;
import java.util.List;

public interface SavedNewsContract {
    void displayBookmarkedNews(List<BookmarkedNews> bookmarkedNews);
    void displayBookmarkedNewsErrorMsg(String msg);
}
