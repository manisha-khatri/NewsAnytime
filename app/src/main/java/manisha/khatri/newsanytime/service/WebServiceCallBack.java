package manisha.khatri.newsanytime.service;

import manisha.khatri.newsanytime.model.News;
import manisha.khatri.newsanytime.util.NewsCategory;

public interface WebServiceCallBack {
    public void onSuccessfulResponse(News news, NewsCategory newsCategory);
    public void onFailureResponse(String errorMsg, NewsCategory newsCategory);
}
