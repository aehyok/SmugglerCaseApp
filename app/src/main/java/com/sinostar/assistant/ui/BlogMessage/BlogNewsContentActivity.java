package com.sinostar.assistant.ui.BlogMessage;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.sinostar.assistant.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BlogNewsContentActivity extends AppCompatActivity {

    @BindView(R.id.title_bar_text)
    TextView titleBarText;

    @BindView(R.id.webView)
    WebView webSitView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_blog_news_content );
        ButterKnife.bind(this);
        titleBarText.setText( "News Details" );

        String url="https://www.cnblogs.com";




        MyWebViewClient my=new MyWebViewClient();
        my.shouldOverrideUrlLoading( webSitView,url );
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

    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (url.startsWith( "http:" ) || url.startsWith( "https:" )) {
                view.loadUrl( url );
                return false;
            } else {
                Intent intent = new Intent( Intent.ACTION_VIEW, Uri.parse( url ) );
                startActivity( intent );
                return true;
            }
        }
    }
}
