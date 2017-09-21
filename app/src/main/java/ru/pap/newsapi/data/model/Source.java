package ru.pap.newsapi.data.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

import java.util.List;

import ru.pap.newsapi.data.db.NewsTypeConverter;

/**
 * Created by alex on 05.08.2017.
 */

@Entity(tableName = "sources")
@TypeConverters(NewsTypeConverter.class)
public class Source extends BaseInfo {

    @PrimaryKey
    private String id;

    private String name;
    private String description;

    private String url;
    private String category;
    private String language;

    private String country;

    private List<String> sortBysAvailable;

    private boolean enabled = true;

    public Source() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public List<String> getSortBysAvailable() {
        return sortBysAvailable;
    }

    public void setSortBysAvailable(List<String> sortBysAvailable) {
        this.sortBysAvailable = sortBysAvailable;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
