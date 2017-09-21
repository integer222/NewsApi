package ru.pap.newsapi.screen.main;

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

/**
 * Created by alex on 08.08.2017.
 */

public class SourcesViewModel extends ViewModel {

    private SourcesRepository mRepository;

    private LiveData<Resource<List<Source>>> mData;
    private MutableLiveData<Boolean> mRefreshData;

    public SourcesViewModel(SourcesRepository repository) {
        mRepository = repository;
        mRefreshData = new MutableLiveData<>();
        mData = Transformations.switchMap(mRefreshData, new Function<Boolean, LiveData<Resource<List<Source>>>>() {
            @Override
            public LiveData<Resource<List<Source>>> apply(final Boolean forceUpdate) {
                return getSources(forceUpdate);
            }
        });
    }

    public LiveData<Resource<List<Source>>> getData() {
        return mData;
    }

    public void loadSources(boolean forceUpdate) {
        Resource<List<Source>> resource = mData.getValue();
        if (resource != null && resource.isLoading()) {
            return;
        }
        mRefreshData.setValue(forceUpdate);
    }


    private LiveData<Resource<List<Source>>> getSources(final boolean forceUpdate) {
        return new RxLiveData<Resource<List<Source>>>(false) {
            @Override
            protected Disposable getDisposable() {
                setValue(Resource.<List<Source>>loading());
                return mRepository.getSources(forceUpdate)
                        .subscribeOn(Schedulers.computation())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<List<Source>>() {
                            @Override
                            public void accept(List<Source> sources) throws Exception {
                                setValue(Resource.success(sources));
                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                setValue(Resource.<List<Source>>error(new Exception(throwable)));
                            }
                        });
            }
        };
    }


    public static class Factory implements ViewModelProvider.Factory {

        private Context mContext;

        public Factory(Context context) {
            mContext = context.getApplicationContext();
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            if (modelClass != SourcesViewModel.class) {
                throw new UnsupportedOperationException(modelClass + " != " + SourcesViewModel.class);
            }
            //noinspection unchecked
            return (T) new SourcesViewModel(Injection.getSourcesRepository(mContext));
        }
    }

}
