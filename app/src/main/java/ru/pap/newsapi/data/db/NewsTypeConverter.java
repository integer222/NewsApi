package ru.pap.newsapi.data.db;

import android.arch.persistence.room.TypeConverter;
import android.arch.persistence.room.util.StringUtil;
import android.text.TextUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Created by alex on 05.08.2017.
 */

public class NewsTypeConverter {
    @TypeConverter
    public static List<String> stringToStringList(String data) {
        if (data == null) {
            return Collections.emptyList();
        }
        return Arrays.asList(data.split(","));
    }

    @TypeConverter
    public static String intListToString(List<String> values) {
        if (values == null || values.isEmpty()) {
            return "";
        }
        return TextUtils.join(",", values);
    }

    @TypeConverter
    public static Date fromTimestamp(Long value) {
        return value == null ? null : new Date(value);
    }

    @TypeConverter
    public static Long dateToTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }

}
