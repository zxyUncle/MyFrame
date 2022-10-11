package com.normal.zbase.http.domain;

import com.normal.zbase.http.domain.service.imp.DeleteHttpServiceImp;
import com.normal.zbase.http.domain.service.imp.GetHttpServiceImp;
import com.normal.zbase.http.domain.service.imp.PostHttpServiceImp;
import com.normal.zbase.http.domain.service.imp.PutHttpServiceImp;

public class ApiFoctory {

    public static PostHttpServiceImp post(String path) {
        return new PostHttpServiceImp(path);
    }

    public static GetHttpServiceImp get(String path) {return new GetHttpServiceImp(path);}

    public static PutHttpServiceImp put(String path) {return new PutHttpServiceImp(path);}

    public static DeleteHttpServiceImp delete(String path) {return new DeleteHttpServiceImp(path);}

}
