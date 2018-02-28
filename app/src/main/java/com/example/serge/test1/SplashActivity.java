package com.example.serge.test1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by sergey37192 on 28.02.2018.
 */

public class SplashActivity extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate( savedInstanceState );

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
