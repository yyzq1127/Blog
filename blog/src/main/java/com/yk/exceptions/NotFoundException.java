package com.yk.exceptions;

/**
 * 404异常
 * @author yk
 * @version 1.0
 * @date 2021/3/31 21:01
 */
public class NotFoundException extends RuntimeException{

    public NotFoundException() {
    }

    public NotFoundException(String message) {
        super(message);
    }
}
