package manisha.khatri.newsanytime.service;

import java.io.IOException;
import manisha.khatri.newsanytime.model.News;
import manisha.khatri.newsanytime.util._enum.APIServiceConst;
import manisha.khatri.newsanytime.util._enum.GenericStrings;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class APINewsRepository {
    APIService apiService;
    Call<News> call;

    public APINewsRepository(){
        apiService = Retrofit2Client.getRetrofitInstance().create(APIService.class);
    }

    public void fetchNewsByLanguageAndCategory(APIResponseCallBack apiResponseCallBack, String category, String language){
        call = apiService.getTopHeadlinesBasedOnCategoryAndLanguage(
                category,
                language,
                APIServiceConst.API_KEY.toString()
        );
        fetchNewsFromServer(call, apiResponseCallBack);
    }

    public void fetchNewsByLanguage(APIResponseCallBack apiResponseCallBack, String language){
        call = apiService.getTopHeadlinesBasedOnLanguage(
                language,
                APIServiceConst.API_KEY.toString()
        );
        fetchNewsFromServer(call, apiResponseCallBack);
    }

    public void fetchNewsByCountryAndLanguage(APIResponseCallBack apiResponseCallBack, String country, String language){
            call = apiService.getTopHeadlinesBasedOnCountryAndLanguage(
                country,
                language,
                    APIServiceConst.API_KEY.toString()
        );
        fetchNewsFromServer(call, apiResponseCallBack);
    }

    public void fetchNewsByLanguageCategoryAndCountry(APIResponseCallBack apiResponseCallBack, String category, String language, String country){
        call = apiService.getTopHeadlinesBasedOnCountryCategoryLang(
                country,
                category,
                language,
                APIServiceConst.API_KEY.toString()
        );
        fetchNewsFromServer(call, apiResponseCallBack);
    }

    private void fetchNewsFromServer(Call<News> call, final APIResponseCallBack apiResponseCallBack) {
        call.enqueue(new Callback<News>() {
            @Override
            public void onResponse(Call<News> call, Response<News> response) {
                if (response.isSuccessful()) {
                    News news = response.body();
                    if (news.getTotalResults() > 0) {
                        apiResponseCallBack.onSuccessfulResponse(news);
                    } else {
                        apiResponseCallBack.onFailureResponse(GenericStrings.No_news_found.toString());
                    }
                }
                else{
                    apiResponseCallBack.onFailureResponse(GenericStrings.Internal_server_error.toString());
                }
            }
            @Override
            public void onFailure(Call<News> call, Throwable t) {
                if(t instanceof IOException){
                    apiResponseCallBack.onFailureResponse(GenericStrings.Network_error.toString());
                }
                else{
                    apiResponseCallBack.onFailureResponse(GenericStrings.Converter_error.toString());
                }
            }
        });
    }


    public void fetchNewsBySearchedKeyword(APIResponseCallBack apiResponseCallBack, String keyword){
        call = apiService.getTopHeadlinesBasedOnSearchedKeyword(
                keyword,
                "en",
                APIServiceConst.API_KEY.toString()
        );
        fetchNewsFromServer(call, apiResponseCallBack);
    }

}
