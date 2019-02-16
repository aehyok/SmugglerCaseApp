package com.sinostar.assistant.ui.mobile.info;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sinostar.assistant.base.ApplicationUtil;
import com.sinostar.assistant.R;
import com.sinostar.assistant.utils.Constent;
import com.sinostar.assistant.utils.WaterMarkBgUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * PDF详情页
 */
public class DocumentInfo extends AppCompatActivity {

    @BindView(R.id.title_bar_text)
    TextView titleBarText;

    String docId;
    Context context;
    @BindView(R.id.document_fail_layout)
    RelativeLayout documentFailLayout;
    @BindView(R.id.document_layout)
    FrameLayout documentLayout;
    @BindView(R.id.progress_layout)
    RelativeLayout progressBar;
    @BindView(R.id.pdf_view)
    WebView webView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_document_info);
        ButterKnife.bind(this);
        documentFailLayout.setVisibility(View.GONE);
        context = DocumentInfo.this;
        Intent intent = getIntent();
        docId = intent.getStringExtra("docId");
        titleBarText.setText("文书查看");
        getData();
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void getData() {
        webView.setBackground(new WaterMarkBgUtil(ApplicationUtil.getWaterMarkText()));
        webView.setBackgroundColor(Color.argb(0, 0, 0, 0));
        //支持javascript
        webView.getSettings().setJavaScriptEnabled(true);
        // 设置可以支持缩放
        webView.getSettings().setSupportZoom(true);
        // 设置出现缩放工具
        webView.getSettings().setBuiltInZoomControls(true);
        //扩大比例的缩放
//        webView.getSettings().setUseWideViewPort(true);
        //自适应屏幕
        webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(Constent.PDF_URL+docId);


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
