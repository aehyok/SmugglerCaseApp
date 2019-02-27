package com.sinostar.assistant.ui.BlogMessage;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.sinostar.assistant.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BlogNewsContentActivity extends AppCompatActivity {

    @BindView(R.id.title_bar_text)
    TextView titleBarText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_blog_news_content );
        ButterKnife.bind(this);
        titleBarText.setText( "News Details" );
    }
}
