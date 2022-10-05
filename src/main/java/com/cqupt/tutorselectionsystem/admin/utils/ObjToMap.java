package com.cqupt.tutorselectionsystem.admin.utils;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class ObjToMap {
    //将对象封装成map
    public static Map<String, String> objToMap(Object object) {
        Map<String, String> map = new HashMap<>();
        //获取所有对应的属性
        Field[] fields = object.getClass().getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            String varName = fields[i].getName();//得到属性名
            varName = varName.toLowerCase();//编程小写
            try {
                //获取原来的访问控制权限
                boolean accessible = fields[i].isAccessible();
                //修改访问控制权限
                fields[i].setAccessible(true);
                //获取属性值
                Object o = fields[i].get(object);
                if (o != null) {
                    map.put(varName, o.toString());
                }
                //恢复访问控制权限
                fields[i].setAccessible(accessible);
            } catch (Exception e) {
                throw new RuntimeException("转换失败！");
            }
        }
        return map;
    }
}
