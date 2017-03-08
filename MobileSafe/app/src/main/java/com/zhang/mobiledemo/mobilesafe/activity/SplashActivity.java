package com.zhang.mobiledemo.mobilesafe.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.zhang.mobiledemo.mobilesafe.R;
import com.zhang.mobiledemo.mobilesafe.utils.UtilsNetwork;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class SplashActivity extends Activity {


    @InjectView(R.id.tv_version)
    TextView tvVersion;
    @InjectView(R.id.progress)
    ProgressBar progress;


    private String mVersionName;
    private int mVersionCode;
    private String mDesc;
    private String mDownloadUrl;

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
                switch (msg.what){
                    case CODE_UPDATE_DIALOG:
                        showUpdateDailog();
                        break;
                    case CODE_NET_ERROR:
                        Toast.makeText(SplashActivity.this,"网络错误",Toast.LENGTH_SHORT).show();
                        break;
                    case CODE_JSON_ERROR:
                        Toast.makeText(SplashActivity.this,"JSON错误",Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        break;
                }

        }
    };

    private static final int CODE_UPDATE_DIALOG = 0;
    private static final int CODE_NET_ERROR=1;
    private static final int CODE_JSON_ERROR=2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.inject(this);

        tvVersion.setText("版本号：" + getVersion());
        checkVersion();
    }

    /**
     * 获取本地版本名称
     *
     * @return
     */
    private String getVersion() {
        PackageManager packageManager = getPackageManager();

        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(getPackageName(), 0);
            int versionCode = packageInfo.versionCode;
            String versionName = packageInfo.versionName;
            System.out.println("versionName:" + versionName);
            System.out.println("versionCode:" + versionCode);
            return versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return "";
    }

    /**
     * 获取本地版本号
     *
     * @return
     */
    private int getVersionCode() {
        PackageManager packageManager = getPackageManager();

        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(getPackageName(), 0);
            int versionCode = packageInfo.versionCode;
            return versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return -1;
    }


    private void checkVersion(){

        final Message msg = Message.obtain();

        new Thread(){
            @Override
            public void run() {
                try {
                    String data = UtilsNetwork.connectNetWork("http://10.0.2.2:8080/update.json");
                    if (data!=null){
                        JSONObject jsonObject = new JSONObject(data);
                        mVersionName = jsonObject.getString("versionName");
                         mVersionCode=jsonObject.getInt("versionCode");
                         mDesc = jsonObject.getString("description");
                         mDownloadUrl  =jsonObject.getString("downloadUrl");
                        System.out.println("code:"+mVersionCode);
                        if (mVersionCode>getVersionCode()){//判断是否有更新
                            msg.what=CODE_UPDATE_DIALOG;
                        }
                    }
                } catch (IOException e) {
                    msg.what=CODE_NET_ERROR;
                    e.printStackTrace();
                } catch (JSONException e) {
                    msg.what=CODE_JSON_ERROR;
                    e.printStackTrace();
                }finally {
                    mHandler.sendMessage(msg);
                }
            }
        }.start();

    }

    protected void showUpdateDailog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("最新版本"+mVersionName);
        builder.setMessage(mDesc);
        builder.setPositiveButton("立即更新", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                System.out.println();
            }
        });

        builder.setNegativeButton("以后再说", null);

        builder.show();
    }

}
