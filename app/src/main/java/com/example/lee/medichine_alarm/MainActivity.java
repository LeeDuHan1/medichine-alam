package com.example.lee.medichine_alarm;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.text.Layout;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewParent;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends Activity {

    private ImageView testView;
    private int id_view;
    private static final int B_ACTIVITY = 0;
    public static Context mContext;
    private ListView mListView = null;
    private ListViewAdapter mAdapter = null;
    TextView tv;
    TabHost tabHost;
    boolean clickstats = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        startActivity(new Intent(this, loadingActivity.class));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toast toast = Toast.makeText(this, "create", Toast.LENGTH_SHORT);
        toast.show();


        //tabhost 선언 및 구성
        tabHost = (TabHost) findViewById(R.id.tabHost);
        tabHost.setup();

        final TabHost.TabSpec spec1 = tabHost.newTabSpec("Tab1");
        spec1.setIndicator("약목록",getResources().getDrawable(R.drawable.fff))
                .setContent(R.id.tab1);

        tabHost.addTab(spec1);

        TabHost.TabSpec spec2 = tabHost.newTabSpec("Tab2").setContent(R.id.tab2).setIndicator("복용통계",getResources().getDrawable(R.drawable.presentation));
        tabHost.addTab(spec2);

        TabHost.TabSpec spec3 = tabHost.newTabSpec("Tab3").setContent(R.id.tab3).setIndicator("설정",getResources().getDrawable(R.drawable.settings));
        tabHost.addTab(spec3);

        tabHost.setCurrentTab(0);

        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                //각각의 탭을 눌렀을때 toast 호출
                if (tabId.equals("Tab1")){
                    Toast toast = Toast.makeText(getApplicationContext(), "1번째 탭", Toast.LENGTH_SHORT);
                    toast.show();
                } else if (tabId.equals("Tab2")) {
                    Toast toast = Toast.makeText(getApplicationContext(), "2번째 탭", Toast.LENGTH_SHORT);
                    toast.show();
                } else if (tabId.equals("Tab3")) {
                    Toast toast = Toast.makeText(getApplicationContext(), "3번째 탭", Toast.LENGTH_SHORT);
                    toast.show();
                }

            }
        });
        mListView = (ListView) findViewById(R.id.listView1);
        Animation listviewAni = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.listveiw_ani);
        final Animation fadein = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fadein);
        final Animation fadeout = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fadeout);
        final Animation right = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.right_trans);

        mAdapter = new ListViewAdapter(this);
        mListView.setAdapter(mAdapter);
        mListView.startAnimation(listviewAni);

        final TextView tutoText = (TextView) findViewById(R.id.tutoText1);
        final ImageView tuto1 = (ImageView) findViewById(R.id.tuto1);
        if(!PreferencesUtil.getPreferences(getApplicationContext(),"first3").contains("true")) {
            tutoText.setVisibility(View.VISIBLE);
            tutoText.setText("메디체크는 여러분의 건강을 위해 정해진 시간 혹은 식후 복용시간에 알람을 울려 줄겁니다. 우선 우측하단의 버튼을 누른 후 생성되는 알람버튼을 눌러 추가하십시오.");
            tutoText.startAnimation(fadein);
            tuto1.setVisibility(View.VISIBLE);
            tuto1.setImageResource(R.drawable.swip);
            tuto1.startAnimation(right);

    }else if(PreferencesUtil.getPreferences(getApplicationContext(),"first3").contains("true")){
            tutoText.setVisibility(View.GONE);
            tuto1.setVisibility(View.GONE);
            tuto1.setImageResource(R.drawable.swip);
        }
       final Button musicB = (Button) findViewById(R.id.MusicButton);
       final Button alarmB = (Button) findViewById(R.id.AlarmButton);
        alarmB.setVisibility(View.GONE);
        musicB.setVisibility(View.GONE);

        Button.OnClickListener mClickListener = new View.OnClickListener() {
            Button b = (Button) findViewById(R.id.addButton);

            public void onClick(View v) {

                switch (v.getId()) {
                    case R.id.addButton:
                        Animation rotate = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.add_ani);
                        Animation rotateBack = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.add_back);
                        Animation music = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.music);
                        Animation musicBack = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.music_back);
                        Animation alarm = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.alarm);
                        Animation alarmBack = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.alarm_back);
