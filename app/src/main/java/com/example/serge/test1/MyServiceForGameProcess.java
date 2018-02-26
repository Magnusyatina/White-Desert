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


    @Override
    public int onStartCommand(Intent intent, int flags, int stardId){
        Toast.makeText(this, "Служба запущена", Toast.LENGTH_SHORT).show();
        Intent notisfactionIntent = new Intent(this, MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notisfactionIntent, PendingIntent.FLAG_CANCEL_CURRENT);
        final NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setSmallIcon( R.mipmap.ic_launcher ).
                setAutoCancel( true)
                .setTicker( "Оповещение" )
                .setContentText( "Джозеф ожидает" )
                .setContentIntent( contentIntent )
                .setWhen( System.currentTimeMillis()+20000 )
                .setContentTitle( "White Desert" )
                .setDefaults( Notification.DEFAULT_ALL );
        final NotificationManager notificationManager = (NotificationManager) getSystemService( Context.NOTIFICATION_SERVICE );

        new Handler( Looper.getMainLooper()).postDelayed( new Runnable() {
            @Override
            public void run() {
                notificationManager.notify( NOTIFY_ID, builder.build() );
            }
        },25000 );
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
