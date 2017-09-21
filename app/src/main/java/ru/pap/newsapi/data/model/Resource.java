package ru.pap.newsapi.data.model;

import android.support.annotation.IntDef;

import java.io.Serializable;

/**
 * Created by alex on 06.08.2017.
 */

public class Resource<T> implements Serializable {

    @IntDef({LOADING, LOADED, ERROR, CANCEL})
    public @interface State {
    }

    public static final int LOADING = 1;
    public static final int LOADED = 2;
    public static final int ERROR = 3;
    public static final int CANCEL = 4;

    @State
    private final int mState;
    private final T mData;
    private final Exception mError;
    private final int mErrorCode;


    public Resource(int state, T data, Exception error) {
       this(state, data, -1, error);
    }

    public Resource(int state, T data, int errorCode, Exception error) {
        mState = state;
        mData = data;
        mError = error;
        mErrorCode = errorCode;
    }

    public static <T> Resource<T> loading() {
        return new Resource<>(LOADING, null, null);
    }

    public static <T> Resource<T> success(T data) {
        return new Resource<>(LOADED, data, null);
    }

    public static <T> Resource<T> error(Exception error) {
        return new Resource<>(ERROR, null, error);
    }

    public static <T> Resource<T> error(int errorCode, Exception error) {
        return new Resource<>(ERROR, null, errorCode, error);
    }

    @State
    public int getState() {
        return mState;
    }

    public boolean isError() {
        return getState() == ERROR;
    }

    public boolean isLoading() {
        return getState() == LOADING;
    }

    public boolean isLoaded() {
        return getState() == LOADED;
    }

    public T getData() {
        return mData;
    }

    public Exception getError() {
        return mError;
    }

    public int getErrorCode() {
        return mErrorCode;
    }
}
