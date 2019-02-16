package com.sinostar.assistant.receiver;

import android.content.Context;
import android.content.Intent;

import com.hyphenate.chat.EMMipushReceiver;
import com.sinostar.assistant.ui.home.HomeActivity;
import com.sinostar.assistant.utils.Constent;
import com.xiaomi.mipush.sdk.ErrorCode;
import com.xiaomi.mipush.sdk.MiPushClient;
import com.xiaomi.mipush.sdk.MiPushCommandMessage;
import com.xiaomi.mipush.sdk.MiPushMessage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 小米推送
 *
 */
public class MIUIMessageReceiver extends EMMipushReceiver {
    private String mRegId;
    private long mResultCode = -1;
    private String mReason;
    private String mCommand;
    private String mMessage;
    private String mTopic;
    private String mAlias;
    private String mUserAccount;
    private String mStartTime;
    private String mEndTime;


//    /**
//     * 用来接收服务器发送的透传消息
//     * @param context
//     * @param message
//     */
//    @Override
//    public void onReceivePassThroughMessage(Context context, MiPushMessage message) {
//        mMessage = message.getContent();
//        if(!TextUtils.isEmpty(message.getTopic())) {
//            mTopic=message.getTopic();
//        } else if(!TextUtils.isEmpty(message.getAlias())) {
//            mAlias=message.getAlias();
//        } else if(!TextUtils.isEmpty(message.getUserAccount())) {
//            mUserAccount=message.getUserAccount();
//        }
//    }
//
    /**
     * 用来接收服务器发来的通知栏消息（用户点击通知栏时触发）
     * @param context
     * @param message
     */
    @Override
    public void onNotificationMessageClicked(Context context, MiPushMessage message) {

        String[] bb = message.getContent().split(",");//将所有,符号截取出来变成一个数组
        String cc = null;//获取每个,之内的内容
        String[] dd = null;//获取每个:号的内容
        String key = null;
        String value = null;
        Map<String, String> dataMap=new HashMap<>();

        for (int j = 0; j < bb.length; j++) {
            cc = bb[j];//获取每个,的内容
            dd = cc.split(":");//拆分:号
            key = dd[0].substring(1, dd[0].length() - 1);//=号前面的值
            value = dd[1].substring(1, dd[1].length() - 1);//=号后面的值
            dataMap.put(key, value);//将值放入map中
        }

        String from=dataMap.get("f");
        String group=dataMap.get("g");
        String personName;

        if(group!=null&&!group.equals("")){ //群组
            personName=group;
        }else{
            personName=from;
        }
        Intent intent = new Intent(context, HomeActivity.class);//将要跳转的界面
        intent.putExtra(Constent.PERSON_NAME, personName);
        context.startActivity(intent);
    }

    /**
     * 用来接收服务器发来的通知栏消息（消息到达客户端时触发，并且可以接收应用在前台时不弹出通知的通知消息）
     * @param context
     * @param message
     */
    @Override
    public void onNotificationMessageArrived(Context context, MiPushMessage message) {

//        mMessage = message.getContent();
//        if(!TextUtils.isEmpty(message.getTopic())) {
//            mTopic=message.getTopic();
//        } else if(!TextUtils.isEmpty(message.getAlias())) {
//            mAlias=message.getAlias();
//        } else if(!TextUtils.isEmpty(message.getUserAccount())) {
//            mUserAccount=message.getUserAccount();
//        }
    }
//
//    /**
//     * 用来接收客户端向服务器发送命令消息后返回的响应
//     * @param context
//     * @param message
//     */
//    @Override
//    public void onCommandResult(Context context, MiPushCommandMessage message) {
//        String command = message.getCommand();
//        List<String> arguments = message.getCommandArguments();
//        String cmdArg1 = ((arguments != null && arguments.size() > 0) ? arguments.get(0) : null);
//        String cmdArg2 = ((arguments != null && arguments.size() > 1) ? arguments.get(1) : null);
//        if (MiPushClient.COMMAND_REGISTER.equals(command)) {
//            if (message.getResultCode() == ErrorCode.SUCCESS) {
//                mRegId = cmdArg1;
//            }
//        } else if (MiPushClient.COMMAND_SET_ALIAS.equals(command)) {
//            if (message.getResultCode() == ErrorCode.SUCCESS) {
//                mAlias = cmdArg1;
//            }
//        } else if (MiPushClient.COMMAND_UNSET_ALIAS.equals(command)) {
//            if (message.getResultCode() == ErrorCode.SUCCESS) {
//                mAlias = cmdArg1;
//            }
//        } else if (MiPushClient.COMMAND_SUBSCRIBE_TOPIC.equals(command)) {
//            if (message.getResultCode() == ErrorCode.SUCCESS) {
//                mTopic = cmdArg1;
//            }
//        } else if (MiPushClient.COMMAND_UNSUBSCRIBE_TOPIC.equals(command)) {
//            if (message.getResultCode() == ErrorCode.SUCCESS) {
//                mTopic = cmdArg1;
//            }
//        } else if (MiPushClient.COMMAND_SET_ACCEPT_TIME.equals(command)) {
//            if (message.getResultCode() == ErrorCode.SUCCESS) {
//                mStartTime = cmdArg1;
//                mEndTime = cmdArg2;
//            }
//        }
//    }
//
    /**
     * 用来接受客户端向服务器发送注册命令消息后返回的响应
     * @param context
     * @param message
     */
    @Override
    public void onReceiveRegisterResult(Context context, MiPushCommandMessage message) {
        String command = message.getCommand();
        List<String> arguments = message.getCommandArguments();
        String cmdArg1 = ((arguments != null && arguments.size() > 0) ? arguments.get(0) : null);
        String cmdArg2 = ((arguments != null && arguments.size() > 1) ? arguments.get(1) : null);
        if (MiPushClient.COMMAND_REGISTER.equals(command)) {
            if (message.getResultCode() == ErrorCode.SUCCESS) {
                mRegId = cmdArg1;
            }
        }

    }
}
