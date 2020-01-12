package manisha.khatri.newsanytime.contract;

import manisha.khatri.newsanytime.room.SavedNews;
import java.util.List;

public interface SavedNewsContract {
    void renderNewsList(List<SavedNews> newsList);
    void handleNoSavedNewsInDB();
}
