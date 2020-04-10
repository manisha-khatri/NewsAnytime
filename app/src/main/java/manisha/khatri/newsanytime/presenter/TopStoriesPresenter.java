package manisha.khatri.newsanytime.presenter;

import manisha.khatri.newsanytime.service.ApiService;
import manisha.khatri.newsanytime.contract.TopStoriesContract;
import manisha.khatri.newsanytime.model.News;

import manisha.khatri.newsanytime.service.WebServiceCallBack;
import manisha.khatri.newsanytime.service.WebRemoteDataSource;
import manisha.khatri.newsanytime.service.WebServiceRequest;
import manisha.khatri.newsanytime.util.Constants;
import manisha.khatri.newsanytime.util.NewsCategory;
import retrofit2.Call;

public class TopStoriesPresenter implements WebServiceCallBack {
    private final TopStoriesContract contract;
    WebRemoteDataSource webRemoteDataSource;

    public TopStoriesPresenter(TopStoriesContract contract){
        this.contract = contract;
    }

    public void fetchNews(){
        webRemoteDataSource = new WebServiceRequest();
        webRemoteDataSource.fetchNewsByLanguage(this, "en", NewsCategory.TOP_STORIES);
    }

    private Call<News> getTopStories(ApiService apiService) {
        return apiService.getTopHeadlinesBasedOnLanguage(
                "en",
                Constants.API_KEY
        );
    }

    @Override
    public void onSuccessfulResponse(News news, NewsCategory newsCategory) {
        contract.onSuccessfulResponse(news, newsCategory);
    }

    @Override
    public void onFailureResponse(String errorMsg, NewsCategory newsCategory) {
        contract.onFailureResponse(errorMsg, newsCategory);
    }
}
