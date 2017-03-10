package com.zhang.mobiledemo.mobilesafe.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.zhang.mobiledemo.mobilesafe.R;
import com.zhang.mobiledemo.mobilesafe.utils.MD5Utils;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class HomeActivity extends Activity {

    @InjectView(R.id.gv_home)
    GridView gvHome;
    @InjectView(R.id.tv_marque)
    TextView tvMarque;


    private String[] mItems = new String[]{"手机防盗", "通讯卫士",
            "软件管理", "进程管理", "流量统计", "手机杀毒", "缓存管理", "高级工具", "设置中心"};
    private int[] mPics = new int[]{R.mipmap.home_safe, R.mipmap.home_callmsgsafe,
            R.mipmap.home_apps, R.mipmap.home_taskmanager, R.mipmap.home_netmanager,
            R.mipmap.home_trojan, R.mipmap.home_sysoptimize, R.mipmap.home_tools, R.mipmap.home_settings};
//    private EditText et_password;
//    private EditText et_confirm;
//    private AlertDialog dialog;
    private SharedPreferences mPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.inject(this);

        mPref = getSharedPreferences("config", MODE_PRIVATE);
        setTextMarquee(tvMarque);
        gvHome.setAdapter(new HomeAdapter());
        gvHome.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0://手机防盗
                        showPasswordDialog();
                        break;
                    case 8://设置中心
                    startActivity(new Intent(HomeActivity.this,SettingActivity.class));
                        break;
                    default:
                        break;
                }
            }
        });
    }

    class HomeAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return mItems.length;
        }

        @Override
        public Object getItem(int position) {
            return mItems[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = View.inflate(HomeActivity.this, R.layout.item_home_list, null);
            ImageView imageView = (ImageView) view.findViewById(R.id.iv_item);
            TextView textView = (TextView) view.findViewById(R.id.tv_item);
            textView.setText(mItems[position]);
            imageView.setImageResource(mPics[position]);
            return view;
        }
    }


    private void showPasswordDialog(){
        //判断是否设置密码
        String savePasswrd = mPref.getString("password",null);
        if (TextUtils.isEmpty(savePasswrd)) {
            showPasswordSetDialog();
        }else {
            showPasswordInputDialog(savePasswrd);
        }
    }

    /**
     * 输入密码
     */
    private void showPasswordInputDialog(final String savePasswrd) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final AlertDialog dialog = builder.create();

        View view = View.inflate(this,R.layout.dailog_input_password,null);


        Button btnOk = (Button)view.findViewById(R.id.btn_ok);
        Button btnCancel = (Button)view.findViewById(R.id.btn_cancel);

        final EditText et_password = (EditText)view.findViewById(R.id.et_password);



        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String password = et_password.getText().toString();
                String savePassword = mPref.getString("password",null);
                System.out.println("md5--->"+savePassword);
                if (TextUtils.isEmpty(password)){
                    Toast.makeText(HomeActivity.this,"密码不能为空",Toast.LENGTH_SHORT).show();
                }else {
                    if (MD5Utils.encode(password).equals(savePassword)){
                        Toast.makeText(HomeActivity.this,"登录成功"+savePassword,Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }else {
                        Toast.makeText(HomeActivity.this,"密码错误",Toast.LENGTH_SHORT).show();

                    }
                }
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();//隐藏dialog
            }
        });


//        dialog.setView(view);
        dialog.setView(view,0,0,0,0);//设置边距为0， 保证在2.x版本运行没问题
        dialog.show();
    }

    /**
     * 设置密码弹窗
     */
    private void showPasswordSetDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final AlertDialog dialog = builder.create();

        View view = View.inflate(this,R.layout.dailog_set_password,null);


        Button btnOk = (Button)view.findViewById(R.id.btn_ok);
        Button btnCancel = (Button)view.findViewById(R.id.btn_cancel);

        final EditText et_password = (EditText)view.findViewById(R.id.et_password);
        final EditText et_confirm = (EditText)view.findViewById(R.id.et_password_confirm);



        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String password = et_password.getText().toString();
                String confirm = et_confirm.getText().toString();

                if ( (!TextUtils.isEmpty(password))  && !TextUtils.isEmpty(confirm)){
                    if (password.equals(confirm)){
                        Toast.makeText(HomeActivity.this,"密码设置成功",Toast.LENGTH_SHORT).show();
                        mPref.edit().putString("password", MD5Utils.encode(password)).commit();
                        dialog.dismiss();
                    }else {
                        Toast.makeText(HomeActivity.this,"两次密码不一致",Toast.LENGTH_SHORT).show();

                    }
                }else {
                    Toast.makeText(HomeActivity.this,"密码不能为空",Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();//隐藏dialog
            }
        });


//        dialog.setView(view);
        dialog.setView(view,0,0,0,0);//设置边距为0， 保证在2.x版本运行没问题
        dialog.show();
    }

    public static void setTextMarquee(TextView textView) {
        if (textView != null) {
            textView.setEllipsize(TextUtils.TruncateAt.MARQUEE);
            textView.setSingleLine(true);
            textView.setSelected(true);
            textView.setFocusable(true);
            textView.setFocusableInTouchMode(true);
        }
    }
}
