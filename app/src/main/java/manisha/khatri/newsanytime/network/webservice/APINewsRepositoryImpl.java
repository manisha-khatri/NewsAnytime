package manisha.khatri.newsanytime.network.webservice;

import manisha.khatri.newsanytime.network.model.News;
import manisha.khatri.newsanytime.util._enum.APIServiceConst;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class APINewsRepositoryImpl implements APINewsRepository {
    APIService apiService;
    Call<News> call;

    public APINewsRepositoryImpl(){
        apiService = Retrofit2Client.getRetrofitInstance().create(APIService.class);
    }

    //you can pass empty strings for parameters
    public void fetchNewsFor( APIResponseCallBack apiResponseCallBack, String country, String language, String category){
        call = apiService.getNewsFor(
                country,
                language,
                category,
                APIServiceConst.API_KEY.toString()
        );
        fetchNewsFromServer(call, apiResponseCallBack);
    }

    public void fetchNewsBySearchedKeyword(APIResponseCallBack apiResponseCallBack, String keyword, String language, String category){
        call = apiService.getNewsBySearchedKeyword(
                keyword,
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
                apiResponseCallBack.onSuccessfulResponse(call, response);
            }
            @Override
            public void onFailure(Call<News> call, Throwable t) {
                apiResponseCallBack.onFailureResponse(call,t);
            }
        });
    }

}
