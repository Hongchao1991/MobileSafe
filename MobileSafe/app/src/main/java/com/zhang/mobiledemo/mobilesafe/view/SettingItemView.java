package com.zhang.mobiledemo.mobilesafe.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhang.mobiledemo.mobilesafe.R;

/**
 * 设置中的自定义控件
 * Created by Android_develop on 2017/3/8.
 */

public class SettingItemView extends RelativeLayout {

    private TextView tvTitle;
    private TextView tvDesc;
    private CheckBox cbStatus;

    private static final String NAMESPACE="http://schemas.android.com/apk/res-auto";
    private String mTitle;
    private String mDescOn;
    private String mDescOff;

    public SettingItemView(Context context) {
        super(context);
        initView();
    }

    public SettingItemView(Context context, AttributeSet attrs) {
        super(context, attrs);

        //根据属性名称获取属性的值
        mTitle = attrs.getAttributeValue(NAMESPACE,"title");
        //根据属性名称获取属性的值
        mDescOn = attrs.getAttributeValue(NAMESPACE,"desc_on");
        //根据属性名称获取属性的值
        mDescOff = attrs.getAttributeValue(NAMESPACE,"desc_off");

        initView();


    }

    public SettingItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    /**
     * 初始化布局
     */
    private void initView(){
        //将自定义的view_item_setting布局文件，给RelativeLayout
        View.inflate(getContext(), R.layout.view_item_setting,this);
        tvTitle = (TextView)findViewById(R.id.tv_title);
        tvDesc = (TextView)findViewById(R.id.tv_desc);
        cbStatus = (CheckBox)findViewById(R.id.cb_status);

        setTitle(mTitle);
    }

    public boolean isChecked(){
        return cbStatus.isChecked();
    }

    public void setChecked(boolean checked){
        //根据选择的状态，更新描述
        if (checked){
            setDesc(mDescOn);
        }else {
            setDesc(mDescOff);
        }
    }

    public void setTitle(String title){
        tvTitle.setText(title);
    }

    public void setDesc(String desc){
        tvDesc.setText(desc);
    }
}
