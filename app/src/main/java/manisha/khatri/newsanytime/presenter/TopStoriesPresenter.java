package manisha.khatri.newsanytime.presenter;

import java.io.IOException;

import manisha.khatri.newsanytime.contract.TopStoriesContract;
import manisha.khatri.newsanytime.model.News;
import manisha.khatri.newsanytime.network.APINewsRepositoryImpl;
import manisha.khatri.newsanytime.network.APIResponseCallBack;
import manisha.khatri.newsanytime.network.APINewsRepository;
import manisha.khatri.newsanytime.util._enum.GenericStrings;
import retrofit2.Call;
import retrofit2.Response;

public class TopStoriesPresenter{
    private final TopStoriesContract contract;
    APINewsRepository apiNewsRepository;

    public TopStoriesPresenter(TopStoriesContract contract){
        this.contract = contract;
    }

    public void fetchNews(){
        apiNewsRepository = new APINewsRepositoryImpl();
        apiNewsRepository.fetchNewsFor(new APIResponseCallBack() {
            @Override
            public void onSuccessfulResponse(Call<News> call, Response<News> response) {
                validateSuccessfulResponse(call, response);
            }

            @Override
            public void onFailureResponse(Call<News> call, Throwable t) {
                validateFailureResponse(call, t);
            }
        }, "","en","");
    }

    private void validateSuccessfulResponse(Call<News> call, Response<News> response){
        if (response.isSuccessful()) {
            News news = response.body();
            if (news.getTotalResults() > 0) {
                contract.displayTopStoriesNews(news);
            } else {
                contract.displayTopStoriesNewsErrMsg(GenericStrings.No_news_found.toString());
            }
        }
        else{
            contract.displayTopStoriesNewsErrMsg(GenericStrings.Internal_server_error.toString());
        }
    }

    private void validateFailureResponse(Call<News> call, Throwable t){
        if(t instanceof IOException){
            contract.displayTopStoriesNewsErrMsg(GenericStrings.Network_error.toString());
        }
        else{
            contract.displayTopStoriesNewsErrMsg(GenericStrings.Converter_error.toString());
        }
    }

}
