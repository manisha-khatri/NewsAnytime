package manisha.khatri.newsanytime.contract;

import manisha.khatri.newsanytime.network.model.News;

public interface NewsContract {
    void displayGeneralNews(News news);
    void displaySportsNews(News news);
    void displayBusinessNews(News news);
    void displayEntertainmentNews(News news);

    void displayGeneralNewsErrorMsg(String errorMsg);
    void displaySportsNewsErrorMsg(String errorMsg);
    void displayBusinessNewsErrorMsg(String errorMsg);
    void displayEntertainmentNewsErrorMsg(String errorMsg);
}
