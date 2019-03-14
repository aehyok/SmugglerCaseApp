package com.sinostar.assistant.ui.addressList;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.sinostar.assistant.R;
import com.sinostar.assistant.base.BaseActivity;
import com.sinostar.assistant.ui.addressList.addressListInfo.AdresslListInfoFragment;
import com.sinostar.assistant.ui.addressList.callRecord.CallRecordFragment;
import com.sinostar.assistant.ui.addressList.message.MessageFragment;
import com.sinostar.assistant.base.ApplicationUtil;
import com.sinostar.assistant.utils.Constent;
import com.sinostar.assistant.utils.FragmentUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.sinostar.assistant.utils.BottomBarUtil.setImage;
import static com.sinostar.assistant.utils.BottomBarUtil.setTextColor;
import static com.sinostar.assistant.utils.FragmentUtil.addFragment;

public class AdressList extends BaseActivity {


    @BindView(R.id.bottom_bar_image1)
    ImageView bottomBarImage1;
    @BindView(R.id.bottom_bar_text1)
    TextView bottomBarText1;
    @BindView(R.id.bottom_bar_image2)
    ImageView bottomBarImage2;
    @BindView(R.id.bottom_bar_text2)
    TextView bottomBarText2;
    @BindView(R.id.bottom_bar_image3)
    ImageView bottomBarImage3;
    @BindView(R.id.bottom_bar_text3)
    TextView bottomBarText3;
    @BindView(R.id.bottom_bar_num1)
    TextView bottomBarNum1;
    @BindView(R.id.bottom_bar_num2)
    TextView bottomBarNum2;
    @BindView(R.id.bottom_bar_num3)
    TextView bottomBarNum3;




    private MessageFragment messageFragment;
    private AdresslListInfoFragment adresslListInfoFragment;
    private CallRecordFragment callRecordFragment;
    private String personName;

    private int image1[] = {R.drawable.thjl_h, R.drawable.txl_h, R.drawable.wxx_h};
    private int image2[] = {R.drawable.thjl_l, R.drawable.txl_l, R.drawable.wxx_1};
    private ImageView imageView[];
    private TextView textView[];
    private FragmentUtil fragmentUtil;
    private Fragment currentFragment;
    Context context;

    private EMMessageListener msgListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adress_list);
        ButterKnife.bind(this);
        bottomBarNum3=findViewById(R.id.bottom_bar_num3);
        personName = getIntent().getStringExtra(Constent.PERSON_NAME);
        context = AdressList.this;
        imageView = new ImageView[]{bottomBarImage1, bottomBarImage2, bottomBarImage3};
        textView = new TextView[]{bottomBarText1, bottomBarText2, bottomBarText3};
        messageFragment = new MessageFragment();
        init();
        callRecordFragment = new CallRecordFragment();
        adresslListInfoFragment = new AdresslListInfoFragment();
        addFragment(getSupportFragmentManager(), callRecordFragment, R.id.adresslsit_fragment_container);
        fragmentUtil = new FragmentUtil(this, callRecordFragment, R.id.adresslsit_fragment_container);
//        currentFragment = callRecordFragment;

        if (personName != null && !"".equals( personName )) {
            setTextColor(this, 2, textView);
            setImage(this, 2, image1, image2, imageView);
            Bundle bundle = new Bundle();
            bundle.putString(Constent.PERSON_NAME, personName);
            messageFragment.setArguments(bundle);
            fragmentUtil.switchFragment(messageFragment);
        } else {
            setTextColor(this, 0, textView);
            setImage(this, 0, image1, image2, imageView);
        }
        getUnReadMessage();
        ApplicationUtil.setOnMessageReceiveListener(new ApplicationUtil.OnMessageReceiveListener() {
            @Override
            public void getMessage(List<EMMessage> messages) {
                getUnReadMessage();
            }
        });
    }



    public void getUnReadMessage(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                final int unreadMsgCountTotal = EMClient.getInstance().chatManager().getUnreadMessageCount();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(unreadMsgCountTotal>0){
                            bottomBarNum3.setVisibility(View.VISIBLE);
                            if(unreadMsgCountTotal>99){
                                bottomBarNum3.setText("···");
                            }else{
                                bottomBarNum3.setText(unreadMsgCountTotal+"");
                            }
                        }else{
                            bottomBarNum3.setVisibility(View.GONE);
                        }
                    }
                });

            }
        }).start();

    }
    @Override
    protected void onResume() {
        super.onResume();
        getUnReadMessage();
    }

    private void init() {
        bottomBarText1.setText(R.string.adress_list_item1);
        bottomBarText2.setText(R.string.adress_list_item2);
        bottomBarText3.setText(R.string.adress_list_item3);
    }

    @OnClick({R.id.bottom_bar_first_layout, R.id.bottom_bar_second_layout, R.id.bottom_bar_third_layout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bottom_bar_first_layout:
                setTextColor(this, 0, textView);
                setImage(this, 0, image1, image2, imageView);
                fragmentUtil.switchFragment(callRecordFragment);
                break;
            case R.id.bottom_bar_second_layout:
                setTextColor(this, 1, textView);
                setImage(this, 1, image1, image2, imageView);

                fragmentUtil.switchFragment(adresslListInfoFragment);
                break;
            case R.id.bottom_bar_third_layout:
                setTextColor(this, 2, textView);
                setImage(this, 2, image1, image2, imageView);

                fragmentUtil.switchFragment(messageFragment);
                break;
        }
    }
}
