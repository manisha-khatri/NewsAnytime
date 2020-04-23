package manisha.khatri.newsanytime.util._enum;

public enum NewsIntent {
    HEADLINE("HEADLINE"),
    IMAGE("IMAGE"),
    DESCRIPTION("DESCRIPTION"),
    CONTENT("CONTENT"),
    PUBLISHEDDATE("PUBLISHEDDATE");


    private final String name;

    private NewsIntent(String str) {
        name = str;
    }

    public boolean equalsName(String otherName) {
        return name.equals(otherName);
    }

    public String toString() {
        return this.name;
    }
}
