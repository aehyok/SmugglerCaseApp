package com.sinostar.assistant.ui.addressList.chat;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMFileMessageBody;
import com.hyphenate.chat.EMImageMessageBody;
import com.hyphenate.chat.EMLocationMessageBody;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMTextMessageBody;
import com.hyphenate.chat.EMVideoMessageBody;
import com.hyphenate.chat.EMVoiceMessageBody;
import com.hyphenate.exceptions.HyphenateException;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction;
import com.sinostar.assistant.R;
import com.sinostar.assistant.ui.addressList.chat.helper.ChatDBHelper;
import com.sinostar.assistant.ui.addressList.chat.helper.ChatHelper;
import com.sinostar.assistant.ui.addressList.chat.video.ChatImageCheck;
import com.sinostar.assistant.ui.cassAssistant.announce.link.MapActivity;
import com.sinostar.assistant.utils.ACache;
import com.sinostar.assistant.utils.AppNetworkMgr;
import com.sinostar.assistant.utils.AppScreenMgr;
import com.sinostar.assistant.utils.Constent;
import com.sinostar.assistant.utils.DensityUtils;
import com.sinostar.assistant.utils.RoundTransform;
import com.sinostar.assistant.utils.DateUtil;
import com.sinostar.assistant.utils.FileUtil;
import com.sinostar.assistant.utils.OnMultiClickListener;
import com.sinostar.assistant.utils.TimeUtil;
import com.sinostar.assistant.utils.ToastUtil;
import com.sinostar.assistant.widget.LodingCircleView;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChatListAdapter extends BaseAdapter {

    private Activity context;
    public List<EMMessage> mList = new ArrayList<>();
    private String personName;
    private OnFromVoiceClick onFromVoiceClick;
    private OnToVoiceClick onToVoiceClick;
    private OnToErrorClick onToErrorClick;
    private Map<String, Boolean> loadingMap = new LinkedHashMap<>();
    private Map<String, Boolean> errorMap = new LinkedHashMap<>();
    private String errorMsgId;
    private List<EMMessage> messageList=new ArrayList<>();
    private Transformation transformation;
    private Map<String,Integer>imagePosition=new LinkedHashMap<>();
    private OnLongClick longClick;
    private EMMessage message;
    private ACache aCache;
    private boolean isLoading;

    public ChatListAdapter(Activity context) {
        this.context = context;
        aCache=ACache.get(context);
    }

    public void clear() {
        mList.clear();
    }

    public void remove(int position) {
        mList.remove(position);
        notifyDataSetChanged();
    }

    public void refresh(String msgId, String msgId1, boolean isSuccess) {
        this.isLoading=isSuccess;
        if (loadingMap.get(msgId) != null && loadingMap.get(msgId)) {
            loadingMap.remove(msgId);
            loadingMap.put(msgId1, isSuccess);
        } else {
            loadingMap.put(msgId, isSuccess);
        }

        notifyDataSetChanged();
    }

    public void showError(String msgId, String msgId1, boolean b) {
        this.errorMsgId = msgId1;
        if (errorMap.get(msgId) != null && errorMap.get(msgId)) {
            errorMap.remove(msgId);
            errorMap.put(msgId1, b);
        } else {
            errorMap.put(msgId, b);
        }
        aCache.put(msgId1,"error");
        if(!b){
            aCache.remove(msgId1);
        }
        notifyDataSetChanged();
    }

    public void getData(List<EMMessage> list, String toName) {
        if (list != null && list.size() != 0) {
            mList.addAll(list);
            this.personName = toName;
            messageList.clear();
            int j=0;
            for (int i = 0; i < mList.size(); i++) {
                if (mList.get(i).getType() == EMMessage.Type.IMAGE||mList.get(i).getType() == EMMessage.Type.VIDEO) {
                    messageList.add(mList.get(i));
                    imagePosition.put(i+"",j);
                    j=j+1;
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
    public int getItemViewType(int position) {
        int num;
        if(mList.get(position).getChatType() == EMMessage.ChatType.GroupChat){
            if(!mList.get(position).getFrom().equals(ChatDBHelper.getChatId())){
                num = 0;
            }else{
                num=1;
            }
        }else if(mList.get(position).getFrom().equals(personName)) {
            num = 0;
        } else {
            num = 1;
        }
        return num;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        int t1 = (int) (mList.get(i).getMsgTime() / 1000);
        Date date1 = new Date(mList.get(i).getMsgTime());
        Date date2;
        if (i == 0) {
            date2 = new Date();
        } else {
             date2 = new Date(mList.get(i-1).getMsgTime());

        }

        String time1=TimeUtil.DateformatTime(date1);
        String time2=TimeUtil.DateformatTime(date2);
        ChatHelper chatHelper=new ChatHelper(context);
        transformation=chatHelper.transformation;

        switch (getItemViewType(i)) {
            /**
             * 收到消息
             */
            case 0:
                final FromViewHolder fromViewHolder;
                if (view == null) {
                    view = LayoutInflater.from(context).inflate(R.layout.chat_from, null);
                    fromViewHolder = new FromViewHolder(view);
                    view.setTag(fromViewHolder);
                } else {
                    fromViewHolder = (FromViewHolder) view.getTag();
                }
                //更新头像
                if(mList.get(i).getChatType()== EMMessage.ChatType.GroupChat){ //判断是否群聊
                    ChatHelper.setPersonAvatar(context,mList.get(i).getFrom(),fromViewHolder.chatFromPersonImage);
                    fromViewHolder.chatFromPersonName.setText(ChatDBHelper.getUserName(mList.get(i).getFrom()));
                    fromViewHolder.chatFromMessageLayout.setPadding(DensityUtils.dp2px(context,3),
                            DensityUtils.dp2px(context,15),
                            0,
                            DensityUtils.dp2px(context,8));
                }else{
                    ChatHelper.setPersonAvatar(context, personName,fromViewHolder.chatFromPersonImage);
                }

                if (!time1.equals(time2)) {
                    fromViewHolder.chatTime.setVisibility(View.VISIBLE);

                    fromViewHolder.chatTime.setText(time1);
                } else {
                    fromViewHolder.chatTime.setVisibility(View.GONE);
                }

                fromViewHolder.chatFromTextLayout.setVisibility(View.GONE);
                fromViewHolder.chatFromVideoLayout.setVisibility(View.GONE);
                fromViewHolder.chatFromVoiceLayout.setVisibility(View.GONE);
                fromViewHolder.chatFromMapLayout.setVisibility(View.GONE);
                fromViewHolder.chatFromImage.setVisibility(View.GONE);
                fromViewHolder.chatFromFileLayout.setVisibility(View.GONE);
                fromViewHolder.chatFromLoadingImage.setVisibility(View.GONE);
                fromViewHolder.chatFromGroupTip.setVisibility(View.GONE);
                fromViewHolder.chatFromLayout.setVisibility(View.VISIBLE);

                fromViewHolder.chatFromText.setOnLongClickListener(new View.OnLongClickListener() {
                            @Override
                            public boolean onLongClick(View view) {
                                message= mList.get(i);
                                fromViewHolder.chatFromText.setSelectAllOnFocus(true);
                                longClick(view,1,message,i);
                                return true;
                    }
                });
                fromViewHolder.chatFromImage.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        message= mList.get(i);
                        longClick(view,2, message, i);
                        return true;
                    }
                });
                fromViewHolder.chatFromVideoImage.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        message= mList.get(i);
                        longClick(view,2, message, i);
                        return true;
                    }
                });
                fromViewHolder.chatFromMapLayout.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        message= mList.get(i);
                        longClick(view,2, message, i);
                        return true;
                    }
                });
                fromViewHolder.chatFromFileLayout.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        message= mList.get(i);
                        longClick(view,2, message, i);
                        return true;
                    }
                });
                fromViewHolder.chatFromVoiceImageLayout.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        message= mList.get(i);
                        longClick(view,3, message, i);
                        return true;
                    }
                });
                switch (mList.get(i).getType()) {
                    case TXT:  //收到文字信息
                        if(mList.get(i).getBooleanAttribute(Constent.IS_GROUP_MESSAGE_TIP,false)){
                            fromViewHolder.chatFromGroupTip.setVisibility(View.VISIBLE);
                            fromViewHolder.chatFromLayout.setVisibility(View.GONE);
                            EMTextMessageBody txtBody = (EMTextMessageBody) mList.get(i).getBody();
                            fromViewHolder.chatFromGroupTip.setText(txtBody.getMessage());
                        }else{
                            fromViewHolder.chatFromTextLayout.setVisibility(View.VISIBLE);
                            EMTextMessageBody txtBody = (EMTextMessageBody) mList.get(i).getBody();
                            fromViewHolder.chatFromText.setText(txtBody.getMessage());
                            fromViewHolder.chatFromText.setMaxWidth(AppScreenMgr.getScreenWidth(context)*5/8);
                        }

                        break;
                    case IMAGE:  //收到图片信息
                        fromViewHolder.chatFromImage.setVisibility(View.VISIBLE);
                        final EMImageMessageBody imageBody = (EMImageMessageBody) mList.get(i).getBody();

                        Picasso.with(context).
                                load(imageBody.getThumbnailUrl())
                                .transform(transformation)
                                .transform(new RoundTransform(context))
                                .into(fromViewHolder.chatFromImage);
                        fromViewHolder.chatFromImage.setOnClickListener(new OnMultiClickListener() {

                            @Override
                            public void onClick1(View view) {
                                if (FileUtil.fileIsExists(imageBody.getLocalUrl())) {
                                    starImageView(i);
                                } else {
                                    downloadAttachment(mList.get(i),i,fromViewHolder);
                                }
                            }
                        });
                        break;
                    case VOICE:  //收到语音消息
                        fromViewHolder.chatFromVoiceLayout.setVisibility(View.VISIBLE);
                        final EMVoiceMessageBody voiceBody = (EMVoiceMessageBody) mList.get(i).getBody();
                        int length = voiceBody.getLength();
                        int width = AppScreenMgr.getScreenWidth(context) * 2 / 5;
                        ViewGroup.LayoutParams params1 = fromViewHolder.chatFromVoiceImageLayout.getLayoutParams();
                        params1.width = width * length / 60 + 150;
                        if (length == 0) {
                            fromViewHolder.chatFromVoiceTime.setText("1''");
                        } else {
                            fromViewHolder.chatFromVoiceTime.setText(length + "''");
                        }
                        fromViewHolder.chatFromVoiceImage.setBackgroundResource(R.drawable.voice_v3);
                        fromViewHolder.chatFromVoiceTip.setVisibility(View.VISIBLE);
//                        判断语音是否已经播放
                        if (mList.get(i).getBooleanAttribute(Constent.isVoiceListened, false)) {
                            fromViewHolder.chatFromVoiceTip.setVisibility(View.GONE);
                        }

                        fromViewHolder.chatFromVoiceImageLayout.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if (FileUtil.fileIsExists(voiceBody.getLocalUrl())) {
                                    onFromVoiceClick.click(i, voiceBody.getLocalUrl(), voiceBody.getLength());
                                    mList.get(i).setAttribute(Constent.isVoiceListened, true);
                                } else {
                                    downloadAttachment(mList.get(i),i, fromViewHolder);
                                }

                            }
                        });
                        break;
                    case LOCATION:  //收到定位消息
                        fromViewHolder.chatFromMapLayout.setVisibility(View.VISIBLE);
                        EMLocationMessageBody locationBody = (EMLocationMessageBody) mList.get(i).getBody();
                        Picasso.with(context)
                                .load(chatHelper.getLocationUrl(i, (EMLocationMessageBody) mList.get(i).getBody()))
                                .into(fromViewHolder.chatFromMapimage);
                        try {
                            String address = mList.get(i).getStringAttribute(Constent.MAP_ADDRESS);
                            fromViewHolder.chatFromMapText2.setText(address);
                        } catch (HyphenateException e) {
                            e.printStackTrace();
                        }
                        fromViewHolder.chatFromMapText1.setText(locationBody.getAddress());
                        fromViewHolder.chatFromMapLayout.setOnClickListener(new OnMultiClickListener() {

                            @Override
                            public void onClick1(View view) {
                                locationClickListener(i);
                            }
                        });
                        break;
                    case VIDEO:  //收到视频消息
                        fromViewHolder.chatFromVideoLayout.setVisibility(View.VISIBLE);
                        final EMVideoMessageBody videoBody = (EMVideoMessageBody) mList.get(i).getBody();

                            Picasso.with(context).
                                    load(videoBody.getThumbnailUrl())
                                    .transform(transformation)
                                    .transform(new RoundTransform(context))
                                    .into(fromViewHolder.chatFromVideoImage);


                        fromViewHolder.chatFromVideoTime.setText(DateUtil.timeParse(videoBody.getDuration()));

                        fromViewHolder.chatFromVideoImage.setOnClickListener(new OnMultiClickListener() {

                            @Override
                            public void onClick1(View view) {
                                if (FileUtil.fileIsExists(videoBody.getLocalUrl())) {
                                    starImageView(i);
                                } else {
                                    downloadAttachment(mList.get(i),i, fromViewHolder);
                                }
                            }
                        });
                        break;
                    case FILE:  //收到文件消息
                        fromViewHolder.chatFromFileLayout.setVisibility(View.VISIBLE);
                        final EMFileMessageBody fileBody = (EMFileMessageBody) mList.get(i).getBody();
                        fromViewHolder.chatFromFileText1.setText(fileBody.getFileName());
                        try {
                           fromViewHolder.chatFromFileText2.setText(mList.get(i).getStringAttribute(Constent.FILE_LENGTH));
                        } catch (HyphenateException e) {
                            e.printStackTrace();
                        }
                        String type = FileUtil.getFileType(fileBody.getLocalUrl());
                        if (fileBody.downloadStatus() != EMFileMessageBody.EMDownloadStatus.SUCCESSED) {
                            fromViewHolder.chatFromFileState.setText("未下载");
                        } else {
                            fromViewHolder.chatFromFileState.setText("已下载");
                        }
                        if (chatHelper.getFileType(type) != 0) {
                            Picasso.with(context)
                                    .load(chatHelper.getFileType(type))
                                    .into(fromViewHolder.chatFromFileimage);
                        } else {
                            Picasso.with(context)
                                    .load(R.drawable.file_blank)
                                    .into(fromViewHolder.chatFromFileimage);
                        }
                        fromViewHolder.chatFromFileLayout.setOnClickListener(new OnMultiClickListener() {

                            @Override
                            public void onClick1(View view) {
                                if (FileUtil.fileIsExists(fileBody.getLocalUrl())) {

                                } else {
                                    downloadAttachment(mList.get(i),i, fromViewHolder);
                                }
                            }
                        });

                        break;
                }
                break;
            /**
             * 发送消息
             */
            case 1:

                final ToViewHolder toViewHolder;
                if (view == null) { 
                    view = LayoutInflater.from(context).inflate(R.layout.chat_to, null);
                    toViewHolder = new ToViewHolder(view);
                    view.setTag(toViewHolder);
                } else {
                    toViewHolder = (ToViewHolder) view.getTag();
                }
                //更新头像
               ChatHelper.setPersonAvatar(context,ChatDBHelper.getChatId(),toViewHolder.chatToPersonImage);
                if(mList.get(i).getChatType()== EMMessage.ChatType.GroupChat){
                    toViewHolder.chatToPersonName.setText(ChatDBHelper.getUserName(ChatDBHelper.getChatId()));
                    toViewHolder.chatToMessageLayout.setPadding(0,
                            DensityUtils.dp2px(context,15),
                            DensityUtils.dp2px(context,3),
                            DensityUtils.dp2px(context,8));
                }
                if (loadingMap != null && loadingMap.size() != 0 && loadingMap.get(mList.get(i).getMsgId()) != null && loadingMap.get(mList.get(i).getMsgId())) {
                    toViewHolder.chatToStateImage.setVisibility(View.VISIBLE);
                    toViewHolder.chatToStateImage.setBackgroundResource(R.drawable.chat_to_state_animation);
                    AnimationDrawable animation = (AnimationDrawable) toViewHolder.chatToStateImage.getBackground();
                    animation.start();
                } else {
                    toViewHolder.chatToStateImage.setVisibility(View.GONE);
                }

                if (!isLoading&&errorMsgId != null && mList.get(i).getMsgId().equals(errorMsgId)) {
                    toViewHolder.chatToStateImage.setVisibility(View.GONE);
                    toViewHolder.chatToErrorImage.setVisibility(View.VISIBLE);
                }
                if(!isLoading&&aCache!=null&&aCache.getAsString(mList.get(i).getMsgId())!=null){
                    toViewHolder.chatToStateImage.setVisibility(View.GONE);
                    toViewHolder.chatToErrorImage.setVisibility(View.VISIBLE);
                }else{
                    toViewHolder.chatToErrorImage.setVisibility(View.GONE);
                }

                //时间显示
                if (!time1.equals(time2)) {
                    toViewHolder.chatTime.setVisibility(View.VISIBLE);
                    toViewHolder.chatTime.setText(time1);
                } else {
                    toViewHolder.chatTime.setVisibility(View.GONE);
                }
                //发送失败图片点击监听
                toViewHolder.chatToErrorImage.setOnClickListener(new OnMultiClickListener() {
                    @Override
                    public void onClick1(View view) {
                        QMUIDialog dialog;
                        final QMUIDialog.MessageDialogBuilder builder=new QMUIDialog.MessageDialogBuilder(context);
                        builder.setMessage("发送失败，是否重发消息");
                        builder.addAction("取消", new QMUIDialogAction.ActionListener() {
                            @Override
                            public void onClick(QMUIDialog dialog, int index) {
                                dialog.dismiss();
                            }
                        });
                        builder.addAction("发送", new QMUIDialogAction.ActionListener() {
                            @Override
                            public void onClick(QMUIDialog dialog, int index) {
                                if(AppNetworkMgr.isNetworkConnected(context)){//判断是否有网络连接
                                    onToErrorClick.click(i);
                                    toViewHolder.chatToErrorImage.setClickable(false);
                                }else{
                                    ToastUtil.showToast(context,"当前网络连接不可用，请稍后再试");
                                }

                                dialog.dismiss();
                            }
                        });
                        builder.setCanceledOnTouchOutside(true);
                        dialog = builder.create();
                        dialog.show();
                    }
                });
                toViewHolder.chatToTextLayout.setVisibility(View.GONE);
                toViewHolder.chatToVideoLayout.setVisibility(View.GONE);
                toViewHolder.chatToVoiceLayout.setVisibility(View.GONE);
                toViewHolder.chatToMapLayout.setVisibility(View.GONE);
                toViewHolder.chatToImage.setVisibility(View.GONE);
                toViewHolder.chatToVoiceTime.setVisibility(View.GONE);
                toViewHolder.chatToFileLayout.setVisibility(View.GONE);
                toViewHolder.chatToGroupTip.setVisibility(View.GONE);
                toViewHolder.chatToLayout.setVisibility(View.VISIBLE);

                toViewHolder.chatToText.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        message=mList.get(i);
                        toViewHolder.chatToText.setSelected(true);
                        longClick(view,1, message, i);
                        return true;
                    }
                });
                toViewHolder.chatToImage.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        message=mList.get(i);
                        longClick(view,2, message, i);
                        return true;
                    }
                });
                toViewHolder.chatToVideoImage.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        message=mList.get(i);
                        longClick(view,2, message, i);
                        return true;
                    }
                });
                toViewHolder.chatToVoiceImageLayout.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        message=mList.get(i);
                        longClick(view,3, message, i);
                        return true;
                    }
                });
                toViewHolder.chatToMapLayout.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        message=mList.get(i);
                        longClick(view,2, message, i);
                        return true;
                    }
                });
                toViewHolder.chatToFileLayout.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        message=mList.get(i);
                        longClick(view,2, message, i);
                        return true;
                    }
                });

                switch (mList.get(i).getType()) {
                    case TXT:  //发出文字消息
                        if(mList.get(i).getBooleanAttribute(Constent.IS_GROUP_MESSAGE_TIP,false)){
                            toViewHolder.chatToGroupTip.setVisibility(View.VISIBLE);
                            toViewHolder.chatToLayout.setVisibility(View.GONE);
                            EMTextMessageBody txtBody = (EMTextMessageBody) mList.get(i).getBody();
                            toViewHolder.chatToGroupTip.setText(txtBody.getMessage());
                        }else{
                            toViewHolder.chatToTextLayout.setVisibility(View.VISIBLE);
                            EMTextMessageBody txtBody = (EMTextMessageBody) mList.get(i).getBody();
                            toViewHolder.chatToText.setText(txtBody.getMessage());
                            toViewHolder.chatToText.setMaxWidth(AppScreenMgr.getScreenWidth(context)*5/8);
                        }
                        break;
                    case IMAGE:  //发出图片消息
                        toViewHolder.chatToImage.setVisibility(View.VISIBLE);
                        final EMImageMessageBody imageBody = (EMImageMessageBody) mList.get(i).getBody();
                        Picasso.with(context).load("file://" + imageBody.getLocalUrl())
                                .transform(transformation)
                                .transform(new RoundTransform(context))
                                .into(toViewHolder.chatToImage);
                        toViewHolder.chatToImage.setOnClickListener(new OnMultiClickListener() {
                            @Override
                            public void onClick1(View view) {
                                starImageView(i);
                            }
                        });
                        break;
                    case VOICE:  //发出语音消息
                        toViewHolder.chatToVoiceLayout.setVisibility(View.VISIBLE);
                        toViewHolder.chatToVoiceTime.setVisibility(View.VISIBLE);
                        final EMVoiceMessageBody voiceBody = (EMVoiceMessageBody) mList.get(i).getBody();
                        int length = voiceBody.getLength();
                        int width = AppScreenMgr.getScreenWidth(context) * 2 / 5;
                        ViewGroup.LayoutParams params1 = toViewHolder.chatToVoiceImageLayout.getLayoutParams();
                        params1.width = width * length / 60 + 150;
                        if (length == 0) {
                            toViewHolder.chatToVoiceTime.setText("1''");
                        } else {
                            toViewHolder.chatToVoiceTime.setText(length + "''");
                        }
                        toViewHolder.chatToVoiceImage.setBackgroundResource(R.drawable.voice_to_v3);
                        toViewHolder.chatToVoiceImageLayout.setOnClickListener(new OnMultiClickListener() {
                            @Override
                            public void onClick1(View view) {
                                onToVoiceClick.click(i, voiceBody.getLocalUrl());
                            }
                        });
                        break;
                    case LOCATION:  //发出定位消息
                        toViewHolder.chatToMapLayout.setVisibility(View.VISIBLE);
                        EMLocationMessageBody locationBody = (EMLocationMessageBody) mList.get(i).getBody();
                        Picasso.with(context)
                                .load(chatHelper.getLocationUrl(i, (EMLocationMessageBody) mList.get(i).getBody()))
                                .into(toViewHolder.chatToMapImage);

                        try {
                            String address = mList.get(i).getStringAttribute(Constent.MAP_ADDRESS);
                            toViewHolder.chatToMapText2.setText(address);
                        } catch (HyphenateException e) {
                            e.printStackTrace();
                        }
                        toViewHolder.chatToMapText1.setText(locationBody.getAddress());
                        toViewHolder.chatToMapLayout.setOnClickListener(new OnMultiClickListener() {
                            @Override
                            public void onClick1(View view) {
                                locationClickListener(i);
                            }
                        });
                        break;
                    case VIDEO:  //发出视频消息
                        toViewHolder.chatToVideoLayout.setVisibility(View.VISIBLE);
                        final EMVideoMessageBody videoBody = (EMVideoMessageBody) mList.get(i).getBody();
                        if(videoBody.getThumbnailUrl()!=null&&!videoBody.getThumbnailUrl().equals("")){
                           Picasso.with(context)
                                   .load(videoBody.getThumbnailUrl())
                                   .transform(transformation)
                                   .transform(new RoundTransform(context))
                                   .into(toViewHolder.chatToVideoImage);
                        }else{
                            Picasso.with(context)
                                    .load(videoBody.getLocalThumb())
                                    .transform(transformation)
                                    .transform(new RoundTransform(context))
                                    .into(toViewHolder.chatToVideoImage);
                        }

                        toViewHolder.chatToVideoTime.setText(DateUtil.timeParse(videoBody.getDuration()));
                        toViewHolder.chatToVideoImage.setOnClickListener(new OnMultiClickListener() {
                            @Override
                            public void onClick1(View view) {
                                starImageView(i);
                            }
                        });
                        break;
                    case FILE:  //发出文件消息
                        toViewHolder.chatToFileLayout.setVisibility(View.VISIBLE);
                        final EMFileMessageBody fileBody = (EMFileMessageBody) mList.get(i).getBody();
                        toViewHolder.chatToFileText1.setText(fileBody.getFileName());
                        try {
                            toViewHolder.chatToFileText2.setText(mList.get(i).getStringAttribute(Constent.FILE_LENGTH));
                        } catch (HyphenateException e) {
                            e.printStackTrace();
                        }
                        if (fileBody.downloadStatus() != EMFileMessageBody.EMDownloadStatus.SUCCESSED) {
                            toViewHolder.chatToFileState.setText("未下载");
                        } else {
                            toViewHolder.chatToFileState.setText("已下载");
                        }
                        String type = fileBody.getFileName().substring(fileBody.getFileName().lastIndexOf(".") + 1);
                        if (chatHelper.getFileType(type) != 0) {
                            Picasso.with(context)
                                    .load(chatHelper.getFileType(type))
                                    .into(toViewHolder.chatToFileImage);
                        } else {
                            Picasso.with(context)
                                    .load(R.drawable.file_blank)
                                    .into(toViewHolder.chatToFileImage);
                        }
                        toViewHolder.chatToFileLayout.setOnClickListener(new OnMultiClickListener() {
                            @Override
                            public void onClick1(View view) {

                            }
                        });


                        break;
                }
                break;
        }

        return view;
    }


    /**
     * 下载附件
     *
     * @param message
     * @param fromViewHolder
     */
    private void downloadAttachment(final EMMessage message, final int position, final FromViewHolder fromViewHolder) {
        message.setMessageStatusCallback(new EMCallBack() {
            @Override
            public void onSuccess() {

                switch (message.getType()){
                    case IMAGE:
                        starImageView(position);
                        break;
                    case VOICE:
                        EMVoiceMessageBody voiceBody = (EMVoiceMessageBody) message.getBody();
                        onFromVoiceClick.click(position, voiceBody.getLocalUrl(), voiceBody.getLength());
                        mList.get(position).setAttribute(Constent.isVoiceListened, true);
                        break;
                    case VIDEO:
                        starImageView(position);
                        break;
                    case FILE:
                        fromViewHolder.chatFromFileState.setText("已下载");
                        break;
                }
            }

            @Override
            public void onProgress(final int progress, String status) {
                context.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (message.getType()== EMMessage.Type.FILE){
                            if(progress==100){
                                fromViewHolder.chatFromFileProgressBar.setVisibility(View.GONE);
                            }else{
                                fromViewHolder.chatFromFileProgressBar.setVisibility(View.VISIBLE);
                                fromViewHolder.chatFromFileProgressBar.setProgress(progress);
                            }
                        }else{
                            if (progress==100){
                                fromViewHolder.chatFromLoadingImage.setVisibility(View.GONE);
                            }else{
                                fromViewHolder.chatFromLoadingImage.setVisibility(View.VISIBLE);
                                fromViewHolder.chatFromLoadingImage.setProgerss(progress,true);
                            }
                        }
                    }
                });
            }

            @Override
            public void onError(int error, String msg) {

            }
        });
        EMClient.getInstance().chatManager().downloadAttachment(message);
    }



    /**
     * 照片浏览或者视频播放
     *
     * @param i
     */
    private void starImageView(int i) {
        Intent intent = new Intent(context, ChatImageCheck.class);
        intent.putExtra("currentPosition",imagePosition.get(i+""));
        intent.putParcelableArrayListExtra("message", (ArrayList<? extends Parcelable>) messageList);
        intent.putExtra(Constent.PERSON_NAME, personName);
        context.startActivity(intent);
    }

    /**
     * 定位对话框的动作监听
     *
     * @param i
     */
    private void locationClickListener(int i) {
        Intent intent = new Intent(context, MapActivity.class);
        List<EMMessage> list = new ArrayList<>();
        list.add(mList.get(i));
        intent.putParcelableArrayListExtra("locationData", (ArrayList<? extends Parcelable>) list);
        try {
            intent.putExtra(Constent.MAP_ADDRESS, mList.get(i).getStringAttribute(Constent.MAP_ADDRESS));
        } catch (HyphenateException e) {
            e.printStackTrace();
        }
        context.startActivity(intent);
    }


    private void longClick(View view, int type, EMMessage message, int i){
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        float OldListY = (float) location[1];
        float OldListX = (float) location[0];
        longClick.longClick(OldListX,OldListY,view.getWidth(),view.getHeight(),type, message,i);
    }



    interface OnFromVoiceClick {
        void click(int position, String path, int length);
    }

    public void setOnFromVoiceClickListener(OnFromVoiceClick onVoiceClickListener) {
        this.onFromVoiceClick = onVoiceClickListener;
    }

    interface OnToVoiceClick {
        void click(int position, String path);
    }

    public void setOnToVoiceClickListener(OnToVoiceClick onToVoiceClick) {
        this.onToVoiceClick = onToVoiceClick;
    }

    interface OnToErrorClick {

        void click(int position);
    }

    public void setOnToErrorClickListener(OnToErrorClick onToErrorClick) {
        this.onToErrorClick = onToErrorClick;

    }
    interface OnLongClick {

        void longClick(float x, float y, int width, int height, int type, EMMessage message, int i);
    }

    public void setOnLongClickListener(OnLongClick onLongClick) {
        this.longClick = onLongClick;

    }

    public class FromViewHolder {
        @BindView(R.id.chat_from_person_image)
        ImageView chatFromPersonImage;
        @BindView(R.id.chat_from_text)
        TextView chatFromText;
        @BindView(R.id.chat_from_group_tip)
        TextView chatFromGroupTip;
        @BindView(R.id.chat_from_person_name)
        TextView chatFromPersonName;
        @BindView(R.id.chat_from_image)
        ImageView chatFromImage;
        @BindView(R.id.chat_from_voice_image)
        ImageView chatFromVoiceImage;
        @BindView(R.id.chat_from_voice_layout)
        RelativeLayout chatFromVoiceLayout;
        @BindView(R.id.chat_from_voice_tip)
        TextView chatFromVoiceTip;
        @BindView(R.id.chat_from_voice_time)
        TextView chatFromVoiceTime;
        @BindView(R.id.chat_from_voice_image_layout)
        RelativeLayout chatFromVoiceImageLayout;
        @BindView(R.id.chat_time)
        TextView chatTime;
        @BindView(R.id.chat_from_message_layout)
        RelativeLayout chatFromMessageLayout;
        @BindView(R.id.chat_from_map_text1)
        TextView chatFromMapText1;
        @BindView(R.id.chat_from_map_text2)
        TextView chatFromMapText2;
        @BindView(R.id.chat_from_map_image)
        ImageView chatFromMapimage;
        @BindView(R.id.chat_from_map_layout)
        RelativeLayout chatFromMapLayout;
        @BindView(R.id.chat_from_text_layout)
        RelativeLayout chatFromTextLayout;
        @BindView(R.id.chat_from_video_image)
        ImageView chatFromVideoImage;
        @BindView(R.id.chat_from_video_time)
        TextView chatFromVideoTime;
        @BindView(R.id.chat_from_video_layout)
        RelativeLayout chatFromVideoLayout;
        @BindView(R.id.chat_from_file_text1)
        TextView chatFromFileText1;
        @BindView(R.id.chat_from_file_text2)
        TextView chatFromFileText2;
        @BindView(R.id.chat_from_file_image)
        ImageView chatFromFileimage;
        @BindView(R.id.chat_from_file_layout)
        RelativeLayout chatFromFileLayout;
        @BindView(R.id.chat_from_file_state)
        TextView chatFromFileState;
        @BindView(R.id.chat_from_loading_image)
        LodingCircleView chatFromLoadingImage;
        @BindView(R.id.chat_from_file_progressBar)
        ProgressBar chatFromFileProgressBar;

        @BindView(R.id.chat_from_layout)
        RelativeLayout chatFromLayout;


        FromViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    class ToViewHolder {
        @BindView(R.id.chat_to_person_image)
        ImageView chatToPersonImage;
        @BindView(R.id.chat_to_text)
        TextView chatToText;
        @BindView(R.id.chat_to_person_name)
        TextView chatToPersonName;
        @BindView(R.id.chat_to_image)
        ImageView chatToImage;
        @BindView(R.id.chat_to_voice_image)
        ImageView chatToVoiceImage;
        @BindView(R.id.chat_to_voice_layout)
        RelativeLayout chatToVoiceLayout;
        @BindView(R.id.chat_to_voice_time)
        TextView chatToVoiceTime;
        @BindView(R.id.chat_to_voice_image_layout)
        RelativeLayout chatToVoiceImageLayout;
        @BindView(R.id.chat_time)
        TextView chatTime;
        @BindView(R.id.chat_to_message_layout)
        RelativeLayout chatToMessageLayout;
        @BindView(R.id.chat_to_map_text1)
        TextView chatToMapText1;
        @BindView(R.id.chat_to_map_text2)
        TextView chatToMapText2;
        @BindView(R.id.chat_to_map_layout)
        RelativeLayout chatToMapLayout;
        @BindView(R.id.chat_to_map_image)
        ImageView chatToMapImage;
        @BindView(R.id.chat_to_text_layout)
        RelativeLayout chatToTextLayout;
        @BindView(R.id.chat_to_video_image)
        ImageView chatToVideoImage;
        @BindView(R.id.chat_to_video_time)
        TextView chatToVideoTime;
        @BindView(R.id.chat_to_video_layout)
        RelativeLayout chatToVideoLayout;
        @BindView(R.id.chat_to_state_image)
        ImageView chatToStateImage;
        @BindView(R.id.chat_to_error_image)
        ImageView chatToErrorImage;
        @BindView(R.id.chat_to_file_text1)
        TextView chatToFileText1;
        @BindView(R.id.chat_to_file_text2)
        TextView chatToFileText2;
        @BindView(R.id.chat_to_file_state)
        TextView chatToFileState;
        @BindView(R.id.chat_to_file_layout)
        RelativeLayout chatToFileLayout;
        @BindView(R.id.chat_to_file_image)
        ImageView chatToFileImage;
        @BindView(R.id.chat_to_group_tip)
        TextView chatToGroupTip;
        @BindView(R.id.chat_to_layout)
        RelativeLayout chatToLayout;

        ToViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }


}
