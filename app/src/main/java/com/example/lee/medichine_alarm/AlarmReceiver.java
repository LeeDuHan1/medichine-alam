package com.example.lee.medichine_alarm;
import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.PowerManager;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.Window;
import android.widget.Toast;

import java.io.File;

public class AlarmReceiver extends BroadcastReceiver {
    static int mount = 0;
    Context context;
    String title;
    @Override
    public void onReceive(final Context context, Intent intent) {
            String name = intent.getStringExtra("name");
            this.title = name;
            this.context = context;
            PowerManager powerManager = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
            PowerManager.WakeLock wakeLock = powerManager.newWakeLock(PowerManager.ACQUIRE_CAUSES_WAKEUP | PowerManager.SCREEN_BRIGHT_WAKE_LOCK,"");
            wakeLock.acquire();

            PendingIntent pendingIntent;
            Toast toast = Toast.makeText(context, "알람이 울립니다.", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.TOP, 0, 200);
            toast.show();
            wakeLock.release();

    try {
        intent = new Intent(context, removeActivity.class);
        pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        intent.putExtra("name", title);
        Log.d("ServicePending++ : ", "" + pendingIntent.toString());
        pendingIntent.send();

    } catch (PendingIntent.CanceledException e) {
        e.printStackTrace();
    }
        notification();
    }

    void notification() {
    Intent intent = new Intent();
    Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
    Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.pills);
    PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_ONE_SHOT);

    //노티피케이션 빌더 : 위에서 생성한 이미지나 텍스트, 사운드등을 설정해줍니다.
    NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context)
            .setSmallIcon(R.drawable.pills)
            .setLargeIcon(bitmap)
            .setContentTitle(title)
            .setContentText("복용하세요~")
            .setAutoCancel(true)
            .setSound(soundUri)
            .setContentIntent(pendingIntent);

    NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
    //노티피케이션을 생성합니다.
         notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }

}
