package com.example.lee.medichine_alarm;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class loadingActivity extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading_layout); //splash이미지 레이아웃
        final ImageView loadPoint = (ImageView)findViewById(R.id.loadPoint);
        Animation scale = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.point_ani);
        final Animation scaleB = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.point_back);
        final Animation loadTrans1 = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.load_text);
        final Animation loadTrans2 = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.load_text);
        final Animation loadTrans3 = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.load_text);
        final Animation loadTrans4 = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.load_text);
        final Animation loadTrans5 = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.load_text);
        final TextView load1 = (TextView)findViewById(R.id.load1);
        final TextView load2 = (TextView)findViewById(R.id.load2);
        final TextView load3 = (TextView)findViewById(R.id.load3);
        final TextView load4 = (TextView)findViewById(R.id.load4);
        final LinearLayout linearLayout_text = (LinearLayout) findViewById(R.id.linearLayout_text);
        loadPoint.startAnimation(scale);
        scale.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                    loadPoint.startAnimation(scaleB);
                scaleB.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {}
                    @Override
                    public void onAnimationEnd(Animation animation) {
                            load1.startAnimation(loadTrans1);
                            loadTrans1.setAnimationListener(new Animation.AnimationListener() {

                                @Override
                                public void onAnimationStart(Animation animation) {}

                                @Override
                                public void onAnimationEnd(Animation animation) {
                                    load2.startAnimation(loadTrans2);
                                    loadTrans2.setAnimationListener(new Animation.AnimationListener() {

                                        @Override
                                        public void onAnimationStart(Animation animation) {}

                                        @Override
                                        public void onAnimationEnd(Animation animation) {
                                            load3.startAnimation(loadTrans3);
                                            loadTrans3.setAnimationListener(new Animation.AnimationListener() {

                                                @Override
                                                public void onAnimationStart(Animation animation) {}

                                                @Override
                                                public void onAnimationEnd(Animation animation) {
                                                    load4.startAnimation(loadTrans4);
                                                    loadTrans4.setAnimationListener(new Animation.AnimationListener() {

                                                        @Override
                                                        public void onAnimationStart(Animation animation) {}

                                                        @Override
                                                        public void onAnimationEnd(Animation animation) {
                                                            linearLayout_text.startAnimation(loadTrans5);
                                                        }
                                                        @Override
                                                        public void onAnimationRepeat(Animation animation) {
                                                        }
                                                    });

                                                }

                                                @Override
                                                public void onAnimationRepeat(Animation animation) {
                                                }
                                            });
                                        }

                                        @Override
                                        public void onAnimationRepeat(Animation animation) {
                                        }
                                    });
                                }
                                @Override
                                public void onAnimationRepeat(Animation animation) {
                                }
                            });
                    }
                    @Override
                    public void onAnimationRepeat(Animation animation) {
                    }
                });
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                overridePendingTransition(R.anim.fadein, R.anim.fadeout);
            }
        };
        handler.sendEmptyMessageDelayed(0,6000);
    }
    public void onBackPressed(){} //백버튼 막기
}