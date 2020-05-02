package manisha.khatri.newsanytime.presenter;

import java.io.IOException;
import manisha.khatri.newsanytime.contract.SearchNewsContract;
import manisha.khatri.newsanytime.network.model.News;
import manisha.khatri.newsanytime.network.webservice.APINewsRepositoryImpl;
import manisha.khatri.newsanytime.network.webservice.APIResponseCallBack;
import manisha.khatri.newsanytime.network.webservice.APINewsRepository;
import manisha.khatri.newsanytime.util._enum.GenericStrings;
import manisha.khatri.newsanytime.util._enum.NewsCategory;
import retrofit2.Call;
import retrofit2.Response;

public class SearchNewsPresenter {
    private final SearchNewsContract contract;
    APINewsRepository apiNewsRepository;

    public SearchNewsPresenter(SearchNewsContract contract){
        this.contract = contract;
        apiNewsRepository = new APINewsRepositoryImpl();
    }

    public void fetchNews(String keyword){
        apiNewsRepository.fetchNewsBySearchedKeyword(new APIResponseCallBack() {
            @Override
            public void onSuccessfulResponse(Call<News> call, Response<News> response) {
                validateSuccessfulResponse(call, response);
            }

            @Override
            public void onFailureResponse(Call<News> call, Throwable t) {
                validateFailureResponse(call, t);
            }
        }, keyword, "en", NewsCategory.GENERAL.toString());
    }

    private void validateSuccessfulResponse(Call<News> call, Response<News> response){
        if (response.isSuccessful()) {
            News news = response.body();
            if (news.getTotalResults() > 0) {
                contract.displaySearchNews(news);
            } else {
                contract.displaySearchNewsErrorMsg(GenericStrings.No_news_found.toString());
            }
        }
        else{
            contract.displaySearchNewsErrorMsg(GenericStrings.Internal_server_error.toString());
        }
    }

    private void validateFailureResponse(Call<News> call, Throwable t){
        if(t instanceof IOException){
            contract.displaySearchNewsErrorMsg(GenericStrings.Network_error.toString());
        }
        else{
            contract.displaySearchNewsErrorMsg(GenericStrings.Converter_error.toString());
        }
    }
}
