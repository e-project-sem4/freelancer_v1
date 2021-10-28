package com.freelancer.utils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
    public static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    public static SimpleDateFormat simple = new SimpleDateFormat("dd/MM/yyyy");
    public static SimpleDateFormat simple2 = new SimpleDateFormat("MM/dd/yyyy");
    public static SimpleDateFormat simpleEnd = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
    public static void main(String[] args) {

    }
    public static Long getTimeLongCurrent(){
        return System.currentTimeMillis();
    }
    public static String getDateFromLong(long date){
       return simpleDateFormat.format(new Date(date));
    }
    public static String getDayFromLong(long date){
        return simple.format(new Date(date));
    }
    public static long setDateLong(int day){
        Calendar c1 = Calendar.getInstance();
        c1.add(Calendar.DATE, day);
        return  c1.getTimeInMillis();
    }

    public static long getLongStartOfMonth() { // mặc định
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        // set day to minimum
        calendar.set(Calendar.DAY_OF_MONTH,
                calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTimeInMillis();
    }
    public static long setStartDayLong(String day){
        try {
            Date d = simpleEnd.parse(day);
            long milliseconds = d.getTime();
            return milliseconds;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
