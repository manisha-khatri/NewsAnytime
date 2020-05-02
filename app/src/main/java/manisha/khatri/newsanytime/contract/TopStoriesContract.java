package manisha.khatri.newsanytime.contract;

import manisha.khatri.newsanytime.model.News;

public interface TopStoriesContract {
    void displayTopStoriesNews(News news);
    void displayTopStoriesNewsErrMsg(String errorMsg);
}
