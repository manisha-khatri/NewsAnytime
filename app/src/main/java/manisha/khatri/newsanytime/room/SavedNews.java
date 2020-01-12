package manisha.khatri.newsanytime.room;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "saved_news")
public class SavedNews {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo
    int id;

    @ColumnInfo(name = "published_date")
    String publishedDate;

    @ColumnInfo(name = "image_url")
    String imageUrl;

    @ColumnInfo(name = "headline")
    String headline;

    @ColumnInfo(name = "description")
    String description;

    @ColumnInfo(name = "content")
    String content;

    public SavedNews(String headline, String imageUrl, String description, String content, String publishedDate){
        this.headline = headline;
        this.imageUrl = imageUrl;
        this.description = description;
        this.content = content;
        this.publishedDate = publishedDate;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getHeadline() {
        return headline;
    }

    public void setHeadline(String headline) {
        this.headline = headline;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(String publishedDate) {
        this.publishedDate = publishedDate;
    }


}
