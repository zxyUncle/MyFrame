package com.normal.zbase.http.exception;


import com.normal.zbase.http.domain.ApiConfig;

public class APIException extends RuntimeException {

    protected Object code;
    private Object data;

    public APIException(Object code, String message) {
        super(message);
        this.code = code;
    }

    public APIException(Object code, String message, Object data) {
        super(message);
        this.code = code;
        this.data = data;
    }

    public Object getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public boolean isTokenInvalid() {
        return code != null && code.equals(ApiConfig.CODE_TOKEN_INVALID);
    }

    public boolean isNoNetwork() {
        return code != null && code.equals(ApiConfig.CODE_NO_NETWORK);
    }

}