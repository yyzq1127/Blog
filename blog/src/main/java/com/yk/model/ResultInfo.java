package com.yk.model;

import org.springframework.stereotype.Component;

/**
 * 封装结果
 * @author yk
 * @version 1.0
 * @date 2021/3/31 21:24
 */

@Component("ResultInfo")
public class ResultInfo {

    private Integer code = 200;
    private String msg = "success!";
    private Object data;


    public static ResultInfo createResult(String msg){
        ResultInfo resultInfo = new ResultInfo();
        resultInfo.setMsg(msg);
        return resultInfo;
    }
    public static ResultInfo createResult(String msg,Object data){
        ResultInfo resultInfo = new ResultInfo();
        resultInfo.setMsg(msg);
        resultInfo.setData(data);
        return resultInfo;
    }
    public ResultInfo() {
    }

    public ResultInfo(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public ResultInfo(Integer code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public void setAll(Integer code,String msg,Object data){
        this.code = code;
        this.msg =  msg;
        this.data = data;
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

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ResultInfo{" +
                "code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
