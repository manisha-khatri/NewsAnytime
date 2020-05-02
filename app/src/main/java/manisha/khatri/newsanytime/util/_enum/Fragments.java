package manisha.khatri.newsanytime.util._enum;

public enum Fragments {

    NATIONAL("National"),
    TOP_STORIES("Top Stories"),
    INTERNATIONAL("International");

    private final String value;

    Fragments(String str) {
        value = str;
    }

    public String toString() {
        return this.value;
    }
}
