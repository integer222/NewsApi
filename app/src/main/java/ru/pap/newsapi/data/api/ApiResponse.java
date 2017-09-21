package ru.pap.newsapi.data.api;

/**
 * Created by alex on 05.08.2017.
 */

public abstract class ApiResponse {

    private Status status;

    public ApiResponse(Status status) {
        this.status = status;
    }

    public Status getStatus() {
        return status;
    }
}
