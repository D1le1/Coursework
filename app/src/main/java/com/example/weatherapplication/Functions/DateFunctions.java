package com.example.weatherapplication.Functions;

import androidx.annotation.NonNull;

import java.util.Calendar;
import java.util.Date;

public class DateFunctions {
    public static String getDate()
    {
        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        return getMonth(date) + " " + cal.get(Calendar.DAY_OF_MONTH) + " " + getDay(date);
    }

    private static String getDay(Date date)
    {
        switch (date.getDay())
        {
            case 1:
                return "Mon";
            case 2:
                return "Tue";
            case 3:
                return "Wed";
            case 4:
                return "Thu";
            case 5:
                return "Fri";
            case 6:
                return "Sat";
            case 0:
                return "Sun";
        }
        return null;
    }

    private static String getMonth(Date date)
    {
        switch (date.getMonth())
        {
            case 0:
                return "Jan";
            case 1:
                return "Feb";
            case 2:
                return "Mar";
            case 3:
                return "Apr";
            case 4:
                return "May";
            case 5:
                return "Jun";
            case 6:
                return "Jul";
            case 7:
                return "Aug";
            case 8:
                return "Sep";
            case 9:
                return "Oct";
            case 10:
                return "Nov";
            case 11:
                return "Dec";
        }
        return null;
    }

}
