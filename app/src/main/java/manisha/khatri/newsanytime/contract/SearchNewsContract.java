package manisha.khatri.newsanytime.contract;

import manisha.khatri.newsanytime.model.News;

public interface SearchNewsContract {
     void displaySearchedNewsArticles(News news);
     void handleInvalidResponseFromServer();
}
