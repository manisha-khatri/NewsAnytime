package manisha.khatri.newsanytime.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface NewsDAO {

    @Insert
    void saveNews(BookmarkedNews bookmarkedNews);

    @Query("select * from bookmarked_news")
    List<BookmarkedNews> fetchNewsList();

    @Query("select headline from bookmarked_news where published_date IN(:publishedDate)")
    String seachNewsByPublishedDate(String publishedDate);

    @Query("DELETE FROM bookmarked_news WHERE published_date = :publishedDate")
    void deleteNewsByPublishDate(String publishedDate);

}
