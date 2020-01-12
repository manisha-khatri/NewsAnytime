
package manisha.khatri.newsanytime.model;

import android.support.annotation.Nullable;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class News implements Serializable
{
    @SerializedName("status")
    @Expose
    @Nullable
    private String status;

    @SerializedName("totalResults")
    @Expose
    @Nullable
    private long totalResults;

    @SerializedName("articles")
    @Expose
    @Nullable
    private List<Article> articles = null;

    private final static long serialVersionUID = -113585310620385215L;

    public News(String status, long totalResults, List<Article> articles) {
        super();
        this.status = status;
        this.totalResults = totalResults;
        this.articles = articles;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public long getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(long totalResults) {
        this.totalResults = totalResults;
    }

    public List<Article> getArticles() {
        return articles;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }

}
