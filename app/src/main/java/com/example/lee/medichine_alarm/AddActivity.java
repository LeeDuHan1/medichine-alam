package com.example.lee.medichine_alarm;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;


import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class AddActivity extends Activity implements View.OnClickListener {
    private ListViewAdapter mAdapter = null;
    private ListView mListView = null;

    private static final int PICK_FROM_CAMERA = 0;
    private static final int PICK_FROM_ALBUM = 1;
    private static final int CROP_FROM_iMAGE = 2;

    private Uri mImageCaptureUri;
    private ImageButton myEmtyBtn;
    private int id_view;
    private String absoultePath = null;

    private String filePath ="";
//    private DB_Manger dbmanger;

    //시간 설정을 위한 객체
    Calendar Time;

    //알람 설정을 위한 객체
    private Intent intent;
    private PendingIntent ServicePending;
    private AlarmManager alarmManager;

    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy년 MM월 dd일 mm분 ss초");

    private TextView mTimeDisplay,mTimeDisplay2,mTimeDisplay3,mTimeDisplay4,mTimeDisplay5;
    private Button mPickTime,mPickTime2,mPickTime3,mPickTime4,mPickTime5;
    private int mHour,mHour2,mHour3,mHour4,mHour5 =0;
    private int mMinute,mMinute2,mMinute3,mMinute4,mMinute5 =0;
    static final int TIME_DIALOG_ID = 1;
    public String imageString ;
    Bundle extra;
    Intent i;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_layout);

        Time = Calendar.getInstance();
        findViewById(R.id.checkBox).setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                printChecked(v);
            }
        });

        findViewById(R.id.checkBox2).setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                printChecked(v);
            }
        });

        Button b = (Button) findViewById(R.id.back);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Button b2 = (Button) findViewById(R.id.saveBtn);

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast = Toast.makeText(getApplicationContext(), "save", Toast.LENGTH_SHORT);
                toast.show();

                Intent intent = getIntent();
                EditText title = (EditText) findViewById(R.id.drugTitle);
