package ru.pap.newsapi.utils;

import android.text.format.DateUtils;

import java.util.Date;

/**
 * Created by alex on 22.08.2017.
 */

public class DateTimeUtils {
    public static String getTimeMark(Date date) {
        if (date == null){
            return null;
        }
        return DateUtils.getRelativeTimeSpanString(date.getTime(),
                System.currentTimeMillis(),
                0,
                DateUtils.FORMAT_ABBREV_ALL)
                .toString();
    }
}
