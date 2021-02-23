package com.uds.akhbar.utils;

import androidx.databinding.BindingAdapter;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class BindingUtils {
    public static String formatDateForDetails(Timestamp date) {
        SimpleDateFormat format = new SimpleDateFormat("dd MMM yyyy | hh:mm aaa", Locale.getDefault());
        return format.format(new Date(date.getTime()));
    }
}
