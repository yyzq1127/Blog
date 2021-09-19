package com.yk.utils;

import com.yk.exceptions.MyException;

/**
 * @author yk
 * @version 1.0
 * @date 2021/4/2 19:19
 */
public class AssertUtil {

    public static void isTrue(boolean flag,String msg){

        if(flag){
            throw new MyException(msg);
        }
    }

}