//                ImageView photo = (ImageView) findViewById(R.id.base1);
                String type = new String();
                CheckBox c1 = (CheckBox) findViewById(R.id.checkBox);
                CheckBox c2 = (CheckBox) findViewById(R.id.checkBox2);
                EditText after = (EditText)findViewById(R.id.afterMinute);
                after.setSelection(after.length()); //커서를 끝에
                int afterMinute = 0;
                if (c1.isChecked()) {
                    type = "식후 복용";
                    afterMinute = Integer.parseInt(""+after.getText());

                } else if (c2.isChecked()) {
                    type = "정해진 시간에 복용";
                }
                if(imageString == null) {
                    imageString = "imageNull";
                }
                    String setJson = JsonPaser.setJson(imageString,title.getText().toString(),type,mHour,mHour2,mHour3,mHour4,mHour5,mMinute,mMinute2,mMinute3,mMinute4,mMinute5,afterMinute,false); //데이터를 JsonArray로 변환
                    PreferencesUtil.setPreferences(getApplicationContext(),"json",setJson);
                    PreferencesUtil.setPreferences(getApplicationContext(),"first3","true");

                Animation right = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.right_trans);
                final TextView tutoText3 = (TextView)findViewById(R.id.tutoText3);
                final ImageView tuto2 = (ImageView)findViewById(R.id.tuto2);
                if(!PreferencesUtil.getPreferences(getApplicationContext(),"first3").contains("true")) {
                    tutoText3.setVisibility(View.VISIBLE);
                    tutoText3.setText("자, 이제 알람 등록을 희망하는 약의 사진과 시간정보를 입력한 후 저장합니다.");
                    tuto2.setVisibility(View.VISIBLE);
                    tuto2.startAnimation(right);
                }else if(PreferencesUtil.getPreferences(getApplicationContext(),"first3").contains("true")){
                    tutoText3.setVisibility(View.GONE);
                    tuto2.setVisibility(View.GONE);
                }
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        mTimeDisplay = (TextView) findViewById(R.id.timeDisplay1);
        mTimeDisplay2 = (TextView) findViewById(R.id.timeDisplay2);
        mTimeDisplay3 = (TextView) findViewById(R.id.timeDisplay3);
        mTimeDisplay4 = (TextView) findViewById(R.id.timeDisplay4);
        mTimeDisplay5 = (TextView) findViewById(R.id.timeDisplay5);

        mPickTime = (Button) findViewById(R.id.timeButton1);
        mPickTime2 = (Button) findViewById(R.id.timeButton2);
        mPickTime3 = (Button) findViewById(R.id.timeButton3);
        mPickTime4 = (Button) findViewById(R.id.timeButton4);
        mPickTime5 = (Button) findViewById(R.id.timeButton5);

        mPickTime.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                showDialog(1);
            }
        });
        mPickTime2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                showDialog(2);
            }
        });
        mPickTime3.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                showDialog(3);
            }
        });
        mPickTime4.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                showDialog(4);
            }
        });
        mPickTime5.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                showDialog(5);
            }
        });

        myEmtyBtn = (ImageButton) this.findViewById(R.id.emtyImage);
        myEmtyBtn.setOnClickListener(this);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();//시간 다이어로그
    }
    public void doTakePhotoAction() // 카메라 촬영 후 이미지 가져오기
    {

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        // 임시로 사용할 파일의 경로를 생성
        String url = "tmp_" + String.valueOf(System.currentTimeMillis()) + ".jpg";
        mImageCaptureUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(), url));

        intent.putExtra(MediaStore.EXTRA_OUTPUT, mImageCaptureUri);
        startActivityForResult(intent, PICK_FROM_CAMERA);
    }

    /**
     * 앨범에서 이미지 가져오기
     */
    public void doTakeAlbumAction() // 앨범에서 이미지 가져오기
    {

        // 앨범 호출
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
        startActivityForResult(intent, PICK_FROM_ALBUM);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != RESULT_OK)
            return;

        switch (requestCode) {
            case PICK_FROM_ALBUM: {
                // 이후의 처리가 카메라와 같으므로 일단  break없이 진행합니다.
                // 실제 코드에서는 좀더 합리적인 방법을 선택하시기 바랍니다.
                mImageCaptureUri = data.getData();
                Log.d("SmartWheel", mImageCaptureUri.getPath().toString());
            }

            case PICK_FROM_CAMERA: {
                // 이미지를 가져온 이후의 리사이즈할 이미지 크기를 결정합니다.
                // 이후에 이미지 크롭 어플리케이션을 호출하게 됩니다.
                Intent intent = new Intent("com.android.camera.action.CROP");
                intent.setDataAndType(mImageCaptureUri, "image/*");

                // CROP할 이미지를 200*200 크기로 저장
                intent.putExtra("outputX", 200); // CROP한 이미지의 x축 크기
                intent.putExtra("outputY", 200); // CROP한 이미지의 y축 크기
                intent.putExtra("aspectX", 1); // CROP 박스의 X축 비율
                intent.putExtra("aspectY", 1); // CROP 박스의 Y축 비율
                intent.putExtra("scale", true);
                intent.putExtra("return-data", true);
                startActivityForResult(intent, CROP_FROM_iMAGE); // CROP_FROM_CAMERA case문 이동
                break;
            }
            case CROP_FROM_iMAGE: {
                // 크롭이 된 이후의 이미지를 넘겨 받습니다.
                // 이미지뷰에 이미지를 보여준다거나 부가적인 작업 이후에
                // 임시 파일을 삭제합니다.
                if (resultCode != RESULT_OK) {
                    return;
                }

                final Bundle extras = data.getExtras();

                // CROP된 이미지를 저장하기 위한 FILE 경로
                filePath = "tmp_" + System.currentTimeMillis() + ".jpg";

                if (extras != null) {

                    Bitmap photo = extras.getParcelable("data"); // CROP된 BITMAP
                    myEmtyBtn.setImageBitmap(photo); // 레이아웃의 이미지칸에 CROP된 BITMAP을 보여줌
                    imageString = BitmapToString(photo);
                    storeCropImage(photo, filePath); // CROP된 이미지를 외부저장소, 앨범에 저장한다.
                    absoultePath = filePath;
                    break;

                }else if (extras == null) {
                    Bitmap bit = BitmapFactory.decodeResource(getResources(), R.id.emtyImage);
                    imageString = BitmapToString(bit);
                }
//                 임시 파일 삭제
                File f = new File(mImageCaptureUri.getPath());
                if (f.exists()) {
                    f.delete();
                }
            }
        }


    }

    @Override
    public void onClick(View v) {
        id_view = v.getId();
        if (v.getId() == R.id.emtyImage) {
            DialogInterface.OnClickListener cameraListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    doTakePhotoAction();
                }
            };
            DialogInterface.OnClickListener albumListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    doTakeAlbumAction();
                }
            };

            DialogInterface.OnClickListener cancelListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            };

            new AlertDialog.Builder(this)
                    .setTitle("업로드할 이미지 선택")
                    .setPositiveButton("사진촬영", cameraListener)
                    .setNeutralButton("앨범선택", albumListener)
                    .setNegativeButton("취소", cancelListener)
                    .show();
        }

    }
    public String BitmapToString(Bitmap bitmap){
        ByteArrayOutputStream baos=new  ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100, baos);
        byte [] b=baos.toByteArray();
        String temp= Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
    }
    /*
     * Bitmap을 저장하는 부분
     */
    public void storeCropImage(Bitmap bitmap, String filePath) {
        // SmartWheel 폴더를 생성하여 이미지를 저장하는 방식이다.
//        String dirPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/SmartWheel";
//        File directory_SmartWheel = new File(dirPath);
//
//        if (!directory_SmartWheel.exists()) // SmartWheel 디렉터리에 폴더가 없다면 (새로 이미지를 저장할 경우에 속한다.)
//            directory_SmartWheel.mkdir();

        File copyFile = new File(filePath);
        BufferedOutputStream out = null;

        try {

            copyFile.createNewFile();
            out = new BufferedOutputStream(new FileOutputStream(copyFile));
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);

            // sendBroadcast를 통해 Crop된 사진을 앨범에 보이도록 갱신한다.
            sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(copyFile)));

            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void printChecked(View v) {
        CheckBox ch1 = (CheckBox) findViewById(R.id.checkBox);
        CheckBox ch2 = (CheckBox) findViewById(R.id.checkBox2);
        FrameLayout fr1 = (FrameLayout) findViewById(R.id.frame1);
        FrameLayout fr2 = (FrameLayout) findViewById(R.id.frame2);
        fr1.setVisibility(v.GONE);
        fr2.setVisibility(v.GONE);

        switch (v.getId()) {
            case R.id.checkBox:
                ch1.setVisibility(v.VISIBLE);
                ch2.setVisibility(v.INVISIBLE);
                fr1.setVisibility(v.VISIBLE);
                break;

            case R.id.checkBox2:
                ch1.setVisibility(v.INVISIBLE);
                ch2.setVisibility(v.VISIBLE);
                fr2.setVisibility(v.VISIBLE);
                break;
        }

    }

    private void updateDisplay(int positon) {
        // TODO Auto-generated method stub
        switch (positon) {
            case 1:
                if(mHour+mMinute!=0) {
                    mTimeDisplay.setText(new StringBuilder().append(pad(mHour)).append(":").append(pad(mMinute)));
                }
            case 2:
                if(mHour2+mMinute2!=0) {
                    mTimeDisplay2.setText(new StringBuilder().append(pad(mHour2)).append(":").append(pad(mMinute2)));
                }
            case 3:
                if(mHour3+mMinute3!=0) {
                    mTimeDisplay3.setText(new StringBuilder().append(pad(mHour3)).append(":").append(pad(mMinute3)));
                }
            case 4:
                if(mHour4+mMinute4!=0) {
                    mTimeDisplay4.setText(new StringBuilder().append(pad(mHour4)).append(":").append(pad(mMinute4)));
                }
            case 5:
                if(mHour5+mMinute5!=0) {
                    mTimeDisplay5.setText(new StringBuilder().append(pad(mHour5)).append(":").append(pad(mMinute5)));
                }
        }

    }

    private static String pad(int c) {
        // TODO Auto-generated method stub
        if (c >= 10) {
            return String.valueOf(c);
        } else
            return "0" + String.valueOf(c);
    }

    private TimePickerDialog.OnTimeSetListener mTimeSetListener = new TimePickerDialog.OnTimeSetListener() {

        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            // TODO Auto-generated method stub
            mHour = hourOfDay;
            mMinute = minute;

            Time.set(Calendar.HOUR_OF_DAY, mHour);
            Log.d("check", "1 = " + hourOfDay);
            Time.set(Calendar.MINUTE, mMinute-1);
            Log.d("check", "2 = " + minute);
            Time.set(Calendar.SECOND, 0);
            updateDisplay(1);
        }
    };
    private TimePickerDialog.OnTimeSetListener mTimeSetListener2 = new TimePickerDialog.OnTimeSetListener() {

        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            // TODO Auto-generated method stub
            mHour2 = hourOfDay;
            mMinute2 = minute;
            updateDisplay(2);
        }
    };
    private TimePickerDialog.OnTimeSetListener mTimeSetListener3 = new TimePickerDialog.OnTimeSetListener() {

        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            // TODO Auto-generated method stub
            mHour3 = hourOfDay;
            mMinute3 = minute;
            updateDisplay(3);
        }
    };
    private TimePickerDialog.OnTimeSetListener mTimeSetListener4 = new TimePickerDialog.OnTimeSetListener() {

        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            // TODO Auto-generated method stub
            mHour4 = hourOfDay;
            mMinute4 = minute;
            updateDisplay(4);
        }
    };
    private TimePickerDialog.OnTimeSetListener mTimeSetListener5 = new TimePickerDialog.OnTimeSetListener() {

        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            // TODO Auto-generated method stub
            mHour5 = hourOfDay;
            mMinute5 = minute;
            updateDisplay(5);
        }
    };


    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case 1:
                return new TimePickerDialog(this, mTimeSetListener, mHour, mMinute, false);
            case 2:
                return new TimePickerDialog(this, mTimeSetListener2, mHour2, mMinute2, false);
            case 3:
                return new TimePickerDialog(this, mTimeSetListener3, mHour3, mMinute3, false);
            case 4:
                return new TimePickerDialog(this, mTimeSetListener4, mHour4, mMinute4, false);
            case 5:
                return new TimePickerDialog(this, mTimeSetListener5, mHour5, mMinute5, false);
            default:
                return null;
        }
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Add Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.example.lee.medichine_alarm/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Add Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.example.lee.medichine_alarm/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
}




