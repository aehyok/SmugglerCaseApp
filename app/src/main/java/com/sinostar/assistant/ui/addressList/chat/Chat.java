package com.sinostar.assistant.ui.addressList.chat;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hyphenate.EMCallBack;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMTextMessageBody;
import com.hyphenate.exceptions.HyphenateException;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.qmuiteam.qmui.widget.popup.QMUIPopup;
import com.scwang.smartrefresh.header.WaterDropHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.sinostar.assistant.R;
import com.sinostar.assistant.ui.addressList.chat.chatMap.ChatMap;
import com.sinostar.assistant.ui.addressList.chat.helper.ChatDBHelper;
import com.sinostar.assistant.ui.addressList.chat.helper.ChatHelper;
import com.sinostar.assistant.ui.addressList.chat.video.ChatVideo;
import com.sinostar.assistant.ui.addressList.chat.voice.MediaHelper;
import com.sinostar.assistant.ui.addressList.chat.voice.RecordButton;
import com.sinostar.assistant.base.ApplicationUtil;
import com.sinostar.assistant.ui.addressList.chatRoom.chatRoomInfo.ChatRoomInfoActivity;
import com.sinostar.assistant.ui.image.ImageSelectImageLoader;
import com.sinostar.assistant.utils.ACache;
import com.sinostar.assistant.utils.AppKeyBoardMgr;
import com.sinostar.assistant.utils.ClickUtil;
import com.sinostar.assistant.utils.Constent;
import com.sinostar.assistant.utils.DensityUtils;
import com.sinostar.assistant.utils.ToastUtil;
import com.sinostar.assistant.widget.CommonPopupWindow;
import com.sinostar.assistant.widget.QQTipItem;
import com.sinostar.assistant.widget.QQTipView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import droidninja.filepicker.FilePickerBuilder;
import droidninja.filepicker.FilePickerConst;
import io.github.rockerhieu.emojicon.EmojiconGridFragment;
import io.github.rockerhieu.emojicon.EmojiconsFragment;
import io.github.rockerhieu.emojicon.emoji.Emojicon;

import static com.sinostar.assistant.ui.addressList.chat.helper.ChatHelper.sendFile;
import static com.sinostar.assistant.ui.addressList.chat.helper.ChatHelper.sendImage;
import static com.sinostar.assistant.ui.addressList.chat.helper.ChatHelper.sendLocation;
import static com.sinostar.assistant.ui.addressList.chat.helper.ChatHelper.sendText;
import static com.sinostar.assistant.ui.addressList.chat.helper.ChatHelper.sendVideo;
import static com.sinostar.assistant.ui.addressList.chat.helper.ChatHelper.sendVoice;
import static com.sinostar.assistant.utils.AppScreenMgr.getScreenWidth;
import static com.sinostar.assistant.utils.FileSizeUtil.getAutoFileOrFilesSize;
import static com.sinostar.assistant.utils.FileSizeUtil.getFileOrFilesSize;


