//package com.example.lee.medichine_alarm;
//
//import android.app.Activity;
//import android.content.Intent;
//import android.os.Bundle;
//import android.os.Handler;
//import android.os.Message;
//import android.view.Window;
//import android.view.WindowManager;
//import android.widget.ListView;
//import android.widget.TextView;
//import android.widget.Toast;
//
///**
// * Created by lee on 2016-06-11.
// */
//public class ShowMsgActivity extends Activity {
//
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND,
//                WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
//        Toast.makeText(this, "알람이 설정되었습니다!.", Toast.LENGTH_SHORT).show();
//    }
//}
////
////        Thread myThread = new Thread(new Runnable() {
////            public void run() {
////                while (true) {
////                    try {
////                        handler.sendEmptyMessage(0);
////                        Thread.sleep(1000);
////                    } catch (InterruptedException e) {
////                        e.printStackTrace();
////                    }
////                }
////            }
////        });
////
////        myThread.start();
////
////        finish();
////
////
////    }
////    Handler handler = new Handler() {
////        @Override
////        public void handleMessage(Message msg) {
////            count--;
////            mtext.setText(count+"초 후에 알람이 울립니다.");
////            updateThread();
////        }
////    };
////    private void updateThread() {
////        Intent intent = new Intent(getApplicationContext(), AlarmPlay.class);
////        startActivity(intent);
////    }
////    @Override
////    public void onStop(){
////        super.onStop();
////        try{
////            Thread.sleep(3000);
////        }catch (InterruptedException e) {
////            e.printStackTrace();
////        }
////
////    }
////    @Override
////    public void onRestart() {
////        super.onRestart();
////        Toast.makeText(getApplicationContext(),"성ㄷ공",Toast.LENGTH_SHORT).show();
////        Intent intent = new Intent(getApplicationContext(), AlarmPlay.class);
////        startActivity(intent);
////    }
//
////    private Runnable mRunnable = new Runnable() {
////        @Override
////        public void run(){
////            try{
////                Thread.sleep(3000);
////            }catch (InterruptedException e){
////                e.printStackTrace();
////                Handler handler = new Handler();
////                handler.post(mRunnable2);
////            }
////        }
////    };
////    private Runnable mRunnable2 = new Runnable() {
////        @Override
////        public void run() {
////            Intent intent = new Intent(getApplicationContext(), AlarmPlay.class);
////        startActivity(intent);
////            Toast.makeText(getApplicationContext(),"ㄱㄱㄱㄱㄱ", Toast.LENGTH_SHORT).show();
////        }
////    };
//
//}
