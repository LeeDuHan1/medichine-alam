package com.example.lee.medichine_alarm;

import android.content.Context;
import android.content.SharedPreferences;

import org.json.JSONArray;

/**
 * preferencesUtil 클래스
 * 파일 형태의 저장소 (Key, Value 형태의 Editing)
 * @author YT
 */

@SuppressWarnings("static-access")
public class PreferencesUtil{

    /**
     * Preference 세팅
     * @author YT
     */
    public static void setPreferences(Context context, String key, String value) {
        if(key.contains("json")) {
            SharedPreferences p = context.getSharedPreferences("json", context.MODE_PRIVATE);
            SharedPreferences.Editor editor = p.edit();
            editor.putString(key, value);
            editor.commit();
        }else  if(key.contains("first3")) {
            SharedPreferences p2 = context.getSharedPreferences("first3", context.MODE_PRIVATE);
            SharedPreferences.Editor editor2 = p2.edit();
            editor2.putString(key, value);
            editor2.commit();
        }
    }
    /**
     * Preference 가져오기
     * @author YT
     */
    public static String getPreferences(Context context, String key) {
        if(key.contains("json")) {
            SharedPreferences p = context.getSharedPreferences("json", context.MODE_PRIVATE);
            p = context.getSharedPreferences("json", context.MODE_PRIVATE);
            return p.getString(key, "");
            }else if(key.contains("first3")) {
            SharedPreferences p2 = context.getSharedPreferences("first", context.MODE_PRIVATE);
            p2 = context.getSharedPreferences("first3", context.MODE_PRIVATE);
            return p2.getString(key, "default");
        }else
        return "default";
    }
}

