package com.example.serge.test1;

import android.content.Intent;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

/**
 * Created by sergey37192 on 28.02.2018.
 */

public class SplashActivity extends AppCompatActivity implements Runnable {
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate( savedInstanceState );
        setContentView(R.layout.splash_activity);
        ((Animatable)((ImageView)findViewById(R.id.splash_image)).getDrawable()).start();
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(this);

    }

    @Override
    public void run() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
