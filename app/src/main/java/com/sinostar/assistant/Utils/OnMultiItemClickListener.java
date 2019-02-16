package com.sinostar.assistant.utils;

import android.view.View;
import android.widget.AdapterView;

public abstract class OnMultiItemClickListener implements AdapterView.OnItemClickListener{
    // 两次点击按钮之间的点击间隔不能少于1000毫秒
    private static final int MIN_CLICK_DELAY_TIME = 1000;
    private static long lastClickTime;

    public abstract void onItemClick1(AdapterView<?> adapterView, View view, int i, long l);

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        long curClickTime = System.currentTimeMillis();
        if((curClickTime - lastClickTime) >= MIN_CLICK_DELAY_TIME) {
            // 超过点击间隔后再将lastClickTime重置为当前点击时间
            lastClickTime = curClickTime;
            onItemClick1( adapterView,  view,  i,  l);
        }
    }
}