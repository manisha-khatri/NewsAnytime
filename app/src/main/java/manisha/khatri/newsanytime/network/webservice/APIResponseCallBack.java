package manisha.khatri.newsanytime.network.webservice;

import manisha.khatri.newsanytime.network.model.News;
import retrofit2.Call;
import retrofit2.Response;

public interface APIResponseCallBack {
    void onSuccessfulResponse(Call<News> call, Response<News> response);
    void onFailureResponse(Call<News> call, Throwable t);
}
