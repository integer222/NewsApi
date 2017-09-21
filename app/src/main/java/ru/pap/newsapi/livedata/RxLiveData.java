package ru.pap.newsapi.livedata;

import android.arch.lifecycle.LiveData;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.concurrent.atomic.AtomicBoolean;

import io.reactivex.disposables.Disposable;

/**
 * Created by alex on 20.08.2017.
 */

public abstract class RxLiveData<T> extends LiveData<T> {

    private boolean mAutoStop;
    private AtomicBoolean mStartBoolean = new AtomicBoolean(false);

    private Disposable mDisposable;

    public RxLiveData(boolean autoStop) {
        mAutoStop = autoStop;
    }

    @Override
    protected void onActive() {
        super.onActive();
        if (!mStartBoolean.compareAndSet(false, true)){
            return;
        }
        mDisposable = getDisposable();
    }


    @Override
    protected void onInactive() {
        super.onInactive();
        if (!mAutoStop){
            return;
        }
        stop();
    }

    @Override
    public void setValue(T value) {
        super.setValue(value);
    }

    public void stop(){
        if (mDisposable !=null && !mDisposable.isDisposed()) {
            mDisposable.dispose();
            mDisposable = null;
        }
        mStartBoolean.set(false);
    }

    protected abstract Disposable getDisposable();
}
