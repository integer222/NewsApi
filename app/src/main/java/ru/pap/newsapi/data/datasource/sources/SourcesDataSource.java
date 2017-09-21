package ru.pap.newsapi.data.datasource.sources;

import java.util.List;

import io.reactivex.Observable;
import ru.pap.newsapi.data.model.Source;

/**
 * Created by alex on 06.08.2017.
 */

public interface SourcesDataSource {

    Observable<List<Source>> getSources();

    Observable<Source> getSource(String sourceId);

    void saveSources(List<Source> sources);

    void saveSource(Source source);

    void clear();

}
