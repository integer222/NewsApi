package ru.pap.newsapi.data.api;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by alex on 06.08.2017.
 */

public interface NewsService {

    @GET("sources")
    public Observable<SourcesResponse> getSources();

    @GET("articles?"+NewsApi.API_KEY_REQUIRED_QUERY)
    public Observable<ArticlesResponse> getArticles(@Query("source") String sourceId, @Query("sortBy") String sortBy);
}