//                        Animation mainActiAni = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.main_acti_ani);
                        if (clickstats == false) {
                            b.startAnimation(rotate);
                            musicB.setVisibility(View.VISIBLE);
                            musicB.startAnimation(music);
                            alarmB.setVisibility(View.VISIBLE);
                            alarmB.startAnimation(alarm);
                            clickstats = true;
                        } else if (clickstats == true) {
                            b.startAnimation(rotateBack);
                            musicB.startAnimation(musicBack);
                            musicB.setVisibility(View.GONE);
                            alarmB.setVisibility(View.GONE);
                            alarmB.startAnimation(alarmBack);
                            clickstats = false;
                        }
                        break;
                    case R.id.AlarmButton:
                        Intent intent = new Intent(MainActivity.this, AddActivity.class);
                        startActivityForResult(intent, 0);
                        break;
                    case R.id.MusicButton:
                        Toast.makeText(getApplicationContext(), "음악추가", Toast.LENGTH_LONG).show();
                        Intent i = new Intent(MainActivity.this,mp3Search.class);
                        startActivity(i);
                        break;
                }
            }
        };
        findViewById(R.id.addButton).setOnClickListener(mClickListener);
        findViewById(R.id.AlarmButton).setOnClickListener(mClickListener);
        findViewById(R.id.MusicButton).setOnClickListener(mClickListener);

        //3번째 탭 액티비티
        Button cb = (Button) findViewById(R.id.callbutton1);
        cb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:01073421239"));
                startActivity(callIntent);
            }
        });
        Button cb2 = (Button) findViewById(R.id.camerabutton1);
        cb2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent camera = new Intent();
                camera.setAction("android.media.action.IMAGE_CAPTURE");
                startActivityForResult(camera, 2);
            }
        });


