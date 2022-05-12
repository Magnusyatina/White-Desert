package org.magnusario.whitedesert;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import org.magnusario.whitedesert.activity.MainActivity;

/**
 * Сервис отвечает за оповещения о том, когда персонаж будет свободен
 */
public class GameProcessService extends Service {
    String gameStage;

    private static final int NOTIFY_ID = 101;
    String NOTIFICATION_CHANNEL_ID = "my_channel_id_01";
    private static long schedule_Time = 0;
    private static Handler cHandler = new Handler(Looper.getMainLooper());

    @Override
    public int onStartCommand(Intent intent, int flags, int stardId) {
        if (intent == null)
            stopSelf();

        schedule_Time = intent.getLongExtra("SCHEDULE_TIME", 0);
        long timer = schedule_Time - System.currentTimeMillis();

        if (timer <= 0)
            stopSelf();

        Toast.makeText(this, "Служба запущена", Toast.LENGTH_SHORT).show();
        Intent notisfactionIntent = new Intent(GameProcessService.this, MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(GameProcessService.this, 0, notisfactionIntent, PendingIntent.FLAG_CANCEL_CURRENT);
        final NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "My Notifications", NotificationManager.IMPORTANCE_DEFAULT);

            // Configure the notification channel.
            notificationChannel.setDescription("Channel description");
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(false);
            notificationManager.createNotificationChannel(notificationChannel);
        }

        final NotificationCompat.Builder builder = new NotificationCompat.Builder(GameProcessService.this, NOTIFICATION_CHANNEL_ID);
        builder.setSmallIcon(R.mipmap.ic_launcher).
                setAutoCancel(true)
                .setTicker("Оповещение")
                .setContentText("Джозеф ожидает")
                .setContentIntent(contentIntent)
                .setWhen(schedule_Time)
                .setContentTitle("White Desert")
                .setDefaults(Notification.DEFAULT_ALL)
                .setSmallIcon(R.drawable.bonfire_icon);

        cHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                notificationManager.notify(NOTIFY_ID, builder.build());
                stopSelf();
            }

        }, timer);


        return Service.START_REDELIVER_INTENT;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onDestroy() {
        Toast.makeText(this, "Служба остановлена",
                Toast.LENGTH_SHORT).show();
        cHandler.removeCallbacksAndMessages(null);
        super.onDestroy();
    }

}
