package com.sinostar.assistant.ui.addressList.chatRoom.create;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hyphenate.EMCallBack;
import com.hyphenate.EMValueCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMGroup;
import com.hyphenate.chat.EMGroupManager;
import com.hyphenate.chat.EMGroupOptions;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMTextMessageBody;
import com.hyphenate.exceptions.HyphenateException;
import com.sinostar.assistant.R;
import com.sinostar.assistant.base.ApplicationUtil;
import com.sinostar.assistant.ui.addressList.AdressList;
import com.sinostar.assistant.ui.addressList.chat.Chat;
import com.sinostar.assistant.ui.addressList.chat.helper.ChatDBHelper;
import com.sinostar.assistant.base.BaseActivity;
import com.sinostar.assistant.bean.User;
import com.sinostar.assistant.utils.Constent;
import com.sinostar.assistant.utils.DateUtil;
import com.sinostar.assistant.utils.DensityUtils;
import com.sinostar.assistant.utils.MapUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 *
 * 新建群聊
 */

public class CreateChatRoom extends BaseActivity {

    @BindView(R.id.title_bar_back_image)
    ImageView titleBarBackImage;
    @BindView(R.id.title_bar_back_text)
    TextView titleBarBackText;
    @BindView(R.id.title_bar_text)
    TextView titleBarText;
    @BindView(R.id.title_bar_rightText)
    TextView titleBarRightText;
    @BindView(R.id.create_chat_room_list)
    ListView createChatRoomList;
    CreateChatRoomAdapter adapter;
    @BindView(R.id.create_chat_room_avatar_list)
    RecyclerView createChatRoomAvatarList;
    @BindView(R.id.search_image)
    ImageView searchImage;
    private Context context = this;
    private List<String> usernames = new ArrayList<>();
    private CreateChatRoomAvatarAdapter avatarAdapter;
    private boolean isCreateGroup =false;
    private ArrayList<String> groupMembers;
    private boolean isMyGroup;
    private String groupId;
    private boolean isAddMembers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_chat_room);
        ButterKnife.bind(this);
        groupMembers = getIntent().getStringArrayListExtra(Constent.GROUP_MEMBERS);
        groupId=getIntent().getStringExtra(Constent.PERSON_NAME);
        isMyGroup=getIntent().getBooleanExtra(Constent.IS_MY_GROUP,false);
        isAddMembers=getIntent().getBooleanExtra(Constent.IS_ADD_MEMBERS,true);
        if(groupMembers!=null&&groupMembers.size()!=0){
            isCreateGroup=false;
        }
        init();
        getData();
        initListListener();
    }

    private void init() {
        titleBarBackImage.setVisibility(View.GONE);
        titleBarBackText.setText("取消");
        titleBarBackText.setPadding(DensityUtils.dp2px(context, 12), 0, 0, 0);
        titleBarRightText.setText("完成");
        titleBarRightText.setClickable(false);
        titleBarRightText.setTextColor(Color.GRAY);
        titleBarText.setText("选择联系人");
        adapter = new CreateChatRoomAdapter(context);
        createChatRoomList.setAdapter(adapter);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        avatarAdapter=new CreateChatRoomAvatarAdapter(context);
        createChatRoomAvatarList.setLayoutManager(linearLayoutManager);
        createChatRoomAvatarList.setAdapter(avatarAdapter);
//
    }

    private void initListListener() {
        createChatRoomList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                adapter.getCheck(i);
            }
        });
