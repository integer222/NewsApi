package ru.pap.newsapi.data.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

import java.util.Date;

import ru.pap.newsapi.data.db.NewsTypeConverter;

/**
 * Created by alex on 05.08.2017.
 */
@Entity(tableName = "articles",
        foreignKeys = @ForeignKey(entity = Source.class,
        parentColumns = "id",
        childColumns = "sourceId",
        onDelete = ForeignKey.CASCADE,
        onUpdate = ForeignKey.CASCADE))
@TypeConverters(NewsTypeConverter.class)
public class Article extends BaseInfo {

    @PrimaryKey(autoGenerate = true)
    private int id = -1;
    private String sourceId;
    private String sortBy;
    private String author;
    private String title;
    private String description;
    private String urlToImage;
    private Date publishedAt;
    private String url;

    public Article(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrlToImage() {
        return urlToImage;
    }

    public void setUrlToImage(String urlToImage) {
        this.urlToImage = urlToImage;
    }

    public Date getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(Date publishedAt) {
        this.publishedAt = publishedAt;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
