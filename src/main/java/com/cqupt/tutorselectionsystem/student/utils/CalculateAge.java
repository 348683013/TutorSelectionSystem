package com.cqupt.tutorselectionsystem.student.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CalculateAge {
    public static Integer getAge(Date date) {
        //得到给定日期的年份
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        String targetYear = sdf.format(date);
        //得到现在的年份
        Date nowDate = new Date();
        String currentYear = sdf.format(nowDate);
        return Integer.parseInt(currentYear) - Integer.parseInt(targetYear);
    }

    public static String getCurrentYear() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        Date date = new Date();
        return sdf.format(date);
    }
}
