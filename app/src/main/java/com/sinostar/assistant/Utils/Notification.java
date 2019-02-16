package com.sinostar.assistant.utils;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;

import com.sinostar.assistant.ui.addressList.chat.helper.ChatDBHelper;
import com.sinostar.assistant.base.ApplicationUtil;
import com.sinostar.assistant.ui.home.HomeActivity;

public class Notification {

    /**
     * 通知栏提示
     * @param context 上下文
     * @param image 通知栏图片
     * @param userId 用户ID
     * @param content 通知栏正文
     */
    public static void showNotifiction(Context context, int image, String userId, String content,String GroupName) {
        int id=1;
        if(ApplicationUtil.getNotificationId()!=0){
            id=ApplicationUtil.getNotificationId()+1;

        };
        ApplicationUtil.setNotificationId(id);
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        Intent intent = new Intent(context, HomeActivity.class);//将要跳转的界面
//        Intent intent = new Intent();//只显示通知，无页面跳转
        intent.putExtra(Constent.PERSON_NAME,userId);
        builder.setAutoCancel(true);//点击后消失
        builder.setSmallIcon(image);//设置通知栏消息标题的头像
        builder.setDefaults(NotificationCompat.DEFAULT_ALL);//设置通知铃声
        builder.setPriority(android.app.Notification.PRIORITY_MAX);//设置最高权限
        builder.setVisibility(android.app.Notification.VISIBILITY_PUBLIC);
        builder.setContentTitle(GroupName!=null?GroupName:ChatDBHelper.getUserName(userId));
        builder.setContentText(content);
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            intent.setAction(Settings.ACTION_APP_NOTIFICATION_SETTINGS);
            intent.putExtra(Settings.EXTRA_APP_PACKAGE, context.getPackageName());
        }

        //利用PendingIntent来包装我们的intent对象,使其延迟跳转
        PendingIntent intentPend = PendingIntent.getActivity(context, id, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        builder.setContentIntent(intentPend);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channelId="1";
            String name="assistant_message";
            NotificationChannel mChannel = new NotificationChannel(channelId, name, NotificationManager.IMPORTANCE_HIGH);
            builder.setChannelId(channelId);
            manager.createNotificationChannel(mChannel);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder.setFullScreenIntent(intentPend, true);
        }

        manager.notify(id, builder.build());
    }
}
