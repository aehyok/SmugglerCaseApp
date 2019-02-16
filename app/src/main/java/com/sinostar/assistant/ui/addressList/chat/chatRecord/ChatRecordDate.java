package com.sinostar.assistant.ui.addressList.chat.chatRecord;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.sinostar.assistant.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 聊天记录按日期查找
 */

public class ChatRecordDate extends AppCompatActivity {

    @BindView(R.id.title_bar_text)
    TextView titleBarText;
    @BindView(R.id.chat_record_date_list)
    ListView chatRecordDateList;
    String [] text={"8月","9月","10月","11月"};
   java.util.List<String>list=new ArrayList<>();
    private Context context=this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_record_date);
        ButterKnife.bind(this);
        for(int i=1;i<=31;i++){
           list.add(i+"");
        }
        ChatRecordDateListAdapter adapter=new ChatRecordDateListAdapter(context,text,list);
        chatRecordDateList.setAdapter(adapter);
    }

    @OnClick({R.id.title_bar_back_image, R.id.title_bar_back_text})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_bar_back_image:
                finish();
                break;
            case R.id.title_bar_back_text:
                finish();
                break;
        }
    }
}