public class Chat extends AppCompatActivity implements EmojiconsFragment.OnEmojiconBackspaceClickedListener,
        EmojiconGridFragment.OnEmojiconClickedListener {

    private static final int RESULT_CODE_PHOTO = 101;
    public static final int RESULT_CODE_MAP = 102;
    public static final int RESULT_CODE_VIDEO = 105;
    private static final int RESULT_CODE_FILE = 106;
    public static final int RESULT_CODE_TRANSMIT = 107;
    @BindView(R.id.title_bar_text)
    TextView titleBarText;
    @BindView(R.id.title_bar_right_image)
    ImageView titleBarRightImage;
    @BindView(R.id.chat_list)
    ListView chatList;
    @BindView(R.id.chat_eidt)
    EditText chatEdit;
    @BindView(R.id.chat_function_layout)
    RelativeLayout chatFunctionLayout;
    @BindView(R.id.chat_send_layout)
    RelativeLayout chatSendLayout;
    @BindView(R.id.chat_function_list)
    GridView chatFunctionList;
    @BindView(R.id.chat_emoji_layout)
    RelativeLayout chatEmojiLayout;
    @BindView(R.id.chat_voice_button)
    RecordButton chatVoiceButton;
    @BindView(R.id.chat_voice)
    ImageView chatVoice;
    @BindView(R.id.chat_emoji)
    ImageView chatEmoji;
    @BindView(R.id.chat_refreshLayout)
    SmartRefreshLayout chatRefreshLayout;
    @BindView(R.id.chat_tip)
    RelativeLayout chatTip;
    @BindView(R.id.chat_edit_tip)
    RelativeLayout chatEditTip;
    @BindView(R.id.chat_bottom_layout)
    RelativeLayout chatBottomLayout;


    private Activity context = Chat.this;
    private boolean isGroupChat;
    private String[] text = {"图片", "拍摄", "位置", "语音输入", "个人名片", "文件"};
    private int[] image = {R.drawable.chat_image, R.drawable.chat_photo, R.drawable.chat_location,
            R.drawable.chat_voice_1, R.drawable.chat_person, R.drawable.chat_file};
    private EMMessageListener msgListener;
    private int width;
    private int height;
    private ChatListAdapter chatListAdapter;
    private EMConversation conversation;
    private String personName;
    private ArrayList<ImageItem> images = new ArrayList<>();
    private int pagesize = 20;
    private String msgId;
    private String msgId1 = "";
    private View imageViewTo;
    private View imageViewFrom;
    private EMMessage message;
    private List<EMMessage> messageList = new ArrayList<>();
    private ClipboardManager cm;
    private CommonPopupWindow popupWindow;
    private Dialog deleteDialog;
    ACache aCache;
    private EMMessage transmitMessage;
    private int originalNum;

    private int messageCount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        ButterKnife.bind(this);
        aCache = ACache.get(context);
        personName = getIntent().getStringExtra(Constent.PERSON_NAME);
        initView();
        chatFunctionList.setAdapter(new ChatFunctionAdapter(context, image, text));
        initImagePicker();
        chatListAdapter = new ChatListAdapter(context);
        chatList.setAdapter(chatListAdapter);
        getMessageRecord();
        getMessage();
        voiceFromClickListener();
        voiceToClickListener();
        refreshData();
        reSendMessage();
        initListener();
        chatLongClickListener();
        editLongClickListener();
    }

    private void initView() {

        //标题栏右边图片隐藏
        titleBarRightImage.setBackgroundResource(R.drawable.more);
        titleBarRightImage.setVisibility(View.VISIBLE);

        //初始化底部图标
        chatVoice.setBackgroundResource(R.drawable.chat_voice);
        chatEmoji.setBackgroundResource(R.drawable.chat_emoji);

        //下拉刷新控件初始化
        chatRefreshLayout.setEnableLoadMore(false);
        chatRefreshLayout.setRefreshHeader(new WaterDropHeader(context));
        chatRefreshLayout.setEnableOverScrollDrag(true);//是否启用越界拖动（仿苹果效果）1.0.4
//        chatRefreshLayout.setEnableScrollContentWhenLoaded(true);//是否在加载完成时滚动列表显示新的内容

        //初始化聊天
        conversation = EMClient.getInstance().chatManager().getConversation(personName);
        if(getIntent().getStringExtra(Constent.IS_GROUP_CHAT)!=null){
            if(getIntent().getStringExtra(Constent.IS_GROUP_CHAT).equals("1")){
                isGroupChat=true;
            }else{
                isGroupChat=false;
            }
        }
        if (conversation != null) {
            messageCount = conversation.getAllMsgCount();
            conversation.markAllMessagesAsRead();
            originalNum = conversation.getAllMessages().size();
            isGroupChat = conversation.getType() == EMConversation.EMConversationType.GroupChat;
        }



        //初始化底部弹窗
        width = getScreenWidth(context);
        height = width * 330 / 470;
        ViewGroup.LayoutParams params = chatFunctionLayout.getLayoutParams();
        params.height = height;
        ViewGroup.LayoutParams params2 = chatEmojiLayout.getLayoutParams();
        params2.height = height;
    }



    @SuppressLint("ClickableViewAccessibility")
    private void initListener() {

        //底部菜单栏点击事件监听
        chatFunctionList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i) {
                    case 0:  //图片
                        Intent intent = new Intent(context, ImageGridActivity.class);
//                        intent.putExtra(ImageGridActivity.EXTRAS_IMAGES, images);
                        startActivityForResult(intent, RESULT_CODE_PHOTO);
                        break;
                    case 1:  //拍摄
                        startActivityForResult(new Intent(context, ChatVideo.class), RESULT_CODE_VIDEO);
                        break;
                    case 2:  //位置
                        startActivityForResult(new Intent(context, ChatMap.class), RESULT_CODE_MAP);
                        break;
                    case 3:  //语音输入
                        break;
                    case 4:  //个人名片
                        break;
                    case 5:  //文件
                        FilePickerBuilder.getInstance().setMaxCount(9)  //设置文件一次性最多能选几个
                                .setActivityTheme(R.style.LibAppTheme)
                                .pickFile(context, RESULT_CODE_FILE);
                        break;
                }
            }
        });

        //输入框监听
        chatEdit.setMaxLines(3);
        chatEdit.setHorizontallyScrolling(false);
        chatEdit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_UNSPECIFIED || actionId == EditorInfo.IME_ACTION_SEND ||
                        (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    String text = chatEdit.getText().toString().trim();
                    if (text.equals("")) {
                        ToastUtil.showToast(context, "输入不能为空");
                        chatEdit.setText("");
                    } else {
                        chatEdit.setText("");
                        sendMessage(sendText(text, personName));
                    }
                }
                return true;
            }
        });
        chatEdit.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                chatEdit.setCursorVisible(true);
                goToListBottom();
                return false;
            }
        });



        //聊天界面的点击事件监听（点击收起输入法）
        chatList.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                AppKeyBoardMgr.hideInputMethod((AppCompatActivity) context);
                hideInput();
                return false;
            }
        });
        chatList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });


        //表情按钮点击事件监听
        chatEmoji.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                chatEdit.setCursorVisible(true);
                goToListBottom();
                return false;
            }
        });


        //语音输入
        chatVoiceButton.setAudioFinishRecordListener(new RecordButton.AudioFinishRecordListener() {
            @Override     //语音输入完成
            public void onFinish(float second, String path) {
                sendMessage(sendVoice(second, path, personName));
            }

            @Override  //语音输入开始（对话框显示一个空的文本框）
            public void onStar() {
                message = EMMessage.createTxtSendMessage("   ", personName);
                final List<EMMessage> list = new ArrayList<>();
                list.add(message);
                chatListAdapter.getData(list, personName);
            }

            @Override  //语音输入结束（把语音输入开始时的文本框去掉）
            public void onEnd() {
                chatListAdapter.mList.remove(chatListAdapter.mList.size() - 1);
                chatListAdapter.notifyDataSetChanged();
            }
        });


    }

    /**
     * 显示删除弹窗
     * @param msgId  删除对话的Id
     * @param i 删除对话所对应的item
     */
    public void showDeleteDialog(final String msgId, final int i) {
        deleteDialog = new Dialog(context, R.style.MapDialog);
        deleteDialog.setCanceledOnTouchOutside(true);
        deleteDialog.setCancelable(true);

        Window window = deleteDialog.getWindow();
        assert window != null;
        window.setGravity(Gravity.BOTTOM);
//        window.setWindowAnimations(R.style.MapDialog);
        View view = View.inflate(context, R.layout.delete_dialog, null);
        view.findViewById(R.id.delete_dialog_confirm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                conversation.removeMessage(msgId);
                chatListAdapter.remove(i);
                deleteDialog.dismiss();
            }
        });
        view.findViewById(R.id.delete_dialog_cancle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                deleteDialog.dismiss();
            }
        });

        window.setContentView(view);
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);//设置横向全屏
        deleteDialog.show();
    }


    /**
     * 下拉刷新聊天记录
     */
    private void refreshData() {
        chatRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                chatRefreshLayout.finishRefresh();
                chatRefreshLayout.finishLoadMoreWithNoMoreData();
                if(getAllMessage()!=null){
                    int currentMsgCount=getAllMessage().size();
                    if(currentMsgCount!=0&&currentMsgCount<messageCount){
                        conversation.loadMoreMsgFromDB(msgId, pagesize);
                        msgId = getAllMessage().get(0).getMsgId();
                        chatListAdapter.clear();
                        chatListAdapter.getData(getAllMessage(), personName);
                        int remainCount = messageCount - currentMsgCount;  //剩余的消息数量
                        if(remainCount<pagesize){
                            chatList.setSelection(remainCount);
                        }else {
                            chatList.setSelection(pagesize);
                        }
                    } else {
                        chatRefreshLayout.finishLoadMoreWithNoMoreData();
                        ToastUtil.showToast(context, "没有更多聊天记录了");
                    }
                }

            }
        });
    }

    /**
     * 初始化聊天记录
     */
    private void getMessageRecord() {
        if (conversation != null) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    conversation.loadMoreMsgFromDB(getAllMessage().get(getAllMessage().size() - 1).getMsgId(), pagesize-1);
                    messageList = conversation.getAllMessages();   //总消息数
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            EMClient.getInstance().chatManager().importMessages(messageList);
                            msgId = conversation.getAllMessages().get(0).getMsgId();
                            chatListAdapter.getData(messageList, personName);
                            chatList.setSelection(messageList.size());
                        }
                    });
                }
            }).start();

        }
    }

    /**
     * 获取所有聊天记录
     *
     * @return
     */
    private List<EMMessage> getAllMessage() {
            return conversation!=null?conversation.getAllMessages():null;

    }

    /**
     * 接收消息
     */
    private void getMessage() {
        ApplicationUtil.setOnChatMessageReceiveListener(new ApplicationUtil.OnChatMessageReceiveListener() {
            @Override
            public void getMessage(final List<EMMessage> messages) {
                conversation.markAllMessagesAsRead();
                if (isGroupChat) {
                    if (messages.get(0).getTo().equals(personName)) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ChatDBHelper.setUserDataMap(messages.get(0).getFrom(), messages.get(0));
                                chatListAdapter.getData(messages, messages.get(0).getTo());
                            }
                        });

                    }
                } else if (messages.get(0).getFrom().equals(personName)&&messages.get(0).getTo().equals(ChatDBHelper.getChatId())) {
                    ChatDBHelper.setUserDataMap(messages.get(0).getFrom(), messages.get(0));
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            chatListAdapter.getData(messages, personName);
                        }
                    });
                }
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
                if (isGroupChat) {  //判断是否为群组聊天
                    message.setChatType(EMMessage.ChatType.GroupChat);
                }
                ext(message);
                message.setAttribute(Constent.MSG_ID, message.getMsgId());

