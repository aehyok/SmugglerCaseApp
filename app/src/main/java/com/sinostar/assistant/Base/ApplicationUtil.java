package com.sinostar.assistant.base;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Process;


import com.hyphenate.EMCallBack;
import com.hyphenate.EMConnectionListener;
import com.hyphenate.EMError;
import com.hyphenate.EMGroupChangeListener;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMMucSharedFile;
import com.hyphenate.chat.EMOptions;
import com.hyphenate.chat.EMTextMessageBody;
import com.hyphenate.util.NetUtils;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechUtility;
import com.lzy.imagepicker.ImagePicker;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreator;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreator;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.sinostar.assistant.ui.DialogActivity;
import com.sinostar.assistant.R;
import com.sinostar.assistant.ui.addressList.chat.helper.ChatDBHelper;
import com.sinostar.assistant.ui.addressList.chat.helper.ChatHelper;
import com.sinostar.assistant.ui.image.ImageSelectImageLoader;
import com.sinostar.assistant.utils.ActivityUtil;
import com.sinostar.assistant.utils.Constent;
import com.sinostar.assistant.utils.DateUtil;
import com.sinostar.assistant.utils.Notification;
import com.uuzuche.lib_zxing.activity.ZXingLibrary;
import com.xiaomi.mipush.sdk.MiPushClient;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static android.app.ProgressDialog.show;



public class ApplicationUtil extends Application {
    static Context context;
    private static String waterMarkText;  //全局水印文字
    private static String userId;     //登陆后返回的useId
    private static String bzid;    //文书列表的bzid
    private static String actionId;//文书列表的actionId
    private static int approveType;  //移动审批的类型
    private static boolean isDocSubmit; //文书审批是否提交
    private static boolean isLinkSubmit; //环节回退是否提交
    private static boolean isReportSubmit; //报上级是否提交
    private static Map<String,Boolean> isSetTop; //是否置顶
    private static Map<String,Boolean> isBeQuiet; //是否请勿打扰


    public static Map<String, Boolean> getIsSetTop() {
        return isSetTop;
    }

    public static void setIsSetTop(Map<String, Boolean> isSetTop) {
        ApplicationUtil.isSetTop = isSetTop;
    }

    public static Map<String, Boolean> getIsBeQuiet() {
        return isBeQuiet;
    }

    public static void setIsBeQuiet(Map<String, Boolean> isBeQuiet) {
        ApplicationUtil.isBeQuiet = isBeQuiet;
    }



    private static String chatId;   //环信id
    private static int expires_in;   //token有效期
    private static boolean isFirstPlay; //视频是否第一次点开
    private static int notificationId;  //通知ID
    private static OnMessageReceiveListener onMessageReceiveListener;
    private static OnChatMessageReceiveListener onChatMessageReceiveListener;
    private EMMessageListener msgListener;
    private EMGroupChangeListener groupListener;

    public static int getNotificationId() {
        return notificationId;
    }

    public static void setNotificationId(int notificationId) {
        ApplicationUtil.notificationId = notificationId;
    }

    public static boolean isIsFirstPlay() {
        return isFirstPlay;
    }

    public static void setIsFirstPlay(boolean isFirstPlay) {
        ApplicationUtil.isFirstPlay = isFirstPlay;
    }

    public static int getExpires_in() {
        return expires_in;
    }

    public static void setExpires_in(int expires_in) {
        ApplicationUtil.expires_in = expires_in;
    }

    public static String getChatId() {
        return chatId;
    }

    public static void setChatId(String chatId) {
        ApplicationUtil.chatId = chatId;
    }



    public static boolean isIsReportSubmit() {
        return isReportSubmit;
    }

    public static void setIsReportSubmit(boolean isReportSubmit) {
        ApplicationUtil.isReportSubmit = isReportSubmit;
    }


    public static boolean isIsLinkSubmit() {
        return isLinkSubmit;
    }

    public static void setIsLinkSubmit(boolean isLinkSubmit) {
        ApplicationUtil.isLinkSubmit = isLinkSubmit;
    }


    public static int getApproveType() {
        return approveType;
    }

    public static void setApproveType(int approveType) {
        ApplicationUtil.approveType = approveType;
    }


    public static boolean isIsDocSubmit() {
        return isDocSubmit;
    }

    public static void setIsDocSubmit(boolean isDocSubmit) {
        ApplicationUtil.isDocSubmit = isDocSubmit;
    }


    public static String getBzid() {
        return bzid;
    }

    public static void setBzid(String bzid) {
        ApplicationUtil.bzid = bzid;
    }

    public static String getActionId() {
        return actionId;
    }

    public static void setActionId(String actionId) {
        ApplicationUtil.actionId = actionId;
    }


