package com.normal.zbase.http.bean;


import com.normal.zbase.http.client.ApiConfig;

public class BaseBean {

    protected String code;
    protected String message;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}