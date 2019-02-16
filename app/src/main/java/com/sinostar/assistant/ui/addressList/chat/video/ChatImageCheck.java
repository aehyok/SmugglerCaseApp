package com.sinostar.assistant.ui.addressList.chat.video;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.hyphenate.chat.EMMessage;
import com.qmuiteam.qmui.util.QMUIStatusBarHelper;
import com.sinostar.assistant.R;
import com.sinostar.assistant.base.ApplicationUtil;
import com.sinostar.assistant.utils.Constent;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * 视频照片浏览根页面
 */
public class ChatImageCheck extends AppCompatActivity {

    @BindView(R.id.chat_image_check_viewpager)
    ViewPager chatImageCheckViewpager;
    private Intent intent;
    private int currentPosition;
    private String personName;

    private List<EMMessage> message;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_image_check);
        ButterKnife.bind(this);
        QMUIStatusBarHelper.translucent(this);
        intent=getIntent();
        initData();
        setData();
    }



    private void initData() {
        message=intent.getParcelableArrayListExtra("message");
        currentPosition=intent.getIntExtra("currentPosition",message.size()-1);
        personName=intent.getStringExtra(Constent.PERSON_NAME);
    }
    private void setData(){
        List<android.support.v4.app.Fragment>list=new ArrayList<>();
        for(int i=0;i<message.size();i++){
            Bundle bundle=new Bundle();
            if(i==currentPosition){
                bundle.putBoolean("isStarVideo",true);
                ApplicationUtil.setIsFirstPlay(true);
            }
            bundle.putParcelable("message", message.get(i));
            bundle.putString(Constent.PERSON_NAME,personName);
            android.support.v4.app.Fragment fragment=VideoFragment.newInstance(bundle);
            list.add(fragment);
        }
        ChatImageAdapter chatImageAdapter=new ChatImageAdapter(getSupportFragmentManager(),list);
        chatImageCheckViewpager.setAdapter(chatImageAdapter);
        chatImageCheckViewpager.setCurrentItem(currentPosition);
    }

}
