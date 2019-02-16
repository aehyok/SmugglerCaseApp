package com.sinostar.assistant.receiver;

import android.content.Context;
import android.os.Bundle;

import com.huawei.hms.support.api.push.PushReceiver;
import com.hyphenate.chat.EMClient;

/**
 * 华为推送
 */
public class HMSPushReceiver extends PushReceiver {
    private static final String TAG = HMSPushReceiver.class.getSimpleName();

    @Override
    public void onToken(Context context, String token, Bundle extras){
        //没有失败回调，假定token失败时token为null
        if(token != null && !token.equals("")){
            EMClient.getInstance().sendHMSPushTokenToServer("100604221", token);
        }else{

        }
    }
}
