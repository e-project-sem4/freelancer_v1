package com.freelancer.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
    public static void main(String[] args) {
        System.out.println(getTimeLongCurrent());
        System.out.println(getDateFromLong(System.currentTimeMillis()));

    }

    public static Long getTimeLongCurrent(){
        return System.currentTimeMillis();
    }
    public static String getDateFromLong(long date){
       return new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date(date));
    }
}
