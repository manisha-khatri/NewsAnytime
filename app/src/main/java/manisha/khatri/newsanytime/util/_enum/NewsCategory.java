package manisha.khatri.newsanytime.util._enum;

public enum NewsCategory {
    SPORTS("sports"),
    BUSINESS("business"),
    ENTERTAINMENT("entertainment");

    private final String name;

    private NewsCategory(String str) {
        name = str;
    }

    public String toString() {
        return this.name;
    }
}
