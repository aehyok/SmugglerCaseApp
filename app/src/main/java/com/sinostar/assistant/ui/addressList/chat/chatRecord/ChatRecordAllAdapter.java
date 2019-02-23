package com.sinostar.assistant.ui.addressList.chat.chatRecord;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hyphenate.chat.EMFileMessageBody;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMTextMessageBody;
import com.hyphenate.exceptions.HyphenateException;
import com.sinostar.assistant.R;
import com.sinostar.assistant.utils.Constent;
import com.sinostar.assistant.utils.DateUtil;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChatRecordAllAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<EMMessage> mList = new ArrayList<>();
    private String toName = "";
    private List<EMMessage> messageList = new ArrayList<>();

    ChatRecordAllAdapter(Context context) {
        this.context = context;

    }

    public void getData(List<EMMessage> list, String toName) {
        if (list != null && list.size() != 0) {
            Collections.reverse(list);   //倒序输出
            mList.addAll(list);
            this.toName = toName;
            messageList.clear();
            for (int i = 0; i < mList.size(); i++) {
                if (mList.get(i).getType() == EMMessage.Type.IMAGE || mList.get(i).getType() == EMMessage.Type.VIDEO) {
                    messageList.add(mList.get(i));
                }
            }
            notifyDataSetChanged();
        }
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.chat_message_record_list, null);
            holder=new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder=(ViewHolder) view.getTag();
        }
        int t1 = (int) (mList.get(i).getMsgTime() / 1000);
        int t2;
        if (i == 0) {
            t2 = 0;
        } else {
            t2 = (int) (mList.get(i - 1).getMsgTime() / 1000);
        }
        String time1 = DateUtil.getDateToString(t1, "yyyy年MM月dd日");
        String time2 = DateUtil.getDateToString(t2, "yyyy年MM月dd日");
        String time3 = DateUtil.getDateToString(t1, "HH:mm:ss");
        if (!time1.equals(time2)) {
            holder.chatMessageRecordTime.setVisibility(View.VISIBLE);
            holder.chatMessageRecordTime.setText(time1);
        } else {
            holder.chatMessageRecordTime.setVisibility(View.GONE);
        }
        Picasso.get()
                .load(R.drawable.ydsp)
                .into(holder.chatMessageRecordImage);
        if(mList.get(i).getFrom().equals(toName)){
            //收到消息
            holder.chatMessageRecordName.setText(mList.get(i).getFrom()+"\t\t"+time3);
            holder.chatMessageRecordName.setTextColor(context.getResources().getColor(R.color.black));
        }else{
            //发出消息
            holder.chatMessageRecordName.setText(mList.get(i).getFrom()+"\t\t"+time3);
            holder.chatMessageRecordName.setTextColor(context.getResources().getColor(R.color.blue));
        }
        holder.chatMessageRecordText.setVisibility(View.GONE);
        holder.chatMessageRecordFileImage.setVisibility(View.GONE);
        holder.chatMessageRecordImage.setVisibility(View.GONE);
        switch (mList.get(i).getType()){
            case TXT:
                holder.chatMessageRecordText.setVisibility(View.VISIBLE);
                EMTextMessageBody textBody= (EMTextMessageBody)mList.get(i).getBody();
                holder.chatMessageRecordText.setText(textBody.getMessage());
                break;
            case IMAGE:
                break;
            case FILE:
                holder.chatMessageRecordFileImage.setVisibility(View.VISIBLE);
                holder.chatMessageRecordText.setVisibility(View.VISIBLE);
                EMFileMessageBody fileBody= (EMFileMessageBody)mList.get(i).getBody();
                try {
                    holder.chatMessageRecordText.setText(fileBody.getFileName()
                            +"\t(" +
                            mList.get(i).getStringAttribute(Constent.FILE_LENGTH)+
                            ")");
                } catch (HyphenateException e) {
                    e.printStackTrace();
                }
                break;
        }
        return view;
    }

    static class ViewHolder {
        @BindView(R.id.chat_message_record_time)
        TextView chatMessageRecordTime;
        @BindView(R.id.chat_message_record_avatar)
        ImageView chatMessageRecordAvatar;
        @BindView(R.id.chat_message_record_name)
        TextView chatMessageRecordName;
        @BindView(R.id.chat_message_name_layout)
        RelativeLayout chatMessageNameLayout;
        @BindView(R.id.chat_message_record_file_image)
        ImageView chatMessageRecordFileImage;
        @BindView(R.id.chat_message_record_text)
        TextView chatMessageRecordText;
        @BindView(R.id.chat_message_record_image)
        ImageView chatMessageRecordImage;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
