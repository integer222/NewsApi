package ru.pap.newsapi.data.datasource.sources.local;

import java.util.Collections;
import java.util.List;

import io.reactivex.Observable;
import ru.pap.newsapi.data.datasource.sources.SourcesDataSource;
import ru.pap.newsapi.data.db.SourcesDao;
import ru.pap.newsapi.data.model.Source;

/**
 * Created by alex on 15.08.2017.
 */

public class SourcesLocalDataSource implements SourcesDataSource{

    private static SourcesLocalDataSource sInstance;
    private SourcesDao mSourcesDao;

    public SourcesLocalDataSource(SourcesDao sourcesDao) {
        mSourcesDao = sourcesDao;
    }

    public static SourcesLocalDataSource getInstance(SourcesDao sourcesDao){
        if (sInstance == null){
            sInstance = new SourcesLocalDataSource(sourcesDao);
        }
        return sInstance;
    }

    @Override
    public Observable<List<Source>> getSources() {
        return mSourcesDao.getSources().toObservable();
    }

    @Override
    public Observable<Source> getSource(String sourceId) {
        return mSourcesDao.getSource(sourceId).toObservable();
    }

    @Override
    public void saveSources(List<Source> sources) {
        mSourcesDao.insertSources(sources);
    }

    @Override
    public void saveSource(Source source) {
        mSourcesDao.insertSources(Collections.singletonList(source));
    }

    @Override
    public void clear() {
        mSourcesDao.clear();
    }
}
