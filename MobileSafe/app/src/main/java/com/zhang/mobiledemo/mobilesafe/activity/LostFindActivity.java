package com.zhang.mobiledemo.mobilesafe.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.zhang.mobiledemo.mobilesafe.R;

public class LostFindActivity extends Activity {

    private SharedPreferences mPrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPrefs = getSharedPreferences("config", MODE_PRIVATE);
        boolean configed = mPrefs.getBoolean("configed",false);
        if (configed){
            setContentView(R.layout.activity_lost_find);
        }else {
            startActivity(new Intent(LostFindActivity.this,Setup1Activity.class));
            finish();
        }
    }
}
