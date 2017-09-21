package ru.pap.newsapi.data.api;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.text.TextUtils;


import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.pap.newsapi.R;
import ru.pap.newsapi.data.db.NewsDB;

/**
 * Created by alex on 05.08.2017.
 */

public class NewsApi {

    public static final String API_KEY_REQUIRED = "api_key_required";
    public static final String API_KEY_REQUIRED_QUERY = "api_key_required=true";
    private static final int CONNECT_TIMEOUT = 15;
    private static final int WRITE_TIMEOUT = 60;
    private static final int READ_TIMEOUT = 60;

    private static NewsApi sInstance;

    private OkHttpClient mOkHttpClient;
    private Retrofit mRetrofit;
    private String mApiKey;

    public static NewsApi getInstance(Context context) {

        if (sInstance == null) {
            synchronized (NewsApi.class) {
                if (sInstance == null) {
                    sInstance = new NewsApi(context.getApplicationContext());
                }
            }
        }
        return sInstance;
    }

    public static void init(Context context) {
        getInstance(context);
    }

    public NewsApi(Context context) {
        mApiKey = context.getString(R.string.api_key);
        mOkHttpClient = new OkHttpClient.Builder()
                .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
                .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
                .addInterceptor(mApiKeyInterceptor)
                .addInterceptor(new StethoInterceptor())
                .build();
        mRetrofit = new Retrofit.Builder()
                .baseUrl(context.getString(R.string.api_uri))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(mOkHttpClient)
                .build();
    }


    private Interceptor mApiKeyInterceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request original = chain.request();
            Request request = original;
            HttpUrl originalHttpUrl = original.url();

            String queryParamValue = originalHttpUrl.queryParameter(API_KEY_REQUIRED);
            if (Boolean.parseBoolean(queryParamValue)) {
                HttpUrl url = originalHttpUrl.newBuilder()
                        .removeAllQueryParameters(API_KEY_REQUIRED)
                        .addQueryParameter("apiKey", mApiKey)
                        .build();

                Request.Builder requestBuilder = original.newBuilder()
                        .url(url);
                request = requestBuilder.build();
            }
            return chain.proceed(request);
        }
    };

    public static NewsService getNewsService(Context context){
        return getInstance(context).mRetrofit.create(NewsService.class);
    }


}
