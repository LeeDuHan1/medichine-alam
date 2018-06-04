package com.example.lee.medichine_alarm;


import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class removeActivity extends Activity {
    Vibrator vide;
    private Intent intent;
    private PendingIntent ServicePending;
    private AlarmManager alarmManager;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alarm_layout);
        String title = "";
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        TextView drugTitle = (TextView)findViewById(R.id.drugTitle);
        drugTitle.setText("약을 복용할 시간입니다.");
        final MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.music);

        vide = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        long[] pattern = { 0, 500, 200};
        vide.vibrate(pattern, 0);
        mediaPlayer.start(); // prepare(); 나 create() 를 호출할 필요 없음
       final ImageView time = (ImageView)findViewById(R.id.timer);
        final Animation vib = AnimationUtils.loadAnimation(getApplication(),R.anim.vibration_ani);
        time.startAnimation(vib);





        //알람 설정, 해제 버튼
        Button.OnClickListener bClickListener = new View.OnClickListener() {

            public void onClick(View v) {
                switch (v.getId()) {

                    case R.id.StopButton:
                        vide.cancel();
                        mediaPlayer.stop();
                        time.clearAnimation();
                        removeAlarm();
                        break;


                }
            }
        };

          findViewById(R.id.StopButton).setOnClickListener(bClickListener);
//        findViewById(R.id.removeAlarm).setOnClickListener(bClickListener);
    }
    void removeAlarm(){

        intent = new Intent("AlarmReceiver");
        //PendingIntent.getBroadcast(Context context, int requestCod, Intent intent, int flag);
        ServicePending = PendingIntent.getBroadcast(removeActivity.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        Log.d("ServicePending : ",""+ServicePending.toString());

        Toast.makeText(getBaseContext(), "알람 해제", Toast.LENGTH_SHORT).show();

        alarmManager.cancel(ServicePending);
    }
}