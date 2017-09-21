package ru.pap.newsapi.data.datasource.sources.remote;

import android.content.Context;
import android.text.TextUtils;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.functions.Function;
import ru.pap.newsapi.data.api.NewsApi;
import ru.pap.newsapi.data.api.SourcesResponse;
import ru.pap.newsapi.data.datasource.sources.SourcesDataSource;
import ru.pap.newsapi.data.model.Source;

/**
 * Created by alex on 14.08.2017.
 */

public class SourcesRemoteDataSource implements SourcesDataSource {

    private static SourcesRemoteDataSource sInstance;

    private Context mContext;

    private SourcesRemoteDataSource(Context context) {
        mContext = context.getApplicationContext();
    }

    public static SourcesRemoteDataSource getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new SourcesRemoteDataSource(context);
        }
        return sInstance;
    }

    @Override
    public Observable<List<Source>> getSources() {
        return NewsApi.getNewsService(mContext).getSources()
                .map(new Function<SourcesResponse, List<Source>>() {
                    @Override
                    public List<Source> apply(SourcesResponse sourcesResponse) throws Exception {
                        return sourcesResponse.getSources();
                    }
                });
    }

    @Override
    public Observable<Source> getSource(final String sourceId) {
        if (TextUtils.isEmpty(sourceId)){
            return Observable.just(null);
        }
        return getSources().map(new Function<List<Source>, Source>() {
            @Override
            public Source apply(List<Source> sources) throws Exception {
                for (Source source : sources){
                    if (sourceId.equals(source.getId())){
                        return source;
                    }
                }
                return null;
            }
        });
    }

    @Override
    public void saveSources(List<Source> sources) {
        throw new UnsupportedOperationException("No support");
    }

    @Override
    public void saveSource(Source source) {
        throw new UnsupportedOperationException("No support");
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException("No support");
    }
}
