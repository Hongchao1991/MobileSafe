package com.zhang.mobiledemo.mobilesafe.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.zhang.mobiledemo.mobilesafe.R;

public class Setup4Activity extends Activity {

    private SharedPreferences mPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup4);
        mPref = getSharedPreferences("config", MODE_PRIVATE);
    }


    //下一步
    public void next(View view){
        startActivity(new Intent(this,LostFindActivity.class));

        //更新SP，表示已经设置完手机向导了，下次不需要再设置
        mPref.edit().putBoolean("configed",true).commit();

        finish();
    }
    //上一步
    public void previous(View view){

        startActivity(new Intent(this,Setup3Activity.class));
        finish();
    }
}
