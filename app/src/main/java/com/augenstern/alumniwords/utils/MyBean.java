package com.augenstern.alumniwords.utils;

public class MyBean {
    public static String url = "https://www.kkdaxue.com/api";
    public static String Cookie = "";

    public static String getUrl() {
        return url;
    }

    public static void setUrl(String url) {
        MyBean.url = url;
    }

    public static String getCookie() {
        return Cookie;
    }

    public static void setCookie(String cookie) {
        Cookie = cookie;
    }
}
