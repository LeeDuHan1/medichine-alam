package com.example.lee.medichine_alarm;

import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by lee on 2016-06-16.
 */
public class JsonPaser {
    public static String setJson(String image, String title, String type,int hour1,int hour2,int hour3,int hour4,int hour5,int minute1,int minute2,int minute3,int minute4,int minute5, int afterMinute, boolean swit){

        JSONArray jArray = new JSONArray();//배열이 필요할때
            JSONObject sObject = new JSONObject();//배열 내에 들어갈 json
            try{
                sObject.put("image",image);
                sObject.put("name",title);
                sObject.put("type",type);
                sObject.put("hour1",hour1);
                sObject.put("minute1",minute1);
                sObject.put("hour2",hour2);
                sObject.put("minute2",minute2);
                sObject.put("hour3",hour3);
                sObject.put("minute3",minute3);
                sObject.put("hour4",hour4);
                sObject.put("minute4",minute4);
                sObject.put("hour5",hour5);
                sObject.put("minute5",minute5);
                sObject.put("after",afterMinute);
                sObject.put("swit",swit);
                jArray.put(sObject);
            }
                catch (JSONException e){
            }
        return jArray.toString();
    }



//    public static String getJson(){
//
//    }

}
