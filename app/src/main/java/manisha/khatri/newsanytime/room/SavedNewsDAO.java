package manisha.khatri.newsanytime.room;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface SavedNewsDAO {

    @Insert
    void saveNews(SavedNews savedNews);

    @Query("select * from saved_news")
    List<SavedNews> fetchNewsList();

    @Query("select headline from saved_news where published_date IN(:publishedDate)")
    String seachNewsByPublishedDate(String publishedDate);

    @Query("DELETE FROM saved_news WHERE published_date = :publishedDate")
    void deleteNewsByPublishDate(String publishedDate);

}
