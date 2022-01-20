package com.normal.zbase.http.bean;

import java.util.List;

public class BaseListResult<T> extends BaseBean {

    private List<T> data;

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}