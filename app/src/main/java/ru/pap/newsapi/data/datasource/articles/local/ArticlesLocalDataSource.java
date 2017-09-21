package ru.pap.newsapi.data.datasource.articles.local;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import ru.pap.newsapi.data.datasource.articles.ArticleDataSource;
import ru.pap.newsapi.data.db.ArticlesDao;
import ru.pap.newsapi.data.model.Article;

/**
 * Created by alex on 05.08.2017.
 */

public class ArticlesLocalDataSource implements ArticleDataSource {

    private static ArticlesLocalDataSource sInstance;
    private ArticlesDao mArticlesDao;

    public ArticlesLocalDataSource(ArticlesDao articlesDao) {
        mArticlesDao = articlesDao;
    }

    public static ArticlesLocalDataSource getInstance(ArticlesDao articlesDao) {
        if (sInstance == null) {
            sInstance = new ArticlesLocalDataSource(articlesDao);
        }
        return sInstance;
    }

    @Override
    public Observable<List<Article>> getArticles(final String sourceId, final String sortBy) {
        return Observable.fromCallable(new Callable<List<Article>>() {
            @Override
            public List<Article> call() throws Exception {
                return mArticlesDao.getArticlesList(sourceId, sortBy);
            }
        });
    }

    @Override
    public Observable<Article> getArticle(int articleId) {
        return mArticlesDao.getArticle(articleId).toObservable();
    }

    @Override
    public void saveArticles(List<Article> articles) {
        mArticlesDao.insertArticles(articles);
    }

    @Override
    public void saveArticle(Article article) {
        mArticlesDao.insertArticles(Collections.singletonList(article));
    }

    @Override
    public int deleteArticlesToSource(String sourceId) {
        return mArticlesDao.deleteArticlesToSource(sourceId);
    }

    @Override
    public int deleteArticlesToSource(String sourceId, String sortBy) {
        return mArticlesDao.deleteArticlesToSource(sourceId, sortBy);
    }

    @Override
    public void clear() {
        mArticlesDao.clear();
    }
}
