package com.cqupt.tutorselectionsystem.student.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhouzhongzhong on 2022/5/27
 */
public class ResultMsg {
    private Integer code;
    private String msg;
    private Map<String, Object> extend = new HashMap<>();

    //成功
    public static ResultMsg success() {
        ResultMsg result = new ResultMsg();
        result.setCode(0);
        result.setMsg("处理成功！");
        return result;
    }

    //失败
    public static ResultMsg fail() {
        ResultMsg result = new ResultMsg();
        result.setCode(1);
        result.setMsg("请求失败！");
        return result;
    }

    //向map中添加内容，this是调用这个方法的Msg对象
    public ResultMsg add(String key, Object value) {
        this.getExtend().put(key, value);
        return this;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Map<String, Object> getExtend() {
        return extend;
    }

    public void setExtend(Map<String, Object> extend) {
        this.extend = extend;
    }
}
