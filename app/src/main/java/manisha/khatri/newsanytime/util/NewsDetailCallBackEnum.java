package manisha.khatri.newsanytime.util;

public enum NewsDetailCallBackEnum {

    NEWS_SAVED_IN_DB ("NEWS SAVED IN DB"),
    NEWS_DELETED_FROM_DB ("NEWS DELETED FROM DB");

    private final String name;

    private NewsDetailCallBackEnum(String str) {
        name = str;
    }

    public boolean equalsName(String otherName) {
        // (otherName == null) check is not needed because name.equals(null) returns false
        return name.equals(otherName);
    }

    public String toString() {
        return this.name;
    }
}
