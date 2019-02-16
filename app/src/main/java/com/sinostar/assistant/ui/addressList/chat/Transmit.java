package com.sinostar.assistant.ui.addressList.chat;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;

import com.hyphenate.chat.EMFileMessageBody;
import com.hyphenate.chat.EMImageMessageBody;
import com.hyphenate.chat.EMLocationMessageBody;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMTextMessageBody;
import com.hyphenate.chat.EMVideoMessageBody;
import com.hyphenate.exceptions.HyphenateException;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction;
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;
import com.qmuiteam.qmui.widget.grouplist.QMUICommonListItemView;
import com.sinostar.assistant.R;
import com.sinostar.assistant.utils.Constent;
import com.sinostar.assistant.utils.FileUtil;
import com.sinostar.assistant.utils.OnMultiItemClickListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.qmuiteam.qmui.widget.dialog.QMUITipDialog.Builder.ICON_TYPE_FAIL;
import static com.qmuiteam.qmui.widget.dialog.QMUITipDialog.Builder.ICON_TYPE_LOADING;
import static com.qmuiteam.qmui.widget.dialog.QMUITipDialog.Builder.ICON_TYPE_SUCCESS;
import static com.sinostar.assistant.ui.addressList.chat.Chat.RESULT_CODE_TRANSMIT;
import static com.sinostar.assistant.ui.addressList.chat.helper.ChatHelper.sendFile;
import static com.sinostar.assistant.ui.addressList.chat.helper.ChatHelper.sendImage;
import static com.sinostar.assistant.ui.addressList.chat.helper.ChatHelper.sendLocation;
import static com.sinostar.assistant.ui.addressList.chat.helper.ChatHelper.sendText;
import static com.sinostar.assistant.ui.addressList.chat.helper.ChatHelper.sendVideo;

/**
 * 消息转发
 */
public class Transmit extends AppCompatActivity {

    @BindView(R.id.title_bar_text)
    TextView titleBarText;
    @BindView(R.id.title_bar_rightText)
    TextView titleBarRightText;
    @BindView(R.id.chat_search_et)
    EditText chatSearchEt;
    @BindView(R.id.create_new_chat)
    QMUICommonListItemView createNewChat;
    Context context = this;
    @BindView(R.id.transmit_list)
    ListView transmitList;
    private List<String> usernames;
    TransmitAdapter transmitAdapter;
    private EMMessage transmitMessage;
    private QMUIDialog dialog;
    private QMUITipDialog statueDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transmit);
        ButterKnife.bind(this);
        transmitMessage=getIntent().getParcelableExtra(Constent.TRANSIMIT_MESSAGE);
        transmitAdapter=new TransmitAdapter(context);
        transmitList.setAdapter(transmitAdapter);
        init();
        initList();
        initListener();
    }

    private void initListener() {
        transmitList.setOnItemClickListener(new OnMultiItemClickListener() {
            @Override
            public void onItemClick1(AdapterView<?> adapterView, View view, int i, long l) {
                showDialog(transmitAdapter.mList.get(i));
            }
        });
    }
    /**
     * 发送消息主体
     */
    private void sendMessage(final EMMessage message) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                message.setAttribute(Constent.MSG_ID, message.getMsgId());
                message.setAttribute("em_force_notification", true);
