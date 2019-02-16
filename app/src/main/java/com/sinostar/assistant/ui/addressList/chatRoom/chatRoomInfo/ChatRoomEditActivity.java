package com.sinostar.assistant.ui.addressList.chatRoom.chatRoomInfo;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.sinostar.assistant.R;
import com.sinostar.assistant.base.BaseActivity;
import com.sinostar.assistant.base.OnEditFocusChange;
import com.sinostar.assistant.utils.Constent;
import com.sinostar.assistant.utils.ToastUtil;
import com.sinostar.assistant.widget.ClearableEditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ChatRoomEditActivity extends BaseActivity {

    @BindView(R.id.title_bar_back_image)
    ImageButton titleBarBackImage;
    @BindView(R.id.title_bar_text)
    TextView titleBarText;
    @BindView(R.id.title_bar_rightText)
    TextView titleBarRightText;
    @BindView(R.id.chat_room_edit_title)
    TextView chatRoomEditTitle;
    @BindView(R.id.chat_room_edit_content)
    ClearableEditText chatRoomEditContent;

    private int type;
    private boolean isGroupName = true;
    private String groupId;
    private String contentText;
    private Context context=this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room_edit);
        ButterKnife.bind(this);
        groupId = getIntent().getStringExtra(Constent.PERSON_NAME);
        initView();
        BaseActivity.setOnEdittFoucusChange(new OnEditFocusChange() {
            @Override
            public void isEditextFoucus() {
                titleBarBackImage.requestFocusFromTouch();
            }
        });
    }

    private void initView() {
        type = getIntent().getIntExtra(Constent.CHAT_ROOM_ET_TYPE, 1);
        contentText=getIntent().getStringExtra(Constent.CHAT_ROOM_ET_TEXT);
        chatRoomEditContent.setText(contentText);
        if (type != 1) {//群公告
            isGroupName = false;
            chatRoomEditTitle.setText("群公告");
            titleBarText.setText("群公告");
        } else {//群昵称
            isGroupName = true;
            chatRoomEditTitle.setText("群名称");
            titleBarText.setText("群名称");
        }
        titleBarRightText.setText("完成");
        titleBarRightText.setTextColor(Color.GREEN);
    }

    private void onChangeSuccess(){
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                ToastUtil.toast("修改成功");
                finish();
            }
        });
    }
    private EMCallBack emCallBack=new EMCallBack() {
        @Override
        public void onSuccess() {
            onChangeSuccess();
        }

        @Override
        public void onError(int i, final String s) {
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    ToastUtil.showToast(context,s);
                    finish();
                }
            });
        }

        @Override
        public void onProgress(int i, String s) {

        }
    };

    @OnClick({R.id.title_bar_back_layout, R.id.title_bar_rightText})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_bar_back_layout:
                finish();
                break;
            case R.id.title_bar_rightText:
                if (chatRoomEditContent.getText().toString().equals(contentText)) {
                    onChangeSuccess();

                }else{
                    if(isGroupName){
                        EMClient.getInstance().groupManager().asyncChangeGroupName(groupId,
                                chatRoomEditContent.getText().toString(), emCallBack);
                    }else{
                        EMClient.getInstance().groupManager().asyncUpdateGroupAnnouncement(groupId,
                                chatRoomEditContent.getText().toString(), emCallBack );
                    }
                }

                break;
        }
    }
}
