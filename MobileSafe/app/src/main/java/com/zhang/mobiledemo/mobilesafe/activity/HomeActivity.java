package com.zhang.mobiledemo.mobilesafe.activity;

import android.app.Activity;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhang.mobiledemo.mobilesafe.R;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class HomeActivity extends Activity {

    @InjectView(R.id.gv_home)
    GridView gvHome;


    private String[] mItems = new String[]{"手机防盗","通讯卫士",
            "软件管理","进程管理","流量统计","手机杀毒","缓存管理","高级工具","设置中心"};
    private int[] mPics = new int[]{R.mipmap.home_safe,R.mipmap.home_callmsgsafe,
            R.mipmap.home_apps,R.mipmap.home_taskmanager,R.mipmap.home_netmanager,
    R.mipmap.home_trojan,R.mipmap.home_sysoptimize,R.mipmap.home_tools,R.mipmap.home_settings};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.inject(this);

        gvHome.setAdapter(new HomeAdapter());
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
            TextView textView = (TextView)view.findViewById(R.id.tv_item);
            textView.setText(mItems[position]);
            imageView.setImageResource(mPics[position]);
            return view;
        }


    }
}
