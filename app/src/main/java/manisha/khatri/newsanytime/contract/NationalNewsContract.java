package manisha.khatri.newsanytime.contract;

import manisha.khatri.newsanytime.model.News;
import manisha.khatri.newsanytime.util.NewsCategory;
import manisha.khatri.newsanytime.util._enum.NationalNewsType;

public interface NationalNewsContract {
    public void onFailureResponse(String errorMsg, NewsCategory newsCategory);
    public void onSuccessfulResponse(News news, NewsCategory newsCategory);

}
