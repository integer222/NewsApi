package ru.pap.newsapi.data.datasource.sources;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import ru.pap.newsapi.data.model.Source;
import ru.pap.newsapi.utils.CollectionUtils;

/**
 * Created by alex on 05.08.2017.
 */

public class SourcesRepository {

    private static SourcesRepository sInstance;

    private SourcesDataSource mLocalDataSource;
    private SourcesDataSource mRemoteDataSources;

    private boolean mCacheIsDirty;

    public SourcesRepository(SourcesDataSource localDataSource, SourcesDataSource remoteDataSources) {
        mLocalDataSource = localDataSource;
        mRemoteDataSources = remoteDataSources;
    }

    public static SourcesRepository getInstance(SourcesDataSource localDataSource, SourcesDataSource remoteDataSources) {
        if (sInstance == null) {
            sInstance = new SourcesRepository(localDataSource, remoteDataSources);
        }
        return sInstance;
    }


    public Observable<List<Source>> getSources(boolean forceUpdate) {
        if (forceUpdate || mCacheIsDirty){
            mCacheIsDirty = true;
            return getAndSaveRemoteSources();
        }
        return getLocalSources();
    }

    private Observable<List<Source>> getAndSaveRemoteSources(){
        return mRemoteDataSources.getSources().doOnNext(new Consumer<List<Source>>() {
            @Override
            public void accept(List<Source> sources) throws Exception {
                mLocalDataSource.clear();
                mLocalDataSource.saveSources(sources);
                mCacheIsDirty = false;
            }
        });
    }

    public Observable<List<Source>> getLocalSources(){
        return mLocalDataSource.getSources().flatMap(new Function<List<Source>, ObservableSource<List<Source>>>() {
            @Override
            public ObservableSource<List<Source>> apply(List<Source> sources) throws Exception {
                if (CollectionUtils.isEmpty(sources)){
                    return getAndSaveRemoteSources();
                }
                return Observable.just(sources);
            }
        });
    }

    public Observable<Source> getSource(String sourceId) {
        return mLocalDataSource.getSource(sourceId);
    }

    public void clear() {
        mLocalDataSource.clear();
    }
}
