package com.cqupt.tutorselectionsystem.student.utils;

import org.junit.jupiter.api.Test;

import java.util.Date;

public class TimeUtilTest {
    @Test
    public void fun1() {
        Date date = new Date();
        String s = TimeUtil.dateToCron(date);
        System.out.println(s);

    }
}
