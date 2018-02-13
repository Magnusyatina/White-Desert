package com.example.serge.test1;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MyServiceForGameProcess extends Service {
    String gameStage;




    @Override
    public int onStartCommand(Intent intent, int flags, int stardId){
        Toast.makeText(this, "Служба запущена", Toast.LENGTH_SHORT).show();
        new Handler( Looper.getMainLooper()).postDelayed( new Runnable() {
            @Override
            public void run() {
                Toast.makeText( MyServiceForGameProcess.this, "Привет мир", Toast.LENGTH_SHORT ).show();
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
