package com.uds.akhbar.utils;

import android.text.Html;
import android.text.TextUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class BindingUtils {
    public static String formatDateForDetails(String publishedTime) {
        String newDate;
        SimpleDateFormat dateFormat = new SimpleDateFormat("E, d MMM yyyy", Locale.getDefault());
        try {
            Date date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").parse(publishedTime);
            newDate = dateFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            newDate = publishedTime;
        }

        return newDate;
    }


    public static String formatDescription(String content, String description) {
        return description + "\n" + (TextUtils.isEmpty(content) ? "" : Html.fromHtml(content));
    }
}
