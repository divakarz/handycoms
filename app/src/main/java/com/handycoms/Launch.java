package com.handycoms;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Launch extends AppCompatActivity {


    private static int SPLASH_TIME_OUT = 1000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_launch);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(Launch.this,Languages.class);
                startActivity(intent);
                finish();
            }
        },SPLASH_TIME_OUT);
    }
}
