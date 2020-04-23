package manisha.khatri.newsanytime.util._enum;

public enum GenericStrings {
    No_news_found("No news found!"),
    Internal_server_error("Internal server error"),
    Network_error("Network error"),
    Converter_error("Converter error"),
    SEARCHED_KEYWORD("SEARCHED_KEYWORD"),
    No_saved_news("No saved news"),
    Data_is_not_available("Data is not available"),
    Internet_is_not_available("Internet is not available!"),
    NEWS_TYPE("NEWS_TYPE");

    private final String name;

    private GenericStrings(String str) {
        name = str;
    }

    public String toString() {
        return this.name;
    }
}
