package com.sinostar.assistant.utils;

import android.content.Context;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;
import com.sinostar.assistant.base.ApplicationUtil;
import com.sinostar.assistant.R;

import static com.sinostar.assistant.utils.AppScreenMgr.getScreenWidth;

/**
 * Toast工具类
 */

public class ToastUtil {
    private static Toast toast;
    public static void toast(String s) {
        View view= LayoutInflater.from(ApplicationUtil.getContext()).inflate(R.layout.toast,null);
        int itemWidth = (getScreenWidth(ApplicationUtil.getContext())) / 4 ;
        AbsListView.LayoutParams param = new AbsListView.LayoutParams(itemWidth, itemWidth);
        view.setLayoutParams(param);
        // 创建一个Toast提示信息
        final android.widget.Toast toast = new android.widget.Toast(ApplicationUtil.getContext());
        // 设置Toast的显示位置
        ImageView imageView=view.findViewById(R.id.toast_image);
        imageView.setBackgroundResource(R.drawable.ic_check_circle_white_48dp);
        TextView textView=view.findViewById(R.id.toast_text);
        textView.setText(s);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setView(view);
        // 设置Toast的显示时间
        toast.setDuration(android.widget.Toast.LENGTH_SHORT);
        toast.show();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                toast.cancel();
            }
        }, 1000);
    }


    public static void showToast(Context context, String content) {
        if (toast == null) {
            toast = Toast.makeText(context, content, Toast.LENGTH_SHORT);
        } else {

            toast.setText(content);
        }
        toast.show();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                toast.cancel();
            }
        }, 1000);
    }
}
