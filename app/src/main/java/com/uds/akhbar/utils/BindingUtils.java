package com.uds.akhbar.utils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

public class BindingUtils {
    public static String formatDateForDetails(String publishedTime) {
//        LocalDate localDateTime=LocalDate.parse(publishedTime);
//        DateTimeFormatter  dateFormat=DateTimeFormatter.ofPattern("dd MMM yyyy | hh:mm aaa");
        return publishedTime;
    }
}
