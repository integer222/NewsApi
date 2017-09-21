package ru.pap.newsapi.data.api;

import java.util.List;

import ru.pap.newsapi.data.model.Source;

/**
 * Created by alex on 06.08.2017.
 */

public class SourcesResponse extends ApiResponse {

    private List<Source> sources;

    public SourcesResponse(Status status, List<Source> sources) {
        super(status);
        this.sources = sources;
    }

    public List<Source> getSources() {
        return sources;
    }
}
