package ru.pap.newsapi;

import android.app.Application;

import com.facebook.stetho.Stetho;

import ru.pap.newsapi.data.db.NewsDB;

/**
 * Created by alex on 05.08.2017.
 */

public class NewsApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        NewsDB.init(getApplicationContext());
        Stetho.initializeWithDefaults(this);

    }
}
