package com.normal.zbase.http.bean;


public class LoginResultBean extends BaseResponse {
    /**
     * code : 0000
     * token : fad38b28b50e41019cbaaf82c2b3eaa6
     * userId : 180402140952277UA001792
     * success : true
     */
    private String token;
    private String userId;
    private boolean success;
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


    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }


    public boolean isSuccess() {
        return success;
    }
}
