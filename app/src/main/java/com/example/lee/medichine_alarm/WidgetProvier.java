package com.example.lee.medichine_alarm;

import android.app.Activity;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.DrawableRes;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RemoteViews;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class WidgetProvier extends AppWidgetProvider{
    private  static final String ACTION_ACTION1 = "com.example.lee.medichine_alarm";


//브로드캐스트를 수신할때, Override된 콜백 메소드가 호출되기 직전에 호출됨
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();

        if(action.equals(ACTION_ACTION1)){
            Toast.makeText(context, "onReceive :: ACTION_ACTION1  ", Toast.LENGTH_SHORT).show();
        }else{
        }
        super.onReceive(context, intent);
    }
    //위젯을 갱신할때 호출됨
    // 주의 : Configure Activity를 정의했을때는 위젯 등록시 처음 한번은 호출이 되지 않습니다

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager,int[] appWidgetIds) {
        appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(context, getClass()));
        for (int i = 0; i < appWidgetIds.length; i++) {
            updateAppWidget(context, appWidgetManager, appWidgetIds[i]);
        }

        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }

      // 위젯이 처음 생성될때 호출됨
      //동일한 위젯이 생성되도 최초 생성때만 호출됨

    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);
    }

      //  위젯의 마지막 인스턴스가 제거될때 호출됨
       //  onEnabled()에서 정의한 리소스 정리할때

    @Override
    public void onDisabled(Context context) {
        super.onDisabled(context);
    }


     // 위젯이 사용자에 의해 제거될때 호출됨

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        super.onDeleted(context, appWidgetIds);
    }

    public static void updateAppWidget(Context context,AppWidgetManager appWidgetManager, int appWidgetId) {

//        ImageView testView = (ImageView)((Activity)context).findViewById(R.id.widetIcon);
//        /**
//         * 현재 시간 정보를 가져오기 위한 Calendar
//         */
//        Calendar mCalendar = Calendar.getInstance();
//        SimpleDateFormat mFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm",
//                Locale.KOREA);

//        /**
//         * RemoteViews를 이용해 Text설정
//         */
        RemoteViews updateViews = new RemoteViews(context.getPackageName(),R.layout.widget_layout);
        /**
         * 레이아웃을 클릭하면 액티비티 실행
         */
//        Intent intent1 = new Intent(context, ShowMsgActivity.class);
//        intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        context.startActivity(intent1);

        Intent intent = new Intent(context, AlarmPlay.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
        updateViews.setOnClickPendingIntent(R.id.mLayout, pendingIntent);

        /**
         * 위젯 업데이트
         */

        appWidgetManager.updateAppWidget(appWidgetId, updateViews);
    }
}
