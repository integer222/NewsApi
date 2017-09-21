package ru.pap.newsapi.data.api;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import ru.pap.newsapi.data.model.Article;

/**
 * Created by alex on 06.08.2017.
 */

public class ArticlesResponse extends ApiResponse {

    @SerializedName("source")
    private String sourceId;
    private String sortBy;

    private List<Article> articles;

    public ArticlesResponse(Status status, String sourceId, String sortBy, List<Article> articles) {
        super(status);
        this.sourceId = sourceId;
        this.sortBy = sortBy;
        this.articles = articles;
    }

    public String getSourceId() {
        return sourceId;
    }

    public String getSortBy() {
        return sortBy;
    }

    public List<Article> getArticles() {
        return articles;
    }
}
