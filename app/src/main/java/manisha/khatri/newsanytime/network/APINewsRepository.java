package manisha.khatri.newsanytime.network;

public interface APINewsRepository {
    void fetchNewsFor(APIResponseCallBack apiResponseCallBack, String country, String language, String category);
    void fetchNewsBySearchedKeyword(APIResponseCallBack apiResponseCallBack, String keyword, String language, String category);
}
