package com.sinostar.assistant.ui.addressList.chatRoom.chatRoomInfo;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMGroup;
import com.sinostar.assistant.R;
import com.sinostar.assistant.ui.addressList.chat.helper.ChatDBHelper;
import com.sinostar.assistant.utils.AppScreenMgr;
import com.sinostar.assistant.utils.Constent;
import com.sinostar.assistant.utils.DensityUtils;
import com.squareup.picasso.Picasso;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import java.io.ByteArrayOutputStream;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ChatRoomCodeActivity extends AppCompatActivity {

    @BindView(R.id.title_bar_text)
    TextView titleBarText;
    @BindView(R.id.chat_room_code_avatar)
    ImageView chatRoomCodeAvatar;
    @BindView(R.id.chat_room_code_name)
    TextView chatRoomCodeName;
    @BindView(R.id.chat_room_code_image)
    ImageView chatRoomCodeImage;
    @BindView(R.id.title_bar_right_image)
    ImageView titleBarRightImage;
    private String personName;
    private EMGroup group;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room_code);
        ButterKnife.bind(this);
        personName=getIntent().getStringExtra(Constent.PERSON_NAME);
        //根据群组ID从本地获取群组基本信息
         group = EMClient.getInstance().groupManager().getGroup(personName);

////根据群组ID从服务器获取群组基本信息
//        EMGroup group = EMClient.getInstance().groupManager().getGroupFromServer(groupId);
        initView();
    }

    private void initView() {
        titleBarText.setText(R.string.chat_room_code_title);
        int width= AppScreenMgr.getScreenWidth(this)- DensityUtils.dp2px(this,160);
        ViewGroup.LayoutParams params=chatRoomCodeImage.getLayoutParams();
        params.width=width;
        params.height=width;
        chatRoomCodeImage.setLayoutParams(params);
        chatRoomCodeImage.setImageBitmap(getCode("sdfsdfsdf"));
        titleBarRightImage.setBackgroundResource(R.drawable.more);
        Picasso.get().load(ChatDBHelper.getUserAvatar(personName)).into(chatRoomCodeAvatar);
        chatRoomCodeName.setText(group.getGroupName());
    }
    private Bitmap getCode(String groupId){
        return CodeUtils.createImage(groupId, 400, 400, null);
    }
    public static String convertIconToString(Bitmap bitmap)
    {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();// outputstream
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] appicon = baos.toByteArray();// 转为byte数组
        return Base64.encodeToString(appicon, Base64.DEFAULT);

    }



    @OnClick({R.id.title_bar_back_layout, R.id.title_bar_right_image})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_bar_back_layout:
                finish();
                break;
            case R.id.title_bar_right_image:
                break;
        }
    }
}
