package com.sinostar.assistant.ui.addressList;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;
import com.sinostar.assistant.R;
import com.sinostar.assistant.base.ApplicationUtil;
import com.sinostar.assistant.ui.HMSPushHelper;
import com.sinostar.assistant.ui.addressList.chat.helper.ChatDBHelper;
import com.sinostar.assistant.bean.User;
import com.sinostar.assistant.base.BaseActivity;
import com.sinostar.assistant.utils.ACache;
import com.sinostar.assistant.utils.Constent;
import com.sinostar.assistant.utils.OnMultiClickListener;
import com.sinostar.assistant.utils.ToastUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class  ChatLogin extends BaseActivity {

    @BindView(R.id.button)
    Button button;
    @BindView(R.id.button2)
    Button button2;
    @BindView(R.id.progress_layout)
    ProgressBar progressLayout;
    private ACache aCache;

    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_login);
        ButterKnife.bind(this);
        context = this;
        aCache=ACache.get(context);

        initListener();

    }
    private void permission(final String name, final String password ) {

        requestRunPermisssion(new String[]{Manifest.permission.RECORD_AUDIO, Manifest.permission.WRITE_EXTERNAL_STORAGE
                , Manifest.permission.READ_EXTERNAL_STORAGE}, new PermissionListener() {
            @Override
            public void onGranted() {
                //表示所有权限都授权了
                login(name, password);
            }

            @Override
            public void onDenied(List<String> deniedPermission) {
                for (String permission : deniedPermission) {
                    ToastUtil.showToast(context, "有部分权限没有授权，请授权后再进行相关操作");
                }
            }
        });

    }

    private void initListener() {
        button.setOnClickListener(new OnMultiClickListener() {
            @Override
            public void onClick1(View v) {
                EMClient.getInstance().logout(false);
                aCache.remove(Constent.USER_ID);
                permission("1","1");

            }
        });
        button2.setOnClickListener(new OnMultiClickListener() {
            @Override
            public void onClick1(View v) {
                EMClient.getInstance().logout(false);
                aCache.remove(Constent.USER_ID);
                permission("2", "2");
            }
        });
    }


    @OnClick({R.id.title_bar_back_image, R.id.title_bar_back_text})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_bar_back_image:
                finish();
                break;
            case R.id.title_bar_back_text:
                finish();
                break;
        }
    }
    public static String getNickName(String userId){
        switch (userId){
            case "1":
                userId="王汉全";
                break;
            case "2":
                userId="陈宏";
                break;
            case "3":
                userId="刘伟聪";
                break;
            case "4":
                userId="许碧珊";
                break;
        }
        return userId;
    }
    public static String getAvatar(String userId){

        switch (userId){
            case "1":
                userId="https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=1458549441,2560765249&fm=26&gp=0.jpg";
                break;
            case "2":
                userId="https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=2732952553,3336840628&fm=26&gp=0.jpg";
                break;
            case "3":
                userId="https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=27529506,3345671682&fm=26&gp=0.jpg";
                break;
            case "4":
                userId="https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=3154282143,2033096662&fm=26&gp=0.jpg";
                break;
                default:
                    userId= Constent.CHAT_GROUP_AVATAR;
        }
        return userId;
    }

    private void login(final String name, final String password) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                EMClient.getInstance().login(name, password, new EMCallBack() {//回调
                    @Override
                    public void onSuccess() {
                        HMSPushHelper.getInstance().getHMSToken(ChatLogin.this);
                        User user=new User();
                        user.setUserName(getNickName(name));
                        user.setUserAvatar(getAvatar(name));
                        ApplicationUtil.setChatId(name);
                        ChatDBHelper.setChatId(name);
                        ChatDBHelper.setUserDataMap(name,user);
                        progressLayout.setVisibility(View.GONE);
                        EMClient.getInstance().groupManager().loadAllGroups();
                        EMClient.getInstance().chatManager().loadAllConversations();
                        //刷新本地群组列表
                        try {
                            EMClient.getInstance().groupManager().getJoinedGroupsFromServer();
                        } catch (HyphenateException e) {
                            e.printStackTrace();
                        }
                        Log.d("main", "登录聊天服务器成功！");

                        startActivity(new Intent(context, AdressList.class));

                    }
                    @Override
                    public void onProgress(int progress, String status) {
                        progressLayout.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onError(int code, String message) {
                        Log.d("main", "登录聊天服务器失败！");
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ToastUtil.showToast(context, "登陆失败");
                            }
                        });

                    }
                });
            }
        }).start();

    }




}
