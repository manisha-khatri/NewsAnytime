package manisha.khatri.newsanytime.util._enum;

public enum APIServiceConst {
    API_KEY("53ad2000d9d243f9b1a7e270275fe3a7"),
    BASE_URL("https://newsapi.org/v2/");

    private final String name;

      APIServiceConst(String str) {
        name = str;
    }

    public String toString() {
        return this.name;
    }
}
