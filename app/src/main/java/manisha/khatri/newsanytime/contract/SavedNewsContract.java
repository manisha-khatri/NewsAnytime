package manisha.khatri.newsanytime.contract;

import manisha.khatri.newsanytime.database.BookmarkedNews;
import java.util.List;

public interface SavedNewsContract {
    void renderNewsList(List<BookmarkedNews> bookmarkedNewsList);
}
