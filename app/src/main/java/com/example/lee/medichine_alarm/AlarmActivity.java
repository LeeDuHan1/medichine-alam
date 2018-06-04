package com.example.lee.medichine_alarm;

        import android.app.AlarmManager;
        import android.app.DatePickerDialog;
        import android.app.PendingIntent;
        import android.app.TimePickerDialog;
        import android.content.Context;
        import android.content.Intent;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.util.Log;
        import android.view.View;
        import android.widget.Button;
        import android.widget.DatePicker;
        import android.widget.TextView;
        import android.widget.TimePicker;
        import android.widget.Toast;

        import java.text.SimpleDateFormat;
        import java.util.Calendar;
        import java.util.GregorianCalendar;
        import java.util.concurrent.atomic.AtomicIntegerArray;

public class AlarmActivity extends AppCompatActivity {

    //시간 설정을 위한 객체
    Calendar Time;

    //알람 설정을 위한 객체
    public Intent intent;
    public PendingIntent ServicePending;
    public AlarmManager alarmManager;

    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy년 MM월 dd일 mm분 ss초");

    TextView textView;

    static boolean setting = true;
    int count =0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent i = getIntent();
        //타임 피커, 데이트 피커 리스너 및 아이디 등록
        String name = i.getStringExtra("name");
        Log.d("타이2",name);
        int hour[] =new int[5];
        int minute[] = new int[5];
        if(i.getStringExtra("hour")==null){
            hour[0]=0;
        }else if(i.getStringExtra("hour")!=null){
            hour[0] = Integer.parseInt(i.getStringExtra("hour"));
        }if(i.getStringExtra("hour2")==null){
            hour[1]=0;
        }else if(i.getStringExtra("hour2")!=null){
            hour[1] = Integer.parseInt(i.getStringExtra("hour2"));
        }if(i.getStringExtra("hour3")==null){
            hour[2]=0;
        }else if(i.getStringExtra("hour3")!=null){
            hour[2] = Integer.parseInt(i.getStringExtra("hour3"));
        }if(i.getStringExtra("hour4")==null){
            hour[3]=0;
        }else if(i.getStringExtra("hour4")!=null){
            hour[3] = Integer.parseInt(i.getStringExtra("hour4"));
        }if(i.getStringExtra("hour5")==null){
            hour[4]=0;
        }else if(i.getStringExtra("hour5")!=null){
            hour[4] = Integer.parseInt(i.getStringExtra("hour5"));
        }
        if(i.getStringExtra("minute")==null){
            minute[0]=0;
        }else if(i.getStringExtra("minute")!=null){
            minute[0] = Integer.parseInt(i.getStringExtra("minute"));
        }if(i.getStringExtra("minute2")==null){
            minute[1]=0;
        }else if(i.getStringExtra("minute2")!=null){
            minute[1] = Integer.parseInt(i.getStringExtra("minute2"));
        }if(i.getStringExtra("minute3")==null){
            minute[2]=0;
        }else if(i.getStringExtra("minute3")!=null){
            minute[2] = Integer.parseInt(i.getStringExtra("minute3"));
        }if(i.getStringExtra("minute4")==null){
            minute[3]=0;
        }else if(i.getStringExtra("minute4")!=null){
            minute[3] = Integer.parseInt(i.getStringExtra("minute4"));
        }if(i.getStringExtra("minute5")==null){
            minute[4]=0;
        }else if(i.getStringExtra("minute5")!=null){
            minute[4] = Integer.parseInt(i.getStringExtra("minute5"));
        }

        for(int j = 0 ; j<5;j++) {
            Calendar cal = new GregorianCalendar();
            cal.setTimeInMillis(System.currentTimeMillis());
            cal.set(Calendar.HOUR_OF_DAY, hour[j]);
            cal.set(Calendar.MINUTE, minute[j]);
            cal.set(Calendar.SECOND, 0);
            cal.set(Calendar.MILLISECOND, 0);
            if (cal.getTimeInMillis() > System.currentTimeMillis()) {
                PendingIntent[] sender = new PendingIntent[5];
                if (setting == true) {
                    if (hour[j] != 0 && minute[j] != 0) {
                        intent = new Intent(getApplicationContext(), AlarmReceiver.class);
                        intent.putExtra("name", name);
                        Log.d("타이3", name);
                        sender[j] = PendingIntent.getBroadcast(getApplicationContext(), count, intent, 0);
                        AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                        am.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), sender[j]);
                    }
                } else if (setting == false) {
                    intent = new Intent("AlarmReceiver");
                    //PendingIntent.getBroadcast(Context context, int requestCod, Intent intent, int flag);

                    ServicePending = PendingIntent.getBroadcast(AlarmActivity.this, j, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                    Toast.makeText(getBaseContext(), "알람 해제" + cal.getTime(), Toast.LENGTH_SHORT).show();
                    alarmManager.cancel(ServicePending);

                }
                finish();
            }else
                finish();
        }

    }
    private void updateLabel() {
        textView.setText(simpleDateFormat.format(Time.getTime()));
    }


    void setAlarm(){
        //Receiver로 보내기 위한 인텐트
        //intent = new Intent(this, AlarmReceiver.class);
        intent = new Intent("AlarmReceiver");
        //PendingIntent.getBroadcast(Context context, int requestCod, Intent intent, int flag);


        ServicePending = PendingIntent.getBroadcast(
                AlarmActivity.this, 111, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        //정해진 시간에 알람 설정
//        alarmManager.set(AlarmManager.RTC_WAKEUP, Time.getTimeInMillis(), ServicePending);
        //alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, Time.getTimeInMillis(), 1000, ServicePending); // Millisec * Second * Minute

    }
    void removeAlarm(){

        intent = new Intent("AlarmReceiver");
        //PendingIntent.getBroadcast(Context context, int requestCod, Intent intent, int flag);


        ServicePending = PendingIntent.getBroadcast(
                AlarmActivity.this, 111, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        Toast.makeText(getBaseContext(), "알람 해제" + Time.getTime(), Toast.LENGTH_SHORT).show();

        alarmManager.cancel(ServicePending);

    }
//    void setRepeatAlarm(){
//        //Receiver로 보내기 위한 인텐트
//        //intent = new Intent(this, AlarmReceiver.class);
//        intent = new Intent("AlarmReceiver");
//        //PendingIntent.getBroadcast(Context context, int requestCod, Intent intent, int flag);
//
//
//        ServicePending = PendingIntent.getBroadcast(
//                AlarmActivity.this, 111, intent, PendingIntent.FLAG_UPDATE_CURRENT);
//        Log.d("ServicePending : ",""+ServicePending.toString());
//
//        //정해진 시간에 알람 설정
//        // alarmManager.set(AlarmManager.RTC_WAKEUP, Time.getTimeInMillis(), ServicePending);
//        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, Time.getTimeInMillis(), 20000, ServicePending); // Millisec * Second * Minute
//
//        Toast.makeText(getBaseContext(), "알람 설정" + Time.getTime(), Toast.LENGTH_SHORT).show();
//
//    }
}
