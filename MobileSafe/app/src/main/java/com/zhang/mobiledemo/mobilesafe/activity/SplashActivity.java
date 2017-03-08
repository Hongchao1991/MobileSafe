package com.zhang.mobiledemo.mobilesafe.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.zhang.mobiledemo.mobilesafe.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SplashActivity extends Activity {


    @BindView(R.id.tv_version)
    TextView tvVersion;
    @BindView(R.id.progress)
    ProgressBar progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);

        tvVersion.setText(getVersion());
    }

    /**
     * 获取版本号
     * @return
     */
    private String getVersion(){
        String version = null;


        return version;
    }


}
