package com.sinostar.assistant.ui.addressList.chat.chatRecord;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.sinostar.assistant.R;
import com.sinostar.assistant.utils.OnMultiItemClickListener;
import com.sinostar.assistant.widget.ClearableEditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * 聊天记录主页面
 */
public class ChatRecord extends AppCompatActivity {

    @BindView(R.id.chat_record_cancle)
    TextView chatRecordCancle;
    @BindView(R.id.chat_record_search)
    ClearableEditText chatRecordSearch;
    @BindView(R.id.chat_record_list)
    ListView chatRecordList;
    private int image[]={R.drawable.chat_record_date,R.drawable.chat_record_image,R.drawable.chat_record_url,R.drawable.chat_record_file};
    private String text[]={"日期","图片及视频","链接","文件"};
    private Context context=this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_record1);
//        QMUIStatusBarHelper.translucent(this);
        ButterKnife.bind(this);
        chatRecordSearch.setmClearDrawable(R.drawable.clear);
        ChatRecordMenuListAdapter adapter=new ChatRecordMenuListAdapter(context,image,text);
        chatRecordList.setAdapter(adapter);
        chatRecordList.setOnItemClickListener(new OnMultiItemClickListener() {
            @Override
            public void onItemClick1(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i){
                    case 0: //日期
                        startActivity(new Intent(context,ChatRecordDate.class));
                        break;
                    case 1:  //图片及视频
                        break;
                    case 2:  //链接
                        break;
                    case 3:  //文件
                        break;
                }
            }
        });

    }

    @OnClick(R.id.chat_record_cancle)
    public void onViewClicked() {
        finish();
    }
}
