package com.augenstern.alumniwords.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class MyPref {
    private static SharedPreferences mysp;
    private static SharedPreferences.Editor editor;
    public MyPref(Context context) {
        mysp = context.getSharedPreferences("MyData",Context.MODE_PRIVATE);
        editor = mysp.edit();
        editor.apply();
    }

    public static void setString(String key,String value){
        editor.putString(key,value);
        editor.apply();
    }

    public static void setBoolean(String key,Boolean value){
        editor.putBoolean(key,value);
        editor.apply();
    }

    public static String getString(String key){
        return mysp.getString(key,"");
    }

    public static Boolean getBoolean(String key){
        return mysp.getBoolean(key,false);
    }

}