//        mListView = (ListView) findViewById(R.id.listView1);
//
//        mAdapter = new ListViewAdapter(this);
//        mListView.setAdapter(mAdapter);

        // 리스트뷰 아이템 클릭시

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View v, final int position, long id) {

                final ListData mData = mAdapter.mListData.get(position);
//                Toast.makeText(MainActivity.this, mData.mTitle, Toast.LENGTH_SHORT).show();
//                id_view = v.getId();
                DialogInterface.OnClickListener modifyListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        doModify();
                    }
                };
                DialogInterface.OnClickListener deleteListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        doDelete(position);
                    }
                };
                DialogInterface.OnClickListener cancelListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                };
                AlertDialog.Builder a = new AlertDialog.Builder(MainActivity.this);
                StringBuffer sb = new StringBuffer();
                a.setTitle(mData.mTitle + " 기능선택");
                sb.append("<" + mData.method + ">");
                sb.append("\n");
                if (mData.method.contains("식후") || mData.afterMinute != 0) {
                    sb.append("위젯 클릭시 " + mData.afterMinute + "분 후 알람이 울립니다.");
                }
                if (mData.hour1 + mData.minute1 != 0) {
                    sb.append("기 상  " + pad2(mData.hour1) + ":" + pad2(mData.minute1));
                    sb.append("\n");
                }
                if (mData.hour2 + mData.minute2 != 0) {
                    sb.append("아 침  " + pad2(mData.hour2) + ":" + pad2(mData.minute2));
                    sb.append("\n");
                }
                if (mData.hour3 + mData.minute3 != 0) {
                    sb.append("점 심  " + pad2(mData.hour3) + ":" + pad2(mData.minute3));
                    sb.append("\n");
                }
                if (mData.hour4 + mData.minute4 != 0) {
                    sb.append("저 녁  " + pad2(mData.hour4) + ":" + pad2(mData.minute4));
                    sb.append("\n");
                }
                if (mData.hour5 + mData.minute5 != 0) {
                    sb.append("취 침  " + pad2(mData.hour5) + ":" + pad2(mData.minute5));
                    sb.append("\n");
                }
                a.setMessage(sb.toString());
                a.setNeutralButton("취소", cancelListener);
                a.setPositiveButton("수정", modifyListener);
                a.setNegativeButton("삭제", deleteListener);
                a.show();

            }
        });
    }


    private String pad2(int c) {
        if (c >= 10) {
            return String.valueOf(c);
        } else
            return "0" + String.valueOf(c);
    }

    public void doModify() //리스트뷰 수정
    {
        Toast t = Toast.makeText(this, "수정", Toast.LENGTH_SHORT);
        t.show();
    }

    public void doDelete(int position) //리스트뷰 삭제
    {
        Toast t = Toast.makeText(this, "삭제", Toast.LENGTH_SHORT);
        t.show();
        mAdapter.remove(position);
        mAdapter.notifyDataSetChanged();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case B_ACTIVITY:
                if (resultCode == RESULT_OK) {
                    //인텐트 이용
//                    testView = (ImageView)findViewById(R.id.imageView);
//                    final Bundle extras = data.getExtras();
//                    Bitmap photo = extras.getParcelable("data_image");
//                    Bitmap myBitmap = BitmapFactory.decodeFile(data.getStringExtra("data_image"));
//                    Drawable drawable = new BitmapDrawable(photo);
//                testView.setImageBitmap(myBitmap);
//                mAdapter.addItem(null,"제이슨변경test",data.getStringExtra("data_method"),true);

                    //sharedpreference만 이용
//                    String title = PreferencesUtil.getPreferences(getApplicationContext(),"name");
//                    String type = PreferencesUtil.getPreferences(getApplicationContext(),"type");
//                    mAdapter.addItem(null,title,type,true);

                    //sharedpreference + json 이용
                    doJson(PreferencesUtil.getPreferences(getApplicationContext(), "json"));
                    ImageView tuto3 = (ImageView)findViewById(R.id.tuto3);
                    Animation right = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.right_trans);
                    final TextView tutoText = (TextView) findViewById(R.id.tutoText1);
                    final ImageView tuto1 = (ImageView) findViewById(R.id.tuto1);
                    tuto1.setVisibility(View.GONE);
                    tutoText.setVisibility(View.GONE);
                    }
                }
        }

    void doJson(String a) {
        String JsonStr = a;
        StringBuffer sb2 = new StringBuffer();
        ImageView test = (ImageView) findViewById(R.id.imageView);
        try {
            JSONArray jarray = new JSONArray(JsonStr);
            for (int i = 0; i < jarray.length(); i++) {
                JSONObject jObject = jarray.getJSONObject(i);  // JSONObject 추출
                String image = jObject.getString("image");
                String address = jObject.getString("type");
                String title = jObject.getString("name");
                Integer hour1 = jObject.getInt("hour1");
                Integer minute1 = jObject.getInt("minute1");
                Integer hour2 = jObject.getInt("hour2");
                Integer minute2 = jObject.getInt("minute2");
                Integer hour3 = jObject.getInt("hour3");
                Integer minute3 = jObject.getInt("minute3");
                Integer hour4 = jObject.getInt("hour4");
                Integer minute4 = jObject.getInt("minute4");
                Integer hour5 = jObject.getInt("hour5");
                Integer minute5 = jObject.getInt("minute5");
                Integer afterMinute = jObject.getInt("after");
                String swit = jObject.getString("swit");

                if (image.contains("imageNull")) {
                    Bitmap bit = BitmapFactory.decodeResource(getResources(), R.id.emtyImage);
                    Drawable icon = new BitmapDrawable(bit);
                    mAdapter.addItem(icon, title, address, false, hour1, minute1, hour2, minute2, hour3, minute3, hour4, minute4, hour5, minute5, afterMinute);

                } else if (!image.contains("imageNull")) {
                    Bitmap bit = StringToBitMap(image);
                    Drawable icon = new BitmapDrawable(  bit);

                    mAdapter.addItem(icon, title, address, false, hour1, minute1, hour2, minute2, hour3, minute3, hour4, minute4, hour5, minute5, afterMinute);
                }
            }
            mAdapter.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public Bitmap StringToBitMap(String encodedString) {
        try {
            byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }

}