package com.zhang.mobiledemo.mobilesafe.activity;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.zhang.mobiledemo.mobilesafe.R;
import com.zhang.mobiledemo.mobilesafe.view.SettingItemView;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class SettingActivity extends Activity {

    @InjectView(R.id.siv_update)
    SettingItemView sivUpdate;//设置升级
    private SharedPreferences mPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.inject(this);
        mPref = getSharedPreferences("config", MODE_PRIVATE);
//        sivUpdate.setTitle("自动更新设置");
        boolean autoUpdate = mPref.getBoolean("auto_update", true);
        if (autoUpdate) {
            sivUpdate.setChecked(true);
//            sivUpdate.setDesc("自动更新已开启");

        } else {
            sivUpdate.setChecked(false);
//            sivUpdate.setDesc("自动更新已关闭");

        }


        sivUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //判断勾选状态
                if (sivUpdate.isChecked()) {
                    sivUpdate.setChecked(false);
//                    sivUpdate.setDesc("自动更新已关闭");
                    mPref.edit().putBoolean("auto_update", false).commit();
                } else {
                    sivUpdate.setChecked(true);
//                    sivUpdate.setDesc("自动更新已开启");
                    mPref.edit().putBoolean("auto_update", true).commit();

                }

            }
        });
    }
}
