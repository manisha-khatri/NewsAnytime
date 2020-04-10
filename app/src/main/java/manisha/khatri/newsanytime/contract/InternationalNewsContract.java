package manisha.khatri.newsanytime.contract;

import manisha.khatri.newsanytime.util.NewsCategory;
import manisha.khatri.newsanytime.model.News;

public interface InternationalNewsContract {
    public void onFailureResponse(String errorMsg, NewsCategory newsCategory);
    public void onSuccessfulResponse(News news, NewsCategory newsCategory);

}
