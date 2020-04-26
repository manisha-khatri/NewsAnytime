package manisha.khatri.newsanytime.database;

public interface DBNewsRepository {
    public void searchNewsByPublishDate(DBRepositorySearchNewsCallBck dbRepositorySearchNewsCallBck, String publishDate);
    public void deleteNewsByPublishDate(String publishDate);
    public void saveNewsInDB(BookmarkedNews bookmarkedNews);
    public void fetchNewsFromDB(DBRepositoryCallBack dbRepositoryCallBack);
}
