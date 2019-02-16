package com.sinostar.assistant.ui.addressList.message;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;
import com.sinostar.assistant.R;
import com.sinostar.assistant.bean.User;
import com.sinostar.assistant.ui.addressList.chat.Chat;
import com.sinostar.assistant.ui.addressList.chat.helper.ChatDBHelper;
import com.sinostar.assistant.ui.addressList.chatRoom.create.CreateChatRoom;
import com.sinostar.assistant.base.ApplicationUtil;
import com.sinostar.assistant.utils.ACache;
import com.sinostar.assistant.utils.ClickUtil;
import com.sinostar.assistant.utils.Constent;
import com.sinostar.assistant.utils.DensityUtils;
import com.sinostar.assistant.utils.RecycleViewDivider;
import com.sinostar.assistant.utils.ToastUtil;
import com.yanzhenjie.recyclerview.swipe.SwipeItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenu;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuBridge;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItem;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static com.scwang.smartrefresh.layout.util.DensityUtil.dp2px;


/**
 * 聊天列表
 */
public class MessageFragment extends Fragment {


    Unbinder unbinder;
    @BindView(R.id.swipeList)
    SwipeMenuRecyclerView swipeList;
    SwipeAdater swipeAdater;
    @BindView(R.id.title_bar_text)
    TextView titleBarText;
    @BindView(R.id.title_bar_right_image)
    ImageView titleBarRightImage;
    private ACache aCache;
    private Gson gson;

    private boolean isConnect;
    private Bundle arg;
    private String personName;



