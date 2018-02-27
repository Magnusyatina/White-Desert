package com.example.serge.test1;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.support.v4.app.NotificationCompat;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MyServiceForGameProcess extends Service {
    String gameStage;

    private static final int NOTIFY_ID = 101;
    private static long schedule_Time = 0;



    @Override
    public int onStartCommand(Intent intent, int flags, int stardId){
        schedule_Time = intent.getLongExtra( "SCHEDULE_TIME", 0 );
        Toast.makeText(this, "Служба запущена", Toast.LENGTH_SHORT).show();
        Intent notisfactionIntent = new Intent(MyServiceForGameProcess.this, MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(MyServiceForGameProcess.this, 0, notisfactionIntent, PendingIntent.FLAG_CANCEL_CURRENT);
        final NotificationCompat.Builder builder = new NotificationCompat.Builder(MyServiceForGameProcess.this);
        builder.setSmallIcon( R.mipmap.ic_launcher ).
                setAutoCancel( true)
                .setTicker( "Оповещение" )
                .setContentText( "Джозеф ожидает" )
                .setContentIntent( contentIntent )
                .setWhen( schedule_Time )
                .setContentTitle( "White Desert" )
                .setDefaults( Notification.DEFAULT_ALL );
        final NotificationManager notificationManager = (NotificationManager) getSystemService( Context.NOTIFICATION_SERVICE );

        long timer = schedule_Time - System.currentTimeMillis();
        if(timer>0)
        new Handler( Looper.getMainLooper()).postDelayed( new Runnable() {
            @Override
            public void run() {
                notificationManager.notify( NOTIFY_ID, builder.build() );
            }
        },timer );
      return Service.START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException( "Not yet implemented" );
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Toast.makeText(this, "Служба остановлена",
                Toast.LENGTH_SHORT).show();
    }

}
