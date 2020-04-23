package manisha.khatri.newsanytime.util._enum;

public enum NewsType {
    NATIONAL("NATIONAL"),
    INTERNATIONAL("INTERNATIONAL");

    private final String name;

    private NewsType(String str) {
        name = str;
    }

    public String toString() {
        return this.name;
    }
}
