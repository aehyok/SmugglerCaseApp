package com.sinostar.assistant.ui.addressList.chatRoom.chatRoomInfo;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import com.allen.library.SuperTextView;
import com.google.gson.Gson;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMGroup;
import com.hyphenate.exceptions.HyphenateException;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction;
import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;
import com.sinostar.assistant.R;
import com.sinostar.assistant.bean.User;
import com.sinostar.assistant.ui.addressList.AdressList;
import com.sinostar.assistant.ui.addressList.chat.helper.ChatDBHelper;
import com.sinostar.assistant.ui.addressList.chatRoom.create.CreateChatRoom;
import com.sinostar.assistant.utils.ACache;
import com.sinostar.assistant.utils.AppNetworkMgr;
import com.sinostar.assistant.utils.Constent;
import com.sinostar.assistant.utils.OnMultiItemClickListener;
import com.sinostar.assistant.utils.ToastUtil;
import com.sinostar.assistant.widget.MyGridView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ChatRoomInfoActivity extends AppCompatActivity {

    @BindView(R.id.title_bar_text)
    TextView titleBarText;
    @BindView(R.id.chat_room_info_grid)
    MyGridView chatRoomInfoGrid;
    @BindView(R.id.chat_room_announce)
    TextView chatRoomAnnounce;
    @BindView(R.id.chat_room_be_quiet)
    SuperTextView chatRoomBeQuiet;
    @BindView(R.id.chat_room_set_top)
    SuperTextView chatRoomBeTop;
    @BindView(R.id.chat_room_name)
    SuperTextView chatRoomName;
    @BindView(R.id.chat_room_more_person)
    SuperTextView chatRoomMorePerson;
    @BindView(R.id.chat_room_announce_text)
    SuperTextView chatRoomAnnounceText;
    @BindView(R.id.chat_room_delete_and_quiet)
    QMUIRoundButton chatRoomDeleteAndQuiet;
    private String personName;
    private String announcement;
    private String groupName;
    private Context context = this;
    private ACache aCache;
    private Gson gson;
    private User user;
    private String groupOwner;
    private boolean isMyGroup = false;
    private List<String> members;
    private ChatRoomInfoAvatarAdapter avatarAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room_info);
        ButterKnife.bind(this);
        personName = getIntent().getStringExtra(Constent.PERSON_NAME);
        gson = new Gson();
        aCache = ACache.get(this);
        user = new User();
        initView();
        initListener();


    }

    private void initListener() {
        chatRoomInfoGrid.setOnItemClickListener(new OnMultiItemClickListener() {
            @Override
            public void onItemClick1(AdapterView<?> adapterView, View view, int i, long l) {
                //添加群成员
                if(isMyGroup){
                    if(i==avatarAdapter.getCount()-1){ //群主删人
                        changeMembers(false);
                    }
                    if(i==avatarAdapter.getCount()-2){//群主加人
                        changeMembers(true);
                    }
                }else{
                    if(i==avatarAdapter.getCount()-1){//成员加人
                        changeMembers(true);
                    }
                }

            }
        });
    }
    private void changeMembers(boolean addMembers){
        members.remove(0);
        Intent intent=new Intent(context, CreateChatRoom.class);
        intent.putExtra(Constent.PERSON_NAME,personName);
        intent.putStringArrayListExtra(Constent.GROUP_MEMBERS, (ArrayList<String>) members);
        intent.putExtra(Constent.IS_MY_GROUP,isMyGroup);
        intent.putExtra(Constent.IS_ADD_MEMBERS,addMembers);
        startActivity(intent);
    }



    @Override
    protected void onResume() {
        super.onResume();
        fetchGroupMembers(personName);
    }

    private void fetchGroupMembers(final String groupId) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                //如果群成员较多，需要多次从服务器获取完成
                final List<String> memberList = new ArrayList<>();

                EMGroup group = null;
                try {
                    //根据群组ID从服务器获取群组基本信息
                    group = EMClient.getInstance().groupManager().getGroupFromServer(groupId, true);
                } catch (final HyphenateException e) {
                    Looper.prepare();
                    Toast.makeText(context, "你已经不在该群聊了，请重新加入后再进行该操作", Toast.LENGTH_SHORT).show();
                    Looper.loop();
                    finish();

                }
                assert group != null;
                //群主
                groupOwner = group.getOwner();
                if (groupOwner.equals(ChatDBHelper.getChatId())) {
                    isMyGroup = true;
                }

                members = group.getMembers();//获取内存中的群成员
                members.add(0,groupOwner);
                announcement = group.getAnnouncement(); //获取公告信息
                groupName = group.getGroupName();//获取群名字
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (memberList.size() < 9) {
                            chatRoomMorePerson.setVisibility(View.GONE);
                        } else {
                            chatRoomMorePerson.setVisibility(View.VISIBLE);
                        }
                        //群公告
                        if (announcement != null && !announcement.equals("")) {
                            chatRoomAnnounce.setVisibility(View.VISIBLE);
                            chatRoomAnnounceText.setRightString("");
                            chatRoomAnnounce.setText(announcement);
                        } else {
                            chatRoomAnnounce.setVisibility(View.GONE);
                            chatRoomAnnounceText.setRightString("未设置");
                            chatRoomAnnounce.setText(announcement);
                        }
                         avatarAdapter = new ChatRoomInfoAvatarAdapter(context, members,isMyGroup);
                        chatRoomInfoGrid.setAdapter(avatarAdapter);

                        chatRoomName.setRightString(groupName);//群名字

                    }
                });

            }
        }).start();

    }


    private void initView() {
        titleBarText.setText("聊天信息");

        if (aCache.getAsObject(personName) != null) {
            user=(User)aCache.getAsObject(personName);
        }
        if (user.isTop()) {
            chatRoomBeTop.setRightTvDrawableRight(getResources().getDrawable(R.drawable.turn_on));
        } else {
            chatRoomBeTop.setRightTvDrawableRight(getResources().getDrawable(R.drawable.turn_off));
        }
        if (user.isQuiet()) {
            chatRoomBeQuiet.setRightTvDrawableRight(getResources().getDrawable(R.drawable.turn_on));
        } else {
            chatRoomBeQuiet.setRightTvDrawableRight(getResources().getDrawable(R.drawable.turn_off));
        }
    }

    /**
     * 显示删除弹窗
     */
    public void showDeleteDialog() {
        final Dialog deleteDialog = new Dialog(context, R.style.MapDialog);
        deleteDialog.setCanceledOnTouchOutside(true);
        deleteDialog.setCancelable(true);
        Window window = deleteDialog.getWindow();
        assert window != null;
        window.setGravity(Gravity.BOTTOM);
//        window.setWindowAnimations(R.style.MapDialog);
        View view = View.inflate(context, R.layout.delete_dialog, null);
        TextView tip = view.findViewById(R.id.delete_dialog_tip);
        tip.setText("退出后不会通知群聊中其他成员，且不会再接收此群聊消息");
        view.findViewById(R.id.delete_dialog_confirm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isMyGroup){
                    destroyGroup(personName);
                }else{
                    leaveGroup(personName);
                }
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
     * 解散群组（群主）
     * @param groupId 群ID
     */
    private void destroyGroup(final String groupId) {
                    EMClient.getInstance().groupManager().asyncDestroyGroup(groupId,groupCallBack);
    }

    /**
     * 退出群聊
     * @param groupId 群ID
     */
    private void leaveGroup(final String groupId) {
        EMClient.getInstance().groupManager().asyncLeaveGroup(groupId,groupCallBack);
    }

    /**
     * 群删除操作回调
     */
    private EMCallBack groupCallBack=new EMCallBack() {
        @Override
        public void onSuccess() {
            startActivity(new Intent(context, AdressList.class));
            finish();
        }

        @Override
        public void onError(int i, String s) {

        }

        @Override
        public void onProgress(int i, String s) {

        }
    };

    private void changGroupMessage(int type,String text){
        Intent intent=new Intent(this,ChatRoomEditActivity.class);
        intent.putExtra(Constent.PERSON_NAME,personName);
        intent.putExtra(Constent.CHAT_ROOM_ET_TYPE,type);
        intent.putExtra(Constent.CHAT_ROOM_ET_TEXT,text);
        startActivity(intent);
    }

    @OnClick({R.id.title_bar_back_image, R.id.title_bar_back_text, R.id.chat_room_name,
            R.id.chat_room_QR_code, R.id.chat_room_announce_text, R.id.chat_room_record,
            R.id.chat_room_be_quiet, R.id.chat_room_set_top, R.id.chat_room_clear,
            R.id.chat_room_more_person, R.id.chat_room_delete_and_quiet})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_bar_back_image:
                finish();
                break;
            case R.id.title_bar_back_text:
                finish();
                break;
            case R.id.chat_room_name:
                if(isMyGroup){
                    changGroupMessage(1,groupName);
                }else{
                    ToastUtil.showToast(context,"只有群主才能修改群名称。");
                }
                break;
            case R.id.chat_room_QR_code:
                Intent intent=new Intent(this,ChatRoomCodeActivity.class);
                intent.putExtra(Constent.PERSON_NAME,personName);
                startActivity(intent);
                break;
            case R.id.chat_room_announce_text:
                if(isMyGroup){
                    changGroupMessage(2,announcement);
                }else{
                    ToastUtil.showToast(context,"只有群主才能修改群公告。");
                }
                break;
            case R.id.chat_room_record:
                break;
            case R.id.chat_room_be_quiet:
                if (user.isQuiet()) {
                    user.setQuiet(false);
                    chatRoomBeQuiet.setRightTvDrawableRight(getResources().getDrawable(R.drawable.turn_off));
                } else {
                    user.setQuiet(true);
                    chatRoomBeQuiet.setRightTvDrawableRight(getResources().getDrawable(R.drawable.turn_on));
                }
                aCache.put(personName, user);
                break;
            case R.id.chat_room_set_top:
                if (user.isTop()) {
                    user.setTop(false);
                    chatRoomBeTop.setRightTvDrawableRight(getResources().getDrawable(R.drawable.turn_off));
                } else {
                    user.setTop(true);
                    chatRoomBeTop.setRightTvDrawableRight(getResources().getDrawable(R.drawable.turn_on));
                }
                aCache.put(personName, user);
                break;
            case R.id.chat_room_clear:
                break;
            case R.id.chat_room_more_person:
                break;
            case R.id.chat_room_delete_and_quiet:
                showDeleteDialog();
                break;
        }
    }
}
