package manisha.khatri.newsanytime.model;

public class News {
    private int totalResults;

    private Article[] articles;

    private String status;

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

    public Article[] getArticles() {
        return articles;
    }

    public void setArticles(Article[] articles) {
        this.articles = articles;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "ClassPojo [totalResults = " + totalResults + ", articles = " + articles + ", status = " + status + "]";
    }
}
