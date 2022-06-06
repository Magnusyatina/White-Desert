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
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import org.magnusario.whitedesert.activity.MainActivity;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Сервис отвечает за оповещения о том, когда персонаж будет свободен
 */
public class GameProcessService extends Service {

    private static final int NOTIFY_ID = 101;

    private static final String NOTIFICATION_CHANNEL_ID = "my_channel_id_01";

    private NotificationManager notificationManager;

    private static long scheduleTime = 0;


    private ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);

    private boolean isHandled = false;

    @Override
    public int onStartCommand(Intent intent, int flags, int stardId) {
        if (intent == null)
            stopSelf();

        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        scheduleTime = intent.getLongExtra("SCHEDULE_TIME", 0);
        long timer = scheduleTime - System.currentTimeMillis();

        if (timer <= 0) {
            Toast.makeText(this, "Сервис не будет запущен, таймер меньше нуля",
                    Toast.LENGTH_SHORT).show();
            stopSelf();
            return Service.START_NOT_STICKY;
        }

        Toast.makeText(this, "Служба запущена", Toast.LENGTH_SHORT).show();
        Intent notisfactionIntent = new Intent(GameProcessService.this, MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(GameProcessService.this, 0, notisfactionIntent, PendingIntent.FLAG_CANCEL_CURRENT);


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
                .setWhen(scheduleTime)
                .setContentTitle("White Desert")
                .setDefaults(Notification.DEFAULT_ALL)
                .setSmallIcon(R.drawable.bonfire_icon);

        executorService.schedule(() -> {
            notificationManager.notify(NOTIFY_ID, builder.build());
            stopSelf();
        }, timer, TimeUnit.MILLISECONDS);


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
        executorService.shutdownNow();
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        super.onTaskRemoved(rootIntent);
    }

}
