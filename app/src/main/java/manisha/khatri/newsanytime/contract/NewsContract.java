package manisha.khatri.newsanytime.contract;

import manisha.khatri.newsanytime.model.News;

public interface NewsContract {

    public void displayInternationalNews(News news);
    public void displayNationalNews(News news);
    public void displaySportsNews(News news);
    public void displayBusinessNews(News news);
    public void displayEntertainmentNews(News news);

    public void onNatInternatNewsFailureResponse(String errorMsg);
    public void onSportsNewsFailureResponse(String errorMsg);
    public void onBusinessNewsFailureResponse(String errorMsg);
    public void onEntertainmentNewsFailureResponse(String errorMsg);
}
