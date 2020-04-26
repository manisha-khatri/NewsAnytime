package manisha.khatri.newsanytime.service;

import java.io.IOException;
import manisha.khatri.newsanytime.model.News;
import manisha.khatri.newsanytime.util._enum.APIServiceConst;
import manisha.khatri.newsanytime.util._enum.GenericStrings;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class APINewsRepositoryImpl implements APINewsRepository {
    APIService apiService;
    Call<News> call;

    public APINewsRepositoryImpl(){
        apiService = Retrofit2Client.getRetrofitInstance().create(APIService.class);
    }

    public void fetchNewsFor( APIResponseCallBack apiResponseCallBack, String country, String language, String category){
        call = apiService.getNewsFor(
                country,
                language,
                category,
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
        call = apiService.getNewsBySearchedKeyword(
                keyword,
                "en",
                APIServiceConst.API_KEY.toString()
        );
        fetchNewsFromServer(call, apiResponseCallBack);
    }

}
