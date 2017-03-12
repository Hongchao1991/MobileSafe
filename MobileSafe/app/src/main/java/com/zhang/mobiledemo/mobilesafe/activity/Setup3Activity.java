package com.zhang.mobiledemo.mobilesafe.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.zhang.mobiledemo.mobilesafe.R;

public class Setup3Activity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup3);
    }


    //下一步
    public void next(View view){
        startActivity(new Intent(this,Setup4Activity.class));
        finish();

    }
    //上一步
    public void previous(View view){
        startActivity(new Intent(this,Setup2Activity.class));
        finish();
    }
}
