package com.sinostar.assistant.utils;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

/**
 * Fragment工具类
 */

public class FragmentUtil {
   AppCompatActivity activity;
   Fragment currentFragment;
   int container;

    public FragmentUtil(AppCompatActivity activity, Fragment currentFragment, int container){
        this.activity=activity;
        this.currentFragment=currentFragment;
        this.container=container;
    }

    /**
     * add fragment封装
     * @param fragmentManager  getFragmentManager()
     * @param fragment   目标Fragnent
     * @param id    Fragment的容器ID
     */
    public static void addFragment(FragmentManager fragmentManager, Fragment fragment, int id){
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(id, fragment);
        transaction.commit();
    }
    /**
     * Fragment切换
     */
    public void switchFragment(Fragment targetFragment) {
        if (targetFragment.equals(currentFragment)) {
            return;
        }
        FragmentTransaction transaction =activity.getSupportFragmentManager()
                .beginTransaction();
        if (!targetFragment.isAdded()) {// 如果这个fragment没被添加过
            transaction.add(container, targetFragment, targetFragment.getClass()
                    .getName());
        }
        if (targetFragment.isHidden()) {// 如果这个fragment是被隐藏的
            transaction.show(targetFragment);
        }
        if (currentFragment != null && currentFragment.isVisible()) {// 隐藏当前显示的fragment
            transaction.hide(currentFragment);
        }
        currentFragment = targetFragment;
        // 提交
        currentFragment = targetFragment;
        transaction.commit();
    }
}
