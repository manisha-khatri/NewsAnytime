package manisha.khatri.newsanytime.presenter;

import manisha.khatri.newsanytime.contract.TopStoriesContract;
import manisha.khatri.newsanytime.model.News;
import manisha.khatri.newsanytime.service.APIResponseCallBack;
import manisha.khatri.newsanytime.service.APINewsRepository;

public class TopStoriesPresenter{
    private final TopStoriesContract contract;
    APINewsRepository apiNewsRepository;

    public TopStoriesPresenter(TopStoriesContract contract){
        this.contract = contract;
    }

    public void fetchNews(){
        apiNewsRepository = new APINewsRepository();
        apiNewsRepository.fetchNewsByLanguage(new APIResponseCallBack() {
            @Override
            public void onSuccessfulResponse(News news) {
                contract.onSuccessfulResponse(news);
            }
            @Override
            public void onFailureResponse(String errorMsg) {
                contract.onFailureResponse(errorMsg);
            }
        }, "en");
    }

}
