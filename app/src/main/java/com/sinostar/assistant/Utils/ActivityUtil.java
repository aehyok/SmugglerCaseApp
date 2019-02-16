package com.sinostar.assistant.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;

import java.util.List;

import static android.content.Context.ACTIVITY_SERVICE;

public class ActivityUtil implements Application.ActivityLifecycleCallbacks{

    private boolean isForground = true;
    /**
     * 判断某个界面是否在前台
     *
     * @param context   Context
     * @param className 界面的类名
     * @return 是否在前台显示
     */
    public static boolean isForeground(Context context, String className) {
        if (context == null || TextUtils.isEmpty(className))
            return false;
        ActivityManager am = (ActivityManager) context.getSystemService(ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> list = am.getRunningTasks(1);
//        boolean flag=false;
        for (ActivityManager.RunningTaskInfo taskInfo : list) {
            if (taskInfo.topActivity.getShortClassName().contains(className)) { // 说明它已经启动了
//                flag = true;
                return true;
            }
        }
        return false;
    }
    public static boolean isActivityForeground(Context context, String className){
        return isForeground(context,className);
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle bundle) {
        isForground = true;
    }

    @Override
    public void onActivityStarted(Activity activity) {

    }

    @Override
    public void onActivityResumed(Activity activity) {

    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {
        isForground = false;
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {

    }
}
