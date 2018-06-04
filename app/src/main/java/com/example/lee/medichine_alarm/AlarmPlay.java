package com.example.lee.medichine_alarm;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by lee on 2016-06-11.
 */
public class AlarmPlay extends Activity{
    Context context;
    MediaPlayer music;
    Button button;
    Vibrator vide;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alarm_layout);
        context = getApplicationContext();
        Thread myThread = new Thread(new Runnable() {
            public void run() {
                while (true) {
                    try {
                        handler.sendEmptyMessage(0);
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        myThread.start();

    }
    Handler handler = new Handler() {
        int count = doJson(PreferencesUtil.getPreferences(context,"json"));
        @Override
        public void handleMessage(Message msg) {
//            TextView mtext = (TextView) findViewById(R.id.countdown);
            count--;

            if(count==0) {
                updateThread();
            }
            if(count >= 0){
//                mtext.setText(count + "초 후에 알람이 울립니다.");
            }
        }
    };
    int doJson(String a){
        int count = 0;
        String JsonStr = a;
        String title = "아직 설정이 되어 있지 않습니다. 식후복용알람을 설정하십시오";
        TextView setText = (TextView)findViewById(R.id.drugTitle);
        try {
            JSONArray jarray = new JSONArray(JsonStr);
            for(int i=0; i < jarray.length(); i++) {
                JSONObject jObject = jarray.getJSONObject(i);  // JSONObject 추출
                String type = jObject.getString("type");
                if(type.contains("식후")) {
                    count = jObject.getInt("after");
                    title = jObject.getString("name");
                }
        }
            setText.setText(title);
        }catch(JSONException e){
            e.printStackTrace();
        }
        return count;
    }

    private void updateThread() {
        music = MediaPlayer.create(this, R.raw.music);
        music.setLooping(true);
        music.start();
        vide = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        long[] pattern = { 0, 500, 200};
        vide.vibrate(pattern,0);
        button = (Button) findViewById(R.id.StopButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                music.stop();
                vide.cancel();
                finish();
            }
        });
    }

}
