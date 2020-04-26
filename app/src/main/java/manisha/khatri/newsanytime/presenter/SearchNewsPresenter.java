package manisha.khatri.newsanytime.presenter;

import manisha.khatri.newsanytime.contract.SearchNewsContract;
import manisha.khatri.newsanytime.model.News;
import manisha.khatri.newsanytime.service.APINewsRepositoryImpl;
import manisha.khatri.newsanytime.service.APIResponseCallBack;
import manisha.khatri.newsanytime.service.APINewsRepository;

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
            public void onSuccessfulResponse(News news) {
                contract.onSuccessfulResponse(news);
            }

            @Override
            public void onFailureResponse(String errorMsg) {
                contract.onFailureResponse();
            }
        }, keyword);
    }
}
