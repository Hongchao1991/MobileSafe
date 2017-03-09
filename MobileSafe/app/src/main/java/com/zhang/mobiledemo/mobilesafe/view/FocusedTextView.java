package com.zhang.mobiledemo.mobilesafe.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * 获取焦点的TextView
 * Created by Android_develop on 2017/3/8.
 */

public class FocusedTextView extends TextView {

    public FocusedTextView(Context context) {
        super(context);

    }

    //有属性时的方法
    public FocusedTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    //有style样式的方法
    public FocusedTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 表示有没有获取焦点
     * 跑马灯流动字体，首先判断此函数是否有焦点，是true字体才会动。
     * 所以我们强制TextView有焦点
     * @return
     */
    @Override
    public boolean isFocused() {
        return true;
    }
}
