package com.normal.zbase.http.interceptor;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;

import android.util.Log;

import com.base.utils.LogUtil;
import com.google.gson.Gson;

/**
 * Created by wqx on 2021/2/27 0027 18:04
 * ******************************************
 * * 请求过滤器
 * ******************************************
 */
public class HttpLoggerInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Charset charset = StandardCharsets.UTF_8;
        Headers headers = request.headers();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("\n" + request.method() + "  " + request.url().toString());
        if (headers.names().size() > 0)
            stringBuilder.append("\n【Hearder】: \n");
        for (String key : headers.names()) {
            stringBuilder.append("\t" + key + ":" + headers.get(key) + "\n");
        }
        Response response = chain.proceed(request);
        ResponseBody responseBody = response.body();
        long contentLength = responseBody.contentLength();
        BufferedSource source = responseBody.source();
        source.request(Long.MAX_VALUE); // Buffer the entire body.
        Buffer buffer = source.buffer();
        if ("POST".equals(request.method())) {
            FormBody formBody = (FormBody) request.body();
            Map<String, String> map = new HashMap<>();
            for (int i = 0; i < formBody.size(); i++) {
                map.put(formBody.encodedName(i), formBody.encodedValue(i));
            }
            stringBuilder.append("【Body】:\n" + new Gson().toJson(map) + "\n");
        }
        stringBuilder.append("【Response】:\n" + buffer.clone().readString(charset));
        Log.e("zxy",stringBuilder.toString());
        return chain.proceed(request);
    }
}
