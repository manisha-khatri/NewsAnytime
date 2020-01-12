package manisha.khatri.newsanytime.contract;

import manisha.khatri.newsanytime.model.News;
import manisha.khatri.newsanytime._enum.NationalNewsType;

public interface NationalNewsContract {

    public void displayNationalNewsArticles(News news, NationalNewsType nationalNewsType);

    public void displaySportsNewsArticles(News news, NationalNewsType nationalNewsType);

    public void displayBusinessNewsArticles(News news, NationalNewsType nationalNewsType);

    public void displayEntertainmentNewsArticles(News news, NationalNewsType nationalNewsType);

    public void handleInvalidResponseFromServer(NationalNewsType nationalNewsType);

}
