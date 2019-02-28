package com.sinostar.assistant.ui.BlogMessage;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.sinostar.assistant.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BlogRemindActivity extends AppCompatActivity {

    @BindView( R.id.title_bar_text )
    TextView titleBarText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_blog_remind );
        ButterKnife.bind( this );
        titleBarText.setText( "cnblogs.com" );
    }

    //返回按键
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
