package ru.pap.newsapi.utils;

import java.util.Collection;

/**
 * Created by alex on 20.08.2017.
 */

public final class CollectionUtils {

    private CollectionUtils() {
    }

    public static boolean isEmpty(Collection collection) {
        return collection == null || collection.isEmpty();
    }
}