    public static String getUserId() {
        return userId;
    }

    public static void setUserId(String userId) {
        ApplicationUtil.userId = userId;
    }

    public static String getWaterMarkText() {
        return waterMarkText;
    }

    public static void setWaterMarkText(String waterMarkText) {
        ApplicationUtil.waterMarkText = waterMarkText;
    }


    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();

        //讯飞语音初始化
        SpeechUtility.createUtility(context, SpeechConstant.APPID + "="+Constent.XUFEI_APPID);

        //二维码
        ZXingLibrary.initDisplayOpinion(this);


        int pid = android.os.Process.myPid();
        String processAppName = getAppName(pid);
        // 如果APP启用了远程的service，此application:onCreate会被调用2次
        // 为了防止环信SDK被初始化2次，加此判断会保证SDK被初始化1次
        // 默认的APP会在以包名为默认的process name下运行，如果查到的process name不是APP的process name就立即返回

        if (processAppName == null || !processAppName.equalsIgnoreCase(context.getPackageName())) {
            // 则此application::onCreate 是被service 调用的，直接返回
            return;
        }

        //初始化Mipush推送服务
        if(shouldInit()) {
            MiPushClient.registerPush(this, Constent.MI_APPID, Constent.MI_APP_KEY);
        }
            //初始化环信
        EMClient.getInstance().init(context, initChatOptions());

        //在做打包混淆时，关闭debug模式，避免消耗不必要的资源
//        EMClient.getInstance().setDebugMode(true);
        initImagePicker();


        createActivity();
        getMessageListener();
        getGroupListener();
    }

    private void initImagePicker() {
        //图片浏览器初始化
        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new ImageSelectImageLoader());   //设置图片加载器
        imagePicker.setShowCamera(false);  //显示拍照按钮
        imagePicker.setCrop(false);        //允许裁剪（单选才有效）
        imagePicker.setSaveRectangle(true); //是否按矩形区域保存
        imagePicker.setSelectLimit(100);    //选中数量限制
