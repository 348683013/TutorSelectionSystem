package com.cqupt.tutorselectionsystem.student.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtil {

    public static String formatDateByPattern(Date date, String dateFormat){
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        String formatTimeStr = null;
        if (date != null) {
            formatTimeStr = sdf.format(date);
        }
        return formatTimeStr;
    }

    /**
     * Date转为cron表达式
     * @param date
     * @return
     */
    public static String dateToCron(Date date){
        String dateFormat="ss mm HH dd MM ? yyyy";
        String cron = formatDateByPattern(date, dateFormat);
        cron = cron.substring(0, cron.length() - 5);
        return cron;
    }

    public static String stdTimeToCron(String stdTime){
        SimpleDateFormat format =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = format.parse(stdTime);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        String cron = dateToCron(date);
        return cron;
    }
}


