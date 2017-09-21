package ru.pap.newsapi.data.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import ru.pap.newsapi.data.model.Article;
import ru.pap.newsapi.data.model.Source;

/**
 * Created by alex on 05.08.2017.
 */

@Database(entities = {Article.class, Source.class}, version = 1)
public abstract class NewsDB extends RoomDatabase {

    private static NewsDB sInstance;

    public static NewsDB getInstance(Context context) {

        if (sInstance == null) {
            synchronized (NewsDB.class) {
                if (sInstance == null) {
                    sInstance = Room.databaseBuilder(context.getApplicationContext(),
                            NewsDB.class, "news.db")
                            .build();
                }
            }
        }
        return sInstance;

    }

    public static void init(Context context) {
        getInstance(context);
    }


    public abstract ArticlesDao articteDao();

    public abstract SourcesDao sourceDao();
}
