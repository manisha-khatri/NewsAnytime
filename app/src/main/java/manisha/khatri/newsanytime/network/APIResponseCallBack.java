package manisha.khatri.newsanytime.network;

import manisha.khatri.newsanytime.model.News;
import retrofit2.Call;
import retrofit2.Response;

public interface APIResponseCallBack {
    void onSuccessfulResponse(Call<News> call, Response<News> response);
    void onFailureResponse(Call<News> call, Throwable t);
}
