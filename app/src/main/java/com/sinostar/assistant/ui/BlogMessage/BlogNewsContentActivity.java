package com.sinostar.assistant.ui.BlogMessage;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.just.agentweb.AgentWeb;
import com.just.agentweb.DefaultWebClient;
import com.sinostar.assistant.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BlogNewsContentActivity extends AppCompatActivity {


    @BindView(R.id.title_bar_text)
    TextView titleBarText;
    @BindView(R.id.news_webview)
    WebView webView;
    @BindView(R.id.progressbar)
    ProgressBar progressbar;

    protected AgentWeb mAgentWeb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_blog_news_content );
        ButterKnife.bind(this);
        titleBarText.setText( "News Details" );
        final Intent getIntent=getIntent();
        String newsId=getIntent().getStringExtra("Id");
        getWebviewData(newsId);
    }


    @SuppressLint("SetJavaScriptEnabled")
    private void getWebviewData(String newsId) {

        webView.setWebChromeClient(webChromeClient);
        webView.setWebViewClient(webViewClient);

        WebSettings webSettings = webView.getSettings();
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);//不使用缓存，只从网络获取数据.
        webSettings.setJavaScriptEnabled(true);//允许使用js
        //支持屏幕缩放
        webSettings.setSupportZoom(true);
        webSettings.setBuiltInZoomControls(true);


        //webView.loadUrl("http://39.104.26.38:8787/Blog/preview?id=" + newsId);
        String url="https://news.cnblogs.com/n/"+newsId+"/";
        webView.loadUrl( url );
    }


    //WebViewClient主要帮助WebView处理各种通知、请求事件
    private WebViewClient webViewClient = new WebViewClient() {
        @Override
        public void onPageFinished(WebView view, String url) {//页面加载完成
            progressbar.setVisibility(View.GONE);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {//页面开始加载
            progressbar.setVisibility(View.VISIBLE);
        }
    };

    //WebChromeClient主要辅助WebView处理Javascript的对话框、网站图标、网站title、加载进度等
    private WebChromeClient webChromeClient = new WebChromeClient() {
        //不支持js的alert弹窗，需要自己监听然后通过dialog弹窗
        @Override
        public boolean onJsAlert(WebView webView, String url, String message, JsResult result) {
            AlertDialog.Builder localBuilder = new AlertDialog.Builder( webView.getContext() );
            localBuilder.setMessage( message ).setPositiveButton( "确定", null );
            localBuilder.setCancelable( false );
            localBuilder.create().show();

            //注意:
            //必须要这一句代码:result.confirm()表示:
            //处理结果为确定状态同时唤醒WebCore线程
            //否则不能继续点击按钮
            result.confirm();
            return true;
        }
    };

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
