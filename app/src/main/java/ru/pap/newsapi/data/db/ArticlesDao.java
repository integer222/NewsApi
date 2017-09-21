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
import ru.pap.newsapi.data.model.Article;

/**
 * Created by alex on 05.08.2017.
 */
@Dao
public interface ArticlesDao {
    @Query("SELECT * FROM articles ORDER BY sourceId ASC")
    public Flowable<List<Article>> getArticles();

    @Query("SELECT * FROM articles WHERE sourceId = :sourceId and sortBy = :sortBy")
    public Flowable<List<Article>> getArticles(String sourceId, String sortBy);

    @Query("SELECT * FROM articles WHERE sourceId = :sourceId and sortBy = :sortBy")
    public List<Article> getArticlesList(String sourceId, String sortBy);

    @Query("SELECT * FROM sources WHERE id = :articleId")
    public Flowable<Article> getArticle(int articleId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertArticles(List<Article> articles);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    public int updateArticles(Article... articles);

    @Query("DELETE FROM articles WHERE sourceId = :sourceId")
    public int deleteArticlesToSource(String sourceId);

    @Query("DELETE FROM articles WHERE sourceId = :sourceId and sortBy = :sortBy")
    public int deleteArticlesToSource(String sourceId, String sortBy);

    @Delete
    public int deleteArticles(Article... articles);

    @Query("DELETE FROM articles")
    public int clear();
}
