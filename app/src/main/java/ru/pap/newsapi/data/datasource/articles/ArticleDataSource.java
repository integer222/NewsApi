package ru.pap.newsapi.data.datasource.articles;

import java.util.List;

import io.reactivex.Observable;
import ru.pap.newsapi.data.model.Article;

/**
 * Created by alex on 05.08.2017.
 */

public interface ArticleDataSource {

    Observable<List<Article>> getArticles(String sourceId, String sortBy);
    Observable<Article> getArticle(int articleId);
    void saveArticles(List<Article> articles);
    void saveArticle(Article article);

    int deleteArticlesToSource(String sourceId);
    int deleteArticlesToSource(String sourceId, String sortBy);

    void clear();




}
