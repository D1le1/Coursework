package com.example.weatherapplication.functionality;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class DateFunctions {
    public static String getDate()
    {
        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        return getMonth(date) + " " + cal.get(Calendar.DAY_OF_MONTH) + " " + getDay(date);
    }

    private static String getDay(Date date)
    {
        Map<Integer, String> day = new HashMap<Integer, String>();
        day.put(1, "Mon");
        day.put(2, "Tue");
        day.put(3, "Wed");
        day.put(4, "Thu");
        day.put(5, "Fri");
        day.put(6, "Sat");
        day.put(0, "Sun");
        return day.get(date.getDay());
    }


    private static String getMonth(Date date)
    {
        Map<Integer, String> month = new HashMap<Integer, String>();
        month.put(0, "Jan");
        month.put(1, "Feb");
        month.put(2, "Mar");
        month.put(3, "Apr");
        month.put(4, "May");
        month.put(5, "Jun");
        month.put(6, "Jul");
        month.put(7, "Aug");
        month.put(8, "Sep");
        month.put(9, "Oct");
        month.put(10, "Nov");
        month.put(11, "Dec");
        return month.get(date.getMonth());
    }

}
