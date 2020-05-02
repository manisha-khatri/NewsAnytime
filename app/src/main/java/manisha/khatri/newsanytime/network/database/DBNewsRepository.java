package manisha.khatri.newsanytime.network.database;

public interface DBNewsRepository {
    void searchNewsByPublishDate(DBRepositorySearchNewsCallBck dbRepositorySearchNewsCallBck, String publishDate);
    void deleteNewsByPublishDate(String publishDate);
    void saveNewsInDB(BookmarkedNews bookmarkedNews);
    void fetchNewsFromDB(DBRepositoryCallBack dbRepositoryCallBack);
}
