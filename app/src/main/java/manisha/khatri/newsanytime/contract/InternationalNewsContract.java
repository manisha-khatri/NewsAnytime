package manisha.khatri.newsanytime.contract;

import manisha.khatri.newsanytime._enum.InternationalNewsType;
import manisha.khatri.newsanytime.model.News;

public interface InternationalNewsContract {
    public void displayInternationalNewsArticles(News news, InternationalNewsType internationalNewsType);

    public void displaySportsNewsArticles(News news, InternationalNewsType internationalNewsType);

    public void displayBusinessNewsArticles(News news, InternationalNewsType internationalNewsType);

    public void displayEntertainmentNewsArticles(News news, InternationalNewsType internationalNewsType);

    public void handleInvalidResponseFromServer(InternationalNewsType internationalNewsType);
}
