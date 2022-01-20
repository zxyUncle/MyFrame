package com.normal.zbase.http.bean;

public class BaseResult<T> extends BaseBean {

    private T data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}