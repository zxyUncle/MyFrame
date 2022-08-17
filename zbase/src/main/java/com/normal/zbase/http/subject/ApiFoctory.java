package com.normal.zbase.http.subject;

import com.normal.zbase.http.subject.service.imp.PostHttpServiceImp;

public class ApiFoctory {


    public static PostHttpServiceImp post(String path) {
        return new PostHttpServiceImp(path);
    }
}
