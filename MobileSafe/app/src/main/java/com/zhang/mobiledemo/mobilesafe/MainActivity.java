package com.zhang.mobiledemo.mobilesafe;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.zhang.mobiledemo.mobilesafe.activity.SplashActivity;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startActivity(new Intent(this, SplashActivity.class));
    }
}
