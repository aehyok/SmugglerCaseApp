package com.sinostar.assistant.ui.cassAssistant.announce.link;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.sinostar.assistant.R;
import com.sinostar.assistant.utils.Constent;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 呈请报告模版
 */
public class ReportDemo extends AppCompatActivity {

    @BindView(R.id.title_bar_text)
    TextView titleBarText;
    private String docId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_demo);
        ButterKnife.bind(this);
        WebView urlWebView = findViewById(R.id.report_webview);
        urlWebView.getSettings().setJavaScriptEnabled(true);//让浏览器支持javascript
        urlWebView.setWebViewClient(new AppWebViewClients());
        urlWebView.loadUrl(Constent.PDF_URL+docId);
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


    public class AppWebViewClients extends WebViewClient {

        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            // TODO Auto-generated method stub
            view.loadUrl(url);
            return true;
        }

        public void onPageFinished(WebView view, String url) {
            // TODO Auto-generated method stub
//            super.onPageFinished(view, url);
        }

    }


}
