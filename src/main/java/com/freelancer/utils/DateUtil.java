package com.freelancer.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
    public static void main(String[] args) {
        System.out.println(setDateLong(-10));
    }
    public static Long getTimeLongCurrent(){
        return System.currentTimeMillis();
    }
    public static String getDateFromLong(long date){
       return new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date(date));
    }
    public static long setDateLong(int day){
        Calendar c1 = Calendar.getInstance();
        c1.add(Calendar.DATE, day);
        return  c1.getTimeInMillis();
    }
}