//                message.setAttribute(Constent.USER_NAME, ApplicationUtil.getWaterMarkText());   //用户昵称
                EMClient.getInstance().chatManager().sendMessage(message);  //发送消息
                if (message.getTo().equals(personName)) {
                    final List<EMMessage> list = new ArrayList<>();
                    list.add(message);
                    messageList.add(message);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            chatListAdapter.getData(list, personName);
                            try {
                                chatListAdapter.refresh(message.getStringAttribute(Constent.MSG_ID), "", true);
                                chatListAdapter.showError((message.getStringAttribute(Constent.MSG_ID)), message.getMsgId(), true);
                            } catch (HyphenateException e) {
                                e.printStackTrace();
                            }
                        }
                    });


                    message.setMessageStatusCallback(new EMCallBack() {  //设置消息回调
                        @Override
                        public void onSuccess() {
                            EMClient.getInstance().chatManager().importMessages(list);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        goToListBottom();
                                        chatListAdapter.refresh((message.getStringAttribute(Constent.MSG_ID)), message.getMsgId(), false);
                                        chatListAdapter.showError((message.getStringAttribute(Constent.MSG_ID)), message.getMsgId(), false);
                                    } catch (HyphenateException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });

                        }

                        @Override
                        public void onError(int i, String s) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        chatListAdapter.refresh((message.getStringAttribute(Constent.MSG_ID)), message.getMsgId(), false);
                                        chatListAdapter.showError((message.getStringAttribute(Constent.MSG_ID)), message.getMsgId(), true);

                                    } catch (HyphenateException e) {
                                        e.printStackTrace();
                                    }

                                }
                            });

                        }

                        @Override
                        public void onProgress(int i, String s) {
                        }
                    });

                }
            }
        }).start();

    }

    /**
     * 自定义推送消息
     * @param message
     */
    private void ext(EMMessage message) {
        String pushText = "收到一条新的消息";
        switch (message.getType()) {
            case TXT:
                EMTextMessageBody textMessageBody = (EMTextMessageBody) message.getBody();
                pushText = textMessageBody.getMessage();
                break;
            case FILE:
                pushText = "[ 文件 ]";
                break;
            case VIDEO:
                pushText = "[ 视频 ]";
                break;
            case IMAGE:
                pushText = "[ 图片 ]";
                break;
            case LOCATION:
                pushText = "[ 位置 ]";
                break;
            case VOICE:
                pushText = "[ 语音 ]";
                break;
        }
        // 设置强制推送
        message.setAttribute("em_force_notification", true);
        message.setAttribute(Constent.USER_AVATAR, ChatDBHelper.getUserAvatar(ChatDBHelper.getChatId()));
        // 设置自定义推送提示
        JSONObject extObject = new JSONObject();
        try {

            extObject.put(Constent.USER_NAME, //自定义推送名称
                    message.getChatType()== EMMessage.ChatType.GroupChat?  //判断是否群聊
                    ChatHelper.getGroupName(personName):  //群昵称
                    ChatDBHelper.getUserName(ChatDBHelper.getChatId()));  //用户昵称
            extObject.put("em_push_content", pushText);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        // 将推送扩展设置到消息中
        message.setAttribute(Constent.USER_EXT, extObject);
    }

    /**
     * 消息回调
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            switch (requestCode) {
                case RESULT_CODE_VIDEO:  //返回结果是相机拍摄的
                    String imageurl = data.getStringExtra(Constent.IMAGE_URL);
                    String videoUrl = data.getStringExtra(Constent.VIDEO_URL);
                    String videoFirstImage = data.getStringExtra(Constent.VIDEO_IMAGE_URL);
                    if (imageurl != null) {  //照片
                        sendMessage(sendImage(imageurl, personName));
                    } else {  //视频
                        sendMessage(sendVideo(videoUrl, videoFirstImage, personName));
                    }
                    break;
                case RESULT_CODE_PHOTO:   //返回结果是相册回调的
                    images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                    for (int i = 0; i < images.size(); i++) {
                        sendMessage(sendImage(images.get(i).path, personName));
                    }
                    break;
                case RESULT_CODE_MAP:  //返回结果是地图回调的
                    final double latitude = data.getDoubleExtra("latitude", 116);
                    final double longitude = data.getDoubleExtra("longitude", 39.92);
                    String title = data.getStringExtra(Constent.MAP_TITLE);
                    String address = data.getStringExtra(Constent.MAP_ADDRESS);
                    sendMessage(sendLocation(latitude, longitude, title, address, personName));
                    break;
                case RESULT_CODE_FILE:    //返回结果是文件选择回调
                    ArrayList<String> fileList = data.getStringArrayListExtra(FilePickerConst.KEY_SELECTED_DOCS);
                    for (int i = 0; i < fileList.size(); i++) {

                        if (getFileOrFilesSize(fileList.get(i), 3) <= 10) {
                            String length = getAutoFileOrFilesSize(fileList.get(i));
                            sendMessage(sendFile(fileList.get(i), length, personName));
                        } else {
                            ToastUtil.showToast(context, "发送的文件不能大于10M，请重新选择");
                        }

                    }
                    break;
                case RESULT_CODE_TRANSMIT:  //返回结果是转发回调
                    String transmitName = data.getStringExtra(Constent.TRANSIMIT_NAME);
                    if (transmitName.equals(personName)) {
                        chatListAdapter.getData(getAllMessage(), personName);
                    }
            }
        }
    }


    /**
     * 发出的语音播放
     */
    public void voiceToClickListener() {
        chatListAdapter.setOnToVoiceClickListener(new ChatListAdapter.OnToVoiceClick() {
            @Override
            public void click(int position, String path) {
                final View view = updateItem(position);
                if (imageViewTo != null) {
                    imageViewTo.setBackgroundResource(R.drawable.voice_to_v3);
                    imageViewTo = null;
                }
                imageViewTo = view.findViewById(R.id.chat_to_voice_image);
                imageViewTo.setBackgroundResource(R.drawable.chat_voice_to_animation);
                AnimationDrawable animation = (AnimationDrawable) imageViewTo.getBackground();
                animation.start();
                MediaHelper.playSound(path, new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mediaPlayer) {
                        imageViewTo.setBackgroundResource(R.drawable.voice_to_v3);

                    }
                });
            }
        });
    }

    /**
     * 收到的语音播放
     */
    private void voiceFromClickListener() {
        chatListAdapter.setOnFromVoiceClickListener(new ChatListAdapter.OnFromVoiceClick() {
            @Override
            public void click(final int position, String path, int length) {
                final View view = updateItem(position);
                if (imageViewFrom != null) {
                    imageViewFrom.setBackgroundResource(R.drawable.voice_v3);
                    imageViewFrom = null;
                }
                imageViewFrom = view.findViewById(R.id.chat_from_voice_image);
                final TextView textView = view.findViewById(R.id.chat_from_voice_tip);
                textView.setVisibility(View.GONE);
                imageViewFrom.setBackgroundResource(R.drawable.chat_voice_from_animation);
                AnimationDrawable animation = (AnimationDrawable) imageViewFrom.getBackground();
                animation.start();
                MediaHelper.playSound(path, new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mediaPlayer) {
                        imageViewFrom.setBackgroundResource(R.drawable.voice_v3);
                    }
                });
            }
        });
    }

    /**
     * 发送失败重新发送消息
     */
    private void reSendMessage() {
        chatListAdapter.setOnToErrorClickListener(new ChatListAdapter.OnToErrorClick() {
            @Override
            public void click(int position) {
                message = chatListAdapter.mList.get(position);
//                messageList.remove(position);

                chatListAdapter.remove(position);
                sendMessage(message);
            }
        });
    }


    /**
     * list局部更新
     *
     * @param position
     * @return
     */
    private View updateItem(int position) {

        /**第一个可见的位置**/
        int firstVisiblePosition = chatList.getFirstVisiblePosition();
        /**最后一个可见的位置**/
        int lastVisiblePosition = chatList.getLastVisiblePosition();

        /**在看见范围内才更新，不可见的滑动后自动会调用getView方法更新**/
        View view1 = null;
        if (position >= firstVisiblePosition && position <= lastVisiblePosition) {
            /**获取指定位置view对象**/
            View view = chatList.getChildAt(position - firstVisiblePosition);
            view1 = chatListAdapter.getView(position, view, chatList);

        }
        return view1;
    }





    @OnClick({R.id.title_bar_back_image, R.id.title_bar_back_text, R.id.chat_eidt, R.id.title_bar_right_image, R.id.chat_voice,
            R.id.chat_add, R.id.chat_emoji})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_bar_back_image:
                finish();
                break;
            case R.id.title_bar_back_text:
                finish();
                break;
            case R.id.title_bar_right_image:  //标题栏右图标
                if (!ClickUtil.isFastClick()) {
                    if(isGroupChat){
                        Intent intent = new Intent(context, ChatRoomInfoActivity.class);
                        intent.putExtra(Constent.PERSON_NAME, personName);
                        startActivity(intent);
                    }else{
                        Intent intent = new Intent(context, ChatInfo.class);
                        intent.putExtra(Constent.PERSON_NAME, personName);
                        startActivity(intent);
                    }

                }
                break;
            case R.id.chat_eidt:  //输入框
                hideInput();
                chatEdit.setCursorVisible(true);
                goToListBottom();
                break;
            case R.id.chat_voice: //语音按钮
                goToListBottom();
                voiceClick();
                AppKeyBoardMgr.hideInputMethod((AppCompatActivity) context);
                break;
            case R.id.chat_add: //菜单按钮
                goToListBottom();
                AppKeyBoardMgr.hideInputMethod((AppCompatActivity) context);
                chatFunctionLayout.setVisibility(View.VISIBLE);
                chatEmojiLayout.setVisibility(View.GONE);
                chatEmoji.setBackgroundResource(R.drawable.chat_emoji);
                chatVoice.setBackgroundResource(R.drawable.chat_voice);
                break;
            case R.id.chat_emoji:  //表情按钮
                goToListBottom();
                chatEdit.setCursorVisible(true);
                if (chatEmojiLayout.getVisibility() == View.VISIBLE) {
                    chatEmojiLayout.setVisibility(View.GONE);
                    chatEdit.setVisibility(View.VISIBLE);
                    chatVoiceButton.setVisibility(View.GONE);
                    AppKeyBoardMgr.showKeybord(chatEdit);
                    chatEmoji.setBackgroundResource(R.drawable.chat_emoji);
                } else {
                    chatEdit.setVisibility(View.VISIBLE);
                    chatVoiceButton.setVisibility(View.GONE);
                    AppKeyBoardMgr.hideKeybord(chatEdit);
                    AppKeyBoardMgr.hideInputMethod((AppCompatActivity) context);
                    chatEmoji.setBackgroundResource(R.drawable.chat_menu);
                    chatFunctionLayout.setVisibility(View.GONE);
                    chatEmojiLayout.setVisibility(View.VISIBLE);
                    chatVoice.setBackgroundResource(R.drawable.chat_voice);
                }

                break;

        }
    }

    /**
     * 点击输入框外收起输入法等一系列操作
     */
    private void hideInput() {
        chatEdit.setCursorVisible(false);
        chatFunctionLayout.setVisibility(View.GONE);
        chatEmojiLayout.setVisibility(View.GONE);
        chatVoice.setBackgroundResource(R.drawable.chat_voice);
        chatEmoji.setBackgroundResource(R.drawable.chat_emoji);
    }

    /**
     * 让listview滚动到底部
     */
    private void goToListBottom() {

        chatList.post(new Runnable() {
            @Override
            public void run() {
                chatList.setSelection(chatList.getBottom());
            }
        });
    }

    /**
     * 语音按钮的点击监听
     */
    private void voiceClick() {
        if (chatVoiceButton.getVisibility() == View.VISIBLE) {
            chatEdit.setVisibility(View.VISIBLE);
            chatVoiceButton.setVisibility(View.GONE);
            AppKeyBoardMgr.showKeybord(chatEdit);
            chatEdit.setCursorVisible(true);
            chatVoice.setBackgroundResource(R.drawable.chat_voice);
        } else {
            chatEdit.setVisibility(View.GONE);
            chatFunctionLayout.setVisibility(View.GONE);
            chatEmojiLayout.setVisibility(View.GONE);
            chatEmoji.setBackgroundResource(R.drawable.chat_emoji);
            chatVoiceButton.setVisibility(View.VISIBLE);
            chatVoice.setBackgroundResource(R.drawable.chat_menu);
        }

    }

    /**
     * 聊天页面的长按事件
     */
    private void chatLongClickListener(){
        chatListAdapter.setOnLongClickListener(new ChatListAdapter.OnLongClick() {
            @Override
            public void longClick(float x, float y, int width, int height, final int type, final EMMessage message, final int i) {

                QQTipView.Builder qqTipView = new QQTipView.Builder(context, chatTip, (int) x + (width / 2),
                        (int) y + DensityUtils.dp2px(context, 20), height);
                if (type == 1) {
                    qqTipView.addItem(new QQTipItem("复制"));
                }
                if (type == 1 || type == 2) {
                    qqTipView.addItem(new QQTipItem("转发"));
                }

                qqTipView.addItem(new QQTipItem("收藏"))
                        .addItem(new QQTipItem("删除"));

                qqTipView.setOnItemClickListener(new QQTipView.OnItemClickListener() {
                    @Override
                    public void onItemClick(String str, final int position) {
                        switch (str) {
                            case "复制":
                                if (message != null) {
                                    EMTextMessageBody textBody = (EMTextMessageBody) message.getBody();
                                    cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
//                                     将文本内容放到系统剪贴板里。
                                    assert cm != null;
                                    cm.setText(textBody.getMessage());
                                }
                                break;
                            case "转发":
                                transmitMessage = message;
                                Intent intent = new Intent(context, Transmit.class);
                                intent.putExtra(Constent.TRANSIMIT_MESSAGE, transmitMessage);
                                startActivityForResult(intent, RESULT_CODE_TRANSMIT);
                                break;
                            case "收藏":
                                break;
                            case "删除":
                                showDeleteDialog(message.getMsgId(), i);


                                break;
                        }
                    }

                    @Override
                    public void dismiss() {

                    }
                })
                        .create();
            }
        });
    }

    /**
     * 输入框的长按粘贴
     */
    private void editLongClickListener() {
        chatEdit.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                chatEdit.setCursorVisible(true);
                AppKeyBoardMgr.showKeybord(chatEdit);
                popupWindow = new CommonPopupWindow.Builder(context)
                        //设置PopupWindow布局
                        .setView(R.layout.chat_edit_tip)
                        //设置宽高
                        .setWidthAndHeight(ViewGroup.LayoutParams.WRAP_CONTENT,
                                ViewGroup.LayoutParams.WRAP_CONTENT)
                        //设置动画
//                        .setAnimationStyle(R.style.AnimDown)
                        //设置背景颜色，取值范围0.0f-1.0f 值越小越暗 1.0f为透明
//                        .setBackGroundLevel(1f)
                        //设置PopupWindow里的子View及点击事件
                        .setViewOnclickListener(new CommonPopupWindow.ViewInterface() {
                            @Override
                            public void getChildView(View view, int layoutResId) {
                                TextView textView = view.findViewById(R.id.chat_edit_tip_text);
                                textView.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        chatEdit.setCursorVisible(true);
                                        AppKeyBoardMgr.showKeybord(chatEdit);
                                        if (cm != null) {
                                            String oriContent = chatEdit.getText().toString();
                                            int index = Math.max(chatEdit.getSelectionStart(), 0);
                                            StringBuilder sBuilder = new StringBuilder(oriContent);
                                            sBuilder.insert(index, cm.getText());

                                            chatEdit.setText(sBuilder.toString().trim());
                                            chatEdit.setSelection(index + cm.getText().length());
                                        }
                                        popupWindow.dismiss();
                                    }
                                });

                            }
                        })
                        //设置外部是否可点击 默认是true
                        .setOutsideTouchable(true)
                        //开始构建
                        .create();
                //弹出PopupWindow
                popupWindow.showAsDropDown(chatBottomLayout, DensityUtils.dp2px(context, 50),
                        -DensityUtils.dp2px(context, 100), Gravity.TOP);
                return true;
            }
        });
    }

    /**
     * 根据生命周期 管理播放录音
     */
    @Override
    protected void onPause() {
        super.onPause();
        MediaHelper.pause();
        EMClient.getInstance().chatManager().removeMessageListener(msgListener);//取消注册
    }

    @Override
    protected void onResume() {
        super.onResume();
        MediaHelper.resume();
        EMClient.getInstance().chatManager().addMessageListener(msgListener);//注册
        if (isGroupChat) {
            titleBarText.setText(ChatHelper.getGroupName(personName));
        } else {
            titleBarText.setText(ChatDBHelper.getUserName(personName));
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        MediaHelper.release();
        EMClient.getInstance().chatManager().removeMessageListener(msgListener);//取消注册

    }

    /**
     * 初始化相片选择器
     */
    private void initImagePicker() {
        //图片浏览器初始化
        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new ImageSelectImageLoader());   //设置图片加载器
        imagePicker.setShowCamera(false);  //显示拍照按钮
        imagePicker.setCrop(false);        //允许裁剪（单选才有效）
        imagePicker.setSaveRectangle(true); //是否按矩形区域保存
        imagePicker.setSelectLimit(9);    //选中数量限制
//        imagePicker.setStyle(CropImageView.Style.RECTANGLE);  //裁剪框的形状
//        imagePicker.setFocusWidth(800);   //裁剪框的宽度。单位像素（圆形自动取宽高最小值）
//        imagePicker.setFocusHeight(800);  //裁剪框的高度。单位像素（圆形自动取宽高最小值）
//        imagePicker.setOutPutX(1000);//保存文件的宽度。单位像素
//        imagePicker.setOutPutY(1000);//保存文件的高度。单位像素
    }

    /**
     * 点击Emoji
     *
     * @param v
     */
    @Override
    public void onEmojiconBackspaceClicked(View v) {
        EmojiconsFragment.backspace(chatEdit);

    }

    /**
     * 点击emoji里面的删除按钮
     *
     * @param emojicon
     */
    @Override
    public void onEmojiconClicked(Emojicon emojicon) {

        chatEdit.append(emojicon.getEmoji());
    }

}
