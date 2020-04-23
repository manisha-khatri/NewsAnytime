package manisha.khatri.newsanytime.service;

import manisha.khatri.newsanytime.model.News;

public interface APIResponseCallBack {
    public void onSuccessfulResponse(News news);
    public void onFailureResponse(String errorMsg);
}
