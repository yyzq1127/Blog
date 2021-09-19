package com.yk.exceptions;

/**
 * 自定义异常
 * @author yk
 * @version 1.0
 * @date 2021/4/2 19:23
 */
public class MyException extends RuntimeException{

    private Integer code = 300;
    private String msg = "未知异常!";


    public MyException(String msg) {
        this.msg = msg;
    }

    public MyException(String message, Integer code) {
        super(message);
        this.code = code;
    }

    public MyException(String message, Integer code, String msg) {
        super(message);
        this.code = code;
        this.msg = msg;
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
}