//        imagePicker.setStyle(CropImageView.Style.RECTANGLE);  //裁剪框的形状
//        imagePicker.setFocusWidth(800);   //裁剪框的宽度。单位像素（圆形自动取宽高最小值）
//        imagePicker.setFocusHeight(800);  //裁剪框的高度。单位像素（圆形自动取宽高最小值）
//        imagePicker.setOutPutX(1000);//保存文件的宽度。单位像素
//        imagePicker.setOutPutY(1000);//保存文件的高度。单位像素
    }

    private EMOptions initChatOptions() {
        EMOptions options = new EMOptions();
        // 默认添加好友时，是不需要验证的，改成需要验证
        options.setAcceptInvitationAlways(true);
        //是否自动登陆
        options.setAutoLogin(true);
        // 是否自动将消息附件上传到环信服务器，默认为True是使用环信服务器上传下载，如果设为 false，需要开发者自己处理附件消息的上传和下载
        options.setAutoTransferMessageAttachments(true);
        // 是否自动下载附件类消息的缩略图等，默认为 true 这里和上边这个参数相关联
        options.setAutoDownloadThumbnail(true);
        options.setMipushConfig(Constent.MI_APPID, Constent.MI_APP_KEY);//小米推送
        return options;
    }


    private void createActivity() {
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle bundle) {

            }

            @Override
            public void onActivityStarted(Activity activity) {

            }

            @Override
            public void onActivityResumed(Activity activity) {
                EMClient.getInstance().chatManager().addMessageListener(msgListener);//注册消息监听
                EMClient.getInstance().groupManager().addGroupChangeListener(groupListener);//注册群组事件监听
                //注册一个监听连接状态的listener
                EMClient.getInstance().addConnectionListener(new MyConnectionListener());
            }

            @Override
            public void onActivityPaused(Activity activity) {

            }

            @Override
            public void onActivityStopped(Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {

            }
        });

    }

    /**
     * 环信账号状态监听
     */
    private class MyConnectionListener implements EMConnectionListener {
        @Override
        public void onConnected() {

        }
        @Override
        public void onDisconnected(final int error) {

            if (error == EMError.USER_REMOVED) {
                // 显示帐号已经被移除
            } else if (error == EMError.USER_LOGIN_ANOTHER_DEVICE) {
                // 显示帐号在其他设备登录
                //登出账号，解绑第三方token
                EMClient.getInstance().logout(false, new EMCallBack() {
                    @Override
                    public void onSuccess() {

                    }
                    @Override
                    public void onError(int i, String s) {
                        EMClient.getInstance().logout(true);
                    }

                    @Override
                    public void onProgress(int i, String s) {

                    }
                });
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        if(isAdressActivity()){
                            startActivity(new Intent(context, DialogActivity.class));
                        }
                    }
                });
            } else {
                if (NetUtils.hasNetwork(context)) {
                    //连接不到聊天服务器
                } else {
                    //当前网络不可用，请检查网络设置
                }

            }
        }

    }

    public interface OnMessageReceiveListener{
        void getMessage(List<EMMessage> messages);

    }
    public static void setOnMessageReceiveListener(OnMessageReceiveListener onMessageReceiveListener1){
        onMessageReceiveListener=onMessageReceiveListener1;

    }

    public interface OnChatMessageReceiveListener{
        void getMessage(List<EMMessage> messages);
    }
    public static void setOnChatMessageReceiveListener(OnChatMessageReceiveListener onChatMessageReceiveListener1){
        onChatMessageReceiveListener=onChatMessageReceiveListener1;
    }

    /**
     * 收到新的聊天消息处理
     * @param messages 收到的消息
     */
    private static void handleMessage(List<EMMessage> messages) {
        ChatDBHelper.setUserDataMap(messages.get(0).getFrom(), messages.get(0));
        //判断通讯录页面是否在前台展示（是就不展示通知）
        if (isAdressActivity()) {
            //判断聊天窗口是否在前台展示
            if (isChatActivity()) {
                onChatMessageReceiveListener.getMessage(messages);
            } else {
                onMessageReceiveListener.getMessage(messages);
            }
            ChatHelper.getVibrate();
        } else {
            //直接发通知
            showNotification(messages.get(0));
        }
    }


    /**
     * 环信消息监听
     */
    private  void getMessageListener() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                msgListener = new EMMessageListener() {
                    @Override
                    public void onMessageReceived(final List<EMMessage> messages) {
                        //收到普通消息
                        handleMessage(messages);
                    }

                    @Override
                    public void onCmdMessageReceived(List<EMMessage> messages) {
                        //收到透传消息
                    }

                    @Override
                    public void onMessageRead(List<EMMessage> messages) {
                        //收到已读回执
                    }

                    @Override
                    public void onMessageDelivered(List<EMMessage> message) {
                        //收到已送达回执
                    }

                    @Override
                    public void onMessageRecalled(List<EMMessage> messages) {
                        //消息被撤回
                    }

                    @Override
                    public void onMessageChanged(EMMessage message, Object change) {
                        //消息状态变动
                    }
                };
            }
        }).start();

    }

    /**
     * 环信群组消息监听
     */
    private void getGroupListener(){

        groupListener=new EMGroupChangeListener() {
            @Override
            public void onInvitationReceived(String groupId, String groupName, String inviter, String reason) {
                //接收到群组加入邀请
            }

            @Override
            public void onRequestToJoinReceived(String groupId, String groupName, String applyer, String reason) {
                //用户申请加入群
            }

            @Override
            public void onRequestToJoinAccepted(String groupId, String groupName, String accepter) {
                //加群申请被同意
            }

            @Override
            public void onRequestToJoinDeclined(String groupId, String groupName, String decliner, String reason) {
                //加群申请被拒绝
            }

            @Override
            public void onInvitationAccepted(String groupId, String inviter, String reason) {
                //群组邀请被同意
            }

            @Override
            public void onInvitationDeclined(String groupId, String invitee, String reason) {
                //群组邀请被拒绝
            }

            @Override
            public void onUserRemoved(String s, String s1) {
                //当前登录用户被管理员移除出群组
                groupChangeListener(s,"你已经被移出了群聊");
            }

            @Override
            public void onGroupDestroyed(String s, String s1) {
                //群组被解散
                groupChangeListener(s,"群主解散了群聊");
            }

            @Override
            public void onAutoAcceptInvitationFromGroup(String groupId, String inviter, String inviteMessage) {
                //接收邀请时自动加入到群组的通知
                groupChangeListener(groupId,ChatDBHelper.getUserName(inviter) + "邀请你加入群聊");
            }

            @Override
            public void onMuteListAdded(String groupId, final List<String> mutes, final long muteExpire) {
                //成员禁言的通知
            }

            @Override
            public void onMuteListRemoved(String groupId, final List<String> mutes) {
                //成员从禁言列表里移除通知
            }

            @Override
            public void onAdminAdded(String groupId, String administrator) {
                //增加管理员的通知
            }

            @Override
            public void onAdminRemoved(String groupId, String administrator) {
                //管理员移除的通知
            }

            @Override
            public void onOwnerChanged(String groupId, String newOwner, String oldOwner) {
                //群所有者变动通知
            }
            @Override
            public void onMemberJoined(final String groupId,  final String member){
                //群组加入新成员通知
                groupChangeListener(groupId,ChatDBHelper.getUserName(member) + "加入了群聊");
            }
            @Override
            public void onMemberExited(final String groupId, final String member) {
                //群成员退出通知
                groupChangeListener(groupId,ChatDBHelper.getUserName(member) + "退出了群聊");
            }

            @Override
            public void onAnnouncementChanged(String groupId, String announcement) {
                //群公告变动通知
                groupChangeListener(groupId,"群主更新了群公告");
            }

            @Override
            public void onSharedFileAdded(String groupId, EMMucSharedFile sharedFile) {
                //增加共享文件的通知
            }

            @Override
            public void onSharedFileDeleted(String groupId, String fileId) {
                //群共享文件删除通知
            }
        };
    }
    public static void groupChangeListener(String groupId, String tipText ){
        EMMessage  groupMsg = EMMessage.createReceiveMessage(EMMessage.Type.TXT);
        groupMsg.setChatType(EMMessage.ChatType.GroupChat);
        groupMsg.setFrom("");
        groupMsg.setTo(groupId);
        groupMsg.setMsgId(UUID.randomUUID().toString());
        groupMsg.setMsgTime(DateUtil.getCurTimeLong());
        groupMsg.addBody(new EMTextMessageBody(tipText));
        groupMsg.setAttribute(Constent.IS_GROUP_MESSAGE_TIP,true);
        // save invitation as messages
        EMClient.getInstance().chatManager().saveMessage(groupMsg);
        // notify invitation message
        List<EMMessage>list=new ArrayList<>();
        list.add(groupMsg);
        handleMessage(list);
    }

    /**
     * 显示通知栏
     * @param messages
     */
    private static void showNotification(EMMessage messages){
        if(messages.getChatType()== EMMessage.ChatType.GroupChat){
            Notification.showNotifiction(ApplicationUtil.getContext(), R.drawable.app, messages.getTo(),
                    ChatHelper.getChatMessageTypeText(messages),ChatHelper.getGroupName(messages.getTo()));
        }else {
            Notification.showNotifiction(ApplicationUtil.getContext(), R.drawable.app, messages.getFrom(),
                    ChatHelper.getChatMessageTypeText(messages),null);
        }
    }

    /**
     * 判断通讯录页面是否已经打开
     * @return
     */
    private static boolean isAdressActivity(){
        if (ActivityUtil.isActivityForeground(context, "AdressList") || ActivityUtil.isForeground(context, "Chat")) {
            return true;
        }else{
            return false;
        }
    }

    /**
     * 判断当前页面是否为聊天页面
     * @return
     */
    private static boolean isChatActivity(){
        Boolean b=false;
        if(isAdressActivity()){
            if (ActivityUtil.isActivityForeground(context, "Chat")) {
                b= true;
            }else{
                b= false;
            }
        }
        return b;
    }

    public static Context getContext() {
        return context;
    }

    private String getAppName(int pID) {
        String processName = null;
        ActivityManager am = (ActivityManager) this.getSystemService(ACTIVITY_SERVICE);
        List l = am.getRunningAppProcesses();
        Iterator i = l.iterator();
        PackageManager pm = this.getPackageManager();
        while (i.hasNext()) {
            ActivityManager.RunningAppProcessInfo info = (ActivityManager.RunningAppProcessInfo) (i.next());
            try {
                if (info.pid == pID) {
                    processName = info.processName;
                    return processName;
                }
            } catch (Exception e) {
                // Log.d("Process", "Error>> :"+ e.toString());
            }
        }
        return processName;
    }
    private boolean shouldInit() {
        ActivityManager am = ((ActivityManager) getSystemService(Context.ACTIVITY_SERVICE));
        List<ActivityManager.RunningAppProcessInfo> processInfos = am.getRunningAppProcesses();
        String mainProcessName = getPackageName();
        int myPid = Process.myPid();
        for (ActivityManager.RunningAppProcessInfo info : processInfos) {
            if (info.pid == myPid && mainProcessName.equals(info.processName)) {
                return true;
            }
        }
        return false;
    }


    //static 代码段可以防止内存泄露
    static {
        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreator(new DefaultRefreshHeaderCreator() {
            @Override
            public RefreshHeader createRefreshHeader(Context context, RefreshLayout layout) {
                layout.setPrimaryColorsId(R.color.background, R.color.black);//全局设置主题颜色

                return new ClassicsHeader(context);//.setTimeFormat(new DynamicTimeFormat("更新于 %s"));//指定为经典Header，默认是 贝塞尔雷达Header
            }
        });
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreator(new DefaultRefreshFooterCreator() {
            @Override
            public RefreshFooter createRefreshFooter(Context context, RefreshLayout layout) {
                //指定为经典Footer，默认是 BallPulseFooter

                return new ClassicsFooter(context);
            }
        });

    }


}

