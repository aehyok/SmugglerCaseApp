package com.sinostar.assistant.ui.addressList.chat;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.allen.library.SuperTextView;
import com.sinostar.assistant.R;
import com.sinostar.assistant.ui.addressList.chat.helper.ChatDBHelper;
import com.sinostar.assistant.utils.CircleTransform;
import com.sinostar.assistant.utils.Constent;
import com.sinostar.assistant.utils.DensityUtils;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ChatInfo extends AppCompatActivity {

    @BindView(R.id.title_bar_text)
    TextView titleBarText;

    Context context;
    @BindView(R.id.chat_info_avatar)
    ImageView chatInfoAvatar;
    @BindView(R.id.chat_info_name)
    TextView chatInfoName;
    @BindView(R.id.chat_info_add_bt)
    ImageView chatInfoAddBt;
    @BindView(R.id.chat_room_be_quiet)
    SuperTextView chatRoomBeQuiet;
    @BindView(R.id.chat_room_set_top)
    SuperTextView chatRoomSetTop;
    private String personName;
    String[] text = {"聊天记录", "消息免打扰", "置顶聊天", "清空聊天记录"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_info);
        ButterKnife.bind(this);
        context = this;
        init();

    }


    private void init() {
        Intent intent = getIntent();
        personName = intent.getStringExtra(Constent.PERSON_NAME);
        titleBarText.setText("聊天详情");
        chatInfoName.setText(ChatDBHelper.getUserName(personName));
        if(ChatDBHelper.isTop(this,personName)){
            chatRoomSetTop.setRightTvDrawableRight(getResources().getDrawable(R.drawable.turn_on));
        }else{
            chatRoomSetTop.setRightTvDrawableRight(getResources().getDrawable(R.drawable.turn_off));
        }
        if(ChatDBHelper.isQueit(this,personName)){
            chatRoomBeQuiet.setRightTvDrawableRight(getResources().getDrawable(R.drawable.turn_on));
        }else{
            chatRoomBeQuiet.setRightTvDrawableRight(getResources().getDrawable(R.drawable.turn_off));
        }
        Picasso.get()
                .load(ChatDBHelper.getUserAvatar(personName))
                .resize(DensityUtils.dp2px(this, 70), DensityUtils.dp2px(this, 70))
                .transform(new CircleTransform(this))
                .into(chatInfoAvatar);

    }

    @OnClick({R.id.title_bar_back_image, R.id.title_bar_back_text, R.id.chat_room_record,
            R.id.chat_room_be_quiet, R.id.chat_room_set_top, R.id.chat_room_clear})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_bar_back_image:
                finish();
                break;
            case R.id.title_bar_back_text:
                finish();
                break;
            case R.id.chat_room_record:
                break;
            case R.id.chat_room_be_quiet:
                if (ChatDBHelper.isQueit(this, personName)) {
                    ChatDBHelper.setQuiet(this, personName, false);
                    chatRoomBeQuiet.setRightTvDrawableRight(getResources().getDrawable(R.drawable.turn_off));
                } else {
                    ChatDBHelper.setQuiet(this, personName, true);
                    chatRoomBeQuiet.setRightTvDrawableRight(getResources().getDrawable(R.drawable.turn_on));
                }
                break;
            case R.id.chat_room_set_top:
                if (ChatDBHelper.isTop(this, personName)) {
                    ChatDBHelper.setTop(this, personName, false);
                    chatRoomSetTop.setRightTvDrawableRight(getResources().getDrawable(R.drawable.turn_off));
                } else {
                    ChatDBHelper.setTop(this, personName, true);
                    chatRoomSetTop.setRightTvDrawableRight(getResources().getDrawable(R.drawable.turn_on));
                }
                break;
            case R.id.chat_room_clear:
                break;

        }
    }
}