    public MessageFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        arg=getArguments();
        if(arg!=null){
            personName= arg.getString(Constent.PERSON_NAME);
        }

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_message, container, false);
        unbinder = ButterKnife.bind(this, view);
        aCache=ACache.get(getActivity());
        if(personName!=null&&!personName.equals("")){
            Intent chat = new Intent(getActivity(), Chat.class);
            chat.putExtra(Constent.PERSON_NAME, personName);
            EMConversation conversation = EMClient.getInstance().chatManager().getConversation(personName);
            //指定会话消息未读数清零
            if(conversation!=null){
                conversation.markAllMessagesAsRead();
            }
            startActivity(chat);
        }


        titleBarText.setText("通讯录");
        titleBarRightImage.setBackgroundResource(R.drawable.ic_add_white_36dp);
        swipeAdater = new SwipeAdater(getActivity());
        LinearLayoutManager layoutmanager = new LinearLayoutManager(getActivity());
        //设置RecyclerView 布局
        swipeList.setLayoutManager(layoutmanager);
        swipeList.addItemDecoration(new RecycleViewDivider(getActivity(),LinearLayoutManager.HORIZONTAL));
        initSwipeList();
        swipeList.setAdapter(swipeAdater);
        ApplicationUtil.setOnMessageReceiveListener(new ApplicationUtil.OnMessageReceiveListener() {
            @Override
            public void getMessage(List<EMMessage> messages) {
                getAllCoversations();
                getUnReadMessage();
            }
        });
        getAllCoversations();
        return view;
    }


    /**
     * 获取并展示未读消息总数
     */
    private void getUnReadMessage(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                final int unreadMsgCountTotal = EMClient.getInstance().chatManager().getUnreadMessageCount();
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        TextView unReadNum=getActivity().findViewById(R.id.bottom_bar_num3);
                        if(unreadMsgCountTotal>0){
                            unReadNum.setVisibility(View.VISIBLE);
                            if(unreadMsgCountTotal>99){
                                unReadNum.setText("···");
                            }else{
                            unReadNum.setText(unreadMsgCountTotal+"");
                            }
                        }else{
                            unReadNum.setVisibility(View.GONE);
                        }
                    }
                });

            }
        }).start();

    }
    @Override
    public void onResume() {
        super.onResume();
        getAllCoversations();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private void initSwipeList() {

        /**
         * 消息的点击跳转
         */
        swipeList.setSwipeItemClickListener(new SwipeItemClickListener() {
            @Override
            public void onItemClick(View itemView, int position) {
                if( !ClickUtil.isFastClick()){
                    Intent chat = new Intent(getActivity(), Chat.class);
                    chat.putExtra(Constent.PERSON_NAME, swipeAdater.keyList.get(position));
                    EMConversation conversation = EMClient.getInstance().chatManager().getConversation(swipeAdater.keyList.get(position));
                    //指定会话消息未读数清零
                    conversation.markAllMessagesAsRead();
                    startActivity(chat);
                }


            }
        });
        /**
         * 消息的左滑按钮设置
         */
        swipeList.setSwipeMenuCreator(new SwipeMenuCreator() {
            @Override
            public void onCreateMenu(SwipeMenu leftMenu, SwipeMenu rightMenu, int viewType) {
                SwipeMenuItem item1 = new SwipeMenuItem(getActivity());
                item1.setBackground(R.color.grey);
                item1.setWidth(DensityUtils.dp2px(getActivity() ,90));
                item1.setHeight(MATCH_PARENT);
                item1.setText("标为未读");
                item1.setTextSize(18);
                item1.setTextColor(Color.WHITE);
                rightMenu.addMenuItem(item1);

                SwipeMenuItem deleteItem = new SwipeMenuItem(getActivity());
                deleteItem.setBackground(R.color.red);
                deleteItem.setWidth(DensityUtils.dp2px(getActivity() ,50));
                deleteItem.setHeight(MATCH_PARENT);

                deleteItem.setText("删除");
                deleteItem.setTextSize(18);

                deleteItem.setTextColor(Color.WHITE);
                rightMenu.addMenuItem(deleteItem);
            }
        });
        /**
         * 消息的左滑按钮点击监听
         */
        swipeList.setSwipeMenuItemClickListener(new SwipeMenuItemClickListener() {
            @Override
            public void onItemClick(SwipeMenuBridge menuBridge) {
                if(!ClickUtil.isFastClick()){
                    // 任何操作必须先关闭菜单，否则可能出现Item菜单打开状态错乱。
                    menuBridge.closeMenu();
                    int direction = menuBridge.getDirection(); // 左侧还是右侧菜单。
                    int adapterPosition = menuBridge.getAdapterPosition(); // RecyclerView的Item的position。
                    int menuPosition = menuBridge.getPosition(); // 菜单在RecyclerView的Item中的Position。
                    switch (menuPosition) {
                        case 0:
                            ToastUtil.showToast(getActivity(), "点击了标记未读");
                            break;
                        case 1:
                            ChatDBHelper.deleteConversation(swipeAdater.keyList.get(adapterPosition),true);
                            swipeAdater.removew(adapterPosition);
                            break;
                    }
                }

            }
        });
    }


    /**
     * 获取所有聊天
     */
    private void getAllCoversations() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                final Map<String, EMConversation> conversations = EMClient.getInstance().chatManager().getAllConversations();

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        List<String> keyList=new ArrayList<>();
                        int topNum=0;
                        for (Map.Entry<String, EMConversation> entry : conversations.entrySet()) {
                            if(ChatDBHelper.isTop(getActivity(),entry.getKey())){
                                keyList.add(0,entry.getKey());
                                topNum=topNum+1;
                            }else{
                                keyList.add(entry.getKey());
                            }

                        }
                        swipeAdater.getData(conversations,keyList,topNum);

                    }
                });
            }
        }).start();
    }


    @OnClick({R.id.title_bar_back_image, R.id.title_bar_back_text, R.id.title_bar_right_image})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_bar_back_image:
                getActivity().finish();
                break;
            case R.id.title_bar_back_text:
                getActivity().finish();
                break;
            case R.id.title_bar_right_image:
                if(!ClickUtil.isFastClick()){
                    startActivity(new Intent(getActivity(), CreateChatRoom.class));
                }
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }




}
