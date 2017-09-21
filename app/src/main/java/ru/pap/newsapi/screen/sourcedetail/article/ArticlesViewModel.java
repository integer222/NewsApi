package ru.pap.newsapi.screen.sourcedetail.article;

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
import ru.pap.newsapi.data.datasource.articles.ArticleRepository;
import ru.pap.newsapi.data.model.Article;
import ru.pap.newsapi.data.model.Resource;
import ru.pap.newsapi.data.model.Source;
import ru.pap.newsapi.livedata.RxLiveData;
import ru.pap.newsapi.screen.main.SourcesViewModel;

/**
 * Created by alex on 23.08.2017.
 */

public class ArticlesViewModel extends ViewModel {
    private ArticleRepository mRepository;

    private LiveData<Resource<List<Article>>> mData;
    private MutableLiveData<ArticlesRequest> mRefreshData;

    public ArticlesViewModel(ArticleRepository repository) {
        mRepository = repository;
        mRefreshData = new MutableLiveData<>();
        mData = Transformations.switchMap(mRefreshData,
                new Function<ArticlesRequest, LiveData<Resource<List<Article>>>>() {
                    @Override
                    public LiveData<Resource<List<Article>>> apply(final ArticlesRequest request) {
                        return getArticles(request);
                    }
                });
    }

    public LiveData<Resource<List<Article>>> getArticles(){
        return mData;
    }

    public void loadArticles(String sourceId, String sortBy, boolean forceUpdate){
        Resource<List<Article>> resource = mData.getValue();
        if (resource != null && resource.isLoading()) {
            return;
        }
        mRefreshData.setValue(new ArticlesRequest(sourceId, sortBy, forceUpdate));
    }


    private LiveData<Resource<List<Article>>> getArticles(final ArticlesRequest request) {
        return new RxLiveData<Resource<List<Article>>>(false) {
            @Override
            protected Disposable getDisposable() {
                setValue(Resource.<List<Article>>loading());
                return mRepository
                        .getArticles(request.sourceId, request.sortBy, request.forceUpdate)
                        .subscribeOn(Schedulers.computation())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<List<Article>>() {
                            @Override
                            public void accept(List<Article> articles) throws Exception {
                                setValue(Resource.success(articles));
                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                setValue(Resource.<List<Article>>error(new Exception(throwable)));
                            }
                        });
            }
        };
    }

    public static class ArticlesRequest {
        public final String sourceId;
        public final String sortBy;
        public final boolean forceUpdate;

        public ArticlesRequest(String sourceId, String sortBy, boolean forceUpdate) {
            this.sourceId = sourceId;
            this.sortBy = sortBy;
            this.forceUpdate = forceUpdate;
        }
    }

    public static class Factory implements ViewModelProvider.Factory {

        private Context mContext;

        public Factory(Context context) {
            mContext = context.getApplicationContext();
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            if (modelClass != ArticlesViewModel.class) {
                throw new UnsupportedOperationException(modelClass + " != " + SourcesViewModel.class);
            }
            //noinspection unchecked
            return (T) new ArticlesViewModel(Injection.getArticleRepository(mContext));
        }
    }
}
