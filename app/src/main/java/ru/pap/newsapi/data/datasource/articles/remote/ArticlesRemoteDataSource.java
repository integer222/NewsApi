package ru.pap.newsapi.data.datasource.articles.remote;

import android.content.Context;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.functions.Function;
import ru.pap.newsapi.data.api.ArticlesResponse;
import ru.pap.newsapi.data.api.NewsApi;
import ru.pap.newsapi.data.datasource.articles.ArticleDataSource;
import ru.pap.newsapi.data.model.Article;

/**
 * Created by alex on 05.08.2017.
 */

public class ArticlesRemoteDataSource implements ArticleDataSource{
    private static ArticlesRemoteDataSource sInstance;

    private Context mContext;

    public ArticlesRemoteDataSource(Context context) {
        mContext = context;
    }

    public static ArticlesRemoteDataSource getInstance(Context context){
        if (sInstance == null){
            sInstance = new ArticlesRemoteDataSource(context.getApplicationContext());
        }
        return sInstance;
    }


    @Override
    public Observable<List<Article>> getArticles(String sourceId, String sortBy) {
        return NewsApi.getNewsService(mContext).getArticles(sourceId, sortBy)
                .map(new Function<ArticlesResponse, List<Article>>() {
                    @Override
                    public List<Article> apply(ArticlesResponse articlesResponse) throws Exception {
                        String sourceId = articlesResponse.getSourceId();
                        String sortBy = articlesResponse.getSortBy();
                        for (Article article : articlesResponse.getArticles()){
                            article.setSourceId(sourceId);
                            article.setSortBy(sortBy);
                        }
                        return articlesResponse.getArticles();
                    }
                });
    }

    @Override
    public Observable<Article> getArticle(int articleId) {
        throw new UnsupportedOperationException("No support");
    }

    @Override
    public void saveArticles(List<Article> articles) {
        throw new UnsupportedOperationException("No support");
    }

    @Override
    public void saveArticle(Article article) {
        throw new UnsupportedOperationException("No support");
    }

    @Override
    public int deleteArticlesToSource(String sourceId) {
        throw new UnsupportedOperationException("No support");
    }

    @Override
    public int deleteArticlesToSource(String sourceId, String sortBy) {
        throw new UnsupportedOperationException("No support");
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException("No support");
    }
}
