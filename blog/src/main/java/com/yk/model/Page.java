package com.yk.model;

/**
 * @author yk
 * @version 1.0
 * @date 2021/4/4 16:23
 */
public class Page {

    private static Integer pageNumber = 1;
    private static Integer pageSize = 10;

    public static Integer getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        Page.pageNumber = pageNumber;
    }

    public static Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        Page.pageSize = pageSize;
    }
}