//
        adapter.setOnUserSelectListener(new CreateChatRoomAdapter.OnUserSelectListener() {
            @Override
            public void onSeleceted(Map<String, Object> avatarMap) {
                    avatarAdapter.additem(avatarMap);
                    if(avatarMap.size()==0){
                        titleBarRightText.setText("完成");
                        titleBarRightText.setClickable(false);
                        titleBarRightText.setTextColor(Color.GRAY);
                        searchImage.setVisibility(View.VISIBLE);
                    }else {
                        titleBarRightText.setText("完成("+avatarMap.size()+")");
                        titleBarRightText.setClickable(true);
                        titleBarRightText.setTextColor(Color.GREEN);
                        searchImage.setVisibility(View.GONE);
                        if (avatarMap.size() >= 5) {
                            ViewGroup.LayoutParams params = new RelativeLayout.LayoutParams(
                                    DensityUtils.dp2px(getBaseContext(), 300), ViewGroup.LayoutParams.WRAP_CONTENT);
                            createChatRoomAvatarList.setLayoutParams(params);
                        } else {
                            ViewGroup.LayoutParams params = new RelativeLayout.LayoutParams(
                                    ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                            createChatRoomAvatarList.setLayoutParams(params);
                        }
                        createChatRoomAvatarList.scrollToPosition(avatarAdapter.getItemCount() - 1);//每次增加一条后RecyclerView滑动到最后
                    }
                }

        });
    }


    private void getData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    usernames = EMClient.getInstance().contactManager().getAllContactsFromServer();
                    if (usernames != null && usernames.size() != 0) {
                        final Map<String, Boolean> map = new HashMap();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if(isCreateGroup){//新建群聊
                                    adapter.getData(usernames,null);
                                }else{
                                    if(isAddMembers){//群聊加人
                                        adapter.getData(usernames,groupMembers);
                                    }else{//群聊删人
                                        adapter.getData(groupMembers,null);
                                    }
                                }

                            }
                        });
                    }
                } catch (HyphenateException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @OnClick({R.id.title_bar_back_text, R.id.title_bar_rightText})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_bar_back_text:
                finish();
                break;

            case R.id.title_bar_rightText:
                List<String> list = MapUtil.getKeyListFromValue(adapter.avatarMap);
                String[] strings = new String[list.size()];
                list.toArray(strings);
                    if(groupMembers!=null&&isMyGroup){
                        if(isAddMembers){
                            addMembers(strings);
                        }else{
                            deleteMembers(strings);
                        }
                    }else{
                        createChatRoom(strings);
                    }



                break;
        }
    }
    private void deleteMembers( String[] members){
        //把username从群组里删除
        for(int i=0;i<members.length;i++){
            if(i==members.length-1){
                EMClient.getInstance().groupManager().asyncRemoveUserFromGroup(groupId, members[i],addMembersCallback);//需异步处理
            }else{
                try {
                    EMClient.getInstance().groupManager().removeUserFromGroup(groupId, members[i]);//需异步处理
                } catch (HyphenateException e) {
                    e.printStackTrace();
                }
            }

        }
    }
    private void addMembers( String[] members)  {
        if(isMyGroup){
            //群主加人调用此方法
            EMClient.getInstance().groupManager().asyncAddUsersToGroup(groupId, members,addMembersCallback);//需异步处理
        }else{
            //私有群里，如果开放了群成员邀请，群成员邀请调用下面方法
            EMClient.getInstance().groupManager().asyncInviteUser(groupId, members, null,addMembersCallback);//需异步处理
        }
    }
    private EMCallBack addMembersCallback= new EMCallBack() {
        @Override
        public void onSuccess() {
            Intent intent=new Intent(context,Chat.class);
            intent.putExtra(Constent.PERSON_NAME,groupId);
            startActivity(intent);
            finish();

        }

        @Override
        public void onError(int i, String s) {

        }

        @Override
        public void onProgress(int i, String s) {

        }
    };

    private void createChatRoom(String[] members) {
        /**
         * 创建群组
         * @param groupName 群组名称
         * @param desc 群组简介
         * @param allMembers 群组初始成员，如果只有自己传空数组即可
         * @param reason 邀请成员加入的reason
         * @param option 群组类型选项，可以设置群组最大用户数(默认200)及群组类型@see {@link EMGroupStyle}
         *               EMGroupStylePrivateOnlyOwnerInvite——私有群，只有群主可以邀请人；
         *               EMGroupStylePrivateMemberCanInvite——私有群，群成员也能邀请人进群；
         *               EMGroupStylePublicJoinNeedApproval——公开群，加入此群除了群主邀请，只能通过申请加入此群；
         *               EMGroupStylePublicOpenJoin ——公开群，任何人都能加入此群。
         * @return 创建好的group
         * @throws HyphenateException
         */
        EMGroupOptions option = new EMGroupOptions();
        option.maxUsers = 200;
        option.style = EMGroupManager.EMGroupStyle.EMGroupStylePrivateMemberCanInvite;
        String groupName = "群组";
        String desc = "";
        final String[] allMembers = members;
        String reason = "";



        EMClient.getInstance().groupManager().asyncCreateGroup(groupName, desc, allMembers, reason, option, new EMValueCallBack<EMGroup>() {
            @Override
            public void onSuccess(EMGroup emGroup) {
                User user=new User();
                user.setUserAvatar(Constent.CHAT_GROUP_AVATAR);
                user.setUserName(emGroup.getGroupName());
                ChatDBHelper.setUserDataMap(emGroup.getGroupId(),user);
                Intent intent=new Intent(context, Chat.class);
                intent.putExtra(Constent.PERSON_NAME,emGroup.getGroupId());
                intent.putExtra(Constent.IS_GROUP_CHAT,"1");
                EMMessage msg = EMMessage.createReceiveMessage(EMMessage.Type.TXT);
                msg.setChatType(EMMessage.ChatType.GroupChat);
                msg.setFrom(ChatDBHelper.getChatId());
                msg.setTo(emGroup.getGroupId());
                msg.setMsgId(UUID.randomUUID().toString());
                msg.setMsgTime(DateUtil.getCurTimeLong());
                String names = "";
                for(int i=0;i<allMembers.length;i++){
                    names=names+ChatDBHelper.getUserName(allMembers[i]);
                    if(i<allMembers.length-1){
                        names=names+"、";
                    }
                }
                msg.addBody(new EMTextMessageBody("你邀请"+names+"进入群聊"));
                msg.setAttribute(Constent.IS_GROUP_MESSAGE_TIP,true);
                // save invitation as messages
                EMClient.getInstance().chatManager().saveMessage(msg);
                startActivity(intent);
                finish();
            }

            @Override
            public void onError(int i, String s) {

            }
        });
    }
}
