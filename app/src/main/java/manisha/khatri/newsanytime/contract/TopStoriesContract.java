package manisha.khatri.newsanytime.contract;

import manisha.khatri.newsanytime.model.News;
import manisha.khatri.newsanytime.util.NewsCategory;

public interface TopStoriesContract {
    public void onSuccessfulResponse(News news, NewsCategory newsCategory);
    public void onFailureResponse(String errorMsg, NewsCategory newsCategory);
}
