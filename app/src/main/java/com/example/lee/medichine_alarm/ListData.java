package com.example.lee.medichine_alarm;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Switch;

import java.util.Calendar;

/**
 * Created by lee on 2016-06-01.
 */
public class ListData {

    public Drawable mIcon;

    public String mTitle;

    public String method;

    public int hour1,hour2,hour3,hour4,hour5;

    public int minute1,minute2,minute3,minute4,minute5;

    public int afterMinute;

    public Boolean alarmSwitch;
}
