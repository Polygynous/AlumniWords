package com.augenstern.alumniwords.utils;

import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class Httptools {
    public static void getAsync(String url, Callback callback){
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .get()
                .header("cookie",MyBean.getCookie())
                .url(MyBean.getUrl() + url)
                .build();
        okHttpClient.newCall(request).enqueue(callback);
    }
    public static void postAsync(String url,String body,Callback callback){
        OkHttpClient okHttpClient = new OkHttpClient();
        MediaType mediaType = MediaType.parse("application/json");
        Request request = new Request.Builder()
                .post(RequestBody.create(mediaType,body))
                .header("cookie",MyBean.getCookie())
                .url(MyBean.getUrl() + url)
                .build();
        okHttpClient.newCall(request).enqueue(callback);
    }
}
