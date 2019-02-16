package com.sinostar.assistant.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.method.LinkMovementMethod;
import android.text.method.PasswordTransformationMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.qmuiteam.qmui.util.QMUIStatusBarHelper;
import com.sinostar.assistant.R;
import com.sinostar.assistant.base.ApplicationUtil;
import com.sinostar.assistant.base.BaseActivity;
import com.sinostar.assistant.base.OnEditFocusChange;
import com.sinostar.assistant.bean.Login;
import com.sinostar.assistant.net.NetMethods;
import com.sinostar.assistant.subscribers.MyObserver;
import com.sinostar.assistant.subscribers.ObserverOnNextListener;
import com.sinostar.assistant.utils.AppKeyBoardMgr;
import com.sinostar.assistant.utils.ClickUtil;
import com.sinostar.assistant.utils.Constent;
import com.sinostar.assistant.utils.LogUtil;
import com.sinostar.assistant.utils.ToastUtil;
import com.squareup.picasso.Picasso;

import java.util.LinkedHashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.sinostar.assistant.utils.AppScreenMgr.getScreenHeight;
import static com.sinostar.assistant.utils.AppScreenMgr.getScreenWidth;

/**
 * 登陆页面
 */
public class LoginActivity extends BaseActivity {
    String userName;
    String userPassport;
    @BindView(R.id.login_uesr_name)
    EditText loginUesrName;
    @BindView(R.id.login_uesr_passport)
    EditText loginUesrPassport;
    Context context;
    Map<String, String> loginMap = new LinkedHashMap<>();
    Gson gson;
    @BindView(R.id.login_layout)
    RelativeLayout loginLayout;
    @BindView(R.id.other_layout)
    RelativeLayout otherLayout;

    @BindView(R.id.login_title_image)
    ImageView loginTitleImage;
    @BindView(R.id.login_chebox)
    CheckBox loginChebox;
    @BindView(R.id.login_chebox_text)
    TextView loginCheboxText;
    @BindView(R.id.login_empty_button)
    Button emptyButton;

    private String cls;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        QMUIStatusBarHelper.translucent(this);
        Intent intent = getIntent();
        cls = intent.getStringExtra("cls");
        gson = new Gson();
        context = LoginActivity.this;
        init();
        //测试用
        loginUesrName.setText("zhaofudi");
        loginUesrPassport.setText("zhaofudi");
        loginUesrPassport.setTransformationMethod(PasswordTransformationMethod.getInstance());
        initListener();
    }

    private void initListener() {
        loginUesrPassport.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                AppKeyBoardMgr.hideInputMethod((AppCompatActivity) context);
                login();
                return false;
            }
        });

        setOnEdittFoucusChange(new OnEditFocusChange() {
            @Override
            public void isEditextFoucus() {
                emptyButton.requestFocusFromTouch();
            }
        });

    }

    private void init() {
        int height = (int) (getScreenHeight(context) / 2.5);
        SpannableStringBuilder s;

        if(Constent.isSZ){
            s= new SpannableStringBuilder(getResources().getString(R.string.login_chebox_sz));
            Picasso.with(context).load(R.drawable.login_image_sz).resize(getScreenWidth(context), height).centerCrop().into(loginTitleImage);
        }else{
            s= new SpannableStringBuilder(getResources().getString(R.string.login_chebox));
            Picasso.with(context).load(R.drawable.login_image).resize(getScreenWidth(context), height).centerCrop().into(loginTitleImage);
        }

        s.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.login_button_text)),
                5, 13, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        loginCheboxText.setMovementMethod(LinkMovementMethod.getInstance());
        s.setSpan(new TextClick(), 5, 13
                , Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        loginCheboxText.setText(s);

    }

    @OnClick({R.id.login_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.login_back:
                finish();
                break;
        }
    }


    private class TextClick extends ClickableSpan {

        @Override
        public void onClick(View widget) {
            //在此处理点击事件
//            ToastUtil.showToast(context, "点击了");
        }
    }


    @OnClick(R.id.login_button)
    public void onViewClicked() {
        if (!ClickUtil.isFastClick()) {
            login();
        }
    }

    private void login() {
        userName = loginUesrName.getText().toString();
        userPassport = loginUesrPassport.getText().toString();
        loginMap.put("LoginName", userName);
        loginMap.put("LoginPassword", userPassport);

        if (userName.equals("")) {
            ToastUtil.showToast(context, "账号不能为空");
        } else if (userPassport.equals("")) {
            ToastUtil.showToast(context, "密码不能为空");
        } else if (!loginChebox.isChecked()) {
            ToastUtil.showToast(context, "请阅读并同意拱北办案助手协议");
        } else {
            ObserverOnNextListener listener = new ObserverOnNextListener<Login>() {
                @Override
                public void onNext(final Login result) {
                    LogUtil.d("登陆结果", gson.toJson(result));
                    if (result.isSuccess()) {
                        loginLayout.setVisibility(View.GONE);
                        otherLayout.setVisibility(View.VISIBLE);
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                ApplicationUtil.setUserId(result.getData().getUserId() + "");
                                ApplicationUtil.setWaterMarkText(result.getData().getUserName());
                                Intent intent = null;
                                try {
                                    intent = new Intent(context, Class.forName(cls));
                                } catch (ClassNotFoundException e) {
                                    e.printStackTrace();
                                }
                                startActivity(intent);
                                finish();
                            }
                        }, 1000);

                    } else {
                        ToastUtil.showToast(context, "登陆失败");
                    }
                }

                @Override
                public void onError(Throwable e) {
                    ToastUtil.showToast(context, "登陆失败");
                    LogUtil.d("登录的结果", e.toString());
                }
            };
            NetMethods.getLogin(new MyObserver<Login>(context, listener), loginMap);
        }
    }

}
