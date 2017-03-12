package com.zhang.mobiledemo.mobilesafe.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.zhang.mobiledemo.mobilesafe.R;

public class Setup1Activity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup1);
    }


    //下一步
    public void next(View view){
        startActivity(new Intent(Setup1Activity.this,Setup2Activity.class));
        finish();
    }
}
