package manisha.khatri.newsanytime.service;

import java.io.IOException;
import manisha.khatri.newsanytime.model.News;
import manisha.khatri.newsanytime.util.Constants;
import manisha.khatri.newsanytime.util.NewsCategory;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WebServiceRequest implements WebRemoteDataSource {
    ApiService apiService;
    Call<News> call;
    WebServiceCallBack webServiceCallBack;

    public WebServiceRequest(){
        apiService = Retrofit2Client.getRetrofitInstance().create(ApiService.class);
    }

    public void fetchNewsByLanguageAndCategory(WebServiceCallBack webServiceCallBack, String category, String language, NewsCategory newsCategory){
        this.webServiceCallBack = webServiceCallBack;
        call = apiService.getTopHeadlinesBasedOnCategoryAndLanguage(
                category,
                language,
                Constants.API_KEY
        );
        fetchNewsFromServer(call, newsCategory);
    }

    public void fetchNewsByLanguage(WebServiceCallBack webServiceCallBack, String language, NewsCategory newsCategory){
        this.webServiceCallBack = webServiceCallBack;
        call = apiService.getTopHeadlinesBasedOnLanguage(
                language,
                Constants.API_KEY
        );
        fetchNewsFromServer(call, newsCategory);
    }

    public void fetchNewsByCountryAndLanguage(WebServiceCallBack webServiceCallBack, String country, String language, NewsCategory newsCategory){
        this.webServiceCallBack = webServiceCallBack;
            call = apiService.getTopHeadlinesBasedOnCountryAndLanguage(
                country,
                language,
                Constants.API_KEY
        );
        fetchNewsFromServer(call, newsCategory);
    }

    public void fetchNewsByLanguageCategoryAndCountry(WebServiceCallBack webServiceCallBack, String category, String language, String country, NewsCategory newsCategory){
        this.webServiceCallBack = webServiceCallBack;
        call = apiService.getTopHeadlinesBasedOnCountryCategoryLang(
                country,
                category,
                language,
                Constants.API_KEY
        );
        fetchNewsFromServer(call, newsCategory);
    }

    public void fetchNewsFromServer(Call<News> call, final NewsCategory newsCategory) {
        call.enqueue(new Callback<News>() {
            @Override
            public void onResponse(Call<News> call, Response<News> response) {
                if (response.isSuccessful()) {
                    News news = response.body();
                    if (news.getTotalResults() > 0) {
                        webServiceCallBack.onSuccessfulResponse(news, newsCategory);
                    } else {
                        webServiceCallBack.onFailureResponse("No news article found", newsCategory);
                    }
                }
                else{
                    webServiceCallBack.onFailureResponse("Internal server error", newsCategory);
                }
            }
            @Override
            public void onFailure(Call<News> call, Throwable t) {
                if(t instanceof IOException){
                    webServiceCallBack.onFailureResponse("Network error", newsCategory);
                }
                else{
                    webServiceCallBack.onFailureResponse("Converter error", newsCategory);
                }
            }
        });
    }


    public void fetchNewsBySearchedKeyword(WebServiceCallBack webServiceCallBack, String keyword, NewsCategory newsCategory){
        this.webServiceCallBack = webServiceCallBack;
        call = apiService.getTopHeadlinesBasedOnSearchedKeyword(
                keyword,
                "en",
                Constants.API_KEY
        );
        fetchNewsFromServer(call, newsCategory);
    }

}
