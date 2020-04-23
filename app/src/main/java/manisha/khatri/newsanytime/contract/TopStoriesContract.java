package manisha.khatri.newsanytime.contract;

import manisha.khatri.newsanytime.model.News;

public interface TopStoriesContract {
    public void onSuccessfulResponse(News news);
    public void onFailureResponse(String errorMsg);
}
