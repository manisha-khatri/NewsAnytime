package manisha.khatri.newsanytime.service;

public interface APINewsRepository {
    public void fetchNewsFor( APIResponseCallBack apiResponseCallBack, String country, String language, String category);
    public void fetchNewsBySearchedKeyword(APIResponseCallBack apiResponseCallBack, String keyword);

}
