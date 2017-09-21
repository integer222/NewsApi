package ru.pap.newsapi.data.datasource.articles;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import ru.pap.newsapi.data.model.Article;
import ru.pap.newsapi.utils.CollectionUtils;

/**
 * Created by alex on 05.08.2017.
 */

public class ArticleRepository {

    private static ArticleRepository sInstance;

    private ArticleDataSource mLocalDataSource;
    private ArticleDataSource mRemoteDataSource;

    private boolean mCacheIsDirty;

    public ArticleRepository(ArticleDataSource localDataSource, ArticleDataSource remoteDataSource) {
        mLocalDataSource = localDataSource;
        mRemoteDataSource = remoteDataSource;
    }

    public static ArticleRepository getInstance(ArticleDataSource localDataSource, ArticleDataSource remoteDataSources) {
        if (sInstance == null) {
            sInstance = new ArticleRepository(localDataSource, remoteDataSources);
        }
        return sInstance;
    }


    public Observable<List<Article>> getArticles(String sourceId, String sortBy, boolean forceUpdate) {
        if (forceUpdate || mCacheIsDirty){
            mCacheIsDirty = true;
            return getAndSaveRemoteArticles(sourceId, sortBy);
        }
        return getLocalArticles(sourceId, sortBy);
    }

    private Observable<List<Article>> getLocalArticles(final String sourceId, final String sortBy){
        return mLocalDataSource.getArticles(sourceId, sortBy).flatMap(new Function<List<Article>, ObservableSource<List<Article>>>() {
            @Override
            public ObservableSource<List<Article>> apply(List<Article> articles) throws Exception {
                if (CollectionUtils.isEmpty(articles)){
                    return getAndSaveRemoteArticles(sourceId, sortBy);
                }
                return Observable.just(articles);
            }
        });
    }

    private Observable<List<Article>> getAndSaveRemoteArticles(final String sourceId, final String sortBy){
        return mRemoteDataSource.getArticles(sourceId, sortBy).doOnNext(new Consumer<List<Article>>() {
            @Override
            public void accept(List<Article> articles) throws Exception {
                mLocalDataSource.deleteArticlesToSource(sourceId, sortBy);
                mLocalDataSource.saveArticles(articles);
                mCacheIsDirty = false;
            }
        });
    }
}
