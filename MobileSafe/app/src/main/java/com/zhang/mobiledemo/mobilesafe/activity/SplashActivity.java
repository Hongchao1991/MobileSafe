package com.zhang.mobiledemo.mobilesafe.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.zhang.mobiledemo.mobilesafe.R;
import com.zhang.mobiledemo.mobilesafe.utils.UtilsNetwork;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class SplashActivity extends Activity {


    @InjectView(R.id.tv_version)
    TextView tvVersion;
    @InjectView(R.id.progress)
    ProgressBar progress;
    @InjectView(R.id.tv_progress)
    TextView tvProgress;


    private String mVersionName;
    private int mVersionCode;
    private String mDesc;
    private String mDownloadUrl;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case CODE_UPDATE_DIALOG:
                    showUpdateDailog();
                    break;
                case CODE_NET_ERROR:
                    Toast.makeText(SplashActivity.this, "网络错误", Toast.LENGTH_SHORT).show();
                    enterHome();
                    break;
                case CODE_JSON_ERROR:
                    Toast.makeText(SplashActivity.this, "JSON错误", Toast.LENGTH_SHORT).show();
                    enterHome();
                    break;
                case CODE_ENTER_HOME:
                    enterHome();
                    break;
                default:
                    break;
            }

        }
    };

    private static final int CODE_UPDATE_DIALOG = 0;
    private static final int CODE_NET_ERROR = 1;
    private static final int CODE_JSON_ERROR = 2;
    private static final int CODE_ENTER_HOME = 3;
    private SharedPreferences mPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.inject(this);

        tvVersion.setText("版本号：" + getVersion());
        //判断是否自动更新
        mPref = getSharedPreferences("config", MODE_PRIVATE);
        boolean autoUpdate = mPref.getBoolean("auto_update",true);
        if (autoUpdate) {
            checkVersion();
        }else {
            mHandler.sendEmptyMessageDelayed(CODE_ENTER_HOME,2000);//发送延时两秒钟消息,进入主界面
        }
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

    /**
     * 下载文件
     * 需要xUtils.jar
     */
    protected void download() {
        //判断SD卡是否挂载
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            tvProgress.setVisibility(View.VISIBLE);
            String target = Environment.getExternalStorageDirectory() + "/update.apk";
            HttpUtils utils = new HttpUtils();
            utils.download(mDownloadUrl, target, new RequestCallBack<File>() {
                @Override
                public void onSuccess(ResponseInfo<File> responseInfo) {

                    installAPK(responseInfo.result);
                }

                @Override
                public void onLoading(long total, long current, boolean isUploading) {
                    super.onLoading(total, current, isUploading);
                    tvProgress.setText("下载进度"+current*100/total+"%");
                }

                @Override
                public void onFailure(HttpException e, String s) {

                }
            });
        } else {
            Toast.makeText(SplashActivity.this, "没有SD", Toast.LENGTH_SHORT).show();

        }

    }

    /**
     * 安装apk
     * 有可能存在签名包冲突问题，包名相同。
     * 正式签名，有效期比较长，大于25年。
     * 测试签名：debug.keystore,有默认别名：Android；密码是：androiddebugkey  在IDE直接运行apk系统默认使用测试签名
     * 不要丢失签名文件。
     */
    protected void installAPK(File file){
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.setDataAndType(Uri.fromFile(file),
                "application/vnd.android.package-archive");
//        startActivity(intent);
        startActivityForResult(intent,0);//如果用户取消安装，会返回结果onActivityResult 回掉方法
    }

    //用户取消安装回掉方法
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        enterHome();
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 检查版本更新
     */
    private void checkVersion() {

        final Message msg = Message.obtain();
        final long startTime = System.currentTimeMillis();
        new Thread() {
            @Override
            public void run() {
                try {
                    String data = UtilsNetwork.connectNetWork("http://10.0.2.2:8080/update.json");
                    if (data != null) {
                        JSONObject jsonObject = new JSONObject(data);
                        mVersionName = jsonObject.getString("versionName");
                        mVersionCode = jsonObject.getInt("versionCode");
                        mDesc = jsonObject.getString("description");
                        mDownloadUrl = jsonObject.getString("downloadUrl");
                        System.out.println("code:" + mVersionCode);
                        if (mVersionCode > getVersionCode()) {//判断是否有更新
                            msg.what = CODE_UPDATE_DIALOG;
                        } else {
                            msg.what = CODE_ENTER_HOME;
                        }
                    }
                } catch (IOException e) {
                    msg.what = CODE_NET_ERROR;
                    e.printStackTrace();
                } catch (JSONException e) {
                    msg.what = CODE_JSON_ERROR;
                    e.printStackTrace();
                } finally {
                    long endTime = System.currentTimeMillis();
                    long useTime = endTime - startTime;

                    if (useTime < 2000) {
                        //强制休眠一段时间，保证闪屏页展示两秒钟
                        try {
                            Thread.sleep(2000 - useTime);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                    mHandler.sendMessage(msg);
                }
            }
        }.start();

    }

    /**
     * 提示更新提示框
     */
    protected void showUpdateDailog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("最新版本" + mVersionName);
        builder.setMessage(mDesc);
        builder.setPositiveButton("立即更新", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                download();
            }
        });

        builder.setNegativeButton("以后再说", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                enterHome();
            }
        });

        //设置取消监听，点击返回键时会触发
        builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                enterHome();
            }
        });

        builder.show();
    }

    private void enterHome() {
        startActivity(new Intent(this, HomeActivity.class));
        this.finish();
    }

}