//                message.setAttribute(Constent.USER_NAME, ApplicationUtil.getWaterMarkText());   //用户昵称
                EMClient.getInstance().chatManager().sendMessage(message);  //发送消息

                    message.setMessageStatusCallback(new EMCallBack() {  //设置消息回调
                        @Override
                        public void onSuccess() {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    statueDialog.dismiss();
                                    showStatueDialog(ICON_TYPE_SUCCESS);
                                    new Handler().postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            Intent intent=new Intent();
                                            intent.putExtra(Constent.TRANSIMIT_NAME, message.getTo());
                                            setResult(RESULT_CODE_TRANSMIT, intent);
                                            finish();
                                            finish();
                                        }
                                    },1000);

                                }
                            });

                        }

                        @Override
                        public void onError(int i, String s) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    statueDialog.dismiss();
                                    showStatueDialog(ICON_TYPE_FAIL);
                                    new Handler().postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            statueDialog.dismiss();
                                        }
                                    },1000);
                                }
                            });

                        }

                        @Override
                        public void onProgress(int i, String s) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                }
                            });
                        }
                    });
                }
        }).start();

    }

    /**
     * 是否发送弹框
     * @param transmitName 被转发用户id
     */
    private void showDialog(final String transmitName){
       dialog=new QMUIDialog.MessageDialogBuilder(context)
        .setMessage("是否发送给"+transmitName+"?")
        .addAction("取消", new QMUIDialogAction.ActionListener() {
            @Override
            public void onClick(QMUIDialog dialog, int index) {
                finish();
            }
        })
        .addAction("发送", new QMUIDialogAction.ActionListener() {
            @Override
            public void onClick(QMUIDialog dialog, int index) {
                switch (transmitMessage.getType()){
                    case TXT:
                        EMTextMessageBody textBody = (EMTextMessageBody) transmitMessage.getBody();

                        sendMessage(sendText(textBody.getMessage(),transmitName));
                        break;
                    case IMAGE:
                        EMImageMessageBody imageBody = (EMImageMessageBody) transmitMessage.getBody();
                        if(FileUtil.fileIsExists(imageBody.getLocalUrl())){
                            sendMessage(sendImage(imageBody.getLocalUrl(),transmitName));
                        }else{
                            downloadAttachment(transmitMessage,transmitName);
                        }
                        break;
                    case LOCATION:
                        EMLocationMessageBody locationBody = (EMLocationMessageBody) transmitMessage.getBody();
                        try {
                            sendMessage(sendLocation(locationBody.getLatitude(),locationBody.getLongitude(),locationBody.getAddress(),
                                    transmitMessage.getStringAttribute(Constent.MAP_ADDRESS),transmitName));
                        } catch (HyphenateException e) {
                            e.printStackTrace();
                        }
                        break;
                    case VIDEO:
                        EMVideoMessageBody videoBody = (EMVideoMessageBody) transmitMessage.getBody();
                       if(videoBody.thumbnailDownloadStatus()== EMFileMessageBody.EMDownloadStatus.SUCCESSED){
                           if(FileUtil.fileIsExists(videoBody.getLocalUrl())){
                               sendMessage(sendVideo(videoBody.getLocalUrl(),videoBody.getLocalThumb(),transmitName));
                           }else{
                               downloadAttachment(transmitMessage,transmitName);
                           }
                       }

                        break;
                    case FILE:
                        EMFileMessageBody fileBody = (EMFileMessageBody) transmitMessage.getBody();
                        try {
                            if(FileUtil.fileIsExists(fileBody.getLocalUrl())){
                                sendMessage(sendFile(fileBody.getLocalUrl(),transmitMessage.getStringAttribute(Constent.FILE_LENGTH),transmitName));
                            }else{
                                downloadAttachment(transmitMessage,transmitName);
                            }
                        } catch (HyphenateException e) {
                            e.printStackTrace();
                        }
                        break;
                }

                dialog.dismiss();
                showStatueDialog(ICON_TYPE_LOADING);

            }
        }).create();
       dialog.show();

    }

    /**
     * 发送状态dialog
     * @param type
     */
    private void showStatueDialog(int type){
        QMUITipDialog.Builder builder = new QMUITipDialog.Builder(context)
                .setIconType(type);
        switch (type){
            case ICON_TYPE_LOADING:
                builder.setTipWord("正在发送");
                break;
            case ICON_TYPE_SUCCESS:
                builder.setTipWord("发送成功");
                break;
            case ICON_TYPE_FAIL:
                builder.setTipWord("发送失败");
                break;
        }
        statueDialog =builder.create(true);
        statueDialog.show();
    }

    /**
     * 下载附件
     * @param transmitMessage
     */
    private void downloadAttachment(final EMMessage transmitMessage, final String transmitName){
        transmitMessage.setMessageStatusCallback(new EMCallBack() {
            @Override
            public void onSuccess() {
                switch (transmitMessage.getType()){
                    case IMAGE:
                        EMImageMessageBody imageBody = (EMImageMessageBody) transmitMessage.getBody();
                        sendMessage(sendImage(imageBody.getLocalUrl(),transmitName));
                        break;
                    case VIDEO:
                        EMVideoMessageBody videoBody = (EMVideoMessageBody) transmitMessage.getBody();
                        sendMessage(sendVideo(videoBody.getLocalUrl(),videoBody.getLocalThumb(),transmitName));
                        break;
                    case FILE:
                        EMFileMessageBody fileBody = (EMFileMessageBody) transmitMessage.getBody();
                        try {
                            sendMessage(sendFile(fileBody.getLocalUrl(),transmitMessage.getStringAttribute(Constent.FILE_LENGTH),transmitName));
                        } catch (HyphenateException e) {
                            e.printStackTrace();
                        }
                        break;
                }
            }

            @Override
            public void onError(int i, String s) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        statueDialog.dismiss();
                        showStatueDialog(ICON_TYPE_FAIL);
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                statueDialog.dismiss();
                            }
                        },1000);

                    }
                });

            }

            @Override
            public void onProgress(int i, String s) {

            }
        });
        EMClient.getInstance().chatManager().downloadAttachment(transmitMessage);
    }

    private void initList() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    usernames = EMClient.getInstance().contactManager().getAllContactsFromServer();
                    if (usernames != null && usernames.size() != 0) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                transmitAdapter.getData(usernames);
                            }
                        });

                    }
                } catch (HyphenateException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    private void init() {
        titleBarText.setText("选择一个聊天");
        titleBarRightText.setText("多选");
        createNewChat.setText("创建新的聊天");

    }

    @OnClick({R.id.title_bar_back_image, R.id.title_bar_back_text, R.id.title_bar_rightText, R.id.chat_search_voice_image, R.id.create_new_chat})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_bar_back_image:
                finish();
                break;
            case R.id.title_bar_back_text:
                finish();
                break;
            case R.id.title_bar_rightText:
                break;
            case R.id.chat_search_voice_image:
                break;
            case R.id.create_new_chat:
                break;
        }
    }
}
