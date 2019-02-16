package com.sinostar.assistant.utils;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.sinostar.assistant.ui.home.HomeActivity;

public class NotificationBroadcastReceiver extends BroadcastReceiver {

    public static final String TYPE = "type"; //这个type是为了Notification更新信息的，这个不明白的朋友可以去搜搜，很多

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        int type = intent.getIntExtra(TYPE, -1);

        if (type != -1) {
            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.cancel(type);
        }

        if (action.equals("notification_clicked")) {
            //处理点击事件
            Intent intent1 = new Intent(context, HomeActivity.class);//将要跳转的界面
            intent1.putExtra(Constent.PERSON_NAME,  intent.getStringExtra(Constent.PERSON_NAME));
            context.startActivity(intent);
        }

        if (action.equals("notification_cancelled")) {
            //处理滑动清除和点击删除事件

        }
    }
}

