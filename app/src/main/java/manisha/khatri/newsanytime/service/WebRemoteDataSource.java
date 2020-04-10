package manisha.khatri.newsanytime.service;

import manisha.khatri.newsanytime.util.NewsCategory;

public interface WebRemoteDataSource {
    public void fetchNewsBySearchedKeyword(WebServiceCallBack webServiceCallBack, String keyword, NewsCategory newsCategory);
    public void fetchNewsByLanguageCategoryAndCountry(WebServiceCallBack webServiceCallBack, String category, String language, String country, NewsCategory newsCategory);
    public void fetchNewsByLanguageAndCategory(WebServiceCallBack webServiceCallBack, String category, String language, NewsCategory newsCategory);
    public void fetchNewsByCountryAndLanguage(WebServiceCallBack webServiceCallBack, String country, String language, NewsCategory newsCategory);
    public void fetchNewsByLanguage(WebServiceCallBack webServiceCallBack, String language, NewsCategory newsCategory);

}
