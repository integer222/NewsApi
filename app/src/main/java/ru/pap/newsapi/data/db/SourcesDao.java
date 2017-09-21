package ru.pap.newsapi.data.db;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import ru.pap.newsapi.data.model.Source;

/**
 * Created by alex on 05.08.2017.
 */
@Dao
public interface SourcesDao {

    @Query("SELECT * FROM sources")
    public Flowable<List<Source>> getSources();

    @Query("SELECT * FROM sources WHERE id = :id")
    public Flowable<Source> getSource(String id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertSources(List<Source> sources);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    public int updateSources(Source... sources);

    @Query("UPDATE sources SET enabled = :enabled WHERE id = :sourceId")
    public int updateEnabled(String sourceId, boolean enabled);

    @Delete
    public int deleteSources(Source... sources);

    @Query("DELETE FROM sources")
    public int clear();

}
