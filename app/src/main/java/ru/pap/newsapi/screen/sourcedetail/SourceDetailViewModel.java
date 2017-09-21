package ru.pap.newsapi.screen.sourcedetail;

import android.arch.core.util.Function;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.content.Context;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import ru.pap.newsapi.Injection;
import ru.pap.newsapi.data.datasource.sources.SourcesRepository;
import ru.pap.newsapi.data.model.Resource;
import ru.pap.newsapi.data.model.Source;
import ru.pap.newsapi.livedata.RxLiveData;
import ru.pap.newsapi.screen.main.SourcesViewModel;

/**
 * Created by alex on 23.08.2017.
 */

public class SourceDetailViewModel extends ViewModel {

    private SourcesRepository mRepository;

    private LiveData<Resource<Source>> mSource;
    private MutableLiveData<String> mRefreshData;

    public SourceDetailViewModel(SourcesRepository repository) {
        mRepository = repository;
        mRefreshData = new MutableLiveData<>();
        mSource = Transformations.switchMap(mRefreshData, new Function<String, LiveData<Resource<Source>>>() {
            @Override
            public LiveData<Resource<Source>> apply(final String sourceId) {
                return getSource(sourceId);
            }
        });
    }

    private LiveData<Resource<Source>> getSource(final String sourceId) {
        return new RxLiveData<Resource<Source>>(false) {
            @Override
            protected Disposable getDisposable() {
                setValue(Resource.<Source>loading());
                return mRepository.getSource(sourceId)
                        .subscribeOn(Schedulers.computation())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<Source>() {
                            @Override
                            public void accept(Source sources) throws Exception {
                                setValue(Resource.success(sources));
                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                setValue(Resource.<Source>error(new Exception(throwable)));
                            }
                        });
            }
        };
    }

    public void loadSource(String sourceId){
        Resource<Source> resource = mSource.getValue();
        if (resource != null && resource.isLoading()) {
            return;
        }
        mRefreshData.setValue(sourceId);
    }

    public LiveData<Resource<Source>> getSource() {
        return mSource;
    }

    public static class Factory implements ViewModelProvider.Factory {

        private Context mContext;

        public Factory(Context context) {
            mContext = context.getApplicationContext();
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            if (modelClass != SourceDetailViewModel.class) {
                throw new UnsupportedOperationException(modelClass + " != " + SourcesViewModel.class);
            }
            //noinspection unchecked
            return (T) new SourceDetailViewModel(Injection.getSourcesRepository(mContext));
        }
    }
}
