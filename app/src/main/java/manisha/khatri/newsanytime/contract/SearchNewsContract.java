package manisha.khatri.newsanytime.contract;

import manisha.khatri.newsanytime.network.model.News;

public interface SearchNewsContract {
     void displaySearchNews(News news);
     void displaySearchNewsErrorMsg(String errMsg);
}
