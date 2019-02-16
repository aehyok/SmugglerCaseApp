package com.sinostar.assistant.ui.addressList.message;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;
import com.sinostar.assistant.R;
import com.sinostar.assistant.ui.addressList.chat.helper.ChatDBHelper;
import com.sinostar.assistant.ui.addressList.chat.helper.ChatHelper;
import com.sinostar.assistant.utils.Constent;
import com.sinostar.assistant.utils.DateUtil;
import com.squareup.picasso.Picasso;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SwipeAdater extends RecyclerView.Adapter<SwipeAdater.ViewHolder> {
    private Context context;
    Map<String, EMConversation> mList = new LinkedHashMap<>();
    List<String> keyList=new ArrayList<>();
    private int topNum;

    SwipeAdater(Context context) {
        this.context = context;

    }

    public void removew(int position) {
        mList.remove(keyList.get(position));
        keyList.remove(position);
        notifyDataSetChanged();
    }

    public void getData(Map<String, EMConversation> list, List<String> keyList, int topNum) {
        mList.clear();
        mList = list;
        this.keyList.clear();
        this.keyList.addAll(keyList);
        this.topNum=topNum;
        notifyDataSetChanged();
    }

    @Override
    public SwipeAdater.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.chat_message_list, parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;

    }

    @Override
    public void onBindViewHolder(SwipeAdater.ViewHolder holder, int i) {
        if (mList != null && mList.size() != 0) {
            if(keyList!=null&&keyList.size()!=0){
                if(i<topNum){
                    holder.chatMessageLayout.setBackgroundColor(context.getResources().getColor(R.color.background));
                }else{
                    holder.chatMessageLayout.setBackgroundColor(context.getResources().getColor(R.color.white));
                }
                String personName=keyList.get(i);
                if ( mList.get(personName) != null && mList.get(personName).getAllMsgCount() != 0) {
                    EMConversation conversation = EMClient.getInstance().chatManager().getConversation(personName);

                    holder.chatMessageName.setText(personName);  //初始时显示用户环信名称
                    int messageCount = conversation.getUnreadMsgCount();  //获取特定用户的未读消息数量
                    if (messageCount > 0) {
                        holder.chatMessageNum.setVisibility(View.VISIBLE);
                        holder.chatMessageNum.setText(messageCount + "");
                    } else {
                        holder.chatMessageNum.setVisibility(View.GONE);
                    }
                    EMMessage message = mList.get(personName).getLastMessage();
                    if(message.getChatType()== EMMessage.ChatType.GroupChat){//判断是否是群聊
                        holder.chatMessageName.setText(ChatHelper.getGroupName(message.getTo()));
                        Picasso.with(context)
                                .load(Constent.CHAT_GROUP_AVATAR)
                                .into(holder.chatMessageAvatar);
                    }else{
                        holder.chatMessageName.setText(ChatDBHelper.getUserName(personName));
                        ChatHelper.setPersonAvatar(context,personName,holder.chatMessageAvatar);
                    }
                    holder.chatMessageContent.setText(ChatHelper.getChatMessageTypeText(message));
                    holder.chatMessageTime.setText(DateUtil.getDateToString(message.getMsgTime() / 1000, "HH:mm"));
                }
                }
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class ViewHolder extends SwipeMenuRecyclerView.ViewHolder {
        @BindView(R.id.chat_message_avatar)
        ImageView chatMessageAvatar;
        @BindView(R.id.chat_message_name)
        TextView chatMessageName;
        @BindView(R.id.chat_message_content)
        TextView chatMessageContent;
        @BindView(R.id.chat_message_time)
        TextView chatMessageTime;
        @BindView(R.id.chat_message_num)
        TextView chatMessageNum;
        @BindView(R.id.chat_message_layout)
        RelativeLayout chatMessageLayout;


        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
