package com.normal.zbase.http.bean;


public class LoginResultBean {
    /**
     * code : 0000
     * token : fad38b28b50e41019cbaaf82c2b3eaa6
     * userId : 180402140952277UA001792
     * success : true
     */

    private String code;
    private String token;
    private String userId;
    private boolean success;
    private String message;
    private String channel;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }


    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public boolean isSuccess() {
        return success;
    }
}
