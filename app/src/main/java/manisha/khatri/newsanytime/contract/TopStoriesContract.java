package manisha.khatri.newsanytime.contract;

import manisha.khatri.newsanytime.model.News;

public interface TopStoriesContract {
    public void displayTopStories(News news);
    public void handleInvalidResponseFromServer();
}
