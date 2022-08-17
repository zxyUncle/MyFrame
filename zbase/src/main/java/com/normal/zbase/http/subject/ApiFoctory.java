package com.normal.zbase.http.subject;

import com.normal.zbase.http.build.PostHttpRequestServiceImp;

public class ApiFoctory {
    public static PostHttpRequestServiceImp post() {
        return new PostHttpRequestServiceImp();
    }
}
