package manisha.khatri.newsanytime.util._enum;

public enum Country {
    INDIA("in"),
    UNITED_ARAB_EMIRATES("ae"),
    AUSTRIA("at"),
    AUSTRALIA("au"),
    BELGIUM("be"),
    BULGARIA("bg"),
    BRAZIL("br"),
    ALL("all");

    private final String name;

    Country(String str) {
        name = str;
    }

    public String toString() {
        return this.name;
    }

}
