package ru.pap.newsapi;

import android.content.Context;

import ru.pap.newsapi.data.datasource.articles.ArticleRepository;
import ru.pap.newsapi.data.datasource.articles.local.ArticlesLocalDataSource;
import ru.pap.newsapi.data.datasource.articles.remote.ArticlesRemoteDataSource;
import ru.pap.newsapi.data.datasource.sources.SourcesRepository;
import ru.pap.newsapi.data.datasource.sources.local.SourcesLocalDataSource;
import ru.pap.newsapi.data.datasource.sources.remote.SourcesRemoteDataSource;
import ru.pap.newsapi.data.db.NewsDB;

/**
 * Created by alex on 08.08.2017.
 */

public class Injection {

    public static SourcesRepository getSourcesRepository(Context context){
        return SourcesRepository.getInstance(
                SourcesLocalDataSource.getInstance(NewsDB.getInstance(context).sourceDao()),
                SourcesRemoteDataSource.getInstance(context));
    }

    public static ArticleRepository getArticleRepository(Context context){
        return ArticleRepository.getInstance(
                ArticlesLocalDataSource.getInstance(NewsDB.getInstance(context).articteDao()),
                ArticlesRemoteDataSource.getInstance(context)
        );
    }

}
